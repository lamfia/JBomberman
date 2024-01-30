package model;

import java.awt.*;
import java.io.IOException;

public class PowerUpTile extends Tile {


    public  PowerUp powerUp;
    public PowerUpTile(int x, int y, PowerUp powerUp) throws IOException {

        super(x, y, 40, 40, new Rectangle(x+5,y+5,30,30),
                "src/view/res/miscellaneous/PowerUp"+ powerUp.toString()+".png", false);
        this.powerUp=powerUp;

    }
}
