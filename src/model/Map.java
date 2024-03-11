package model;


import controller.Direzione;
import controller.TileManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Questa classe contiene tutte le impostazioni di ogni mappa, inclusi tiles, nemici, power-ups e il portale di uscita.
 * Le mappe sono specificate tramite l'enumerazione Maps.
 *
 * @see Maps
 */
public class Map {
    /**
     * La mappa selezionata.
     */
    private Maps selectedMap;

    /**
     * Lista degli enemici presenti sulla mappa.
     */
    public ArrayList<Enemico> Enemici = new ArrayList<>();

    /**
     * Lista dei tiles attraversabili sulla mappa.
     */
    public ArrayList<Tile> WalkingTiles = new ArrayList<>();

    /**
     * Lista dei power-ups presenti sulla mappa.
     */
    public ArrayList<PowerUpTile> PowerUpTiles = new ArrayList<>();

    /**
     * Lista dei tiles distruttibili sulla mappa.
     */
    public ArrayList<Tile> DestructibilesTiles = new ArrayList<>();

    /**
     * Il tile che rappresenta la porta di uscita sulla mappa.
     */
    public Tile PortaTile;

    /**
     * Costruisce una mappa in base alla mappa selezionata.
     *
     * @param selectedMap La mappa selezionata.
     * @throws IOException Eccezione lanciata in caso di errori di input/output.
     */

    public Map(Maps selectedMap) throws IOException {
        this.selectedMap = selectedMap;

        setTiles();
        setPowerUps();

        setPorta();
    }


    /**
     * Restituisce il percorso dell'immagine di sfondo della mappa.
     *
     * @return Il percorso dell'immagine di sfondo della mappa.
     */
    public String getMapPath() {
        switch (selectedMap) {
            case TheSevenSeas:
                return "src/view/maps/Pirate/pirata.png";
            case Spaceman:
                return "src/view/maps/Spaceman/Spaceman1.png";
        }

        return null;
    }


    /**
     * Restituisce la posizione iniziale x del giocatore sulla mappa.
     *
     * @return La posizione x iniziale del giocatore.
     */
    public int getXInizialeGiocatore() {

        switch (selectedMap) {
            case TheSevenSeas:
                return 380;
            case Spaceman:
                return 380;
        }

        return 0;
    }

    /**
     * Restituisce la posizione iniziale y del giocatore sulla mappa.
     *
     * @return La posizione y iniziale del giocatore.
     */
    public int getYInizialeGiocatore() {

        switch (selectedMap) {
            case TheSevenSeas:
                return 200;
            case Spaceman:
                return 200;
        }

        return 0;
    }

    /**
     * Resetta la configurazione della mappa, rimuovendo gli enemici.
     */
    public void resetMapConfig() {
        //Reset enimici
        this.Enemici = new ArrayList<>();
        //setEnemici();
    }

