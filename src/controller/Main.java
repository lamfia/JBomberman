package controller;

import view.*;

import model.*;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        //Variabili di avvio
        Boolean showHitboxes = true;
        AudioManager.getInstance().enable = true;
        //f.getGamePanel().playMusic(0); //Background music

        //Crea il giocatore
        var giocatore = new Giocatore(380, 200, 100, 2, 40, 40);

        ApplicationManager.movimento = giocatore.movimento;

        ApplicationManager.attaco = giocatore.attaco; //Press spacebar to attack!

        //Crea la view
        var f = new Finestra(800, 600);
        //Add del giocatore
        f.getGamePanel().addGiocatore(giocatore);





        //Creazione di partita
        Partita partita = new Partita(Maps.TheSevenSeas);

        //Creo TileM e lo inietto dentro al GP
        var tileM = new TileManager(f.getGamePanel(), giocatore, partita);
        tileM.showHitboxes = showHitboxes;
        f.getGamePanel().setTileM(tileM);

        ApplicationManager.movimento.setTileM(tileM);


        //Add degli enimici //TODO questa config da mettere dentro alla mappa , partita
        var enemico1= new Enemico(100,100,100,2,40,40);
        f.getGamePanel().addEnemico(enemico1);

        var time = new Time();

        //binding tra observer ed observable
        time.addObserver(f.getGamePanel());
        giocatore.movimento.addObserver(f.getGamePanel());
        giocatore.attaco.addObserver(f.getGamePanel());

        partita.addObserver(f.getGamePanel());


        //LOOP DEL GAME
        while (true) {
            try {


                //Implementare qui i movimenti degli enimici

                time.notifyCurrentTime();

                f.getGamePanel().repaintTask();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}