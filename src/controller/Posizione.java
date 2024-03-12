package controller;

import java.awt.*;




/**
 * Rappresenta la posizione di un personaggio nel gioco,
 * inclusi la coordinata (x, y),
 * la direzione del personaggio, l'immagine attuale e la hitbox.
 * Questa classe fornisce metodi per aggiornare
 * la posizione del personaggio e la hitbox.
 *
 * @author Gabriel Guerra
 */
public class Posizione {

    /**
     * Coordinata x della posizione del personaggio.
     */
    public int pos_x;
    /**
     * Coordinata y della posizione del personaggio.
     */
    public int pos_y;
    /**
     * Coordinata x iniziale della posizione del personaggio.
     */
    public int pos_x_iniziale;
    /**
     * Coordinata y iniziale della posizione del personaggio.
     */
    public int pos_y_iniziale;

    /**
     * Larghezza del personaggio
     */
    public int width;

    /**
     * Lunghezza del personaggio
     */
    public int height;

    /**
     * Direzione del personaggio.
     */
    public Direzione direzione;

    /**
     * Oggetto che contiene i percorsi delle immagini del personaggio.
     */
    public PathImages pathImages;

    /**
     * Percorso dell'immagine attuale del personaggio.
     */
    public String ImageAttuale;


    /**
     * Hitbox del personaggio.
     */
    public Rectangle hitbox;


    /**
     * Crea un nuovo oggetto Posizione con
     * le coordinate (x, y) specificate e le
     * dimensioni width e height.
     *
     * @param pos_x Coordinata x della posizione iniziale.
     * @param pos_y Coordinata y della posizione iniziale.
     * @param width Larghezza del personaggio.
     * @param height Altezza del personaggio.
     */
    public Posizione(int pos_x, int pos_y, int width, int height) {

        this.pos_y = pos_y;
        this.pos_x = pos_x;

        this.pos_x_iniziale = pos_x;
        this.pos_y_iniziale = pos_y;

        this.width = width;
        this.height = height;

        AggiornaHitbox();

    }


    /**
     * Aggiorna la hitbox del personaggio
     * in base alla sua posizione e dimensioni correnti.
     */
    public void AggiornaHitbox(){
        this.hitbox = new Rectangle(pos_x + 8, pos_y + 14, width - 19, height - 20);
    }

    /**
     * Aggiorna la posizione del personaggio in
     * base alla direzione e alla velocità specificate.
     *
     * @param direzione La direzione in cui muovere il personaggio.
     * @param velocita La velocità di movimento del personaggio.
     */
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
