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
        this.giocatore = new Giocatore(0, 0, 3, 2, 40, 40);
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

        //TODO
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

    public void SaveGame() {
        //Save del Game TODO
    }

    public void OpenPortal() {
        OpenPortal = true;
        this.map.apriPorta();
        notifica();
    }

    public void LoadGame(int idUtente) {

        GestioneUtente gestioneUtente = new GestioneUtente();
        this.utente = gestioneUtente.getUtenti().get(idUtente);

        this.points=utente.puntiOttenuti;

    }
}
