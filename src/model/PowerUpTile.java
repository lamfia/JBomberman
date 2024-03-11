package model;

import java.awt.*;
import java.io.IOException;

public class PowerUpTile extends Tile {


    public  PowerUp powerUp;
    public PowerUpTile(int x, int y, PowerUp powerUp) throws IOException {


        super(x, y, 40, 40, new Rectangle(x+8,y+8,25,25),
                "JBomberman/src/view/res/miscellaneous/PowerUp"+ powerUp.toString()+".png", false);
        this.powerUp=powerUp;

    }
}