    /**
     * Imposta il tile associato al portale di uscita sulla mappa, in base alla mappa selezionata.
     * Il portale è rappresentato da un tile specifico con un'immagine di porta chiusa.
     */
    private void setPorta() {


        switch (selectedMap) {

            case TheSevenSeas:
                try {
                    PortaTile = new Tile(580, 480, 46, 46,
                            "src/view/res/common/DoorClosed.png", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case Spaceman:
                try {
                    PortaTile = new Tile(581, 480, 46, 46,
                            "src/view/res/common/DoorClosed.png", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public void apriPorta() {
        try {
            this.PortaTile.image = ImageIO.read(new File("src/view/res/common/DoorOpen.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Inizializza la configurazione dei tiles sulla mappa in base alla mappa selezionata.
     * Vengono impostati i tiles distruttibili e i tiles per la navigazione del giocatore.
     *
     * @throws IOException Eccezione lanciata in caso di errori di input/output durante l'inizializzazione.
     */
    private void setTiles() throws IOException {

        switch (selectedMap) {
            case TheSevenSeas:

                //Settings del Pirate Map
                var pathImageTiles = "src/view/maps/Pirate/LifeSave1.png";


                DestructibilesTiles.add(new Tile(80, 80, 46, 46,
                        pathImageTiles, true));//0,0
                DestructibilesTiles.add(new Tile(126, 80, 46, 46,
                        pathImageTiles, true));//0,1
                DestructibilesTiles.add(new Tile(172, 80, 46, 46,
                        pathImageTiles, true));//0,2
                DestructibilesTiles.add(new Tile(218, 80, 46, 46,
                        pathImageTiles, true)); //0,3

                DestructibilesTiles.add(new Tile(377, 80, 46, 46,
                        pathImageTiles, true)); //0,4

                DestructibilesTiles.add(new Tile(680, 80, 46, 46,
                        pathImageTiles, true)); //0,6
                DestructibilesTiles.add(new Tile(634, 80, 46, 46,
                        pathImageTiles, true)); //0,7
                DestructibilesTiles.add(new Tile(580, 80, 46, 46,
                        pathImageTiles, true)); //0,8
                DestructibilesTiles.add(new Tile(530, 80, 46, 46,
                        pathImageTiles, true)); //0,9


                DestructibilesTiles.add(new Tile(80, 125, 46, 46,
                        pathImageTiles, true));//1,0

                DestructibilesTiles.add(new Tile(80, 170, 46, 46,
                        pathImageTiles, true));//2,0

                DestructibilesTiles.add(new Tile(80, 170, 46, 46,
                        pathImageTiles, true));//2,1


                DestructibilesTiles.add(new Tile(377, 285, 46, 46,
                        pathImageTiles, true)); //5,4

                DestructibilesTiles.add(new Tile(377, 480, 46, 46,
                        pathImageTiles, true)); //10,04

                 DestructibilesTiles.add(new Tile(580, 480, 46, 46,
                        pathImageTiles, true)); //10,06

                //Walking tiles
                WalkingTiles.add(new Tile(171, 95, 52, 430)); //left column
                WalkingTiles.add(new Tile(375, 0, 52, 600)); //center column
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


                break;

            case Spaceman:

                //Settings del Pirate Map
                var pathImageTilesStar = "src/view/maps/Spaceman/Star.png";

                DestructibilesTiles.add(new Tile(377, 285, 46, 46,
                        pathImageTilesStar, true));


                DestructibilesTiles.add(new Tile(80, 100, 46, 46,
                        pathImageTilesStar, true));

                //Walking tiles

                //Columms
                WalkingTiles.add(new Tile(170, 80, 52, 450)); //left1 column
                WalkingTiles.add(new Tile(270, 80, 52, 450)); //left2 column
                WalkingTiles.add(new Tile(375, 80, 52, 450)); //center column
                WalkingTiles.add(new Tile(470, 80, 52, 450));//righ1 column
                WalkingTiles.add(new Tile(575, 80, 52, 450));//righ2 column

                WalkingTiles.add(new Tile(75, 80, 52, 450));//right corner column
                WalkingTiles.add(new Tile(680, 80, 52, 450));//left corner column

                WalkingTiles.add(new Tile(80, 80, 640, 40)); // large row 1
                WalkingTiles.add(new Tile(80, 170, 640, 40)); // large row 2
                WalkingTiles.add(new Tile(80, 250, 640, 40)); // large row 3
                WalkingTiles.add(new Tile(80, 330, 640, 40)); // large row 3
                WalkingTiles.add(new Tile(80, 410, 640, 40)); // large row 3
                WalkingTiles.add(new Tile(80, 490, 640, 40)); // large row 3

                break;
        }
    }

    /**
     * Inizializza la configurazione degli enemici sulla mappa in base alla mappa selezionata.
     * Aggiunge gli enemici alla lista degli enemici presenti sulla mappa.
     *
     * @param tileM Il gestore dei tiles necessario per l'inizializzazione degli enemici.
     */
    public void setEnemici(TileManager tileM) {

        switch (selectedMap) {
            case TheSevenSeas:
               // this.Enemici.add(new Enemico2(80, 150, 4, 40, 40, Direzione.UP, tileM));
                this.Enemici.add(new Enemico(180, 130, 4, 40, 40, Direzione.DOWN, tileM));
                break;

            case Spaceman:
                //this.Enemici.add(new Enemico2(80, 150, 4, 40, 40, Direzione.UP, tileM));
                this.Enemici.add(new Enemico(250, 130, 4, 40, 40, Direzione.DOWN, tileM));
                break;
        }
    }

    /**
     * Rimuove tutti gli enemici presenti sulla mappa, resettando la lista degli enemici.
     */
    public void removeAllEnimici() {
        this.Enemici = new ArrayList<>();
    }


    /**
     * Resetta la configurazione dei powerups sulla mappa, rimuovendo tutti i powerups attuali e reimpostandoli.
     * Viene chiamato per ripristinare la disposizione originale dei powerups sulla mappa.
     * In caso di errori di input/output durante la reimpostazione, viene lanciata un'eccezione RuntimeException.
     */
    public void resetPowerUpsTiles() {
        removeAllPowerUps();

        try {
            setPowerUps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Rimuove tutti i powerups attualmente presenti sulla mappa, resettando la lista dei powerups.
     */
    private void removeAllPowerUps() {
        this.PowerUpTiles = new ArrayList<>();
    }


    /**
     * Inizializza la configurazione dei powerups sulla mappa in base alla mappa selezionata.
     * La disposizione e i tipi di powerups sono specifici per ciascuna mappa.
     *
     * @throws IOException Eccezione lanciata in caso di errori di input/output durante l'inizializzazione.
     */
    private void setPowerUps() throws IOException {

        switch (selectedMap) {
            case TheSevenSeas:

                //PowerUps Tiles
                PowerUpTiles.add(new PowerUpTile(680, 80, PowerUp.ExtraBomb));
                PowerUpTiles.add(new PowerUpTile(80, 80, PowerUp.SpeedUp));
                PowerUpTiles.add(new PowerUpTile(80, 360, PowerUp.IncreaseExplosion));

                break;

            case Spaceman:

                //PowerUps Tiles
                PowerUpTiles.add(new PowerUpTile(681, 80, PowerUp.ExtraBomb));
                PowerUpTiles.add(new PowerUpTile(80, 80, PowerUp.SpeedUp));
                PowerUpTiles.add(new PowerUpTile(80, 360, PowerUp.IncreaseExplosion));

                break;
        }
    }


}
