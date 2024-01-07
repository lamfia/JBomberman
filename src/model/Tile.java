package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class Tile {

    public BufferedImage image;

    public boolean collision=false;

    public int x;

    public int y;

    public int tileSize;

    public Rectangle collisionRectangle;

    public Tile(int x, int y, int tileSize,String Pathimage , boolean collision) throws IOException {

        this.x=x;
        this.y=y;
        this.image= ImageIO.read(new File(Pathimage));
        this.collision=collision;
        this.tileSize=tileSize;

        int tileSizeHitbox=tileSize-19;
        this.collisionRectangle= new Rectangle(x+8,y+16,tileSizeHitbox,tileSizeHitbox);

    }

}
