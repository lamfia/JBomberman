package model;

import java.util.Observable;


/**
 * Classe che notifica all'observer che il personnaggio ha fatto un attaco
 */
public class Attaco extends Observable {




    public Attaco(){

    }

    public void Attacare(){

        notifica();

    }

    private void notifica() {

        setChanged();

        notifyObservers("Bomb!");


    }

}
