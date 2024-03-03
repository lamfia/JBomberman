package model;


import controller.Direzione;
import controller.TileManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Contiene tutte le impostazioni di ogni mappa, tiles, enemici, powerups, portaleUscita
 */
public class Map {


    Maps selectedMap;

    public ArrayList<Enemico> Enemici = new ArrayList<>();
    public ArrayList<Tile> WalkingTiles = new ArrayList<>();
    public ArrayList<PowerUpTile> PowerUpTiles = new ArrayList<>();
    public ArrayList<Tile> DestructibilesTiles = new ArrayList<>();

    private TileManager tileManager;


    public Map(Maps selectedMap) throws IOException {
        this.selectedMap = selectedMap;

        setTiles();
        setPowerUps();
    }



    /**
     * Imposta lo sfondo della mappa selezionata
     */
    public String getMapPath() {
        switch (selectedMap) {
            case TheSevenSeas:
                return "src/view/maps/Pirate/pirata.png";
        }

        return null;
    }



    public void  resetMapConfig(){
        //Reset enimici
        this.Enemici= new ArrayList<>();
        //setEnemici();
    }

    /**
     * Get dei tiles della mappa selezionata
     */
    private void setTiles() throws IOException {

        switch (selectedMap) {
            case TheSevenSeas:

                //Settings del Pirate Map
                var pathImageTiles = "src/view/maps/Pirate/";

                DestructibilesTiles.add(new Tile(377, 285, 46, 46,
                        pathImageTiles + "LifeSave1.png", true));


                DestructibilesTiles.add(new Tile(80, 100, 46, 46,
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


                break;
        }
    }

    /**
     * Questo set viene fatto dopo perchè ha bisogno del tileM
     *
     * @param tileM
     */
    public void setEnemici(TileManager tileM) {

        switch (selectedMap) {
            case TheSevenSeas:
                this.Enemici.add(new Enemico2(80, 150, 4, 40, 40, Direzione.UP, tileM));
                this.Enemici.add(new Enemico(180, 150, 4, 40, 40, Direzione.UP, tileM));
                break;
        }
    }


    /**
     * Get dei powerUps della mappa selezionata
     */
    private void setPowerUps() throws IOException {

        switch (selectedMap) {
            case TheSevenSeas:

                //PowerUps Tiles
                PowerUpTiles.add(new PowerUpTile(680, 80, PowerUp.ExtraBomb));
                PowerUpTiles.add(new PowerUpTile(80, 80, PowerUp.SpeedUp));
                PowerUpTiles.add(new PowerUpTile(80, 360, PowerUp.IncreaseExplosion));

                break;
        }
    }


}
