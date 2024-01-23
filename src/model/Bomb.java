package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Oggetto {
    public int x;
    public int y;
    public int width = 36;
    public int height = 36;
    public int explosionRange;
    public boolean explodes;


    //Questa è una lista di tutte le bombe che sono presenti.
    private static ArrayList<Bomb> allBombs = new ArrayList<>();

    public Bomb(int x, int y, int explosionRange) {
        this.x = x;
        this.y = y;
        this.explosionRange = explosionRange;
        var pathSource = "src/view/res/miscellaneous/";

        ArrayList<String> pathImages = new ArrayList<>();
        pathImages.add(pathSource + "Bomb1.png");
        pathImages.add(pathSource + "Bomb2.png");
        pathImages.add(pathSource + "Bomb3.png");
        //pathImages.add(pathSource + "Bomb4.png");
        super.pathSprites = pathImages;
        super.secondiCambioSprite = 1;
        super.CambiaSprite = new CambiaSprite(this);

        this.explodes = false;

        allBombs.add(this);

        // Pianifica un'attività che imposta explodes a true dopo X secondi
        Timer timer = new Timer(true);
        timer.schedule(new ExplosionTask(this), 3000); // 3 secondi
    }


    public static ArrayList<Bomb> getAllBombs() {
        return allBombs;
    }

    private class ExplosionTask extends TimerTask {

        private Bomb bombInstance;

        public ExplosionTask(Bomb bombInstance) {
            this.bombInstance = bombInstance;
        }

        @Override
        public void run() {
            // Quando l'attività viene eseguita dopo X secondi, imposta explodes a true
            bombInstance.explodes = true;

            //TODO mettere qui lo sprite della explosion?

            System.out.println("Bomb exploded!");

            //Mettere qui logica di remove della bomba di "allbombs" e cambio sprite di esplosione

            allBombs.remove(this.bombInstance);
        }
    }


}
