package model;


import controller.PathImages;

/**
 * E' il personaggio principale del gioco
 * il quale è controllato dal giocatore
 * @author Gabriel Guerra
 */
public class Giocatore extends  Personaggio{



    public Giocatore(int posX, int posY, int Salute, int velocita){
        super(posX,posY,Salute,velocita);


//         //Set degli sprites
         var pathImages= new PathImages();

         var pathSource="E:\\Projects\\JBomberman\\JBomberman\\src\\view\\res\\giocatore\\";

         pathImages.down1=pathSource+"down1.png";
         pathImages.down2=pathSource+"down2.png";
         pathImages.down3=pathSource+"down3.png";
         pathImages.down4=pathSource+"down4.png";
         pathImages.downidle=pathSource+"downidle.png";

         pathImages.up1=pathSource+"up1.png";
         pathImages.up2=pathSource+"up2.png";
         pathImages.up3=pathSource+"up3.png";
         pathImages.up4=pathSource+"up4.png";
        pathImages.upidle=pathSource+"upidle.png";

         pathImages.right1=pathSource+"right1.png";
         pathImages.right2=pathSource+"right2.png";
         pathImages.right3=pathSource+"right3.png";
         pathImages.right4=pathSource+"right4.png";
         pathImages.rightidle=pathSource+"rightidle.png";

         pathImages.left1=pathSource+"left1.png";
         pathImages.left2=pathSource+"left2.png";
         pathImages.left3=pathSource+"left3.png";
         pathImages.left4=pathSource+"left4.png";
         pathImages.leftidle=pathSource+"leftidle.png";

         super.movimento.posizione.pathImages=pathImages;
    }




}
