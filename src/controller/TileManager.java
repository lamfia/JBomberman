package controller;

import model.Tile;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TileManager {


    GamePanel gp;

    Tile[] tile;

    public TileManager(GamePanel gp)  {

        this.gp=gp;

        tile= new Tile[1];


        try {
            getTileImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getTileImage() throws IOException {

        tile[0]= new Tile();
        try {

        tile[0].image= ImageIO.read(new File("src/view/maps/Pirate/Barrel.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics g2){

        g2.drawImage(tile[0].image, 0 ,0,gp.tileSize,gp.tileSize,null);
    }

}
