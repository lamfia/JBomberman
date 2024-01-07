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

   Rectangle ExpendedeHitbox;

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

        var tile1 = new Tile(172, 190, 60, "src/view/maps/Pirate/Barrel.png", true);
        tile[0] = tile1;

    }

    /**
     * USATO STREAM QUI
     * Utilizzato gli stream per ottenere il valore di collisione in base alle coordinate x e y
     *
     * @param x
     * @param y
     * @return true or false se è un tile con collission true
     */
    public boolean isTileBlocked(Posizione posizione) {

        // var isBlocked = Arrays.stream(tile).filter(i -> i.x == x && i.y == y).findFirst().orElse(false).get().collision;

//        var isBlocked = Arrays.stream(tile)
//                .filter(i -> i.x == x && i.y == y)
//                .findFirst()
//                .map(tile -> tile.collision)
//                .orElse(false);


        //TODO mettere hitbox variabile secondo personaggio?
        //hitbox
        //int finalX = playerX + 7;
        //int finalY = playerY + 11;

        //Hitbox!
        //g.setColor(Color.RED);
        //g2.fillRect(posGiocatoreX+8 , posGiocatoreY +11, GiocatoreWidth-19, GiocatoreHeight-20);

        // var x = hitbox.
        //Se è una direzione diversa da quella che si vuole passare è OK

        Rectangle expandedHitbox = posizione.hitbox.getBounds();

        System.out.println(posizione.hitbox.x );
        System.out.println(posizione.hitbox.y );
        System.out.println(posizione.hitbox.width );
        System.out.println(posizione.hitbox.height );


        // Espandi solo il lato associato alla direzione
        switch (posizione.direzione) {
            case UP:
                // Espandi solo il lato superiore
                expandedHitbox.y = expandedHitbox.y - 2;
                break;
            case DOWN:
                // Espandi solo il lato inferiore
                expandedHitbox.y = expandedHitbox.y + 2;
                break;

            case RIGHT:
                // Espandi solo il lato destro
                expandedHitbox.x = expandedHitbox.x + 2;
                break;
            case LEFT:
                // Espandi solo il lato sinistro
                expandedHitbox.x = expandedHitbox.x - 2;
                break;
        }

        ExpendedeHitbox=expandedHitbox;

        var isBlocked = Arrays.stream(tile)
                .filter(tile -> tile.collision)
                .anyMatch(tile -> expandedHitbox.intersects(tile.collisionRectangle));

//        var isBlocked = Arrays.stream(tile)
//                .filter(tile -> tile.collision)
//                .anyMatch(tile -> {
//                    Rectangle tileRectangle = tile.collisionRectangle.getBounds();
//                    return expandedHitbox.intersects(tileRectangle) && !expandedHitbox.intersection(tileRectangle).isEmpty();
//                });


        return isBlocked;
    }

    public void draw(Graphics g2) {

        g2.drawImage(tile[0].image, tile[0].x, tile[0].y, 60,60, null);

        g2.setColor(Color.blue);
        g2.fillRect(tile[0].x, tile[0].y, 60, 60);

        if (ExpendedeHitbox!=null){

        g2.setColor(Color.orange);
        g2.fillRect(ExpendedeHitbox.x,ExpendedeHitbox.y,ExpendedeHitbox.width,ExpendedeHitbox.height);
        }

        //g2.drawImage(tile[0].image, tile[0].x, tile[0].y, gp.tileSize, gp.tileSize, null);
    }


}
