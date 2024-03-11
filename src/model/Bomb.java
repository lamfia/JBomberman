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

/**
 * Rappresenta una bomba nel gioco
 *
 * @author Gabriel Guerra
 */
public class Bomb extends Oggetto {

    /**
     * Raggio di esplosione della bomba.
     */
    public int explosionRange;

    /**
     * Flag che indica se la bomba è esplosa.
     */
    public boolean explodes;

    /**
     * Hitbox dell'esplosione sull'asse x.
     */
    public Rectangle explosion_x;

    /**
     * Hitbox dell'esplosione sull'asse y.
     */
    public Rectangle explosion_y;

    /**
     * Sprite dell'esplosione sull'asse x.
     */
    public BufferedImage explosion_x_sprite;

    /**
     * Sprite dell'esplosione sull'asse y.
     */
    public BufferedImage explosion_y_sprite;

    /**
     * Lista di tutte le bombe presenti nel gioco.
     */
    private static ArrayList<Bomb> allBombs = new ArrayList<>();


    /**
     * Crea una nuova istanza di Bomba con la posizione specificata e il raggio di esplosione.
     *
     * @param x             La coordinata x della bomba.
     * @param y             La coordinata y della bomba.
     * @param explosionRange Il raggio di esplosione della bomba.
     */
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


    /**
     * Restituisce la lista di tutte le bombe attive nel gioco.
     *
     * @return La lista di tutte le bombe attive.
     */
    public static ArrayList<Bomb> getAllBombs() {
        return allBombs;
    }

    /**
     * Restituisce la lista di hitbox dell'esplosione delle bombe.
     *
     * @return La lista di hitbox dell'esplosione delle bombe.
     */
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
    /**
     * Rappresenta un'attività programmata per gestire l'esplosione di una bomba.
     *
     * @author Gabriel Guerra
     */
    private class ExplosionTask extends TimerTask {

        /**
         * Istanza della bomba associata a questa attività di esplosione.
         */
        private Bomb bombInstance;


        /**
         * Crea una nuova istanza di ExplosionTask associata alla bomba specificata.
         *
         * @param bombInstance La bomba associata a questa attività di esplosione.
         */
        public ExplosionTask(Bomb bombInstance) {
            this.bombInstance = bombInstance;
        }



        /**
         * Esegui l'attività di esplosione.
         */
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


    /**
     * Rappresenta un'attività programmata per rimuovere una bomba dalla lista globale dopo un certo periodo.
     *
     * @author Gabriel Guerra
     */
    private class removeBombTask extends TimerTask {

        /**
         * Istanza della bomba da rimuovere dalla lista globale.
         */
        private Bomb bombInstance;

        /**
         * Crea una nuova istanza di removeBombTask associata alla bomba specificata.
         *
         * @param bombInstance La bomba da rimuovere dalla lista globale.
         */
        public removeBombTask(Bomb bombInstance) {
            this.bombInstance = bombInstance;
        }

        /**
         * Esegui l'attività di rimozione della bomba dalla lista globale.
         */
        @Override
        public void run() {
            allBombs.remove(this.bombInstance);
        }

    }

}
