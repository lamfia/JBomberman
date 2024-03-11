package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;


/**
 * Questa classe rappresenta un oggetto generico nel gioco.
 * Gli oggetti possono avere una posizione, dimensioni, immagine corrente,
 * un timer per il cambio sprite, una lista di percorsi delle immagini,
 * un gestore del cambio sprite e una hitbox associata.
 * È una classe astratta che può essere estesa da tipi specifici di oggetti nel gioco.
 * @author Gabriel Guerra
 */
public abstract class Oggetto {

    /**
     * La coordinata x dell'oggetto.
     */
    public int x;
    /**
     *  La coordinata y dell'oggetto.
     */
    public int y;
    /**
     * La larghezza dell'oggetto.
     */
    public int width;
    /**
     * L'altezza dell'oggetto.
     */
    public int height;

    /**
     * Il tempo in secondi per il cambio sprite.
     */
    public int secondiCambioSprite;

    /**
     *  L'immagine corrente dell'oggetto.
     */
    public BufferedImage currentImage;
    /**
     *  La lista dei percorsi delle immagini associate all'oggetto.
     */
    ArrayList<String> pathSprites;

    /**
     *  Il gestore del cambio sprite associato all'oggetto.
     */
    CambiaSprite CambiaSprite;
    /**
     * La hitbox associata all'oggetto.
     */
    public Hitbox hitbox;

    /**
     *  Il percorso di base delle risorse del gioco.
     */
    public  String pathSource="src/view/res/";

    /**
     * Imposta l'immagine corrente dell'oggetto utilizzando il percorso specificato.
     * @param path Il percorso dell'immagine da impostare.
     */
    public void setCurrentImage(String path) {

        try {
            this.currentImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
