package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CambiaSprite {
    private ArrayList<String> pathImages;
    private int secondiCambioSprite;
    private int currentIndex;
    private Oggetto OggettoInstance;

    public Object istanzaObject;

    public CambiaSprite(Oggetto OggettoInstance) {

        this.OggettoInstance = OggettoInstance;
        this.pathImages = OggettoInstance.pathSprites;
        this.secondiCambioSprite = OggettoInstance.secondiCambioSprite;
        this.currentIndex = 0;

        // Avvia il timer per cambiare l'immagine ogni tot millisecondi
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new ImageChangeTask(), 0, secondiCambioSprite * 1000);
    }

    private class ImageChangeTask extends TimerTask {
        @Override
        public void run() {
            // Cambia l'immagine ogni tot secondi
            String nextImagePath = getNextImagePath();
            OggettoInstance.setCurrentImage(nextImagePath);
        }

        private String getNextImagePath() {
            // Ottiene il percorso dell'immagine successiva
            String nextImagePath = pathImages.get(currentIndex);

            // Incrementa l'indice per ottenere l'immagine successiva alla prossima chiamata
            currentIndex = (currentIndex + 1) % pathImages.size();

            return nextImagePath;
        }
    }
}
