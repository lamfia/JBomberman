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


    ArrayList<Tile> WalkingTiles;


    Rectangle ExpendedeHitbox;

    Giocatore giocatore;

    boolean showHitboxes = false;

    final int tileSize = 20; //19x19 tile

    public TileManager(GamePanel gp, Giocatore giocatore) {

        this.gp = gp;

        this.giocatore = giocatore;

        tiles = new ArrayList<>();


        WalkingTiles = new ArrayList<>();

        try {
            getTileImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getTileImage() throws IOException {

        var pathImageTiles = "src/view/maps/Pirate/";


        var tile1 = new Tile(172, 190, 50, pathImageTiles + "LifeSave1_2.png", true);
        var tile2 = new Tile(400, 300, 50, pathImageTiles + "LifeSave1_2.png", true);

        tiles.add(tile1);
        tiles.add(tile2);


        WalkingTiles.add(new Tile(350, 190,100,600));


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

//
//        var isBlocked2 = WalkingTiles.stream()
//                    .anyMatch(WalkingTiles -> expandedHitbox.intersects(WalkingTiles.collisionRectangle));

        var isBlocked2 = WalkingTiles.stream()
                .anyMatch(WalkingTiles -> WalkingTiles.collisionRectangle.contains(expandedHitbox));

        //se fa collission un tile collision true OPPURE NON va in intersection a un walkingtile

                                                    //OPPURE non sia piu overlaping


        return isBlocked  ||  !isBlocked2;
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

                    //Logica di rimozione dei tiles STREAM
                    var ExplodedTile = tiles.stream()
                            .filter(tile -> bomb.explosion_x.intersects(tile.collisionRectangle) || bomb.explosion_y.intersects(tile.collisionRectangle))
                            .findFirst();
                    ExplodedTile.ifPresent(explodedTile -> {
                        tiles.remove(ExplodedTile.get());
                    });

                    if (showHitboxes == true) {
                        g2.setColor(Color.red);
                        g2.fillRect(bomb.explosion_x.x, bomb.explosion_x.y, bomb.explosion_x.width, bomb.explosion_x.height);
                        g2.fillRect(bomb.explosion_y.x, bomb.explosion_y.y, bomb.explosion_y.width, bomb.explosion_y.height);
                    }

                    // Larghezza di ciascuna sprite di esplosione
                    int explosionNewX = bomb.explosion_x.x;

                    int explosionNewY = bomb.explosion_y.y;

                    for (int i = 0; i < bomb.explosionRange; i++) {

                        // Disegna la sprite di esplosione in X
                        g2.drawImage(bomb.explosion_x_sprite, explosionNewX, bomb.hitbox.hitboxRec.y,
                                bomb.hitbox.hitboxRec.width, bomb.hitbox.hitboxRec.height, null);

                        // Disegna la sprite di esplosione in Y
                        g2.drawImage(bomb.explosion_y_sprite, bomb.hitbox.hitboxRec.x, explosionNewY,
                                bomb.hitbox.hitboxRec.width, bomb.hitbox.hitboxRec.height, null);

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


        //Draw dei walking tiles
        for (Tile WalkingTile : WalkingTiles) {
            g2.setColor(WalkingTile.color);
            g2.fillRect( WalkingTile.x, WalkingTile.y,
                    WalkingTile.collisionRectangle.width, WalkingTile.collisionRectangle.height);
        }


    }


}
