package model;


import java.util.ArrayList;

/**
 * Contiene tutte le impostazioni di ogni mappa, tiles, enemici, powerups, portaleUscita
 */
public class Map {


    Maps selectedMap;

    public ArrayList<Enemico> Enemici = new ArrayList<>();

    public Map(Maps selectedMap) {
        this.selectedMap = selectedMap;

        setTiles();
        setPowerUps();
    }

    /**
     * Get dei tiles della mappa selezionata
     */
    private void setTiles() {

        switch (selectedMap) {
            case TheSevenSeas:
                break;
        }
    }

    /**
     * Get dei powerUps della mappa selezionata
     */
    private void setPowerUps() {

        switch (selectedMap) {
            case TheSevenSeas:
                break;
        }
    }

    /**
     * Imposta lo sfondo della mappa selezionata
     */
    private void setImageMap() {

        switch (selectedMap) {
            case TheSevenSeas:
                break;
        }
    }

    private void setEnemici() {

        //TODO Usare questa config per creare gli enimici
        switch (selectedMap) {
            case TheSevenSeas:
               // Enemici.add(new Enemico(100, 100, 1, 2, 50, 50));
                break;
        }
    }

}
