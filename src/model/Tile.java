package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {

    public BufferedImage image;

    public boolean collision = false;

    public int x;

    public int y;
    public int width;


    public int height;

    public int tileSize;

    public Color color;

    public Rectangle collisionRectangle;

    public Tile(int x, int y, int tileSize, String Pathimage, boolean collision) throws IOException {

        this.x = x;
        this.y = y;
        this.image = ImageIO.read(new File(Pathimage));
        this.collision = collision;
        this.tileSize = tileSize;

        int tileSizeHitbox = tileSize - 19;
        this.collisionRectangle = new Rectangle(x + 9, y + 9, tileSizeHitbox, tileSizeHitbox);

    }
//     tiles.add(new Tile(377, 285, 46, 46, new Rectangle(380, 288, 40, 40),
//    pathImageTiles + "LifeSave1.png", true));

    /**
     * Questo l'ho usato per creare i tiles destructitibles dellla mappa pirata
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param Pathimage
     * @param collision
     * @throws IOException
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
     * Construttore per fare i "walking" tiles
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws IOException
     */
    public Tile(int x, int y, int width, int height) throws IOException {

        this.x = x;
        this.y = y;
        this.color = Color.green;
        this.collisionRectangle = new Rectangle(x, y, width, height);

    }

}
