package model;

import java.awt.*;


/**
 * Rappresenta una hitbox associata a un oggetto, che è un rettangolo utilizzato per determinare le collisioni.
 * @author Gabriel Guerra
 */
public class Hitbox {

    /**
     * Il rettangolo che rappresenta la hitbox
     */
    public Rectangle hitboxRec;

    /**
     * Crea una nuova hitbox con una distanza esterna specificata rispetto alle coordinate e alle dimensioni fornite.
     *
     * @param x                  La coordinata X dell'angolo in alto a sinistra del rettangolo.
     * @param y                  La coordinata Y dell'angolo in alto a sinistra del rettangolo.
     * @param width              La larghezza del rettangolo.
     * @param height             L'altezza del rettangolo.
     * @param distanza_esterna   La distanza esterna aggiunta a ciascun lato del rettangolo.
     */
    public Hitbox(int x, int y, int width, int height, int distanza_esterna) {

        this.hitboxRec = new Rectangle(x+distanza_esterna, y+distanza_esterna, width-(distanza_esterna*2),height-(distanza_esterna*2));
    }

    /**
     * Crea una nuova hitbox con le coordinate e le dimensioni fornite.
     * @param x      La coordinata X dell'angolo in alto a sinistra del rettangolo.
     * @param y      La coordinata Y dell'angolo in alto a sinistra del rettangolo.
     * @param width  La larghezza del rettangolo.
     * @param height L'altezza del rettangolo.
     */
    public Hitbox(int x, int y, int width, int height) {

        this.hitboxRec = new Rectangle(x, y, width,height);
    }

}
