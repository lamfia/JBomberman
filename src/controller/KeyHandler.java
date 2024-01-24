package controller;

import org.apache.commons.io.filefilter.TrueFileFilter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class KeyHandler implements KeyListener {


    private boolean uppressed, downpressed, rightpressed, leftpressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();


            if (code == KeyEvent.VK_W) {

                ApplicationManager.movimento.goUp(false);
            }
            if (code == KeyEvent.VK_A) {

                ApplicationManager.movimento.goLeft(false);

            }
            if (code == KeyEvent.VK_S) {

                ApplicationManager.movimento.goDown(false);
            }
            if (code == KeyEvent.VK_D) {

                ApplicationManager.movimento.goRight(false);

            }



        //Attack!
        if (code == KeyEvent.VK_SPACE) {
            //Aggiungere logica di ultimo tasto
            // premuto e continuare il suo movimento
            //fino ad fare il keyRealease
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


}
