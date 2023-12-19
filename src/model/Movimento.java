package model;

import controller.Direzione;
import controller.PathImages;
import controller.Posizione;

import javax.swing.*;
import java.util.Map;
import java.util.Observable;

public class Movimento extends Observable {


    public Posizione posizione;

    public int velocita;

    private final long imageChangeInterval = 150; // 0.15 secondi
    public int sprite = 0;

    //double nextDrawTime= System.nanoTime()+drawInterval;

    public Movimento(int posX, int posY, int velocita) {
        posizione = new Posizione();
        posizione.pos_x = posX;
        posizione.pos_y = posY;
        this.velocita = velocita;
    }

    private long lastImageChangeTime = System.currentTimeMillis();



    public void goUp(Boolean isIdle) {


        if (isIdle) {

            this.posizione.ImageAttuale = this.posizione.pathImages.upidle;

        } else {

            posizione.pos_y = posizione.pos_y - velocita;
            posizione.direzione = Direzione.UP;

            //intervalo per cambiare l'immaggine
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastImageChangeTime >= imageChangeInterval) {

                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up1;
                        sprite++;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up2;
                        sprite++;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up3;
                        sprite++;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up4;
                        sprite = 0;
                        break;
                }

                // Aggiorna il tempo dell'ultimo cambio di immagine
                lastImageChangeTime = currentTime;

            } else {

                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up1;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up2;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up3;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.up4;
                        break;


                }

            }


        }
        notifica();

    }

    public void goDown(Boolean isIdle) {

        if (isIdle) {

            this.posizione.ImageAttuale = this.posizione.pathImages.downidle;

        } else {
            posizione.pos_y = posizione.pos_y + velocita;
            posizione.direzione = Direzione.DOWN;

            //intervalo per cambiare l'immaggine
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastImageChangeTime >= imageChangeInterval) {

                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down1;
                        sprite++;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down2;
                        sprite++;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down3;
                        sprite++;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down4;
                        sprite = 0;
                        break;
                }

                // Aggiorna il tempo dell'ultimo cambio di immagine
                lastImageChangeTime = currentTime;

            } else {
                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down1;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down2;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down3;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.down4;
                        break;
                }
            }

        }

        notifica();
    }

    public void goLeft(Boolean isIdle) {
        if (isIdle) {

            this.posizione.ImageAttuale = this.posizione.pathImages.leftidle;

        } else {
            posizione.pos_x = posizione.pos_x - velocita;
            posizione.direzione = Direzione.LEFT;


            //intervalo per cambiare l'immaggine
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastImageChangeTime >= imageChangeInterval) {
                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left1;
                        sprite++;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left2;
                        sprite++;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left3;
                        sprite++;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left4;
                        sprite = 0;
                        break;
                }
                // Aggiorna il tempo dell'ultimo cambio di immagine
                lastImageChangeTime = currentTime;
            } else {

                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left1;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left2;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left3;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.left4;
                        break;
                }

            }


        }


        notifica();
    }

    public void goRight(Boolean isIdle) {

        if (isIdle) {

            this.posizione.ImageAttuale = this.posizione.pathImages.rightidle;

        } else {

            posizione.pos_x = posizione.pos_x + velocita;
            posizione.direzione = Direzione.RIGHT;

            //intervalo per cambiare l'immaggine
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastImageChangeTime >= imageChangeInterval) {
                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right1;
                        sprite++;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right2;
                        sprite++;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right3;
                        sprite++;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right4;
                        sprite = 0;
                        break;

                }

                // Aggiorna il tempo dell'ultimo cambio di immagine
                lastImageChangeTime = currentTime;

            } else {

                switch (sprite) {
                    case 0:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right1;
                        break;

                    case 1:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right2;
                        break;

                    case 2:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right3;
                        break;

                    case 3:
                        this.posizione.ImageAttuale = this.posizione.pathImages.right4;
                        break;
                }
            }

        }

        notifica();
    }

    private void notifica() {



        setChanged();
        notifyObservers(posizione);
    }



}
