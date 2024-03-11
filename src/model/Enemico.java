package model;

import controller.AudioManager;
import controller.Direzione;
import controller.PathImages;
import controller.TileManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Rappresenta un nemico nel gioco, derivato dalla classe astratta Personaggio.
 * Gestisce il movimento automatico e la possibile eliminazione dell'enemico.
 *
 * @author Gabriel Guerra
 */
public class Enemico extends Personaggio {


    /**
     * Flag che indica se il movimento dell'enemico è attivo.
     */
    private Boolean movimentoAttivo = true;

    /**
     * Ultima direzione in cui si è mosso l'enemico.
     */
    private Direzione ultimaDirezione;

    /**
     * Punteggio assegnato all'enemico quando viene eliminato.
     */
    public int puntiEnemico;

    /**
     * Crea un nuovo enemico con posizione iniziale, velocità, dimensioni, direzione iniziale e gestione dei tile specificati.
     *
     * @param posX               La coordinata X iniziale dell'enemico.
     * @param posY               La coordinata Y iniziale dell'enemico.
     * @param velocita           La velocità di movimento dell'enemico.
     * @param width              Larghezza dell'enemico.
     * @param height             Altezza dell'enemico.
     * @param direzioneIniziale  La direzione iniziale in cui si muove l'enemico.
     * @param tileManager        Il gestore dei tile per il controllo del movimento.
     */
    public Enemico(int posX, int posY, int velocita, int width, int height, Direzione direzioneIniziale, TileManager tileManager) {
        super(posX, posY, velocita, width, height);

        this.ultimaDirezione =direzioneIniziale;
        super.movimento.posizione.direzione = direzioneIniziale;

        super.movimento.setTileM(tileManager);

        //Set degli sprites
        var pathImages = new PathImages();

        var pathSource = "src/view/res/enemico/";

        pathImages.down1 = pathSource + "down1.png";
        pathImages.down2 = pathSource + "down2.png";
        pathImages.down3 = pathSource + "down3.png";
        pathImages.down4 = pathSource + "down1.png";
        pathImages.downidle = pathSource + "downidle.png";

        pathImages.up1 = pathSource + "up1.png";
        pathImages.up2 = pathSource + "up2.png";
        pathImages.up3 = pathSource + "up3.png";
        pathImages.up4 = pathSource + "up1.png";
        pathImages.upidle = pathSource + "upidle.png";

        pathImages.right1 = pathSource + "right1.png";
        pathImages.right2 = pathSource + "right2.png";
        pathImages.right3 = pathSource + "right3.png";
        pathImages.right4 = pathSource + "right1.png";
        pathImages.rightidle = pathSource + "rightidle.png";

        pathImages.left1 = pathSource + "left1.png";
        pathImages.left2 = pathSource + "left2.png";
        pathImages.left3 = pathSource + "left3.png";
        pathImages.left4 = pathSource + "left1.png";
        pathImages.leftidle = pathSource + "leftidle.png";

        super.movimento.posizione.pathImages = pathImages;


        //Default image
        this.movimento.posizione.ImageAttuale = this.movimento.posizione.pathImages.downidle;


        // Creazione e avvio di un servizio executor programmato per il movimento continuo
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::autoMovimento, 0, 700, TimeUnit.MILLISECONDS);


        // allEnemies.add(this);

        this.ultimaDirezione = direzioneIniziale;

        this.puntiEnemico=100;

    }

    /**
     * Disattiva il movimento dell'enemico e restituisce il punteggio ottenuto eliminando l'enemico.
     *
     * @return Il punteggio ottenuto eliminando l'enemico.
     */
    public int eliminaEnemico() {

        //TODO Se c'è tempo, mettere animazione di morte dell'enemico
        // super.movimento.posizione.ImageAttuale = "src/view/res/enemico/dead1.png";
        movimentoAttivo = false;

        return puntiEnemico;
    }

    /**
     * TODO
     * Aggiungere un metodo per spostarsi in X e Y secondo un patron
     */

//    public void autoMovimento() {
//        if (movimentoAttivo) {
//            // Continua con l'ultima direzione
//            switch (ultimaDirezione) {
//                case UP -> super.movimento.goUp(false);
//                case DOWN -> super.movimento.goDown(false);
//                case RIGHT -> super.movimento.goRight(false);
//                case LEFT -> super.movimento.goLeft(false);
//            }
//
//            // Patrone di movimento ciclico
//            Direzione[] direzioniCicliche = {Direzione.UP, Direzione.RIGHT, Direzione.DOWN, Direzione.LEFT};
//
//            for (Direzione direzione : direzioniCicliche) {
//                if (!super.movimento.tileM.isTileBlocked(super.movimento.posizione, super.movimento.velocita)) {
//                    ultimaDirezione = direzione;
//                    break;
//                } else {
//                    direzioniCicliche = ruotaArray(direzioniCicliche);
//                }
//            }
//        }
//    }
//
//    private Direzione[] ruotaArray(Direzione[] array) {
//        Direzione temp = array[0];
//        System.arraycopy(array, 1, array, 0, array.length - 1);
//        array[array.length - 1] = temp;
//        return array;
//    }

    /**
     * Metodo che gestisce il movimento automatico dell'enemico.
     */
    public void autoMovimento() {

        if (movimentoAttivo) {

            //Continuare con l'ultima direzione
            switch (ultimaDirezione) {
                case UP -> super.movimento.goUp(false);
                case DOWN -> super.movimento.goDown(false);
                case RIGHT -> super.movimento.goRight(false);
                case LEFT -> super.movimento.goLeft(false);
            }

            //TODO finere patrone di movimento
            //Patrone di movimento
            if (super.movimento.tileM.isTileBlocked(super.movimento.posizione, super.movimento.velocita,super.movimento.noClip)) {

                //se trova obstaculo down allora va up
                if (ultimaDirezione == Direzione.DOWN) {
                    ultimaDirezione = Direzione.UP;
                }
                //se trova obstaculo up allora va down
                else if (ultimaDirezione == Direzione.UP) {
                    ultimaDirezione = Direzione.DOWN;
                }
                //se trova obstaculo right allora va left
                else if (ultimaDirezione == Direzione.RIGHT) {
                    ultimaDirezione = Direzione.LEFT;
                }
                //se trova obstaculo right allora va left
                else if (ultimaDirezione == Direzione.LEFT) {
                    ultimaDirezione = Direzione.RIGHT;
                }
            }



        }

    }
}
