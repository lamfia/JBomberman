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
        AudioManager.getInstance().enable = true;

        GestioneUtente gestioneUtente = new GestioneUtente();

        ArrayList<Utente> listaUtenti = new ArrayList<>();
        var utente = new Utente();
        utente.Nickname = "Lamfia";
        utente.avatar = Avatar.BombermanTheKid;
        utente.lastLevelArrived = 1;
        utente.partiteGiocate = 2;
        utente.partiteVinte = 3;
        utente.partitePerse = 6;
        listaUtenti.add(utente);

        var utente2 = new Utente();
        utente2.Nickname = "ProLamfia";
        utente2.avatar = Avatar.PrettyBomberman;
        utente2.lastLevelArrived = 2;
        utente2.partiteGiocate = 4;
        utente2.partiteVinte = 7;
        utente2.partitePerse = 10;
        listaUtenti.add(utente2);

        var Lamfia = gestioneUtente.getUtente("Lamfia");


        //Creazione di partita
        Partita partita = new Partita();

        //Set del tile state
        partita.setStatoPartita(StatoPartita.Title);
        partita.newGame(Maps.TheSevenSeas); //TODO attenzione al tile M quando si commenta questa, creare il tile M con la partita (?)


        var keyHandler = new KeyHandler(partita);
        //Crea la view
        var f = new Finestra(800, 600, partita, keyHandler);


        //Add del giocatore
        f.getGamePanel().addGiocatore(partita.giocatore);

        var tileM = new TileManager(f.getGamePanel(), partita.giocatore, partita);
        tileM.showHitboxes = showHitboxes;
        f.getGamePanel().setTileM(tileM);

        ApplicationManager.movimento.setTileM(tileM);

        var time = new Time();

        //binding tra observer ed observable
        time.addObserver(f.getGamePanel());
        partita.giocatore.movimento.addObserver(f.getGamePanel());
        partita.giocatore.attaco.addObserver(f.getGamePanel());

        partita.addObserver(f.getGamePanel());

        keyHandler.addObserver(f.getGamePanel());


        //LOOP DEL GAME
        while (true) {
            try {

                //time.notifyCurrentTime(); TODO spostare in partita model

                f.getGamePanel().repaintTask();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}