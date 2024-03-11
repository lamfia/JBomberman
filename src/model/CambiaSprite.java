package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Gestisce il cambio automatico delle immagini per un oggetto specificato.
 * Utilizza un timer per cambiare l'immagine ogni tot secondi.
 *
 * @author Gabriel Guerra
 */
public class CambiaSprite {

    /**
     * Elenco dei percorsi delle immagini da utilizzare per il cambio.
     */
    private ArrayList<String> pathImages;

    /**
     * Intervallo di tempo in secondi per il cambio dell'immagine.
     */
    private int secondiCambioSprite;

    /**
     * Indice corrente dell'immagine nell'elenco.
     */
    private int currentIndex;

    /**
     * Istanza dell'oggetto per cui gestire il cambio delle immagini.
     */
    private Oggetto OggettoInstance;

    /**
     * Crea una nuova istanza di CambiaSprite associata all'oggetto specificato.
     *
     * @param OggettoInstance L'oggetto per cui gestire il cambio delle immagini.
     */
    public CambiaSprite(Oggetto OggettoInstance) {

        this.OggettoInstance = OggettoInstance;
        this.pathImages = OggettoInstance.pathSprites;
        this.secondiCambioSprite = OggettoInstance.secondiCambioSprite;
        this.currentIndex = 0;


        // Avvia il timer per cambiare l'immagine ogni tot millisecondi
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new ImageChangeTask(), 0, secondiCambioSprite * 1000);
    }

    /**
     * Classe interna che rappresenta l'attività programmata per il cambio delle immagini.
     *
     * @author Gabriel Guerra
     */
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
