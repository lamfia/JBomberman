package controller;

import view.*;

import model.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GamePanel p= new GamePanel();

       // p.startGameThread();

        // crea un model
        Counter counter=new Counter();
        Movimento Movimento=new Movimento(0,0);
//
        ApplicationManager.counter=counter;
        ApplicationManager.movimento=Movimento;

        // crea la view
        Finestra f=new Finestra();

        // innesca il meccanismo Observer Observable
        counter.addObserver(f.getPannelloSpaziale());

        Movimento.addObserver(f.getPannelloSpaziale());

        // FARE COSE
        //counter.reset();

//        while (true)
//        {
//            try {
//
//
//                Thread.sleep(1000);
//                counter.inc();
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


    }
}