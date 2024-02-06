package model;


/**
 * Contiene tutte le impostazioni di ogni mappa, tiles, enemici, powerups, portaleUscita
 */
public class Map {


    Maps selectedMap;

    public Map(Maps selectedMap){
        this.selectedMap=selectedMap;

        setTiles();
        setPowerUps();
    }

    /**
     * Get dei tiles della mappa selezionata
     */
    private void setTiles(){

        switch (selectedMap){
            case TheSevenSeas:
                break;
        }
    }

    /**
     * Get dei powerUps della mappa selezionata
     */
    private void setPowerUps(){

        switch (selectedMap){
            case TheSevenSeas:
                break;
        }
    }

    /**
     * Imposta lo sfondo della mappa selezionata
     */
    private void setImageMap(){

        switch (selectedMap){
            case TheSevenSeas:
                break;
        }
    }

}
