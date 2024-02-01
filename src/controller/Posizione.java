package controller;

import java.awt.*;

/**
 * Classe che rappresenta la posizione (x,y), direzione,
 * image attuale e tutte le image del personaggio.
 *
 * @author Gabriel Guerra
 */
public class Posizione {
    public int pos_x;

    public int pos_y;

    /**
     * Larghezza del personaggio
     */
    public int width;

    /**
     * Lunghezza del personaggio
     */
    public int height;

    public Direzione direzione;
    public PathImages pathImages;

    public String ImageAttuale;

    public Rectangle hitbox;

    public Posizione(int pos_x, int pos_y, int width, int height) {

        this.pos_y = pos_y;
        this.pos_x = pos_x;

        this.width = width;
        this.height = height;

        AggiornaHitbox();

    }

    private void AggiornaHitbox(){
        this.hitbox = new Rectangle(pos_x + 8, pos_y + 14, width - 19, height - 20);
    }

    public void aggiornaPosizione(Direzione direzione, int velocita) {

        switch (direzione) {
            case UP:
                this.pos_y = this.pos_y - velocita;
                break;
            case DOWN:
                this.pos_y = this.pos_y + velocita;
                break;
            case LEFT:
                this.pos_x = this.pos_x - velocita;
                break;
            case RIGHT:
                this.pos_x = this.pos_x + velocita;
                break;

        }
        AggiornaHitbox();
    }


}
