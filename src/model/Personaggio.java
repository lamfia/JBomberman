package model;

import controller.PathImages;

import java.awt.*;
import java.util.Observable;

/**
 * Classe abstratta per definere tutti i metodi e attributi dei
 * personaggi del gioco
 * @author Gabriel Guerra
 */
public abstract class Personaggio  {

    /**
     * Azione per il movimento del personnaggio della partita
     */
    public Movimento movimento ;

    /**
     * Costruttore della classe Personaggio.
     * @param posX La coordinata x iniziale del personaggio.
     * @param posY La coordinata y iniziale del personaggio.
     * @param velocita La velocità del personaggio.
     * @param width La larghezza del personaggio.
     * @param height L'altezza del personaggio.
     */
    public Personaggio(int posX, int posY,int velocita, int width, int height){

       this.movimento = new Movimento(posX, posY,velocita, width,height);

    }


}
