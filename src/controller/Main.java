package controller;

import view.*;

import model.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {



        // crea un model
        Counter c=new Counter();
        ApplicationManager.modelInstance=c;

        // crea la view
        Finestra f=new Finestra();

        // innesca il meccanismo Observer Observable
        c.addObserver(f.getPannelloSpaziale());

        // FARE COSE
        c.reset();

        while (true)
        {

            if (ApplicationManager.automatic)
                c.inc();

            try {

                Thread.sleep(1000);
                c.inc();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}