package view;


import controller.*;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePanel extends JPanel implements Observer, Runnable {
    private TileManager tileM;
    private String TempoGioco = "00:00:00";
    //private Image image1;
    private Image map;
    private Graphics2D externalGraphics;
    private Giocatore player;

    private int TitleScreenState = 0; //0: prima title ; 1: load game
    // ; 2:stage select ; 3:CreazioneUtente

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

                //Draw della mappa selezionatas
                drawFullImage(g2, ImageIO.read(new File(partita.map.getMapPath())));

                //Aggiorna i tiles
                drawTiles();

                //Draw dell'info game
                drawInfoGame(g2);
            }

            if (partita.statoPartita == StatoPartita.GameOver) {
                drawScreenGameOver(g2);
            }

            if (partita.statoPartita == StatoPartita.Win) {
                drawScreenWin(g2);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Timer del gioco //TODO spostare in partita model
//        g2.setColor(Color.white);
//        g2.drawString(TempoGioco, 10, 20);


    }

    private void drawFullImage(Graphics2D g2, Image img) {

        g2.drawImage(img, 0, 0, dimensionWidth, dimensionHeight, this);
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

            //Quit game
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Quit Game", 260, 400);
            if (commandNum == 3) {
                g2.drawImage(bombMenuImage, 230, 370, 27, 37, this);
            }

        }

        //Load Game
        if (TitleScreenState == 1) {


            //Save 1
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("1) ", 260, 250);
            drawSavedGame(g2, 0, 230, 220);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230, 220, 27, 37, this);
            }

            //Save 2
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("2) ", 260, 300);
            drawSavedGame(g2, 1, 230, 270);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
            }

            //Save 3
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("3) ", 260, 350);
            drawSavedGame(g2, 2, 230, 320);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 230, 320, 27, 37, this);
            }

            //Save 4
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("4)", 260, 400);
            drawSavedGame(g2, 3, 230, 370);
            if (commandNum == 3) {
                g2.drawImage(bombMenuImage, 230, 370, 27, 37, this);
            }

            //Return menu
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Return menu", 260, 450);
            if (commandNum == 4) {
                g2.drawImage(bombMenuImage, 230, 420, 27, 37, this);
            }

        }


        //Stage select
        if (TitleScreenState == 2) {

            //Level 1 pirates
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Level 1 - Pirates!", 260, 250);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230, 220, 27, 37, this);
            }

            //Level 2 spaceMan!
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Level 2 - Spaceman", 260, 300);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
            }

            //Return menu
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Return menu", 260, 350);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 230, 320, 27, 37, this);
            }
        }

        //Crea Utente
        if (TitleScreenState == 3) {

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            g2.drawString("Nickname", 300, 210);


            JTextField textField = new JTextField(30); // Specifica la larghezza desiderata
            textField.setBounds(300, 230, 150, 20);

            // Aggiungi un KeyListener al JTextField
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        // Se premi "Enter", trasferisci il focus
                        textField.transferFocus();
                    }
                }
            });


