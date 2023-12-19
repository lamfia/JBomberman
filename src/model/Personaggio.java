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

    public  int Salute;

    public Rectangle solidArea;

    public boolean collisionOn=false;

    /*
    Descrive la imaggine con un accesso alla data della stessa
     */


    public Personaggio(int posX, int posY, int Salute,int velocita){

       this.movimento = new Movimento(posX, posY,velocita);

       this.Salute= Salute;

    }




}
