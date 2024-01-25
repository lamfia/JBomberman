package model;

import java.awt.*;

public class Hitbox {
    public Rectangle hitboxRec;
    public Hitbox(int x, int y, int width, int height, int distanza_esterna) {

        this.hitboxRec = new Rectangle(x+distanza_esterna, y+distanza_esterna, width-(distanza_esterna*2),height-(distanza_esterna*2));
    }
    public Hitbox(int x, int y, int width, int height) {

        this.hitboxRec = new Rectangle(x, y, width,height);
    }

}
