package view;


import controller.Direzione;
import controller.KeyHandler;
import controller.Posizione;
import controller.TileManager;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePanel extends JPanel implements Observer, Runnable {
    private TileManager tileM;
    private String TempoGioco = "00:00:00";
    //private Image image1;
    private Image map;
    private Graphics2D externalGraphics;
    private Personaggio player;

    private int TitleScreenState = 0; //0: prima title ; 1: load game ; 2:stage select

    public Partita partita;

    /**
     * Dimensioni del gamePanel
     **/
    private int dimensionWidth;
    private int dimensionHeight;

    private int commandNum = 0;
    private int MaxcommandNum = 3;

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
    public GamePanel(Color colorBackGround, int dimensionWidth, int dimensionHeight, Partita partita) {

        this.dimensionHeight = dimensionHeight;
        this.dimensionWidth = dimensionWidth;

        this.partita = partita;
    }

    public void repaintTask() {
        repaint();
    }

    public void addGiocatore(Giocatore giocatore) throws IOException {

        //Default image
        giocatore.movimento.posizione.ImageAttuale = giocatore.movimento.posizione.pathImages.downidle;

        this.player = giocatore;

        repaint();

    }

    //non serve qui, il paint dell'enemico si fa in tilemanager
//    public void addEnemico(Enemico enemico) throws IOException {
//
//        //Default image
//        enemico.movimento.posizione.ImageAttuale = enemico.movimento.posizione.pathImages.downidle;
//
//        this.personaggi.add(enemico);
//        repaint();
//
//    }

    private Graphics grafica;

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        var g2 = (Graphics2D) g;

        externalGraphics = g2;

        try {

            if (partita.statoPartita == StatoPartita.Title) {
                drawTitle(g2);
            }


            if (partita.statoPartita == StatoPartita.Playing) {


                //TODO da mettere questa setting nella partita model
                map = ImageIO.read(new File("src/view/maps/Pirate/pirata.png"));
                g2.drawImage(map, 0, 0, dimensionWidth, dimensionHeight, this);

                //Aggiorna i tiles
                drawTiles();

                drawInfoGame(g2);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Timer del gioco //TODO spostare in partita model
//        g2.setColor(Color.white);
//        g2.drawString(TempoGioco, 10, 20);


    }

    private void drawTitle(Graphics2D g2) throws IOException {


        //Background
        var background = ImageIO.read(new File("src/view/res/TitleScreen/background column.png"));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                g2.drawImage(background, 80 * i, 260 * j, 80, 260, this);
            }

        }

        //Title bomberman
        var title = ImageIO.read(new File("src/view/res/TitleScreen/bomberman title.png"));
        g2.drawImage(title, 240, 30, 270, 150, this);

        //Bomb menu
        var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


        if (TitleScreenState == 0) {
            //Start
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Start Game", 260, 250);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230, 220, 27, 37, this);
            }

            //Load Game
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Load Game", 260, 300);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
            }

            //Stage select
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Stage Select", 260, 350);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 230, 320, 27, 37, this);
            }

            //Quit
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Quit", 260, 400);
            if (commandNum == 3) {
                g2.drawImage(bombMenuImage, 230, 370, 27, 37, this);
            }

        }

        if (TitleScreenState == 2) {


            //Start
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Level 1 - Pirates!", 260, 250);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230, 220, 27, 37, this);
            }

            //Load Game
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Level 2 - Spaceman", 260, 300);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
            }
        }
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
        var giocatore = player;


        //Tempo! TODO mettere questo valore nella classe "partita"
        if (observable instanceof Time) {
            TempoGioco = (String) arg;
        }

        //Partita info
        if (observable instanceof Partita) {

            var partita = (Partita) arg;

            if (partita.statoPartita == StatoPartita.GameOver) {
                System.out.println("Game over!");

                //Draw title GameOver
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

        //Title manager
        if (observable instanceof KeyHandler) {

            var direzione = (Direzione) arg;
            //System.out.println(direzione.toString());

            switch (direzione) {
                case UP:
                    if (commandNum > 0)
                        commandNum -= 1;
                    break;
                case DOWN:
                    if (commandNum < MaxcommandNum)
                        commandNum += 1;
                    break;
                case SPACE:
                    switch (commandNum) {

                        //StarStart
                        case 0:
                            partita.statoPartita = StatoPartita.Playing;
                            break;
                        //Load Game
                        case 1:
                            System.out.println("Load Game");
                            break;
                        //Stage select
                        case 2:
                            TitleScreenState = 2;
                            //Cambio di menu
                            commandNum = 0;
                            MaxcommandNum = 1;
                            break;

                        //Quit
                        case 3:
                            System.exit(0);
                            break;

                    }

            }

            repaint();

        }


    }


    private void drawScreenGameOver() {

    }

    private void drawInfoGame(Graphics2D g2) {

        //Timer del gioco //TODO spostare in partita model
        g2.setColor(Color.white);
        g2.drawString(TempoGioco, 10, 20);


//        //Stage select
//        g2.setColor(Color.white);
//        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
//        g2.drawString("Stage Select", 260, 350);
//
        //g2.drawImage(bombMenuImage, 230, 320, 27, 37, this);


    }
}
