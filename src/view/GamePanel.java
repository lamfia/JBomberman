package view;


import controller.AudioManager;
import controller.CollisionChecker;
import controller.Posizione;
import controller.TileManager;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements Observer, Runnable {
    private TileManager tileM;
    private String TempoGioco = "00:00:00";
    //private Image image1;
    private Image map;
    private Graphics2D externalGraphics;

    //TODO aggiungere una lista di personaggi (usare classe Personaggio)
//    private int posGiocatoreX;
//    private int posGiocatoreY;
//    private int GiocatoreWidth;
//    private int GiocatoreHeight;

    private ArrayList<Personaggio> personaggi = new ArrayList<>();


    /**
     * Dimensioni del gamePanel
     **/
    private int dimensionWidth;
    private int dimensionHeight;

    Thread gameThread;

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //private int FPS=30;
    @Override
    public void run() {

//        double drawInterval=1000000000/FPS;
//        double nextDrawTime= System.nanoTime()+drawInterval;
//
//        while(gameThread !=null){
//
//
//            repaint();
//
//
//            try {
//                double remainingTime= nextDrawTime-System.nanoTime();
//
//                remainingTime=remainingTime/1000000;
//
//                if(remainingTime<0){
//                    remainingTime=0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime+=drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

    }

    //Constructor
    public GamePanel(Color colorBackGround, int dimensionWidth, int dimensionHeight) {

        this.dimensionHeight = dimensionHeight;
        this.dimensionWidth = dimensionWidth;

    }

    public void repaintTask() {
        repaint();
    }

    public void addGiocatore(Giocatore giocatore) throws IOException {

//        this.posGiocatoreX = giocatore.movimento.posizione.pos_x;
//        this.posGiocatoreY = giocatore.movimento.posizione.pos_y;
//
//        this.GiocatoreWidth = giocatore.movimento.posizione.width;
//        this.GiocatoreHeight = giocatore.movimento.posizione.height;
//
//        this.image1 = ImageIO.read(new File(giocatore.movimento.posizione.pathImages.downidle));
//
//        var giocatoremodel= new Giocatore(
//                giocatore.movimento.posizione.pos_x,
//                giocatore.movimento.posizione.pos_y,100,2,2,2);

        //Default image
        giocatore.movimento.posizione.ImageAttuale = giocatore.movimento.posizione.pathImages.downidle;

        this.personaggi.add(giocatore);

        repaint();

    }

    public void addEnemico(Enemico enemico) throws IOException {

        enemico.movimento.posizione.ImageAttuale = enemico.movimento.posizione.pathImages.downidle;
        this.personaggi.add(enemico);
        repaint();

    }

    private Graphics grafica;

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        var g2 = (Graphics2D) g;

        externalGraphics = g2;

        try {
            map = ImageIO.read(new File("src/view/maps/Pirate/pirata.png"));
            g2.drawImage(map, 0, 0, dimensionWidth, dimensionHeight, this);

            //Aggiorna i tiles
            drawTiles();

            //Draw dei personnaggi
            for (Personaggio personaggio : personaggi) {

                g2.drawImage(
                        // ImageIO.read(new File(giocatore.movimento.posizione.pathImages.downidle),
                        ImageIO.read(new File(personaggio.movimento.posizione.ImageAttuale)),
                        personaggio.movimento.posizione.pos_x,
                        personaggio.movimento.posizione.pos_y,
                        personaggio.movimento.posizione.width,
                        personaggio.movimento.posizione.height, null);


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Timer del gioco
        g2.setColor(Color.white);
        g2.drawString(TempoGioco, 10, 20);


    }

    public void drawTiles() {

        tileM.draw(this.externalGraphics);
    }

    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }


    @Override
    public void update(Observable observable, Object arg) {

        //player
        var giocatore = personaggi.get(0);


        //Tempo! TODO mettere questo valore nella classe "partita"
        if (observable instanceof Time) {
            TempoGioco = (String) arg;
        }

        //Partita info
        if (observable instanceof Partita) {

            var partita = (Partita) arg;

            if (partita.statoPartita == StatoPartita.GameOver) {
                System.out.println("Game over!");
            }

        }

        //Movimento!
        if (observable instanceof Movimento) {

            var movimento = (Posizione) arg;

            giocatore.movimento.posizione.pos_x = movimento.pos_x;
            giocatore.movimento.posizione.pos_y = movimento.pos_y;
            giocatore.movimento.posizione.ImageAttuale = movimento.ImageAttuale;

        }

        //Attack!
        if (observable instanceof Attaco) {
            System.out.println(arg.toString());
            tileM.AggiungiBomba(giocatore.movimento.posizione.pos_x, giocatore.movimento.posizione.pos_y + 5);
        }

        repaint();

    }


//    public void playMusic(int index){
//        AudioManager.getInstance().play();
//
//    }


}
