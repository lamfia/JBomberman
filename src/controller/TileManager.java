package controller;

import model.*;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TileManager {


    GamePanel gp;

    ArrayList<Tile> tiles;

    ArrayList<Tile> WalkingTiles;
    ArrayList<PowerUpTile> PowerUpTiles;
    ArrayList<Personaggio> Personaggi = new ArrayList<>();
    Rectangle ExpendedeHitbox;

    Giocatore giocatore;
    Partita partita;

    Posizione posizione; //mi salvo la lastPosition
    boolean showHitboxes = false;

    public TileManager(GamePanel gp, Giocatore giocatore, Partita partita) {

        this.gp = gp;

        this.giocatore = giocatore;

        tiles = new ArrayList<>();

        WalkingTiles = new ArrayList<>();

        PowerUpTiles = new ArrayList<>();

        this.aggiungPersonaggio(giocatore); //Aggiungo il giocatore nella lista di personaggi (è sempre il primo)

        this.partita = partita;

        try {
            getTileImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Add degli altri personaggi oltre al giocatore principale
     *
     * @param personaggio
     */
    public void aggiungPersonaggio(Personaggio personaggio) {
        this.Personaggi.add(personaggio);
    }

    public void getTileImage() throws IOException {


        //Settings del Pirate Map
        var pathImageTiles = "src/view/maps/Pirate/";


        //Tiles collision true , destructibles

//        tiles.add(new Tile(172, 190, 60, pathImageTiles + "LifeSave1.png", true));

        tiles.add(new Tile(377, 285, 46, 46,
                pathImageTiles + "LifeSave1.png", true));


        //Walking tiles
        WalkingTiles.add(new Tile(375, 0, 52, 600)); //left column
        WalkingTiles.add(new Tile(171, 95, 52, 430)); //center column
        WalkingTiles.add(new Tile(575, 95, 52, 430));//right column


        WalkingTiles.add(new Tile(325, 245, 150, 40));//center  small row
        WalkingTiles.add(new Tile(325, 325, 150, 40));//center  small row

        WalkingTiles.add(new Tile(80, 171, 640, 40)); //top large row
        WalkingTiles.add(new Tile(80, 410, 640, 40)); //botton large row

        WalkingTiles.add(new Tile(80, 80, 195, 40)); //top left small row
        WalkingTiles.add(new Tile(80, 490, 195, 40)); //botton left small row

        WalkingTiles.add(new Tile(525, 490, 190, 40)); //botton right small row
        WalkingTiles.add(new Tile(525, 80, 190, 40)); //top right small row

        WalkingTiles.add(new Tile(80, 80, 40, 170)); //Top left corner column
        WalkingTiles.add(new Tile(80, 360, 40, 170)); //Bottom left corner column

        WalkingTiles.add(new Tile(680, 360, 40, 170)); //Bottom right corner column
        WalkingTiles.add(new Tile(680, 80, 40, 170)); //Top right corner column

        //PowerUps Tiles
        PowerUpTiles.add(new PowerUpTile(680, 80, PowerUp.ExtraBomb));
        PowerUpTiles.add(new PowerUpTile(80, 80, PowerUp.SpeedUp));
        PowerUpTiles.add(new PowerUpTile(80, 360, PowerUp.IncreaseExplosion));

    }

    /**
     * USATO STREAM QUI
     * Utilizzato gli stream per ottenere il valore di collisione in base alle coordinate x e y
     *
     * @return true or false se è un tile con collission true
     */
    public boolean isTileBlocked(Posizione posizione, int velocita) {

        //TODO mettere hitbox variabile secondo personaggio?

        //Se è una direzione diversa da quella che si vuole passare è OK
        Rectangle expandedHitbox = posizione.hitbox.getBounds();


        // Espandi solo il lato associato alla direzione
        switch (posizione.direzione) {
            case UP:
                // Espandi solo il lato superiore
                expandedHitbox.y = expandedHitbox.y - velocita;
                break;
            case DOWN:
                // Espandi solo il lato inferiore
                expandedHitbox.y = expandedHitbox.y + velocita;
                break;

            case RIGHT:
                // Espandi solo il lato destro
                expandedHitbox.x = expandedHitbox.x + velocita;
                break;
            case LEFT:
                // Espandi solo il lato sinistro
                expandedHitbox.x = expandedHitbox.x - velocita;
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


        return isBlocked || !isBlocked2;
    }

    public void AggiungiBomba(int x, int y) {
        giocatore.attaco.Aggiungibomba(x, y);
    }

    /**
     * Metodo per fare i draw dei tiles
     **/
    public void draw(Graphics g2) {

        //Draw dei walking tiles
        if (showHitboxes == true) {
            for (Tile WalkingTile : WalkingTiles) {
                g2.setColor(WalkingTile.color);
                g2.fillRect(WalkingTile.x, WalkingTile.y,
                        WalkingTile.collisionRectangle.width, WalkingTile.collisionRectangle.height);
            }
        }

        //Draw delle bombe
        if (giocatore.attaco.getActiveBombs() != null) {

            for (Bomb bomb : giocatore.attaco.getActiveBombs()) {

                g2.drawImage(bomb.currentImage, bomb.x, bomb.y, bomb.width, bomb.height, null);

                if (bomb.explodes == true) {

                    //GameOver change stato partita
                    isGameOver();

                    //Logica attaco verso enemico
                    var enemici = Personaggi.stream()
                            .skip(1) // Salta il primo elemento
                            .map(personaggio -> (Enemico) personaggio)
                            .collect(Collectors.toList());

                    var enemiciEliminati = enemici.stream().filter(enemico ->
                            enemico.movimento.posizione.hitbox.intersects(bomb.explosion_x)
                                    ||
                                    enemico.movimento.posizione.hitbox.intersects(bomb.explosion_y)
                    ).collect(Collectors.toList());

                    for (Enemico enemicoEliminato : enemiciEliminati) {
                        enemicoEliminato.eliminaEnemico();
                        Personaggi.remove(enemicoEliminato);
                    }


                    //Logica esplosion dei tiles
                    var tilesEsplosi = tiles.stream()
                            .filter(tile -> bomb.explosion_x.intersects(tile.collisionRectangle)
                                    || bomb.explosion_y.intersects(tile.collisionRectangle)).collect(Collectors.toList());

                    for (Tile tileEsploso : tilesEsplosi) {
                        tiles.remove(tileEsploso);
                    }

                    //Explosion hitbox
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

        //Draw dei tiles
        for (Tile tile : tiles) {
            g2.drawImage(tile.image, tile.x, tile.y, tile.width, tile.height, null);
        }

        //Draw dei PowerUpds
        for (Tile PowerUpTile : PowerUpTiles) {
            g2.drawImage(PowerUpTile.image, PowerUpTile.x, PowerUpTile.y, PowerUpTile.width, PowerUpTile.height, null);

            //hitbox del powerup
            if (showHitboxes == true) {
                g2.setColor(Color.orange);
                g2.fillRect(PowerUpTile.collisionRectangle.x, PowerUpTile.collisionRectangle.y,
                        PowerUpTile.collisionRectangle.width, PowerUpTile.collisionRectangle.height);
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


        //Draw dei personnaggi
        for (Personaggio personaggio : Personaggi) {

            try {
                g2.drawImage(
                        // ImageIO.read(new File(giocatore.movimento.posizione.pathImages.downidle),
                        ImageIO.read(new File(personaggio.movimento.posizione.ImageAttuale)),
                        personaggio.movimento.posizione.pos_x,
                        personaggio.movimento.posizione.pos_y,
                        personaggio.movimento.posizione.width,
                        personaggio.movimento.posizione.height, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (showHitboxes == true) {
                //Draw del hitbox dei personaggio
                var hitbox = personaggio.movimento.posizione.hitbox;
                g2.setColor(Color.red);
                g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            }
        }

    }


    private void isGameOver() {

        var hitboxGiocatore = giocatore.movimento.posizione.hitbox.getBounds();

        //GameOver AutoEsplosioneBomba
        boolean isGameOver1 = giocatore.attaco.getExplosionBombsHitbox().stream()
                .anyMatch(rectangle -> rectangle.intersects(hitboxGiocatore));

        // Controlla se il primo elemento interseca almeno uno degli altri
        boolean isGameOver2 = Personaggi.stream()
                .skip(1) // Salta il primo elemento che è il giocatore
                .anyMatch(personaggio -> Personaggi.get(0).movimento.posizione.hitbox.intersects(personaggio.movimento.posizione.hitbox));

        if (isGameOver1 || isGameOver2) {
            partita.changeStatoPartita(StatoPartita.GameOver);
        }
    }

    /**
     * Questa azione controlla il movimento del personaggio
     * cioè le azioni attive per esempio movimento verso i powerups o attraversamento portale per win
     *
     * @param posizione
     */
    public void AzioneListener(Posizione posizione) {

        isGameOver();


        //Se non è il giocatore return!
        if (posizione !=giocatore.movimento.posizione)
        {
            return;
        }

        var hitbox = posizione.hitbox.getBounds();

        //In caso di Win! TODO da fare qui
        //Fare cambio stato della partita per notificare la win


        //In caso di pick up del powerUps
        Optional<PowerUpTile> pickedPowerUpOptional = PowerUpTiles.stream()
                .filter(powerUpTile -> powerUpTile.collisionRectangle.intersects(hitbox))
                .findFirst();

        if (pickedPowerUpOptional.isPresent()) {
            PowerUpTile pickedPowerUp = pickedPowerUpOptional.get();

            // Usa l'oggetto pickedPowerUp come necessario
            System.out.println("Ha preso il power up: " + pickedPowerUp.powerUp.toString());

            //SE del pickup dell'oggetto
            AudioManager.getInstance().playSE(2);

            switch (pickedPowerUp.powerUp) {
                case ExtraBomb:
                    this.giocatore.attaco.AumentaQuantitaBombe();
                    break;
                case SpeedUp:
                    this.giocatore.movimento.velocita += 2;
                    break;
                case IncreaseExplosion:
                    this.giocatore.attaco.AumentaExplosionRange();
                    break;

            }
            //Cancella il powerUp dalla mappa
            PowerUpTiles.remove(pickedPowerUp);

        }

    }


}
