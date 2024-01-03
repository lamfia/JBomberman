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

    public Rectangle collisionRectangle;

    public Tile(int x, int y, int tileSize,String Pathimage , boolean collision) throws IOException {

        this.x=x;
        this.y=y;

        this.collisionRectangle= new Rectangle(x,y,tileSize,tileSize);

        this.image= ImageIO.read(new File(Pathimage));
        this.collision=collision;

    }

}
