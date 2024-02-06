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

   // public Hitbox hitbox;

    /*
    Descrive la imaggine con un accesso alla data della stessa
     */


    public Personaggio(int posX, int posY, int Salute,int velocita, int width, int height){

        //hitbox (rectangle area)
       // solidArea = new Rectangle(posX,posY,width,height);
       this.movimento = new Movimento(posX, posY,velocita, width,height);

       this.Salute= Salute;

       // this.hitbox= new Hitbox(hitboxRec);



    }

//    public void updateHitbox(int x, int y, int width, int height){
//        this.hitbox.hitboxRec= new Rectangle(x,y,width,height);
//    }
//    public void updateHitbox(Rectangle hitboxRec){
//        this.hitbox.hitboxRec= hitboxRec;
//    }

}
