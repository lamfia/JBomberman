package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;


/**
 * Classe che notifica all'observer che il personnaggio ha fatto un attaco
 */
public class Attaco extends Observable {


    ArrayList<Bomb> bombs = new ArrayList<>();

    int quantitaExtraBombe;
    int quantitaExtraBombeIniziale;

    int contBombeAttive;

    //Fare questo attributo "propio" del momento e non dipendente della classe "bomb"
    //Cosi ogni bomba nel corso del tempo, avra il suo explosion range del momento della sua creazione
    int explosionRange = 3; //Questo numero deve essere sempre dispari. per poter fare la croce. conta anche la explosion del centro
    int explosionRangeIniziale = 3;

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<Bomb> getActiveBombs() {
        return Bomb.getAllBombs();
    }

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

    public Attaco(int quantitaExtraBombe) {

        this.quantitaExtraBombe = quantitaExtraBombe;
        this.quantitaExtraBombeIniziale = quantitaExtraBombe;


    }

    /**
     * Aumenta la explosion range in 1 quantita
     */
    public void AumentaExplosionRange() {
        this.explosionRange += 2;
    }

    public void AumentaQuantitaBombe() {
        this.quantitaExtraBombe += 1;
    }

    public void Aggiungibomba(int x, int y) {
        this.bombs.add(new Bomb(x, y, explosionRange));
    }


    /**
     * Metodo che viene chiamato dall'input SpaceBar del giocatore
     */
    public void Attacare() {

//
//        //remove di esplodes=true
        this.bombs = (ArrayList<Bomb>) this.bombs.stream().filter(bomb -> bomb.explodes == false).collect(Collectors.toList());
//
//        //contare quante sono le bombe attive nell'array cioè non esplose.
//
//        //se posso piazzare la bomba allora la creo, e la inserisco nell'array, altrimenti non fa nulla
//
        //attacare solo se ci sono meno bombe attive che la quantita massima

        contBombeAttive = this.bombs.size();
        if (contBombeAttive <= quantitaExtraBombe) {
            notifica();
        }

        //  notifica();
    }

    /**
     * Notifica al gamepanel di aggiungere una bomba
     * (Ritorna al TileManager con il metodo "AggiungiBomba")
     */
    private void notifica() {

        setChanged();

        notifyObservers("Bomb!");


    }


    public void resetQuantitaBombe() {

        quantitaExtraBombe = this.quantitaExtraBombeIniziale;
    }

    public void resetExplosionRange() {

        explosionRange = explosionRangeIniziale;
    }
}
