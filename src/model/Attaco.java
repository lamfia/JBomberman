package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;


/**
 * Classe che notifica all'observer che il giocatore ha fatto un attacco.
 * Gestisce la creazione e l'esplosione delle bombe.
 * @author Gabriel Guerra
 */
public class Attaco extends Observable {


    /**
     * Lista delle bombe presenti nel gioco
     */
    ArrayList<Bomb> bombs = new ArrayList<>();

    /**
     * Quantità massima di bombe aggiuntive che il giocatore può possedere
     */
    int quantitaExtraBombe;

    /**
     * Quantità iniziale di bombe aggiuntive
     */
    int quantitaExtraBombeIniziale;

    /**
     * Contatore delle bombe attive
     */
    int contBombeAttive;


    /**
     * Raggio dell'esplosione delle bombe
     */
    int explosionRange = 3; //Questo numero deve essere sempre dispari. per poter fare la croce. conta anche la explosion del centro

    /**
     * Raggio dell'esplosione delle bombe all'inizio
     */
    int explosionRangeIniziale = 3; //Questo numero deve essere sempre dispari

    /**
     * Restituisce la lista di tutte le bombe presenti nel gioco.
     *
     * @return Lista di bombe.
     */
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Restituisce la lista di tutte le bombe attualmente attive nel gioco.
     *
     * @return Lista di bombe attive.
     */
    public ArrayList<Bomb> getActiveBombs() {
        return Bomb.getAllBombs();
    }

    /**
     * Restituisce la lista di tutte le hitbox delle esplosioni delle bombe attualmente attive nel gioco.
     *
     * @return Lista di hitbox delle esplosioni delle bombe attive.
     */
    public ArrayList<Rectangle> getExplosionBombsHitbox() {

        ArrayList<Rectangle> ExplosionHitbox = new ArrayList<>();

        var BombeEsplose = Bomb.getAllBombs().stream().
                filter(bomba -> bomba.explodes == true) //where bomb explodes= true
                .collect(Collectors.toList()); //get lista


        //ForEach per aggiungere tutte le hitbox delle bombe esplose
        for (Bomb bomba : BombeEsplose) {
            ExplosionHitbox.addAll(bomba.getExplosionHitboxRec());
        }

        return ExplosionHitbox;
    }

    /**
     * Costruttore della classe Attaco.
     *
     * @param quantitaExtraBombe La quantità massima di bombe aggiuntive che il giocatore può possedere.
     */
    public Attaco(int quantitaExtraBombe) {
        this.quantitaExtraBombe = quantitaExtraBombe;
        this.quantitaExtraBombeIniziale = quantitaExtraBombe;
    }

    /**
     * Aumenta il raggio dell'esplosione di 1 unità.
     */
    public void AumentaExplosionRange() {
        this.explosionRange += 2;
    }

    /**
     * Aumenta la quantità di bombe aggiuntive di 1 unità.
     */
    public void AumentaQuantitaBombe() {
        this.quantitaExtraBombe += 1;
    }

    /**
     * Aggiunge una bomba alla lista delle bombe.
     *
     * @param x Coordinata x della bomba.
     * @param y Coordinata y della bomba.
     */
    public void Aggiungibomba(int x, int y) {
        this.bombs.add(new Bomb(x, y, explosionRange));
    }

    /**
     * Metodo chiamato quando il giocatore attacca (preme la barra spaziatrice).
     * Notifica agli osservatori di aggiungere una bomba se il numero di bombe attive è inferiore alla quantità massima consentita.
     */
    public void Attacare() {

        this.bombs = (ArrayList<Bomb>) this.bombs.stream().filter(bomb -> bomb.explodes == false).collect(Collectors.toList());
        //contare quante sono le bombe attive nell'array cioè non esplose.
        //se posso piazzare la bomba allora la creo, e la inserisco nell'array, altrimenti non fa nulla
        //attacare solo se ci sono meno bombe attive che la quantita massima

        contBombeAttive = this.bombs.size();
        if (contBombeAttive <= quantitaExtraBombe) {
            notifica();
        }
    }

    /**
     * Notifica agli osservatori di aggiungere una bomba.
     */
    private void notifica() {

        setChanged();

        notifyObservers("Bomb!");


    }

    /**
     * Resetta la quantità di bombe aggiuntive alla quantità iniziale.
     */
    public void resetQuantitaBombe() {

        quantitaExtraBombe = this.quantitaExtraBombeIniziale;
    }

    /**
     * Resetta il raggio dell'esplosione delle bombe al valore iniziale.
     */
    public void resetExplosionRange() {

        explosionRange = explosionRangeIniziale;
    }
}
