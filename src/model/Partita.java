package model;

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

    Map map;

    public Partita(Maps selectedMap){
        //Quando si crea la partita nuova con il primo mappa
        //si imposta in stato playing
        this.statoPartita=StatoPartita.Playing;

        map= new Map(selectedMap);
    }

    public void  notifica(){
        setChanged();
        notifyObservers(this);

    }


    public void changeStatoPartita(StatoPartita statoPartita){

        this.statoPartita=statoPartita;
        notifica();

    }


}
