package model;


import controller.PathImages;

import java.awt.*;

/**
 * Rappresenta il personaggio principale del gioco, controllato dal giocatore.
 * Derivato dalla classe astratta Personaggio.
 * Possiede un sistema di attacco e un numero di vite che diminuiscono quando il giocatore muore.
 *
 * @author Gabriel Guerra
 */
public class Giocatore extends Personaggio {

    /**
     * Oggetto che gestisce l'attacco del giocatore
     */
    public Attaco attaco;

    /**
     * Numero di vite attuali del giocatore
     */
    public int vite;

    /**
     * Numero di vite iniziali del giocatore
     */
    public int vite_inziali;


    /**
     * Crea un nuovo giocatore con posizione iniziale, numero di vite, velocità, larghezza e altezza specificati.
     *
     * @param posX     La coordinata X iniziale del giocatore.
     * @param posY     La coordinata Y iniziale del giocatore.
     * @param vite     Il numero iniziale di vite del giocatore.
     * @param velocita La velocità di movimento del giocatore.
     * @param width    La larghezza del giocatore.
     * @param height   L'altezza del giocatore.
     */
    public Giocatore(int posX, int posY, int vite, int velocita, int width, int height) {

        super(posX, posY, velocita, width, height);

        //setAvatar(Avatar.Bomberman);

        this.attaco = new Attaco(0);

        this.vite = vite;

        this.vite_inziali = vite;


        var pathImages = new PathImages();

        var pathSource = "src/view/res/characters/" + "WhiteBomberman" + "/";

        pathImages.down1 = pathSource + "down1.png";
        pathImages.down2 = pathSource + "down2.png";
        pathImages.down3 = pathSource + "down3.png";
        pathImages.down4 = pathSource + "down4.png";
        pathImages.downidle = pathSource + "downidle.png";

        pathImages.up1 = pathSource + "up1.png";
        pathImages.up2 = pathSource + "up2.png";
        pathImages.up3 = pathSource + "up3.png";
        pathImages.up4 = pathSource + "up4.png";
        pathImages.upidle = pathSource + "upidle.png";

        pathImages.right1 = pathSource + "right1.png";
        pathImages.right2 = pathSource + "right2.png";
        pathImages.right3 = pathSource + "right3.png";
        pathImages.right4 = pathSource + "right4.png";
        pathImages.rightidle = pathSource + "rightidle.png";

        pathImages.left1 = pathSource + "left1.png";
        pathImages.left2 = pathSource + "left2.png";
        pathImages.left3 = pathSource + "left3.png";
        pathImages.left4 = pathSource + "left4.png";
        pathImages.leftidle = pathSource + "leftidle.png";

        super.movimento.posizione.pathImages = pathImages;
    }

    /**
     * Imposta gli sprites delll'avatar scelto specifici
     * Avatar di default è whiteBomberman
     * @param avatar Avatar da impostare
     */
    public void setSpritesAvatar(Avatar avatar) {


        if (avatar != null) {

            switch (avatar) {
                case avatar.PrettyBomberman -> setSprites("PrettyBomberman");
            }

        } else {
            setSprites("WhiteBomberman");
        }
    }

    private void setSprites(String personaggio) {

        //Set degli sprites
        var pathImages = new PathImages();

        var pathSource = "src/view/res/characters/" + personaggio + "/";

        pathImages.down1 = pathSource + "down1.png";
        pathImages.down2 = pathSource + "down2.png";
        pathImages.down3 = pathSource + "down3.png";
        pathImages.down4 = pathSource + "down4.png";
        pathImages.downidle = pathSource + "downidle.png";

        pathImages.up1 = pathSource + "up1.png";
        pathImages.up2 = pathSource + "up2.png";
        pathImages.up3 = pathSource + "up3.png";
        pathImages.up4 = pathSource + "up4.png";
        pathImages.upidle = pathSource + "upidle.png";

        pathImages.right1 = pathSource + "right1.png";
        pathImages.right2 = pathSource + "right2.png";
        pathImages.right3 = pathSource + "right3.png";
        pathImages.right4 = pathSource + "right4.png";
        pathImages.rightidle = pathSource + "rightidle.png";

        pathImages.left1 = pathSource + "left1.png";
        pathImages.left2 = pathSource + "left2.png";
        pathImages.left3 = pathSource + "left3.png";
        pathImages.left4 = pathSource + "left4.png";
        pathImages.leftidle = pathSource + "leftidle.png";

        super.movimento.posizione.pathImages = pathImages;

        super.movimento.posizione.ImageAttuale=super.movimento.posizione.pathImages.downidle;
    }

    /**
     * Metodo chiamato quando il giocatore muore.
     * Riduce il numero di vite e potrebbe attivare un'animazione di morte.
     */
    public void morte() {

        //TODO FARE ANIMAZIONE DI MORTE , vietare il movimento
        this.vite = this.vite - 1;

    }

    /**
     * Reimposta il numero di vite del giocatore ai valori iniziali.
     */
    public void reimpostaViteIniziali() {
        this.vite = this.vite_inziali;
    }

}
