package view;

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


    Thread gameThread;

    public void startGameThread(){
            gameThread= new Thread(this);

            gameThread.start();

    }
    @Override
    public void run() {

        //Gameloop

    }
    public PannelloSpaziale()  {
        setBackground(Color.blue);

        try {

            image1 = ImageIO.read(new File("bomberman.png"));


        }catch (IOException ex){

        }


    }

    @Override
    public void update(Observable o, Object arg) {
        testoPannello = (String) arg;
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image1, 40, 0, 40, 40, this);
        g2.setColor(Color.white);
        g2.drawString(testoPannello, 20, 20);

    }


}
