package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public abstract class Oggetto {

    public int x;
    public int y;
    public int width;
    public int height;
    public int secondiCambioSprite;

    public BufferedImage currentImage;

    ArrayList<String> pathSprites;

    CambiaSprite CambiaSprite;

    public Hitbox hitbox;

    //Mettere attributo Hitbox ad ogni oggetto?

    public  String pathSource="src/view/res/";

    public void setCurrentImage(String path) {

        try {
            this.currentImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
