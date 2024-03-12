package controller;


import model.Partita;
import model.StatoPartita;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;


/**
 * Gestisce gli eventi della tastiera durante il gioco.
 *
 * @author Gabriel Guerra
 */
public class KeyHandler extends Observable implements KeyListener {

    private boolean uppressed, downpressed, rightpressed, leftpressed;

    private Partita partita;


    /**
     * Costruttore che riceve la partita
     * associata al gestore dei tasti.
     *
     * @param partita La partita associata al gestore dei tasti
     */
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

            if (partita.isPlayingState()) {
                ApplicationManager.movimento.goUp(false);
            }

        }
        if (code == KeyEvent.VK_A) {
            if (partita.isPlayingState()) {
                ApplicationManager.movimento.goLeft(false);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (partita.isPlayingState()) {
                ApplicationManager.movimento.goDown(false);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (partita.isPlayingState()) {
                ApplicationManager.movimento.goRight(false);
            }

        }


        //Attack!
        if (code == KeyEvent.VK_SPACE) {

            ApplicationManager.attaco.Attacare();

        }
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
     * Notifica l'osservatore con la lettera premuta.
     *
     * @param keyCode Il codice della lettera premuta
     */
    private void notificaLettera(int keyCode) {
        setChanged();
        String keyText = KeyEvent.getKeyText(keyCode);
        notifyObservers(keyText);
    }


}
