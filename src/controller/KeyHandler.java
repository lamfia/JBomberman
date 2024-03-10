package controller;


import model.Partita;
import model.StatoPartita;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class KeyHandler extends Observable implements KeyListener {


    private boolean uppressed, downpressed, rightpressed, leftpressed;




    private Partita partita;

    public KeyHandler(Partita partita) {
        this.partita = partita;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (partita.isTitleState()) {
            notificaLettera(code);
            return;
        }

        if (code == KeyEvent.VK_W) {

            //Title manager
//            if (partita.isTitleState()) {
//                notifica(Direzione.UP);
//            }

            if (partita.statoPartita == StatoPartita.Playing) {
                ApplicationManager.movimento.goUp(false);
            }

        }
        if (code == KeyEvent.VK_A) {
            if (partita.statoPartita == StatoPartita.Playing) {
                ApplicationManager.movimento.goLeft(false);
            }
        }
        if (code == KeyEvent.VK_S) {
            //Title manager
//            if (partita.isTitleState()) {
//                notifica(Direzione.DOWN);
//            }

            if (partita.statoPartita == StatoPartita.Playing) {
                ApplicationManager.movimento.goDown(false);
            }
        }
        if (code == KeyEvent.VK_D) {

            if (partita.statoPartita == StatoPartita.Playing) {
                ApplicationManager.movimento.goRight(false);
            }

        }


        //Attack!
        if (code == KeyEvent.VK_SPACE) {

            //Title manager confirm with "space"
//            if (partita.isTitleState()) {
//                notifica(Direzione.SPACE);
//            }else{

                //TODO
                //Aggiungere logica di ultimo tasto
                // premuto e continuare il suo movimento
                //fino ad fare il keyRealease
                ApplicationManager.attaco.Attacare();

//            }


        }


//        //Solo in title e quando si crea l'utente, conferma di nickname
//        if(code==KeyEvent.VK_ENTER){
//            System.out.println("Enter!");
//            //finestra.requestFocus();
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            ApplicationManager.movimento.goUp(true);
        }
        if (code == KeyEvent.VK_A) {

            ApplicationManager.movimento.goLeft(true);
        }
        if (code == KeyEvent.VK_S) {
            ApplicationManager.movimento.goDown(true);
        }
        if (code == KeyEvent.VK_D) {
            ApplicationManager.movimento.goRight(true);
        }

    }


    /**
     * Notifica al gamepanel di cambiare di opzione Selezionata
     */
    private void notifica(Direzione direzione) {
        setChanged();
        notifyObservers(direzione);
    }

    private void notificaLettera(int keyCode) {
        setChanged();
        String keyText = KeyEvent.getKeyText(keyCode);
        notifyObservers(keyText);
    }



}
