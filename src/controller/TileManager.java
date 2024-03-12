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


/**
 * Questa classe gestisce i tiles, i personaggi,
 * le bombe e i power-ups nel gioco.
 * Gestisce anche il disegno degli elementi
 * sulla schermata di gioco e controlla
 * le interazioni tra di essi.
 */
public class TileManager {

    /**
     * Lista dei personaggi nel gioco, inclusi gli enemici
     */
    ArrayList<Personaggio> Personaggi = new ArrayList<>();

    /**
     * Rettangolo espanso per le collisioni
     */
    Rectangle ExpendedeHitbox;

    /**
     * Riferimento al giocatore
     */
    Giocatore giocatore;

    /**
     * Riferimento alla partita
     */
    Partita partita;

    /**
     * Flag per mostrare le hitbox
     * solo ambiente in sviluppo
     */
    boolean showHitboxes = false;


    /**
     * Costruttore della classe `TileManager`.
     * @param gp Il pannello di gioco.
     * @param giocatore Il giocatore principale.
     * @param partita La partita in corso.
     */
    public TileManager(GamePanel gp, Giocatore giocatore, Partita partita) {

        this.giocatore = giocatore;

        this.partita = partita;

        SetEnemici();

        Personaggi.addAll(partita.map.Enemici);
    }

    /**
     * Rimuove tutti gli enemici attualmente
     * presenti sulla mappa e li ricrea con
     * i parametri iniziali.
     * Questo metodo è utile per reinizializzare
     * gli enemici durante il gioco, in caso di new game,
     * game over o continue.
     */
    public void RiSetEnemici() {

        //fare cancel degli enimici
        this.partita.map.removeAllEnimici();
        Personaggi = new ArrayList<>();

        SetEnemici();
        Personaggi.addAll(partita.map.Enemici);
    }

    /**
     * Rimuove tutti gli enemici attualmente
     * presenti sulla mappa e li ricrea con i
     * parametri iniziali.
     * Questo metodo è utile per reinizializzare
     * gli enemici durante il gioco.
     */
    public void RiSetPowerUps() {
        this.partita.map.resetPowerUpsTiles();

        this.giocatore.attaco.resetQuantitaBombe();
        this.giocatore.movimento.resetVelocita();
        this.giocatore.attaco.resetExplosionRange();


    }

    /**
     * Resetta tutti i power-ups presenti sulla
     * mappa e riporta il giocatore alle impostazioni iniziali.
     * Questo metodo è utile per ripristinare lo
     * stato iniziale dei power-ups e del giocatore
     * durante il gioco.
     */
    private void SetEnemici() {
        this.partita.map.setEnemici(this);
    }

    /**
     * Add degli altri personaggi oltre al giocatore principale
     *
     * @param personaggio personaggio da aggiungere
     */
    public void aggiungPersonaggio(Personaggio personaggio) {
        this.Personaggi.add(personaggio);
    }



    //USATO STREAM QUI
    /**
     * Verifica se il tile associato alla posizione
     * specificata è bloccato.
     * Determina se il
     * tile ha una collisione in base alle coordinate x e y.
     *
     * @param posizione La posizione del tile da controllare.
     * @param velocita La velocità del movimento.
     * @param noClip True se la modalità no-clip è attiva, altrimenti False.
     * @return True se il tile è bloccato a causa di una collisione, altrimenti False.
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


    /**
     * Aggiunge una bomba alla posizione
     * specificata sulla mappa.
     *
     * @param x La coordinata x della posizione in cui aggiungere la bomba.
     * @param y La coordinata y della posizione in cui aggiungere la bomba.
     */
    public void AggiungiBomba(int x, int y) {
        giocatore.attaco.Aggiungibomba(x, y);
    }

    /**
     * Disegna gli elementi della mappa di gioco
     * sui graphics passato in ingresso.
     *
     * @param g2 Il contesto grafico su cui disegnare gli elementi.
     */
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


                    CanOpenPortal();


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


        //Draw della porta
        g2.drawImage(this.partita.map.PortaTile.image, this.partita.map.PortaTile.x, this.partita.map.PortaTile.y,
                this.partita.map.PortaTile.width, this.partita.map.PortaTile.height, null);


        //Draw dei tiles
        for (Tile tile : this.partita.map.DestructibilesTiles) {
            g2.drawImage(tile.image, tile.x, tile.y, tile.width, tile.height, null);
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
               // g2.fillRect(ExpendedeHitbox.x, ExpendedeHitbox.y, ExpendedeHitbox.width, ExpendedeHitbox.height);

            } else {

                g2.setColor(Color.yellow);
                g2.fillRect(giocatore.movimento.posizione.hitbox.x
                        , giocatore.movimento.posizione.hitbox.y, giocatore.movimento.posizione.hitbox.width,
                        giocatore.movimento.posizione.hitbox.height);
            }
        }


    }

    private long lastHitTime = 0;
    private final long invulnerabilityDuration = 4000; // 4 secondi di invulnerabilità


    /**
     * Verifica se il gioco è GameOver a causa di una collisione con una bomba esplosa o con un nemico.
     * Se il giocatore è stato colpito da un'esplosione di bomba o ha intersecato un nemico, controlla se è passato
     * abbastanza tempo dall'ultimo colpo per determinare se è vulnerabile o meno. Se il giocatore ha esaurito tutte
     * le vite, cambia lo stato della partita in "Game Over".
     *
     * @return true se il gioco è terminato, false altrimenti.
     */
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
     * Controlla se è possibile aprire il portale per passare al livello successivo. Il portale può essere aperto solo
     * se non ci sono più nemici vivi sulla mappa.
     */
    public void CanOpenPortal() {

        if (Personaggi.stream().count() == 0) {
            OpenPortal();
        }
    }

    /**
     * Comunica alla partita di aprire
     * il portale per passare al livello successivo.
     */
    private void OpenPortal() {
        partita.OpenPortal();
    }

    /**
     * Verifica se il giocatore ha vinto la partita.
     * Il giocatore vince quando non ci sono
     * più nemici sulla mappa e
     * interseca il portale aperto.
     */
    public void isWin() {


        if (partita.isPlayingState()) {

            //Se sono tutti gli enemici eliminati
            if (Personaggi.stream().count() == 0) {

                //Se il portale è aperto
                if (this.partita.OpenPortal == true) {

                    //Se il giocatore sta nel portale
                    var win = this.partita.map.PortaTile.collisionRectangle.intersects(giocatore.movimento.posizione.hitbox);

                    if (win == true) {
                        partita.changeStatoPartita(StatoPartita.Win);
                    }
                }
            }

        }
    }

    /**
     * Questo metodo gestisce le azioni relative al movimento del personaggio, come il movimento verso i power-up
     * o il passaggio attraverso il portale per vincere. Controlla se il giocatore ha terminato il gioco o ha vinto
     * la partita. Se la posizione passata non è quella del giocatore, il metodo termina. Se il giocatore interseca
     * un power-up, lo raccoglie e ne applica gli effetti. Inoltre, gestisce la riproduzione dei suoni relativi
     * all'acquisizione del power-up. Dopo aver raccolto un power-up, lo rimuove dalla mappa.
     *
     * @param posizione La posizione del personaggio da controllare.
     */
    public void AzioneListener(Posizione posizione) {
        isGameOver();
        isWin();
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
