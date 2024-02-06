package model;

import controller.AudioManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Oggetto {

    public int explosionRange;
    public boolean explodes;
    public Rectangle explosion_x;
    public Rectangle explosion_y;

    public BufferedImage explosion_x_sprite;
    public BufferedImage explosion_y_sprite;

    //Questa è una lista di tutte le bombe che sono presenti in tutto il gioco.
    private static ArrayList<Bomb> allBombs = new ArrayList<>();

    public Bomb(int x, int y, int explosionRange) {
        this.x = x;
        this.y = y;
        this.explosionRange = explosionRange;
        super.width = 36;
        super.height = 36;

        super.hitbox = new Hitbox(x + 5, y + 12, width - 15, height - 15);

        var pathSource = super.pathSource + "miscellaneous/";

        ArrayList<String> pathImages = new ArrayList<>();
        pathImages.add(pathSource + "Bomb1.png");
        pathImages.add(pathSource + "Bomb2.png");
        pathImages.add(pathSource + "Bomb3.png");
        //pathImages.add(pathSource + "Bomb4.png");
        super.pathSprites = pathImages;
        super.secondiCambioSprite = 1;
        super.CambiaSprite = new CambiaSprite(this);

        this.explodes = false;


        //sprite delle esplosions
        try {
            this.explosion_x_sprite = ImageIO.read(new File(pathSource + "ExplosionX.png"));

            this.explosion_y_sprite = ImageIO.read(new File(pathSource + "ExplosionY.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allBombs.add(this);

        // Pianifica un'attività che imposta explodes a true dopo X secondi
        Timer timer = new Timer(true);
        timer.schedule(new ExplosionTask(this), 3000); // 3 secondi
    }


    public static ArrayList<Bomb> getAllBombs() {
        return allBombs;
    }

    public ArrayList<Rectangle> getExplosionHitboxRec() {

        if (explosion_x != null && explosion_y != null) {
            ArrayList<Rectangle> ExplosionHitboxRec = new ArrayList<>();
            ExplosionHitboxRec.add(explosion_x);
            ExplosionHitboxRec.add(explosion_y);
            return ExplosionHitboxRec;
        } else {
            return null;
        }

    }

    private class ExplosionTask extends TimerTask {

        private Bomb bombInstance;

        public ExplosionTask(Bomb bombInstance) {
            this.bombInstance = bombInstance;
        }

        @Override
        public void run() {
            // Quando l'attività viene eseguita dopo X secondi, imposta explodes a true

            AudioManager.getInstance().playSE(1);

            bombInstance.explodes = true;

            //TODO attenzione, in caso di explosion, solo fare range di explosion
            // negli spazi di "walking tiles" cosi non vanno oltre ai muri.
            // Importante? dopo vediamo

            var hitboxrec_old = bombInstance.hitbox.hitboxRec;

            int estremiEsplosione = (explosionRange - 1) / 2;

            bombInstance.explosion_y =
                    new Rectangle(hitboxrec_old.x, hitboxrec_old.y - (hitboxrec_old.height * estremiEsplosione), hitboxrec_old.width,
                            hitboxrec_old.height * explosionRange);

            bombInstance.explosion_x =
                    new Rectangle(hitboxrec_old.x - (hitboxrec_old.width * estremiEsplosione),
                            hitboxrec_old.y, hitboxrec_old.width * explosionRange, hitboxrec_old.height);


            System.out.println("Bomb exploded!");


            //Questo perchè devo cancellare l'immagine della bomba che sta dietro al range dell'esplosione
            bombInstance.currentImage = null;


            //Remove della bomba dopo 1 secondo della explosione
            Timer timer = new Timer(true);
            timer.schedule(new removeBombTask(bombInstance), 1000); // 1 secondi

        }
    }

    private class removeBombTask extends TimerTask {

        private Bomb bombInstance;

        public removeBombTask(Bomb bombInstance) {
            this.bombInstance = bombInstance;
        }

        @Override
        public void run() {
            allBombs.remove(this.bombInstance);
        }

    }

}
