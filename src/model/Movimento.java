package model;

import controller.Direzione;
import controller.PathImages;
import controller.Posizione;
import controller.TileManager;

import javax.swing.*;
import java.util.Map;
import java.util.Observable;


/**
 * Gestisce il movimento dei personaggi sulla mappa di gioco.
 * Questa classe estende Observable, permettendo la notifica agli osservatori
 * in caso di aggiornamenti della posizione del personaggio.
 * La direzione del personaggio e le interazioni con il TileManager vengono gestite
 * nei metodi di movimento (goUp, goDown, goLeft, goRight).
 *
 * @author Gabriel Guerra
 */
public class Movimento extends Observable {

    /**
     * Rappresenta la posizione del personaggio sulla mappa di gioco.
     */
    public Posizione posizione;

    /**
     * La velocità corrente del personaggio.
     */
    public int velocita;

    /**
     * La velocità iniziale del personaggio.
     */
    public int velocitaIniziale;

    /**
     * Intervallo di tempo per cambiare l'immagine durante il movimento.
     * Misurato in millisecondi.
     */
    private final long imageChangeInterval = 150; // 0.15 secondi

    /**
     * Indice dello sprite corrente del personaggio.
     */
    public int sprite = 0;

    /**
     * Gestore dei tiles sulla mappa di gioco.
     */
    public TileManager tileM;

    /**
     * Attributo che indica se il personaggio può attraversare i tiles distruttibili.
     * Il valore di default è false.
     */
    public boolean noClip = false;

    /**
     * Tempo dell'ultimo cambio di immagine durante il movimento.
     * Inizializzato con il valore corrente del sistema al momento della creazione dell'istanza.
     */
    private long lastImageChangeTime = System.currentTimeMillis();

    /**
     * Costruttore della classe Movimento.
     *
     * @param posX     La coordinata x iniziale della posizione del personaggio.
     * @param posY     La coordinata y iniziale della posizione del personaggio.
     * @param velocita La velocità iniziale del personaggio.
     * @param width    Larghezza del personaggio.
     * @param height   Altezza del personaggio.
     */
    public Movimento(int posX, int posY, int velocita, int width, int height) {

        posizione = new Posizione(posX, posY, width, height);

        this.velocita = velocita;
        this.velocitaIniziale = velocita;
    }

    /**
     * Muove il personaggio verso l'alto, gestendo gli aggiornamenti
     * della posizione, dell'immagine e le interazioni con il TileManager.
     *
     * @param isIdle Indica se il personaggio è in stato di "idle".
     */
    public void goUp(Boolean isIdle) {

        posizione.direzione = Direzione.UP;

        tileM.AzioneListener(posizione);

        if (posizione.pos_y < 0 || (tileM.isTileBlocked(posizione, this.velocita, noClip))) {

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

    /**
     * Muove il personaggio verso il basso, gestendo gli aggiornamenti
     * della posizione, dell'immagine e le interazioni con il TileManager.
     *
     * @param isIdle Indica se il personaggio è in stato di "idle".
     */
    public void goDown(Boolean isIdle) {

        posizione.direzione = Direzione.DOWN;

        tileM.AzioneListener(posizione);
        if (posizione.pos_y > 520 || (tileM.isTileBlocked(posizione, this.velocita, noClip))) {

            this.posizione.ImageAttuale = this.posizione.pathImages.downidle;

        } else {

            if (isIdle) {

                this.posizione.ImageAttuale = this.posizione.pathImages.downidle;

            } else {

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

    /**
     * Muove il personaggio verso sinistra, gestendo gli aggiornamenti
     * della posizione, dell'immagine e le interazioni con il TileManager.
     *
     * @param isIdle Indica se il personaggio è in stato di "idle".
     */
    public void goLeft(Boolean isIdle) {

        posizione.direzione = Direzione.LEFT;

        tileM.AzioneListener(posizione);

        //Controllo collisione
        if (posizione.pos_x < 0 || (tileM.isTileBlocked(posizione, this.velocita, noClip))) {

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


    /**
     * Muove il personaggio verso destra, gestendo gli aggiornamenti
     * della posizione, dell'immagine e le interazioni con il TileManager.
     *
     * @param isIdle Indica se il personaggio è in stato di "idle".
     */
    public void goRight(Boolean isIdle) {

        posizione.direzione = Direzione.RIGHT;

        tileM.AzioneListener(posizione);

        if (posizione.pos_x > 745 || (tileM.isTileBlocked(posizione, this.velocita, noClip))) {

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


    /**
     * Notifica agli osservatori l'aggiornamento della posizione nella view.
     * Viene chiamato quando il personaggio può muoversi.
     */
    private void notifica() {

        //Se si può muovere allora notifica
        // agli observers di aggiornare la pos nella view
        setChanged();
        notifyObservers(posizione);

    }

    /**
     * Imposta il tileManager per il movimento.
     *
     * @param tileM Il gestore delle piastrelle da impostare.
     */
    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    /**
     * Reimposta la velocità del movimento alla velocità iniziale.
     */
    public void resetVelocita() {

        velocita = this.velocitaIniziale;

    }
}
