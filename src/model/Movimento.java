package model;

import controller.Direzione;
import controller.PathImages;
import controller.Posizione;
import controller.TileManager;

import javax.swing.*;
import java.util.Map;
import java.util.Observable;

public class Movimento extends Observable {


    public Posizione posizione;

    public int velocita;

    private final long imageChangeInterval = 150; // 0.15 secondi
    public int sprite = 0;

    private TileManager tileM;

    //double nextDrawTime= System.nanoTime()+drawInterval;
    public Movimento(int posX, int posY, int velocita, int witdh, int height) {

        posizione = new Posizione(posX, posY, witdh, height);

        this.velocita = velocita;
    }

    private long lastImageChangeTime = System.currentTimeMillis();

    public void goUp(Boolean isIdle) {

        posizione.direzione = Direzione.UP;

        if (posizione.pos_y < 0 || (tileM.isTileBlocked(posizione))) {

            this.posizione.ImageAttuale = this.posizione.pathImages.upidle;

        } else {


            if (isIdle) {

                this.posizione.ImageAttuale = this.posizione.pathImages.upidle;

            } else {

//                posizione.pos_y = posizione.pos_y - velocita;
//                posizione.direzione = Direzione.UP;

                posizione.aggiornaPosizione(Direzione.UP, velocita);

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
        }
        notifica();

    }

    public void goDown(Boolean isIdle) {

        posizione.direzione = Direzione.DOWN;

        if (posizione.pos_y > 520 || (tileM.isTileBlocked(posizione))) {
            this.posizione.ImageAttuale = this.posizione.pathImages.downidle;

        } else {

            if (isIdle) {

                this.posizione.ImageAttuale = this.posizione.pathImages.downidle;

            } else {
//                posizione.pos_y = posizione.pos_y + velocita;
//                posizione.direzione = Direzione.DOWN;

                posizione.aggiornaPosizione(Direzione.DOWN, velocita);

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
        }
        notifica();
    }

    public void goLeft(Boolean isIdle) {

        posizione.direzione = Direzione.LEFT;

        //notificare finche idle sia false

        //fare do finche isIdle sia true



            //Controllo collisione
            if (posizione.pos_x < 0 || (tileM.isTileBlocked(posizione))) {

                this.posizione.ImageAttuale = this.posizione.pathImages.leftidle;

            } else {

                //Controlle se è idle
                if (isIdle) {

                    this.posizione.ImageAttuale = this.posizione.pathImages.leftidle;

                } else {

                    posizione.aggiornaPosizione(Direzione.LEFT, velocita);

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
            }
            notifica();

    }

    public void goRight(Boolean isIdle) {

        posizione.direzione = Direzione.RIGHT;

        if (posizione.pos_x > 745 || (tileM.isTileBlocked(posizione))) {

            this.posizione.ImageAttuale = this.posizione.pathImages.rightidle;


        } else {

            if (isIdle) {

                this.posizione.ImageAttuale = this.posizione.pathImages.rightidle;

            } else {


                posizione.aggiornaPosizione(Direzione.RIGHT, velocita);

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

        }

        notifica();
    }

    private void notifica() {

        //Se si può muovere allora notifica agli observers di aggiornare la pos nella view
        setChanged();
        notifyObservers(posizione);


    }


    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }
}
