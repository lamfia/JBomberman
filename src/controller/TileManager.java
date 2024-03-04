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

    ArrayList<Personaggio> Personaggi = new ArrayList<>();
    Rectangle ExpendedeHitbox;
    Giocatore giocatore;
    Partita partita;

    boolean showHitboxes = false;

    public TileManager(GamePanel gp, Giocatore giocatore, Partita partita) {

        this.giocatore = giocatore;

        this.partita = partita;

        SetEnemici();

        Personaggi.addAll(partita.map.Enemici);

        try {
            getTilesConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Cancella gli enemici attuali e li ricrea con i parametri iniziali
     */
    public void RiSetEnemici() {

        //fare cancel degli enimici
        this.partita.map.removeAllEnimici();
        Personaggi = new ArrayList<>();

        SetEnemici();
        Personaggi.addAll(partita.map.Enemici);
    }

    public void RiSetPowerUps() {
        this.partita.map.resetPowerUpsTiles();

        this.giocatore.attaco.resetQuantitaBombe();
        this.giocatore.movimento.resetVelocita();
        this.giocatore.attaco.resetExplosionRange();


    }


    private void SetEnemici() {
        this.partita.map.setEnemici(this);
    }

    /**
     * Add degli altri personaggi oltre al giocatore principale
     *
     * @param personaggio
     */
    public void aggiungPersonaggio(Personaggio personaggio) {
        this.Personaggi.add(personaggio);
    }

    public void getTilesConfig() throws IOException {

        //this.WalkingTiles = this.partita.map.WalkingTiles;

        //this.PowerUpTiles = this.partita.map.PowerUpTiles;

        //this.this.partita.map.DestructibilesTiles = this.partita.map.this.partita.map.DestructibilesTiles;

    }

    /**
     * USATO STREAM QUI
     * Utilizzato gli stream per ottenere il valore di collisione in base alle coordinate x e y
     *
     * @return true or false se è un tile con collission true
     */
    public boolean isTileBlocked(Posizione posizione, int velocita, boolean noClip) {

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

        //Blocco da i destructibiles tiles
        var isBlocked = this.partita.map.DestructibilesTiles.stream()
                .filter(tile -> tile.collision)
                .anyMatch(tile -> expandedHitbox.intersects(tile.collisionRectangle));

        //Se noClip è true allora non c'è il blocco per i destructibiles tiles
        if (noClip == true) {
            isBlocked = false;
        }


        //Blocco quando va fuori a walking tiles
        var isBlocked2 = this.partita.map.WalkingTiles.stream()
                .anyMatch(WalkingTiles -> WalkingTiles.collisionRectangle.contains(expandedHitbox));


        //se fa collission un tile collision true OPPURE NON contiene in walkingtile
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
            for (Tile WalkingTile : this.partita.map.WalkingTiles) {
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
                            // .skip(1) // Salta il primo elemento
                            .map(personaggio -> (Enemico) personaggio)
                            .collect(Collectors.toList());

                    var enemiciEliminati = enemici.stream().filter(enemico ->
                            enemico.movimento.posizione.hitbox.intersects(bomb.explosion_x)
                                    ||
                                    enemico.movimento.posizione.hitbox.intersects(bomb.explosion_y)
                    ).collect(Collectors.toList());

                    for (Enemico enemicoEliminato : enemiciEliminati) {
                        partita.points += enemicoEliminato.eliminaEnemico();
                        Personaggi.remove(enemicoEliminato);

                    }

//TODO decommmentare
                    //CanOpenPortal();
                    isWin();


                    //Logica esplosion dei tiles
                    var tilesEsplosi = this.partita.map.DestructibilesTiles.stream()
                            .filter(tile -> bomb.explosion_x.intersects(tile.collisionRectangle)
                                    || bomb.explosion_y.intersects(tile.collisionRectangle)).collect(Collectors.toList());

                    for (Tile tileEsploso : tilesEsplosi) {
                        this.partita.map.DestructibilesTiles.remove(tileEsploso);
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
        for (Tile tile : this.partita.map.DestructibilesTiles) {
            g2.drawImage(tile.image, tile.x, tile.y, tile.width, tile.height, null);
        }

        //Draw dei PowerUpds
        for (Tile PowerUpTile : this.partita.map.PowerUpTiles) {
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
            for (Tile tile : this.partita.map.DestructibilesTiles) {
                g2.fillRect(tile.collisionRectangle.x, tile.collisionRectangle.y, tile.collisionRectangle.width, tile.collisionRectangle.height);
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


        //Draw del player
        try {
            g2.drawImage(

                    ImageIO.read(new File(giocatore.movimento.posizione.ImageAttuale)),
                    giocatore.movimento.posizione.pos_x,
                    giocatore.movimento.posizione.pos_y,
                    giocatore.movimento.posizione.width,
                    giocatore.movimento.posizione.height, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (showHitboxes == true) {
            //hitbox del personaggio
            if (ExpendedeHitbox != null) {

                g2.setColor(Color.yellow);
                g2.fillRect(ExpendedeHitbox.x, ExpendedeHitbox.y, ExpendedeHitbox.width, ExpendedeHitbox.height);

            } else {

                g2.setColor(Color.yellow);
                g2.fillRect(giocatore.movimento.posizione.hitbox.x
                        , giocatore.movimento.posizione.hitbox.y, giocatore.movimento.posizione.hitbox.width,
                        giocatore.movimento.posizione.hitbox.height);
            }
        }


    }

    private long lastHitTime = 0;
    private final long invulnerabilityDuration = 4000; // 2 secondi di invulnerabilità

    private Boolean isGameOver() {

        var hitboxGiocatore = giocatore.movimento.posizione.hitbox.getBounds();

        //GameOver AutoEsplosioneBomba
        boolean isGameOver1 = giocatore.attaco.getExplosionBombsHitbox().stream()
                .anyMatch(rectangle -> rectangle.intersects(hitboxGiocatore));

        // Controlla se il giocatore interseca almeno uno degli enemici
        boolean isGameOver2 = Personaggi.stream()
                //.skip(1) // Salta il primo elemento che è il giocatore
                .anyMatch(personaggio -> giocatore.movimento.posizione.hitbox.intersects(personaggio.movimento.posizione.hitbox));

        if (isGameOver1 || isGameOver2) {
            long currentTime = System.currentTimeMillis();

            // Verifica se è passato abbastanza tempo dall'ultimo colpo
            if (currentTime - lastHitTime > invulnerabilityDuration) {
                // Il giocatore è vulnerabile, esegui le azioni di game over
                lastHitTime = currentTime; // Aggiorna il tempo dell'ultimo colpo

                // TP del giocatore in posizione iniziale
                giocatore.movimento.posizione.pos_x = giocatore.movimento.posizione.pos_x_iniziale;
                giocatore.movimento.posizione.pos_y = giocatore.movimento.posizione.pos_y_iniziale;


                giocatore.morte();

                if (giocatore.vite <= 0) {
                    System.out.println("Game Over!");
                    partita.changeStatoPartita(StatoPartita.GameOver);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Apre il portale se non ci sono più enemici vivi
     */
    public void CanOpenPortal() {

        if (partita.map.Enemici.stream().count() == 0) {
            OpenPortal();
        }
    }

    /**
     * Comunicare alla partita di aprire il portale
     */
    private void OpenPortal() {
        partita.OpenPortal();
    }

    public void isWin() {

        if (Personaggi.stream().count() == 0) {

            //TODO fare condizione di quando il giocatore sta nel portal

            partita.changeStatoPartita(StatoPartita.Win);

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
        if (posizione != giocatore.movimento.posizione) {
            return;
        }


        var hitbox = posizione.hitbox.getBounds();

        //In caso di Win! TODO da fare qui
        //Fare cambio stato della partita per notificare la win


        //In caso di pick up del powerUps
        Optional<PowerUpTile> pickedPowerUpOptional = this.partita.map.PowerUpTiles.stream()
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
            this.partita.map.PowerUpTiles.remove(pickedPowerUp);

        }

    }


}
