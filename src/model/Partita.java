package model;

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


    public Partita(Maps selectedMap) throws IOException {
        //Quando si crea la partita nuova con il primo mappa
        //si imposta in stato playing
        //this.statoPartita = StatoPartita.Playing;

        map = new Map(selectedMap);
    }

    public void setStatoPartita( StatoPartita statoPartita){
        this.statoPartita=statoPartita;
    }

    public void notifica() {
        setChanged();
        notifyObservers(this);

    }


    /**
     * In caso di cambio dello stato della
     * partita si notifica alla view
     * per fare gli eventuali cambi
     * @param statoPartita
     */
    public void changeStatoPartita(StatoPartita statoPartita) {

        this.statoPartita = statoPartita;
        notifica();

    }


}
