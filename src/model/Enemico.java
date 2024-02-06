package model;

import controller.AudioManager;
import controller.PathImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Enemico extends Personaggio {

    public BufferedImage currentImage;

    ArrayList<String> pathSprites;

    public Hitbox hitbox;

    public String pathSource = "src/view/res/";

    public void setCurrentImage(String path) {

        try {
            this.currentImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Enemico(int posX, int posY, int Salute, int velocita, int width, int height) {
        super(posX, posY, Salute, velocita, width,height);

        //Set degli sprites
        var pathImages = new PathImages();

        var pathSource = "src/view/res/enemico/";

        pathImages.down1 = pathSource + "down1.png";
        pathImages.down2 = pathSource + "down2.png";
        pathImages.down3 = pathSource + "down3.png";
        pathImages.downidle = pathSource + "downidle.png";

        pathImages.up1 = pathSource + "up1.png";
        pathImages.up2 = pathSource + "up2.png";
        pathImages.up3 = pathSource + "up3.png";
        pathImages.upidle = pathSource + "upidle.png";

        pathImages.right1 = pathSource + "right1.png";
        pathImages.right2 = pathSource + "right2.png";
        pathImages.right3 = pathSource + "right3.png";
        pathImages.rightidle = pathSource + "rightidle.png";

        pathImages.left1 = pathSource + "left1.png";
        pathImages.left2 = pathSource + "left2.png";
        pathImages.left3 = pathSource + "left3.png";
        pathImages.leftidle = pathSource + "leftidle.png";

        super.movimento.posizione.pathImages = pathImages;


        Timer timer = new Timer(true);
        timer.schedule(new Enemico.AutoMovimentoTask(this), 1000); // 1 secondi
    }

    private class AutoMovimentoTask extends TimerTask {

        private Enemico EnemicoInstance;

        public AutoMovimentoTask(Enemico EnemicoInstance) {
            this.EnemicoInstance = EnemicoInstance;
        }

        @Override
        public void run() {

            //EnemicoInstance.movimento.goDown(true);
            // Quando l'attività viene eseguita dopo X secondi, imposta explodes a true

//            AudioManager.getInstance().playSE(1);
//
//            bombInstance.explodes = true;
//
//            //TODO attenzione, in caso di explosion, solo fare range di explosion
//            // negli spazi di "walking tiles" cosi non vanno oltre ai muri.
//            // Importante? dopo vediamo
//
//            var hitboxrec_old = bombInstance.hitbox.hitboxRec;
//
//            int estremiEsplosione = (explosionRange - 1) / 2;
//
//            bombInstance.explosion_y =
//                    new Rectangle(hitboxrec_old.x, hitboxrec_old.y - (hitboxrec_old.height * estremiEsplosione), hitboxrec_old.width,
//                            hitboxrec_old.height * explosionRange);
//
//            bombInstance.explosion_x =
//                    new Rectangle(hitboxrec_old.x - (hitboxrec_old.width * estremiEsplosione),
//                            hitboxrec_old.y, hitboxrec_old.width * explosionRange, hitboxrec_old.height);
//
//
//            System.out.println("Bomb exploded!");
//
//
//            //Questo perchè devo cancellare l'immagine della bomba che sta dietro al range dell'esplosione
//            bombInstance.currentImage = null;
//
//
//            //Remove della bomba dopo 1 secondo della explosione
//            Timer timer = new Timer(true);
//            timer.schedule(new Bomb.removeBombTask(bombInstance), 1000); // 1 secondi

        }
    }

    /**
     * TODO
     * Aggiungere un metodo per spostarsi in X e Y secondo un patron
     */

    public void AutoMovimento() {


        super.movimento.goDown(true);



    }


}
