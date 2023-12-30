package view;


import controller.CollisionChecker;
import controller.Posizione;
import controller.TileManager;
import model.Movimento;
import model.Personaggio;
import model.Time;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer, Runnable {

    //Tiles
    final int originalTileSize = 20; //19x19 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    private TileManager tileM;
    private String TempoGioco = "00:00:00";
    private Image image1;
    private Image map;
    private Graphics2D externalGraphics;
    private int posGiocatoreX;
    private int posGiocatoreY;


    private int dimensionWidth;
    private int dimensionHeight;
    private int GiocatoreVelocita = 0;

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


//
//        try {
//
//            //image1 = ImageIO.read(new File(""));
//
//            //image1 = ImageIO.read(getClass().getResourceAsStream());
//
//        }catch (IOException ex){
//
//        }

    }

    public void addPersonaggio(Personaggio personaggio) throws IOException {

        this.posGiocatoreX = personaggio.movimento.posizione.pos_x;
        this.posGiocatoreY = personaggio.movimento.posizione.pos_y;

        this.image1 = ImageIO.read(new File(personaggio.movimento.posizione.pathImages.downidle));

        // .image1= personaggio.pathImages.down1;


//        var g = this.getGraphics();
//        //super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.white);
//        g2.drawString("HOLAAAAAAAAAAA", 100, 200);
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

            drawTiles();




        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TileSquare
        g.setColor(Color.BLUE);
        g2.fillRect(172 , 190 , 60, 60);

        //player
      //  g2.drawImage(image1, posGiocatoreX, posGiocatoreY, 40, 40, this);

        //player square
        g.setColor(Color.RED);
        g2.fillRect(posGiocatoreX , posGiocatoreY , 40, 40);



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
    public void update(Observable o, Object arg) {

        if (o instanceof Time) {
            TempoGioco = (String) arg;
        }

        if (o instanceof Movimento) {

            var movimento = (Posizione) arg;

            //Aggiorna posizione dentro alla view
            posGiocatoreX = movimento.pos_x;
            posGiocatoreY = movimento.pos_y;




            try {
                this.image1 = ImageIO.read(new File(movimento.ImageAttuale));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        repaint();

    }


}
