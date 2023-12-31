package controller;

import view.*;

import model.*;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {


        //Crea il giocatore
        var giocatore = new Giocatore(400, 200, 100, 2, 40,40);

        ApplicationManager.movimento = giocatore.movimento;

        //Crea la view
        var f = new Finestra(800, 600);
        f.getGamePanel().addPersonaggio(giocatore);

        //Creo TileM e lo inietto dentro al GP
        var tileM = new TileManager(f.getGamePanel());
        f.getGamePanel().setTileM(tileM);

        ApplicationManager.movimento.setTileM(tileM);






        var time = new Time();
        time.addObserver(f.getGamePanel());


        giocatore.movimento.addObserver(f.getGamePanel());


        //LOOP DEL GAME
        while (true) {
            try {


                //Implementare qui i movimenti degli enimici

                time.notifyCurrentTime();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}