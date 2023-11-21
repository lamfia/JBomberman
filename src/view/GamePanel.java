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

        setBackground(colorBackGround);
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

        g2.drawImage(image1, posGiocatoreX, posGiocatoreY, 40, 40, this);

        g2.setColor(Color.white);

        //Timer del gioco
        g2.drawString(TempoGioco, 10, 20);

        //g2.fillRect(150,100,48,48);

    }
//    double drawInterval=1000000000/FPS;
//    double nextDrawTime= System.nanoTime()+drawInterval;

    @Override
    public void update(Observable o, Object arg ) {

        if (o instanceof Time){
            TempoGioco = (String) arg;
        }

        if ( o instanceof Movimento) {

            var movimento= (Posizione) arg ;

            posGiocatoreX= movimento.pos_x; //X
            posGiocatoreY= movimento.pos_y; //Y

            try {
                this.image1 = ImageIO.read(new File( movimento.ImageAttuale));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //this.image1 = ImageIO.read(new File(pos.pathImages.down1));
        }


            repaint();

//            try {
//                double remainingTime= nextDrawTime-System.nanoTime();
//
//                remainingTime=remainingTime/1000000;
//
//                if(remainingTime<0){
//                    remainingTime=0;
//                }
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime+=drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }


       // repaint();
    }

//    public void startGameThread(){
//            gameThread= new Thread(this);
//
//            gameThread.start();
//
//    }
//    @Override
//    public void run() {
//
//        while  (gameThread!=null){
//
//            // 1 Update information of character as position
//           // update();
//
//            // 2 Draw screen
//           // repaint();
//            //Se questo update e draw si fanno 3o volte al secondo vuol dire che va a 30fps
//
//        }
//
//    }


}
