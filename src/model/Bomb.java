package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Oggetto {
    public int x;
    public int y;
    public int width=36;
    public int height=36;
    public int explosionRange;


    public Bomb(int x, int y, int explosionRange) {
        this.x = x;
        this.y = y;
        this.explosionRange = explosionRange;
        var pathSource = "src/view/res/miscellaneous/";

        ArrayList<String> pathImages= new ArrayList<>();
        pathImages.add(pathSource + "Bomb1.png");
        pathImages.add(pathSource + "Bomb2.png");
        pathImages.add(pathSource + "Bomb3.png");
        pathImages.add(pathSource + "Bomb4.png");
        super.pathSprites=pathImages;
        super.secondiCambioSprite=1;
        super.CambiaSprite = new CambiaSprite(this);

//        // Inizializza l'immagine corrente
//        // (assicurati di implementare il codice per caricare un'immagine appropriata)
//        this.currentImage = loadInitialImage();
//
//        // Avvia il timer per cambiare l'immagine ogni tot millisecondi
//        Timer timer = new Timer(true);
//        timer.scheduleAtFixedRate(new ImageChangeTask(this), 0, 2000); // Cambia ogni 2000 millisecondi (2 secondi)
    }

//    private BufferedImage loadInitialImage() {
//        // Implementa il codice per caricare l'immagine iniziale
//        // Restituisci l'immagine iniziale
//        return null;
//    }
//
//    private class ImageChangeTask extends TimerTask {
//        private Bomb bombInstance;
//
//        public ImageChangeTask(Bomb bombInstance) {
//            this.bombInstance = bombInstance;
//        }
//
//        @Override
//        public void run() {
//            // Implementa il codice per cambiare l'immagine
//            // (assicurati di implementare il codice per caricare un'immagine appropriata)
//            bombInstance.currentImage = loadNextImage();
//        }
//
//        private BufferedImage loadNextImage() {
//            // Implementa il codice per caricare la prossima immagine
//            // Restituisci l'immagine successiva
//            return null;
//        }
//    }
}
