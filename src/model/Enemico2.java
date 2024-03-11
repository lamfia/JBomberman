package model;

import controller.Direzione;
import controller.PathImages;
import controller.TileManager;

public class Enemico2 extends Enemico {


    /**
     * Rappresenta una variante avanzata di nemico nel gioco, derivata dalla classe Enemico.
     * Aggiunge un disegno distintivo, più punti e la capacità di attraversare i muri distruttibili.
     * @author Gabriel Guerra
     */
    public Enemico2(int posX, int posY, int velocita, int width, int height, Direzione direzioneIniziale, TileManager tileManager) {
        super(posX, posY, velocita, width, height, direzioneIniziale, tileManager);


        var pathSource = "src/view/res/enemico2/";
        //Set degli sprites
        var pathImages = new PathImages();

        pathImages.down1 = pathSource + "Enemico2_1.png";
        pathImages.down2 = pathSource + "Enemico2_2.png";
        pathImages.down3 = pathSource + "Enemico2_3.png";
        pathImages.down4 = pathSource + "Enemico2_4.png";
        pathImages.downidle = pathSource + "Enemico2_1.png";

        pathImages.up1 = pathSource + "Enemico2_1.png";
        pathImages.up2 = pathSource + "Enemico2_2.png";
        pathImages.up3 = pathSource + "Enemico2_3.png";
        pathImages.up4 = pathSource + "Enemico2_4.png";
        pathImages.upidle = pathSource + "Enemico2_1.png";

        pathImages.right1 = pathSource + "Enemico2_1.png";
        pathImages.right2 = pathSource + "Enemico2_2.png";
        pathImages.right3 = pathSource + "Enemico2_3.png";
        pathImages.right4 = pathSource + "Enemico2_4.png";
        pathImages.rightidle = pathSource + "Enemico2_1.png";

        pathImages.left1 = pathSource + "Enemico2_1.png";
        pathImages.left2 = pathSource + "Enemico2_2.png";
        pathImages.left3 = pathSource + "Enemico2_3.png";
        pathImages.left4 = pathSource + "Enemico2_4.png";
        pathImages.leftidle = pathSource + "Enemico2_1.png";

        super.movimento.posizione.pathImages = pathImages;

       //Questo permette al personaggio di attraversare i destructibiles tiles
        super.movimento.noClip = true;

        super.puntiEnemico=200;

        //Default image
        this.movimento.posizione.ImageAttuale = this.movimento.posizione.pathImages.downidle;

    }


}
