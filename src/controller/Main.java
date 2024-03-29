package controller;

import view.*;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        //Variabili di avvio
        Boolean showHitboxes = false;
        AudioManager.getInstance().enable = true  ;

        //Creazione di partita
        Partita partita = new Partita();

        //Set del tile state
        partita.changeStatoPartita(StatoPartita.Title);
        partita.newGame(Maps.TheSevenSeas);

        //Crea la view
        var keyHandler = new KeyHandler(partita);
        var f = new Finestra(800, 600, partita, keyHandler);

        keyHandler.addObserver(f.getGamePanel());

        //Add del giocatore
        f.getGamePanel().addGiocatore(partita.giocatore);

        var tileM = new TileManager(f.getGamePanel(), partita.giocatore, partita);
        tileM.showHitboxes = showHitboxes;
        f.getGamePanel().setTileM(tileM);
        ApplicationManager.movimento.setTileM(tileM);

        //binding tra observer ed observable
        partita.giocatore.movimento.addObserver(f.getGamePanel());
        partita.giocatore.attaco.addObserver(f.getGamePanel());
        partita.addObserver(f.getGamePanel());

        //LOOP DEL GAME
        while (true) {
            try {

                f.getGamePanel().repaintTask();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}