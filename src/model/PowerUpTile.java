package model;

import java.awt.*;
import java.io.IOException;


/**
 * Classe che rappresenta un tile di power-up sulla mappa di gioco.
 * Ogni istanza di questa classe corrisponde a un power-up posizionato in una
 * specifica posizione della mappa.
 * La classe estende la classe Tile e include un'enumerazione PowerUp per identificare
 * il tipo di power-up presente sul tile.
 *
 * @author Gabriel Guerra
 */
public class PowerUpTile extends Tile {


    /**
     * Tipo di power-up presente sulla casella.
     */
    public  PowerUp powerUp;

    /**
     * Costruttore della classe PowerUpTile.
     *
     * @param x       Coordinata x della casella sulla mappa.
     * @param y       Coordinata y della casella sulla mappa.
     * @param powerUp Tipo di power-up presente sulla casella.
     * @throws IOException Eccezione lanciata in caso di errori durante la lettura delle immagini.
     */
    public PowerUpTile(int x, int y, PowerUp powerUp) throws IOException {

        super(x, y, 40, 40, new Rectangle(x+8,y+8,25,25),
                "src/view/res/miscellaneous/PowerUp"+ powerUp.toString()+".png", false);
        this.powerUp=powerUp;
    }
}
