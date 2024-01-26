package controller;

import model.Bomb;
import model.Giocatore;
import model.Tile;
import view.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TileManager {


    GamePanel gp;

    ArrayList<Tile> tiles;


    Rectangle ExpendedeHitbox;

    Giocatore giocatore;

    boolean showHitboxes = false;

    final int tileSize = 20; //19x19 tile

    public TileManager(GamePanel gp, Giocatore giocatore) {

        this.gp = gp;

        this.giocatore = giocatore;

        tiles = new ArrayList<>();

        try {
            getTileImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getTileImage() throws IOException {

        var tile1 = new Tile(172, 190, 60, "src/view/maps/Pirate/Barrel.png", true);
        var tile2 = new Tile(400, 300, 60, "src/view/maps/Pirate/Barrel.png", true);

        tiles.add(tile1);
        tiles.add(tile2);

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

        //Se è una direzione diversa da quella che si vuole passare è OK
        Rectangle expandedHitbox = posizione.hitbox.getBounds();

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

        ExpendedeHitbox = expandedHitbox;

        var isBlocked = tiles.stream()
                .filter(tile -> tile.collision)
                .anyMatch(tile -> expandedHitbox.intersects(tile.collisionRectangle));

        return isBlocked;
    }

    public void AggiungiBomba(int x, int y) {
        giocatore.attaco.Aggiungibomba(x, y);
    }

    /**
     * Metodo per fare i draw dei tiles
     **/
    public void draw(Graphics g2) {



        //Draw delle bombe
        if (giocatore.attaco.getActiveBombs() != null) {

            for (Bomb bomb : giocatore.attaco.getActiveBombs()) {

                g2.drawImage(bomb.currentImage, bomb.x, bomb.y, bomb.width, bomb.height, null);

                if (bomb.explodes == true) {

                   // tiles.remove(0);

                    //TODO FARE LOGICA QUI DI REMOVE TILES
                    //SE IL REC DEL TILE intersect rec di explosion X or explosion Y allora fare remove del tile

                    if (showHitboxes == true) {
                        g2.setColor(Color.red);
                        g2.fillRect(bomb.explosion_x.x, bomb.explosion_x.y, bomb.explosion_x.width, bomb.explosion_x.height);
                        g2.fillRect(bomb.explosion_y.x, bomb.explosion_y.y, bomb.explosion_y.width, bomb.explosion_y.height);
                    }

                    // Larghezza di ciascuna sprite di esplosione
                    int explosionNewX = bomb.explosion_x.x;

                    int explosionNewY = bomb.explosion_y.y;

                    for (int i = 0; i <  bomb.explosionRange; i++) {

                        // Disegna la sprite di esplosione in X
                        g2.drawImage(bomb.explosion_x_sprite, explosionNewX, bomb.hitbox.hitboxRec.y,
                                bomb.hitbox.hitboxRec.width,  bomb.hitbox.hitboxRec.height, null);

                        // Disegna la sprite di esplosione in Y
                        g2.drawImage(bomb.explosion_y_sprite, bomb.hitbox.hitboxRec.x, explosionNewY,
                                bomb.hitbox.hitboxRec.width,  bomb.hitbox.hitboxRec.height, null);

                        // Calcola la posizione x per ogni sprite di esplosione
                        explosionNewX = explosionNewX + bomb.hitbox.hitboxRec.width;
                        explosionNewY = explosionNewY + bomb.hitbox.hitboxRec.height;
                    }
                }

            }

        }

        if (showHitboxes == true) {

            //hitbox del tile
            g2.setColor(Color.blue);
            for (Tile tile : tiles) {
                g2.fillRect(tile.collisionRectangle.x, tile.collisionRectangle.y, tile.collisionRectangle.width, tile.collisionRectangle.height);
            }

            //hitbox del personaggio
            if (ExpendedeHitbox != null) {

                g2.setColor(Color.orange);
                g2.fillRect(ExpendedeHitbox.x, ExpendedeHitbox.y, ExpendedeHitbox.width, ExpendedeHitbox.height);

            }

        }


        //Draw dei tiles
        for (Tile tile : tiles) {
            g2.drawImage(tile.image, tile.x, tile.y, tile.tileSize, tile.tileSize, null);
        }

    }


}
