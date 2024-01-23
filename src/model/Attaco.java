package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;


/**
 * Classe che notifica all'observer che il personnaggio ha fatto un attaco
 */
public class Attaco extends Observable {


    ArrayList<Bomb> bombs = new ArrayList<>();

    int quantitaMaxBombe;

    int contBombeAttive;

    //Fare questo attributo "propio" del momento e non dipendente della classe "bomb"
    //Cosi ogni bomba nel corso del tempo, avra il suo explosion range del momento della sua creazione
    int explosionRange;

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<Bomb> getActiveBombs() {
        return Bomb.getAllBombs();
    }

    public Attaco(int quantitaMaxBombe) {

        this.quantitaMaxBombe = quantitaMaxBombe;

    }

    public void Aggiungibomba(int x, int y ){
        this.bombs.add(new Bomb(x,y,explosionRange));
    }


    /**
     * Metodo che viene chiamato dall'input SpaceBar del giocatore
     */
    public void Attacare() {

//
//        //remove di esplodes=true
         this.bombs= (ArrayList<Bomb>) this.bombs.stream().filter(bomb->bomb.explodes==false).collect(Collectors.toList());
//
//        //contare quante sono le bombe attive nell'array cioè non esplose.
//
//        //se posso piazzare la bomba allora la creo, e la inserisco nell'array, altrimenti non fa nulla
//
        //attacare solo se ci sono meno bombe attive che la quantita massima

        contBombeAttive=  this.bombs.size();
        if (contBombeAttive<=quantitaMaxBombe){
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

}
