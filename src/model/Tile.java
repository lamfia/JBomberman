package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe che rappresenta un singolo tile nel gioco.
 * I tiles possono essere utilizzati per definire la mappa di gioco, possono avere immagini,
 * determinare collisioni e possono rappresentare vari tipi di oggetti.
 *
 * @author Gabriel Guerra
 */
public class Tile {

    /**
     * Immagine associata al tile.
     */
    public BufferedImage image;

    /**
     * Flag che indica se il tile è soggetto a collisioni.
     */
    public boolean collision = false;

    /**
     * Coordinata x del tile.
     */
    public int x;

    /**
     * Coordinata y del tile.
     */
    public int y;
    /**
     * Larghezza del tile.
     */
    public int width;

    /**
     * Altezza del tile.
     */
    public int height;

    /**
     * Dimensione del tile.
     */
    public int tileSize;

    /**
     * Colore associato al tile.
     */
    public Color color;

    /**
     * Rettangolo di collisione associato al tile.
     */
    public Rectangle collisionRectangle;


    /**
     * Costruttore per un tile con immagine e possibilità di collisione.
     *
     * @param x        Coordinata x del tile.
     * @param y        Coordinata y del tile.
     * @param tileSize Dimensione del tile.
     * @param Pathimage Percorso dell'immagine associata al tile.
     * @param collision Flag che indica se il tile è soggetto a collisioni.
     * @throws IOException Eccezione lanciata in caso di errori durante la lettura dell'immagine.
     */
    public Tile(int x, int y, int tileSize, String Pathimage, boolean collision) throws IOException {

        this.x = x;
        this.y = y;
        this.image = ImageIO.read(new File(Pathimage));
        this.collision = collision;
        this.tileSize = tileSize;

        int tileSizeHitbox = tileSize - 19;
        this.collisionRectangle = new Rectangle(x + 9, y + 9, tileSizeHitbox, tileSizeHitbox);

    }


    /**
     * Costruttore per un tile con immagine, dimensioni personalizzate e possibilità di collisione.
     *
     * @param x               Coordinata x del tile.
     * @param y               Coordinata y del tile.
     * @param width           Larghezza del tile.
     * @param height          Altezza del tile.
     * @param Pathimage       Percorso dell'immagine associata al tile.
     * @param collision       Flag che indica se il tile è soggetto a collisioni.
     * @throws IOException    Eccezione lanciata in caso di errori durante la lettura dell'immagine.
     */
    public Tile(int x, int y, int width, int height, String Pathimage, boolean collision) throws IOException {

        try {


            this.x = x;
            this.y = y;
            this.image = ImageIO.read(new File(Pathimage));
            this.collision = collision;

            this.width = width;
            this.height = height;

            this.collisionRectangle = new Rectangle(x + 3, y + 3, 40, 40);
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    /**
     * Costruttore per un tile con immagine, dimensioni personalizzate e possibilità di collisione.
     *
     * @param x                  Coordinata x del tile.
     * @param y                  Coordinata y del tile.
     * @param width              Larghezza del tile.
     * @param height             Altezza del tile.
     * @param collisionRectangle Rettangolo di collisione personalizzato associato al tile.
     * @param Pathimage          Percorso dell'immagine associata al tile.
     * @param collision          Flag che indica se il tile è soggetto a collisioni.
     * @throws IOException       Eccezione lanciata in caso di errori durante la lettura dell'immagine.
     */
    public Tile(int x, int y, int width, int height, Rectangle collisionRectangle, String Pathimage, boolean collision) throws IOException {

        this.x = x;
        this.y = y;
        this.image = ImageIO.read(new File(Pathimage));
        this.collision = collision;

        this.width = width;
        this.height = height;

        this.collisionRectangle = collisionRectangle;

    }


    /**
     * Costruttore per un tile "walking" con dimensioni personalizzate.
     *
     * @param x      Coordinata x del tile.
     * @param y      Coordinata y del tile.
     * @param width  Larghezza del tile.
     * @param height Altezza del tile.
     * @throws IOException Eccezione lanciata in caso di errori durante la lettura dell'immagine.
     */
    public Tile(int x, int y, int width, int height) throws IOException {

        this.x = x;
        this.y = y;
        this.color = Color.green;
        this.collisionRectangle = new Rectangle(x, y, width, height);

    }

}
