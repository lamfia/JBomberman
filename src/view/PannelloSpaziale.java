package view;


import controller.KeyHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class PannelloSpaziale extends JPanel implements Observer , Runnable {

    private String testoPannello = "ciao";
    private Image image1;

    private int posGiocatoreX=0;
    private int posGiocatoreY=0;
    private int GiocatoreVelocita=0;

     KeyHandler keyH = new KeyHandler();

    Thread gameThread;

    //Questo è il construttore
    public PannelloSpaziale()  {
        setBackground(Color.blue);
        try {
            image1 = ImageIO.read(new File("bomberman.png"));

        }catch (IOException ex){
        }
    }

    public void startGameThread(){
            gameThread= new Thread(this);

            gameThread.start();

    }
    @Override
    public void run() {

        while  (gameThread!=null){

            // 1 Update information of character as position
           // update();

            // 2 Draw screen
           // repaint();
            //Se questo update e draw si fanno 3o volte al secondo vuol dire che va a 30fps

        }

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image1, posGiocatoreX, posGiocatoreY, 40, 40, this);
        g2.setColor(Color.white);
        g2.drawString(testoPannello, 20, 20);
        g2.fillRect(100,100,48,48);

    }


    @Override
    public void update(Observable o, Object arg) {
        testoPannello = (String) arg;

        this.addKeyListener(keyH);
        posGiocatoreY-=1;
        if (keyH.upPressed){
            posGiocatoreY-=1;
        }

        repaint();
    }




}
