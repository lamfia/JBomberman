package view;


import model.Time;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {

    private String TempoGioco = "00:00:00";
    private Image image1;

    private int posGiocatoreX;
    private int posGiocatoreY;
    private int GiocatoreVelocita=0;

    Thread gameThread;

    //Questo è il construttore
    public GamePanel()  {
        setBackground(Color.gray);
        try {
            image1 = ImageIO.read(new File("bomberman.png"));
            //this.addKeyListener(new KeyHandler());

        }catch (IOException ex){
        }
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image1, posGiocatoreX, posGiocatoreY, 40, 40, this);
        g2.setColor(Color.white);

        g2.drawString(TempoGioco, 10, 20);

        g2.fillRect(100,100,48,48);

    }

    @Override
    public void update(Observable o, Object arg ) {



        if (o instanceof Time){

            TempoGioco = (String) arg;

        }else {
            ArrayList<Integer> pos= (ArrayList<Integer>) arg;
            posGiocatoreX= pos.get(0); //X
            posGiocatoreY= pos.get(1); //Y
        }


        repaint();
    }




}
