package controller;

import model.Tile;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TileManager {


    GamePanel gp;

    Tile[] tile;

    final int tileSize = 20; //19x19 tile

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[1];

        try {
            getTileImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getTileImage() throws IOException {

        tile[0] = new Tile();
        try {

            tile[0].image = ImageIO.read(new File("src/view/maps/Pirate/Barrel.png"));
            tile[0].collision = true;
            tile[0].x = 172;
            tile[0].y = 190;

            tile[0].collisionRectangle= new Rectangle(172 , 190 , 60, 60);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * USATO STREAM QUI
     * Utilizzato gli stream per ottenere il valore di collisione in base alle coordinate x e y
     *
     * @param x
     * @param y
     * @return true or false se è un tile con collission true
     */
    public boolean isTileBlocked(int playerX, int playerY) {

       // var isBlocked = Arrays.stream(tile).filter(i -> i.x == x && i.y == y).findFirst().orElse(false).get().collision;

//        var isBlocked = Arrays.stream(tile)
//                .filter(i -> i.x == x && i.y == y)
//                .findFirst()
//                .map(tile -> tile.collision)
//                .orElse(false);

        var isBlocked = Arrays.stream(tile)
                .filter(tile -> tile.collision)
                .anyMatch(tile -> tile.collisionRectangle.intersects(playerX, playerY, 40, 40));

        return isBlocked;
    }

    public void draw(Graphics g2) {

        g2.drawImage(tile[0].image, tile[0].x, tile[0].y, gp.tileSize, gp.tileSize, null);
    }

}
