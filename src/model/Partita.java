package model;

import controller.ApplicationManager;
import controller.GestioneUtente;

import java.io.IOException;
import java.util.Observable;


/**
 * Questa classe gestisce e contiene i dati relativi a una partita.
 * Estende la classe Observable per supportare il pattern Observer e
 * comunicare agli obsevers.
 *
 * @author Gabriel Guerra
 * @version 1.0
 */
public class Partita extends Observable {

    /**
     * Lo stato attuale della partita.
     */
    public StatoPartita statoPartita;

    /**
     * La mappa corrente della partita.
     */
    public Map map;

    /**
     * Il punteggio attuale della partita.
     */
    public int points;

    /**
     * Il giocatore associato alla partita.
     */
    public Giocatore giocatore;

    /**
     * Indica se il portale è aperto o chiuso.
     */
    public boolean OpenPortal = false;

    /**
     * L'ultima mappa giocata.
     */
    public Maps lastMapPlayed;

    /**
     * L'utente associato alla partita.
     */
    public Utente utente;

    /**
     * Verifica se la partita si trova nello stato di schermata del titolo, Game Over o vittoria.
     *
     * @return true se la partita è in uno degli stati sopra menzionati, altrimenti false.
     */
    public boolean isTitleState() {

        if (statoPartita == StatoPartita.Title || statoPartita == StatoPartita.GameOver || statoPartita == StatoPartita.Win) {
            return true;
        }

        return false;
    }

    /**
     * Verifica se la partita è nello stato di gioco.
     *
     * @return true se la partita è in uno degli stati di gioco, altrimenti false.
     */
    public boolean isPlayingState() {

        if (statoPartita == StatoPartita.Playing || statoPartita == StatoPartita.Playing_StageSelect) {
            return true;
        }

        return false;
    }

    /**
     * Costruttore predefinito che inizializza la posizione iniziale del giocatore e altri parametri.
     */
    public Partita() {
        //Crea il giocatore
        //this.giocatore = new Giocatore(380, 200, 1, 2, 40, 40);
        this.giocatore = new Giocatore(0, 0, 1, 4, 40, 40);
        ApplicationManager.movimento = giocatore.movimento;
        ApplicationManager.attaco = giocatore.attaco; //Press spacebar to attack!
    }

    /**
     * Avvia una nuova partita sulla mappa selezionata.
     *
     * @param selectedMap La mappa selezionata per la nuova partita.
     * @throws IOException Eccezione lanciata in caso di errori di input/output.
     */
    public void newGame(Maps selectedMap) throws IOException {
        points = 0;
        OpenPortal = false;
        lastMapPlayed = selectedMap;
        map = new Map(selectedMap);
        giocatore.reimpostaViteIniziali();
        this.giocatore.movimento.posizione.pos_x = map.getXInizialeGiocatore();
        this.giocatore.movimento.posizione.pos_y = map.getYInizialeGiocatore();

        this.giocatore.movimento.posizione.pos_x_iniziale = map.getXInizialeGiocatore();
        this.giocatore.movimento.posizione.pos_y_iniziale = map.getYInizialeGiocatore();

        this.giocatore.movimento.posizione.AggiornaHitbox();

    }

    /**
     * Riprende una partita in corso. che è stata previamente in GameOver
     */
    public void continueGame() {

        resetGame();
        this.statoPartita = StatoPartita.Playing;
        this.giocatore.reimpostaViteIniziali();
    }

    /**
     * Resettare la partita, ripristinando il punteggio e tutti parametri.
     */
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

    /**
     * Imposta lo stato della partita con il nuovo stato specificato.
     *
     * @param statoPartita Il nuovo stato della partita da impostare.
     */
    public void setStatoPartita(StatoPartita statoPartita) {
        this.statoPartita = statoPartita;
    }

    /**
     * Notifica gli osservatori della modifica dello stato della partita.
     * Questo metodo è chiamato dopo ogni cambiamento significativo nella partita.
     * Gli osservatori vengono informati dell'aggiornamento tramite il pattern Observer.
     */
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

        //Se sta giocando Arcade
        if (this.statoPartita == StatoPartita.Playing) {
            if (statoPartita == StatoPartita.Win) {
                SaveGame(StatoPartita.Win);
            }
            if (statoPartita == StatoPartita.GameOver) {
                SaveGame(StatoPartita.GameOver);
                StopGame();
            }
        }

        //Aggiorno l'attributo
        this.statoPartita = statoPartita;

        notifica();

    }

    /**
     * Ferma la partita.
     */
    public void StopGame() {
    }

    /**
     * Salva i dati della partita in caso di vittoria o sconfitta.
     *
     * @param statoPartita Lo stato della partita (Win o GameOver).
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
            this.utente.partiteGiocate = this.utente.partiteGiocate + 1;
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

    /**
     * Apre il portale nella mappa corrente.
     */
    public void OpenPortal() {
        OpenPortal = true;
        this.map.apriPorta();
        notifica();
    }

    /**
     * Carica una partita esistente associata all'utente specificato.
     *
     * @param idUtente L'ID dell'utente associato alla partita da caricare.
     */
    public void LoadGame(int idUtente) {

        GestioneUtente gestioneUtente = new GestioneUtente();
        this.utente = gestioneUtente.getUtenti().get(idUtente);

        this.points = utente.puntiOttenuti;

    }


    /**
     * Aggiorna gli sprites dell'avatar selezionato
     * nell'attributo utente.avatar
     */
    public void cambiaSpritesGiocatore() {
        this.giocatore.setSpritesAvatar(this.utente.avatar);
    }
}

