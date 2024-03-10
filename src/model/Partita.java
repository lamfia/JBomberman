package model;

import controller.ApplicationManager;
import controller.GestioneUtente;
import controller.TileManager;

import java.io.IOException;
import java.util.Observable;

/**
 * Classe che ha gli attributi dello stato della partita,
 * playing, win o gameOver.
 * punteggio attuale, vite rimanenti? TODO
 * Timer
 * si crea quando viene fatto new game
 * contiene anche la mappa selezionata
 * Fa la comunicazione diretta con la view tramite il pattern OO
 */
public class Partita extends Observable {

    public StatoPartita statoPartita;

    public Map map;

    public int points;

    public Giocatore giocatore;

    public boolean OpenPortal = false;

    private Maps lastMapPlayed;

    public Utente utente;

    public boolean isTitleState() {

        if (statoPartita == StatoPartita.Title || statoPartita == StatoPartita.GameOver || statoPartita == StatoPartita.Win) {
            return true;
        }

        return false;
    }

    public boolean isGameRunning() {

        if (statoPartita == StatoPartita.Playing) {
            return true;
        }

        return false;
    }


    public Partita() {
        //Crea il giocatore
        //this.giocatore = new Giocatore(380, 200, 1, 2, 40, 40);
        this.giocatore = new Giocatore(0, 0, 3, 4, 40, 40);
        ApplicationManager.movimento = giocatore.movimento;
        ApplicationManager.attaco = giocatore.attaco; //Press spacebar to attack!
    }

    public void newGame(Maps selectedMap) throws IOException {
        OpenPortal = false;
        lastMapPlayed = selectedMap;
        map = new Map(selectedMap);

        this.giocatore.movimento.posizione.pos_x = map.getXInizialeGiocatore();
        this.giocatore.movimento.posizione.pos_y = map.getYInizialeGiocatore();

        this.giocatore.movimento.posizione.pos_x_iniziale = map.getXInizialeGiocatore();
        this.giocatore.movimento.posizione.pos_y_iniziale = map.getYInizialeGiocatore();

        this.giocatore.movimento.posizione.AggiornaHitbox();

        // TP del giocatore in posizione iniziale
//        giocatore.movimento.posizione.pos_x = giocatore.movimento.posizione.pos_x_iniziale;
//        giocatore.movimento.posizione.pos_y = giocatore.movimento.posizione.pos_y_iniziale;

    }

    public void continueGame() {

        resetGame();
        this.statoPartita = StatoPartita.Playing;
        this.giocatore.reimpostaViteIniziali();
    }

    public void resetGame() {


        points = 0;

        //reset del personaggio abilities
        try {
            newGame(lastMapPlayed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Ricreare tutti i tiles, powerups, enemici, personnagio con nuove vite, points a 0, timer al massimo


    }


    public void setStatoPartita(StatoPartita statoPartita) {
        this.statoPartita = statoPartita;
    }

    public void notifica() {
        setChanged();
        notifyObservers(this);

    }


    /**
     * In caso di cambio dello stato della
     * partita si notifica alla view
     * per fare gli eventuali cambi
     *
     * @param statoPartita
     */
    public void changeStatoPartita(StatoPartita statoPartita) {

        this.statoPartita = statoPartita;

        if (this.statoPartita == StatoPartita.GameOver) {
            StopGame();
            SaveGame(StatoPartita.GameOver);
        }

        if (this.statoPartita == StatoPartita.Win) {
            SaveGame(StatoPartita.Win);
        }

        notifica();

    }


    public void StopGame() {
        //TODO
    }


    public void stopGame() {
        resetGame();
        //reset e stop del game TODO
    }


    /**
     * Save game in caso di win
     */
    private void SaveGame(StatoPartita statoPartita) {
        var GestioneUtente = new GestioneUtente();

        var listaUtenti = GestioneUtente.getUtenti();

        var utenteTrovatoOptional = listaUtenti.stream().filter(utente -> utente.Nickname.equals(this.utente.Nickname)).findFirst();

        Utente utenteTrovato = null;
        if (utenteTrovatoOptional.isPresent()) {
            //get dell'utente esistente
            utenteTrovato = utenteTrovatoOptional.get();
        } else {
            //crea nuovo utente
            this.utente.partiteGiocate=this.utente.partiteGiocate+1;
            listaUtenti.add(this.utente);
            GestioneUtente.salvaUtenti(listaUtenti);
            utenteTrovato = this.utente;
        }


        //Aggiorna i dati
        if (statoPartita == StatoPartita.Win) {
            utenteTrovato.partiteVinte = utenteTrovato.partiteVinte + 1;
        }

        if (statoPartita == StatoPartita.GameOver) {
            utenteTrovato.partitePerse = utenteTrovato.partitePerse + 1;
        }

        utenteTrovato.puntiOttenuti = points;

        utenteTrovato.lastLevelArrived = this.lastMapPlayed.ordinal() + 1;

        // Trova l'indice dell'utente nella lista
        int IndexSpecifico = listaUtenti.indexOf(utenteTrovato);

        listaUtenti.set(IndexSpecifico, utenteTrovato);

        // Verifica se l'indice è valido prima di aggiornare la lista
        if (IndexSpecifico != -1) {
            listaUtenti.set(IndexSpecifico, utenteTrovato);
            GestioneUtente.salvaUtenti(listaUtenti);
        } else {
            System.out.println("Errore: Impossibile trovare l'indice dell'utente nella lista.");
        }


    }

    public void OpenPortal() {
        OpenPortal = true;
        this.map.apriPorta();
        notifica();
    }

    public void LoadGame(int idUtente) {

        GestioneUtente gestioneUtente = new GestioneUtente();
        this.utente = gestioneUtente.getUtenti().get(idUtente);

        this.points = utente.puntiOttenuti;

    }
}
