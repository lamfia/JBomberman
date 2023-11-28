package controller;

import view.*;

import model.*;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {



        //Crea i model
        //var Movimento=new Movimento(0,0);

        var time =new Time();

        var giocatore= new Giocatore(400,200,100,2);

        ApplicationManager.movimento= giocatore.movimento;

        //Crea la view
        var f=new Finestra();
        f.getPannelloSpaziale().addPersonaggio(giocatore);


        //Observer, observable
        //Movimento.addObserver(f.getPannelloSpaziale());

        time.addObserver(f.getPannelloSpaziale());

        giocatore.movimento.addObserver(f.getPannelloSpaziale());

        f.getPannelloSpaziale().startGameThread();

        //LOOP DEL GAME
        while (true)
        {
            try {

                time.notifyCurrentTime();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}