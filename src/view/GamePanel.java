package view;


import controller.Posizione;
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

public class GamePanel extends JPanel implements Observer,Runnable {

    private String TempoGioco = "00:00:00";
    private Image image1;

    private Image map;


    private int posGiocatoreX;
    private int posGiocatoreY;
    private int GiocatoreVelocita=0;

    private String pathImage;

    private BufferedImage bufferedImage;


    Thread gameThread;

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    //private int FPS=30;
    @Override
    public void run(){

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

    //Questo è il construttore
    public GamePanel( Color colorBackGround)  {





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

        this.posGiocatoreX= personaggio.movimento.posizione.pos_x;
        this.posGiocatoreY= personaggio.movimento.posizione.pos_y;

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

        Graphics2D g2 = (Graphics2D) g;

        try {
            map = ImageIO.read(new File("src/view/maps/pirata.png"));
            g2.drawImage(map,0,0,800,600,this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(image1, posGiocatoreX, posGiocatoreY, 40, 40, this);

        g2.setColor(Color.white);

        //Timer del gioco
        g2.drawString(TempoGioco, 10, 20);

    }

    @Override
    public void update(Observable o, Object arg ) {

        if (o instanceof Time){
            TempoGioco = (String) arg;
        }

        if ( o instanceof Movimento) {

            var movimento= (Posizione) arg ;

            posGiocatoreX= movimento.pos_x;
            posGiocatoreY= movimento.pos_y;

            try {
                this.image1 = ImageIO.read(new File( movimento.ImageAttuale));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
            repaint();

    }



}
