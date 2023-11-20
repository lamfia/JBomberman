package controller;

import view.*;

import model.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GamePanel p= new GamePanel();

        //Crea i model
        var Movimento=new Movimento(0,0);
        var time =new Time();

        ApplicationManager.movimento=Movimento;

        // crea la view
        Finestra f=new Finestra();

        //Observer, observable
        Movimento.addObserver(f.getPannelloSpaziale());

        time.addObserver(f.getPannelloSpaziale());

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