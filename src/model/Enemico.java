package model;

import controller.AudioManager;
import controller.Direzione;
import controller.PathImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Enemico extends Personaggio {


    // private static ArrayList<Enemico> allEnemies = new ArrayList<>();
    private Boolean movimentoAttivo = true;
    private Direzione UltimaDirezione;

    public Enemico(int posX, int posY, int Salute, int velocita, int width, int height, Direzione direzioneIniziale) {
        super(posX, posY, Salute, velocita, width, height);

        this.UltimaDirezione =direzioneIniziale;
        super.movimento.posizione.direzione = direzioneIniziale;

        //Set degli sprites
        var pathImages = new PathImages();

        var pathSource = "src/view/res/enemico/";

        pathImages.down1 = pathSource + "down1.png";
        pathImages.down2 = pathSource + "down2.png";
        pathImages.down3 = pathSource + "down3.png";
        pathImages.down4 = pathSource + "down1.png";
        pathImages.downidle = pathSource + "downidle.png";

        pathImages.up1 = pathSource + "up1.png";
        pathImages.up2 = pathSource + "up2.png";
        pathImages.up3 = pathSource + "up3.png";
        pathImages.up4 = pathSource + "up1.png";
        pathImages.upidle = pathSource + "upidle.png";

        pathImages.right1 = pathSource + "right1.png";
        pathImages.right2 = pathSource + "right2.png";
        pathImages.right3 = pathSource + "right3.png";
        pathImages.right4 = pathSource + "right1.png";
        pathImages.rightidle = pathSource + "rightidle.png";

        pathImages.left1 = pathSource + "left1.png";
        pathImages.left2 = pathSource + "left2.png";
        pathImages.left3 = pathSource + "left3.png";
        pathImages.left4 = pathSource + "left1.png";
        pathImages.leftidle = pathSource + "leftidle.png";

        super.movimento.posizione.pathImages = pathImages;


        // Creazione e avvio di un servizio executor programmato per il movimento continuo
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::AutoMovimento, 0, 700, TimeUnit.MILLISECONDS);


        // allEnemies.add(this);


    }

    public void eliminaEnemico() {

        //TODO Se c'è tempo, mettere animazione di morte dell'enemico
        // super.movimento.posizione.ImageAttuale = "src/view/res/enemico/dead1.png";
        movimentoAttivo = false;
    }


    /**
     * TODO
     * Aggiungere un metodo per spostarsi in X e Y secondo un patron
     */

    public void AutoMovimento() {

        if (movimentoAttivo) {

            switch (UltimaDirezione) {
                case UP -> super.movimento.goUp(false);
                case DOWN -> super.movimento.goDown(false);
                case RIGHT -> super.movimento.goRight(false);
                case LEFT -> super.movimento.goLeft(false);
            }

            //TODO finere patrone di movimento
            if (super.movimento.tileM.isTileBlocked(super.movimento.posizione, super.movimento.velocita)) {

                if (UltimaDirezione == Direzione.DOWN) {
                    UltimaDirezione = Direzione.UP;
                } else if (UltimaDirezione == Direzione.UP) {
                    UltimaDirezione = Direzione.DOWN;
                }
            }



        }

    }


}