//            textField.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    // Quando viene cliccato fuori dal JTextField, perdi il focus
//                    if (!textField.getBounds().contains(e.getPoint())) {
//                        textField.transferFocus();
//                    }
//                }
//            });

            // Aggiungi il FocusListener al JTextField
            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Questo metodo è chiamato quando il JTextField ottiene il focus
                    System.out.println("Focus Otttenuto");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // Questo metodo è chiamato quando il JTextField perde il focus
                    // Puoi aggiungere qui la logica che vuoi eseguire quando il focus viene perso
                    System.out.println("Focus perso");
                }

            });


            add(textField);


            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 260, 220, 27, 37, this);
            }

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            g2.drawString("Avatar", 330, 300);


            var gestioneUtente = new GestioneUtente();

            BufferedImage AvatarIcon = null;

            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(Avatar.Bomberman)));
            g2.drawImage(AvatarIcon, 270, 330, 60, 50, this);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 285, 370, 27, 37, this);
            }

            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(Avatar.BombermanTheKid)));
            g2.drawImage(AvatarIcon, 320, 330, 60, 50, this);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 335, 370, 27, 37, this);
            }

            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(Avatar.PrettyBomberman)));
            g2.drawImage(AvatarIcon, 370, 330, 60, 50, this);
            if (commandNum == 3) {
                g2.drawImage(bombMenuImage, 385, 370, 27, 37, this);
            }

            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(Avatar.PunkBomberman)));
            g2.drawImage(AvatarIcon, 420, 330, 60, 50, this);
            if (commandNum == 4) {
                g2.drawImage(bombMenuImage, 435, 370, 27, 37, this);
            }


            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            g2.drawString("Start new game", 270, 440);
            if (commandNum == 5) {
                g2.drawImage(bombMenuImage, 240, 410, 27, 37, this);
            }

        }
    }

    private void drawSavedGame(Graphics2D g2, int idSavedGame, int x, int y) {

        //Get x utente
        var gestioneUtente = new GestioneUtente();

        var utenti = gestioneUtente.getUtenti();


        Utente utente = getSavedGameEsistente(idSavedGame);

        if (utente != null) {

            // Utente utente = utenti.get(idSavedGame);
            try {
                //Icon
                var AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(utente.avatar)));
                g2.drawImage(AvatarIcon, x + 70, y - 10, 60, 50, this);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
                //Nickname
                g2.drawString("Nickname: " + utente.Nickname, x + 130, y);
                g2.drawString("Games played: " + utente.partiteGiocate, x + 310, y);
                g2.drawString("Wins: " + utente.partiteVinte, x + 450, y);
                g2.drawString("Loses: " + utente.partitePerse, x + 130, y + 30);
                g2.drawString("Level reached: " + utente.lastLevelArrived, x + 220, y + 30);
                g2.drawString("Points: " + utente.puntiOttenuti, x + 360, y + 30);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
            g2.drawString("NO DATA", x + 130, y + 20);

        }


    }

    private Utente getSavedGameEsistente(int idSavedGame) {
        //Get x utente
        var gestioneUtente = new GestioneUtente();

        var utenti = gestioneUtente.getUtenti();

        if (idSavedGame >= 0 && idSavedGame < utenti.size()) {

            return utenti.get(idSavedGame);
        } else {
            return null;
        }

    }

    public void drawTiles() {

        tileM.draw(this.externalGraphics);
    }

    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    private void cambioMenuReset(int maxCommandNum) {
        //Cambio di menu
        commandNum = 0;
        MaxcommandNum = maxCommandNum;
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
                cambioMenuReset(2);
            }

            if (partita.statoPartita == StatoPartita.Win) {
                cambioMenuReset(2);
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

            gestioneMenu(direzione);

            repaint();

        }


    }

    private void gestioneMenu(Direzione direzione) {
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

                switch (partita.statoPartita) {

                    //Primo title state =0
                    case Title:

                        if (TitleScreenState == 0) {
                            switch (commandNum) {

                                //Start
                                case 0:
                                    TitleScreenState = 1;
                                    cambioMenuReset(4);

//                                    partita.statoPartita = StatoPartita.Playing;
//
//                                    //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
//                                    tileM.RiSetEnemici();
//                                    tileM.RiSetPowerUps();

                                    break;
                                //Load Game
                                case 1:
                                    //Gestione utente
                                    TitleScreenState = 1;
                                    cambioMenuReset(4);
                                    break;
                                //Stage select
                                case 2:
                                    //Secondo title state =2
                                    TitleScreenState = 2;
                                    //Cambio di menu
                                    cambioMenuReset(2);
                                    break;

                                //Quit
                                case 3:
                                    System.exit(0);
                                    break;

                            }
                        } else if (TitleScreenState == 1) {
                            Utente utente = null;
                            //Load save
                            switch (commandNum) {

                                //Save 1
                                case 0:

                                    //se la partita salvata esiste allora fare carica partita 1
                                    utente = getSavedGameEsistente(0);
                                    //Esiste allora carico X partita
                                    if (utente != null) {
                                        partita.LoadGame(0);
                                    } else {
                                        //Crea utente
                                        TitleScreenState = 3;
                                        cambioMenuReset(5);
                                        break;
                                    }

                                    partita.statoPartita = StatoPartita.Playing;
//                                   //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
                                    tileM.RiSetEnemici();
                                    tileM.RiSetPowerUps();

                                    break;
                                //save 2
                                case 1:
                                    System.out.println("Save 2");
                                    break;
                                case 2:
                                    System.out.println("Save 3");

                                    //se la partita salvata esiste allora fare carica partita 1
                                    utente = getSavedGameEsistente(2);
                                    //Esiste allora carico X partita
                                    if (utente != null) {
                                        partita.LoadGame(2);
                                    } else {
                                        //new Game
                                        //Creare new utente
                                        TitleScreenState = 3;
                                        cambioMenuReset(5);
                                        break;
                                    }

                                    partita.statoPartita = StatoPartita.Playing;
//                                   //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
                                    tileM.RiSetEnemici();
                                    tileM.RiSetPowerUps();
                                    break;
                                case 3:
                                    System.out.println("Save 4");
                                    break;

                                //return menu
                                case 4:
                                    TitleScreenState = 0;
                                    cambioMenuReset(3);
                                    break;


                            }
                        } else if (TitleScreenState == 2) {

                            //Stage Select state
                            switch (commandNum) {

                                case 0:
                                    System.out.println("Pirates");
                                    try {
                                        partita.newGame(Maps.TheSevenSeas);
                                        partita.changeStatoPartita(StatoPartita.Playing);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                //Load Game
                                case 1:
                                    System.out.println("Spaceman");
                                    try {
                                        partita.newGame(Maps.Spaceman);
                                        partita.changeStatoPartita(StatoPartita.Playing);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                //return menu
                                case 2:
                                    TitleScreenState = 0;
                                    cambioMenuReset(3);
                                    break;
                            }
                        }

                        break;
                    case GameOver:

                        switch (commandNum) {

                            //Continue
                            case 0:
                                partita.continueGame();

                                //Qui si fa il reset degli enimici, powerupTiles della mappa selezionata
                                tileM.RiSetEnemici();
                                tileM.RiSetPowerUps();
                                break;

                            //Save Game
                            case 1:
//                                    partita.SaveGame();
//                                    System.out.println("Save Game");
                                break;

                            //Quit (Return to menu)
                            case 2:
                                TitleScreenState = 0;
                                partita.changeStatoPartita(StatoPartita.Title);
                                cambioMenuReset(3);
                                break;

                        }
                        break;

                    case Win:
                        switch (commandNum) {

                            //Next Stage
                            case 0:
                                try {
                                    partita.newGame(Maps.Spaceman);
                                    partita.changeStatoPartita(StatoPartita.Playing);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
//                                    //Qui si fa il reset degli enimici, powerupTiles della mappa selezionata
//                                    tileM.RiSetEnemici();
//                                    tileM.RiSetPowerUps();
                                break;

                            //Save Game
                            case 1:
//                                    partita.SaveGame();
//                                    System.out.println("Save Game");
                                break;

                            //Quit (Return to menu)
                            case 2:
                                TitleScreenState = 0;
                                partita.changeStatoPartita(StatoPartita.Title);
                                cambioMenuReset(3);
                                break;

                        }
                        break;


                }


        }
    }


    private void drawScreenGameOver(Graphics2D g2) {

        try {

            //Background image
            drawFullImage(g2, ImageIO.read(new File("src/view/res/common/GameOver.png")));

            //Bomb menu
            var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


            //Continue
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 550, 280, 27, 37, this);
            }

            //Save game
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 550, 320, 27, 37, this);
            }

            //Quit
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 550, 370, 27, 37, this);
            }

            // }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawScreenWin(Graphics2D g2) {

        try {
            drawFullImage(g2, ImageIO.read(new File("src/view/res/common/Win2.png")));


            //Bomb menu
            var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


            //Continue
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 550, 310, 27, 37, this);
            }

            //Save game
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 550, 370, 27, 37, this);
            }

            //Quit
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 550, 435, 27, 37, this);
            }

            // }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawInfoGame(Graphics2D g2) throws IOException {

        //Timer del gioco //TODO spostare in partita model
        g2.setColor(Color.white);
        g2.drawString(TempoGioco, 100, 20);

        Color c = new Color(0, 0, 0f, .4f);
        g2.setColor(c);
        g2.fillRect(0, 0, 900, 30);

        //Bomberman icon image
        var bombermanIcon = ImageIO.read(new File("src/view/res/icons/bomberman.png"));

        int totvite = player.vite;

        int x_spacebetween = 200;
        for (int i = 0; i < totvite; i++) {
            g2.drawImage(bombermanIcon, x_spacebetween, 8, 20, 20, this);
            x_spacebetween += 40;
        }


        g2.setColor(Color.white);
        g2.drawString("Points: ", 500, 20);
        g2.drawString(String.valueOf(partita.points), 560, 20);


    }
}
