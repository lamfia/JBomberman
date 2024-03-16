package view;


import controller.*;
import model.*;
import model.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Questa classe rappresenta il gamePanel del gioco,
 * gestiona tutta la sezione visuale del gioco, cosi anche come
 * il menu di selezione, paint degli sprites e fps del gioco.
 * implementa la classe Observer per essere aggiornata ad ogni iterazione
 * con gli observables che sono i models.
 *
 * @author Gabriel Guerra
 */


/**
 * Questa classe rappresenta il gamepanel del gioco.
 * Gestisce tutta la parte visuale del gioco, inclusi
 * i menu di selezione,
 * il disegno degli sprites e il controllo dei frame per
 * secondo (FPS).
 * Implementa l'interfaccia Observer per essere
 * aggiornata ad ogni iterazione
 * dagli oggetti osservati (models).
 *
 * @author Gabriel Guerra
 */
public class GamePanel extends JPanel implements Observer {
    private TileManager tileM;
    private Graphics2D externalGraphics;
    private Giocatore player;
    private int TitleScreenState = 0; //0: prima title ; 1: load game
    // ; 2:stage select ; 3:CreazioneUtente
    public Partita partita;
    public String NewNickName = "";
    public Utente newUtente = new Utente();

    /**
     * Dimensioni del gamePanel
     **/
    private int dimensionWidth;
    private int dimensionHeight;
    private int commandNum = 0;
    private int MaxcommandNum = 2;
    private int MincommandNum = 0;

    /**
     * Construttore della classe
     *
     * @param dimensionWidth  dimensione dello schermo lungo
     * @param dimensionHeight dimensione dello schermo largo
     * @param partita         partita previamente creata
     */
    public GamePanel(int dimensionWidth, int dimensionHeight, Partita partita) {

        this.dimensionHeight = dimensionHeight;
        this.dimensionWidth = dimensionWidth;

        this.partita = partita;
    }

    int FPS = 60;
    double drawInterval = 1000000000 / FPS;
    double nextDrawTime = System.nanoTime() + drawInterval;

    /**
     * Metodo per gestire il repaint del gioco (FPS) .
     * Viene chiamato ad intervalli regolari (60 per secondo) per aggiornare la grafica.
     *
     * @throws InterruptedException Se il thread viene interrotto mentre è in attesa.
     */
    public void repaintTask() throws InterruptedException {
        repaint();

        double remaininTime = nextDrawTime - System.nanoTime();

        remaininTime = remaininTime / 1000000;

        if (remaininTime < 0) {
            remaininTime = 0;
        }

        Thread.sleep((long) remaininTime);

        nextDrawTime += drawInterval;

    }

    /**
     * Aggiunge un giocatore principale al pannello di gioco.
     *
     * @param giocatore Il giocatore da aggiungere.
     */
    public void addGiocatore(Giocatore giocatore) {

        //Default image
        giocatore.movimento.posizione.ImageAttuale = giocatore.movimento.posizione.pathImages.downidle;

        this.player = giocatore;

        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        var g2 = (Graphics2D) g;

        externalGraphics = g2;

        try {

            if (partita.statoPartita == StatoPartita.Title) {
                drawTitle(g2);
            }

            if (partita.statoPartita == StatoPartita.Playing || partita.statoPartita == StatoPartita.Playing_StageSelect) {

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
                //Mappa normale


                //ultima mappa, draw del win non continue
                if (partita.lastMapPlayed == Maps.Spaceman) {

                    commandNum = 1;
                    cambioMenuReset(1, 1);
                    drawScreenWinNonContinue(g2);
                } else {
                    //Mappa normale
                    cambioMenuReset(0, 1);
                    drawScreenWin(g2);
                }


            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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


        //Bomb menu
        var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


        if (TitleScreenState == 0) {


            //Title bomberman
            var title = ImageIO.read(new File("src/view/res/TitleScreen/bomberman title.png"));
            g2.drawImage(title, 240, 30, 270, 150, this);

            //Start
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Start Game", 260, 250);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230, 220, 27, 37, this);
            }

            //Load GameTolto non serve perchè è un arcade
//            g2.setColor(Color.white);
//            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
//            g2.drawString("Load Game", 260, 300);
//            if (commandNum == 1) {
//                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
//            }

            //Stage select
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Stage Select", 260, 300);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230, 270, 27, 37, this);
            }

            //Quit game
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Quit Game", 260, 350);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 230, 320, 27, 37, this);
            }

        }

        //Load Game
        if (TitleScreenState == 1) {

            int XSpace = 100;

            int arcDiameter = 20; // Puoi personalizzare il raggio del bordo curvo
            Color colorRect = new Color(0, 0, 255, 150); // 0-255 per RGB, 0-255 per l'alfa (trasparenza

            //rect
            g2.setColor(colorRect);
            g2.fillRoundRect(300 - XSpace, 200, 450, 60, arcDiameter, arcDiameter);

            //Save 1
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("1) ", 260 - XSpace, 250);
            drawSavedGame(g2, 0, 230 - XSpace, 220);
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 230 - XSpace, 220, 27, 37, this);
            }


            //rect
            g2.setColor(colorRect);
            g2.fillRoundRect(300 - XSpace, 250 + 30, 450, 60, arcDiameter, arcDiameter);
            //Save 2
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("2) ", 260 - XSpace, 300 + 30);
            drawSavedGame(g2, 1, 230 - XSpace, 270 + 30);
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 230 - XSpace, 270 + 30, 27, 37, this);
            }


            //Save 3
            //rect
            g2.setColor(colorRect);
            g2.fillRoundRect(300 - XSpace, 320 + 32, 450, 60, arcDiameter, arcDiameter);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("3) ", 260 - XSpace, 350 + 50);
            drawSavedGame(g2, 2, 230 - XSpace, 320 + 50);
            if (commandNum == 2) {
                g2.drawImage(bombMenuImage, 230 - XSpace, 320 + 50, 27, 37, this);
            }

            //Save 4
            g2.setColor(colorRect);
            g2.fillRoundRect(300 - XSpace, 400 + 25, 450, 60, arcDiameter, arcDiameter);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("4)", 260 - XSpace, 400 + 65);
            drawSavedGame(g2, 3, 230 - XSpace, 370 + 80);
            if (commandNum == 3) {
                g2.drawImage(bombMenuImage, 230 - XSpace, 370 + 65, 27, 37, this);
            }

            //Return menu
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            g2.drawString("Return menu", 400 - XSpace, 450 + 80);
            if (commandNum == 4) {
                g2.drawImage(bombMenuImage, 350 - XSpace, 420 + 80, 27, 37, this);
            }

        }


        //Stage select
        if (TitleScreenState == 2) {
            drawStageSelect(g2);
        }

        //Crea Utente
        if (TitleScreenState == 3) {

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            g2.drawString("Nickname", 300, 210);

//
//            JTextField textField = new JTextField(30); // Specifica la larghezza desiderata
//            textField.setBounds(300, 230, 150, 20);
//            add(textField);

            g2.setColor(Color.white);
            g2.fillRect(300, 230, 150, 20);

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
            g2.drawString(NewNickName, 300, 250);

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

    private void drawStageSelect(Graphics2D g2) throws IOException {

        var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));

        var imageSevenSeas = ImageIO.read(new File(partita.map.getMapPath(Maps.TheSevenSeas)));
        g2.drawImage(imageSevenSeas, 40, 80, 300, 300, this);

        //Level 1 pirates
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.drawString("Level 1 - Pirates", 40, 440);
        if (commandNum == 0) {
            g2.drawImage(bombMenuImage, 15, 410, 27, 37, this);
        }
        var imageSpaceMan = ImageIO.read(new File(partita.map.getMapPath(Maps.Spaceman)));
        g2.drawImage(imageSpaceMan, 420, 80, 300, 300, this);

        //Level 2 spaceMan!
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.drawString("Level 2 - Spaceman", 400, 440);
        if (commandNum == 1) {
            g2.drawImage(bombMenuImage, 375, 410, 27, 37, this);
        }

        //Return menu
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        g2.drawString("Return menu", 260, 510);
        if (commandNum == 2) {
            g2.drawImage(bombMenuImage, 230, 480, 27, 37, this);
        }
    }

    private void drawSavedGame(Graphics2D g2, int idSavedGame, int x, int y) {

        var gestioneUtente = new GestioneUtente();

        Utente utente = getSavedGameEsistente(idSavedGame);

        if (utente != null) {

            // Utente utente = utenti.get(idSavedGame);
            try {
                //Icon
                var AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(utente.avatar)));
                g2.drawImage(AvatarIcon, x + 70, y - 10, 60, 50, this);

                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
                g2.drawString("Nickname: " + utente.Nickname, x + 130, y);
                g2.drawString("Games played: " + utente.partiteGiocate, x + 310, y);
                g2.drawString("Wins: " + utente.partiteVinte, x + 450, y);
                g2.drawString("Loses: " + utente.partitePerse, x + 130, y + 24);
                g2.drawString("Level reached: " + utente.lastLevelArrived, x + 220, y + 24);
                g2.drawString("Points: " + utente.puntiOttenuti, x + 360, y + 24);


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

        if (utenti == null) {
            return null;
        }


        if (idSavedGame >= 0 && idSavedGame < utenti.size()) {

            return utenti.get(idSavedGame);
        } else {
            return null;
        }

    }

    private void SalvaModificheUtente(Utente utente) {
        var gestioneUtente = new GestioneUtente();
        gestioneUtente.salvaModificheUtente(utente);

    }

    /**
     * Disegna i tiles sul pannello di gioco utilizzando il manager dei tiles.
     * Questo metodo richiama il metodo draw del TileManager
     * per disegnare i tiles sul pannello utilizzando il contesto grafico esterno.
     */
    public void drawTiles() {

        tileM.draw(this.externalGraphics);
    }

    /**
     * Imposta il tileManage.
     *
     * @param tileM Il manager dei tiles da impostare per il pannello di gioco.
     */
    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    private void cambioMenuReset(int maxCommandNum) {
        //Cambio di menu
        commandNum = 0;
        MaxcommandNum = maxCommandNum;
    }

    private void cambioMenuReset(int minCommandNum, int maxCommandNum) {
        //Cambio di menu
        MincommandNum = minCommandNum;
        MaxcommandNum = maxCommandNum;

    }

    private final static String alphabet = "qwertyuiopasdfghjklzxcvbnm";

    @Override
    public void update(Observable observable, Object arg) {

        //player
        var giocatore = player;

        //Partita info
        if (observable instanceof Partita) {

            var partita = (Partita) arg;
            if (partita.statoPartita == StatoPartita.GameOver) {
                commandNum = 0;
                cambioMenuReset(0, 1);
            }

            if (partita.statoPartita == StatoPartita.Win) {
                commandNum = 0;
                cambioMenuReset(0, 1);
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

            String lettera = (String) arg;
            //System.out.println(direzione.toString());

            //Sta in creazione utente
            if (this.TitleScreenState == 3) {

                //Se nickname is null allora lo deve inserire
                if (newUtente.Nickname == null) {
                    //Seleziona nickname
                    if (lettera.equals("Backspace") || lettera.equals("Space")) {
                        if (!NewNickName.isEmpty()) {
                            NewNickName = NewNickName.substring(0, NewNickName.length() - 1);
                        }
                    } else {
                        if (alphabet.contains(lettera.toLowerCase())) {
                            NewNickName += lettera.toUpperCase();
                        }
                    }

                    if (lettera.equals("Enter") && !NewNickName.isEmpty()) {
                        gestioneMenu(Direzione.DOWN);
                        newUtente.Nickname = NewNickName;
                        commandNum = 1;
                        cambioMenuReset(1, 4);
                    }
                } else if (newUtente.avatar == null) {
                    //Se avatar non c'è lo deve inserire selezionando destrta e sinistra
                    if (lettera.equals("A")) {
                        gestioneMenu(Direzione.UP);
                    } else if (lettera.equals("D")) {
                        gestioneMenu(Direzione.DOWN);
                    } else if (lettera.equals("Space")) {
                        gestioneMenu(Direzione.SPACE);
                    }

                    if (lettera.equals("Enter")) {

                        Avatar newAvatar = null;
                        switch (commandNum) {
                            case 1 -> newAvatar = Avatar.Bomberman;
                            case 2 -> newAvatar = Avatar.BombermanTheKid;
                            case 3 -> newAvatar = Avatar.PrettyBomberman;
                            case 4 -> newAvatar = Avatar.PunkBomberman;
                        }
                        newUtente.avatar = newAvatar;

                        commandNum = 5;
                        cambioMenuReset(5, 5);
                    }

                } else {
                    //start new game arcade!
                    if (lettera.equals("Enter")) {

                        partita.utente = newUtente;

//                        partita.statoPartita = StatoPartita.Playing;
//                       //
//                        //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
//                        tileM.RiSetEnemici();
//                        tileM.RiSetPowerUps();
//                        try {
//                            partita.newGame(Maps.TheSevenSeas);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
                        partita.utente.avatar = newUtente.avatar;

                        this.TitleScreenState = 0;
                        NewNickName = "";
                        newUtente = new Utente();


                        try {
                            this.partita.cambiaSpritesGiocatore();
                            partita.newGame(Maps.TheSevenSeas);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        tileM.RiSetEnemici();
                        partita.changeStatoPartita(StatoPartita.Playing);


                    }
                }


            } else {

                //Sta in stage Select
                if (TitleScreenState == 2){


                    if (lettera.equals("A")) {
                        gestioneMenu(Direzione.UP);
                    } else if (lettera.equals("D")) {
                        gestioneMenu(Direzione.DOWN);
                    } else if (lettera.equals("Enter")) {
                        gestioneMenu(Direzione.SPACE);
                    } else if (lettera.equals("S")) {
                        commandNum=2;
                    }else if (lettera.equals("W")) {
                        commandNum=0;
                    }

                }else {
                    if (lettera.equals("W")) {
                        gestioneMenu(Direzione.UP);
                    } else if (lettera.equals("S")) {
                        gestioneMenu(Direzione.DOWN);
                    } else if (lettera.equals("Enter")) {
                        gestioneMenu(Direzione.SPACE);
                    }
                }


            }


            repaint();

        }


    }

    private void gestioneMenu(Direzione direzione) {
        switch (direzione) {
            case UP:
                if (commandNum > MincommandNum)
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
//                                case 1:
//                                    //Gestione utente
//                                    TitleScreenState = 1;
//                                    cambioMenuReset(4);
//                                    break;
                                //Stage select
                                case 1:
                                    //Secondo title state =2
                                    TitleScreenState = 2;
                                    //Cambio di menu
                                    cambioMenuReset(2);
                                    break;

                                //Quit
                                case 2:
                                    System.exit(0);
                                    break;

                            }
                        } else if (TitleScreenState == 1) {
                            Utente utente = null;
                            //Load save
                            switch (commandNum) {

                                //Save 1
                                case 0:

                                    LoadGame(0);

                                    break;
                                //save 2
                                case 1:
                                    // System.out.println("Save 2");
                                    LoadGame(1);
                                    break;
                                case 2:
                                    System.out.println("Save 3");
                                    LoadGame(2);
//                                    //se la partita salvata esiste allora fare carica partita 1
//                                    utente = getSavedGameEsistente(2);
//                                    //Esiste allora carico X partita
//                                    if (utente != null) {
//                                        utente.partiteGiocate = utente.partiteGiocate + 1;
//                                        partita.LoadGame(2);
//                                    } else {
//                                        //new Game
//                                        //Creare new utente
//                                        TitleScreenState = 3;
//                                        cambioMenuReset(5);
//                                        break;
//                                    }
//
//                                    partita.statoPartita = StatoPartita.Playing;
////                                   //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
//                                    tileM.RiSetEnemici();
//                                    tileM.RiSetPowerUps();
                                    break;
                                case 3:
                                    // System.out.println("Save 4");
                                    LoadGame(3);
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
                                        tileM.RiSetEnemici();
                                        partita.changeStatoPartita(StatoPartita.Playing_StageSelect);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                //Load Game
                                case 1:
                                    System.out.println("Spaceman");
                                    try {
                                        partita.newGame(Maps.Spaceman);
                                        tileM.RiSetEnemici();
                                        partita.changeStatoPartita(StatoPartita.Playing_StageSelect);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                //return menu
                                case 2:
                                    TitleScreenState = 0;
                                    commandNum=0;
                                    cambioMenuReset(0, 2);
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

//                            //Save Game
//                            case 1:
////                                    partita.SaveGame();
////                                    System.out.println("Save Game");
//                                break;

                            //Quit (Return to menu)
                            case 1:
                                TitleScreenState = 0;
                                partita.changeStatoPartita(StatoPartita.Title);
                                cambioMenuReset(0, 2);
                                break;

                        }
                        break;

                    case Win:
                        switch (commandNum) {

                            //Next Stage
                            case 0:
                                try {
                                    partita.newGame(Maps.Spaceman);
                                    tileM.RiSetEnemici();
                                    partita.changeStatoPartita(StatoPartita.Playing);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
//                                    //Qui si fa il reset degli enimici, powerupTiles della mappa selezionata
//                                    tileM.RiSetEnemici();
//                                    tileM.RiSetPowerUps();
                                break;

                            //Save Game
//                            case 1:
////                                    partita.SaveGame();
////                                    System.out.println("Save Game");
//                                break;

                            //Quit (Return to menu)
                            case 1:
                                TitleScreenState = 0;
                                partita.changeStatoPartita(StatoPartita.Title);
                                cambioMenuReset(0, 2);
                                break;

                        }
                        break;


                }


        }
    }

    private void LoadGame(int saveId) {

        var utente = getSavedGameEsistente(saveId);

        //Esiste allora carico la new partita e azzero i parametri della new partita
        if (utente != null) {
            utente.partiteGiocate = utente.partiteGiocate + 1;
            utente.puntiOttenuti = 0;
            SalvaModificheUtente(utente);
            partita.LoadGame(saveId);
            try {
                this.partita.cambiaSpritesGiocatore();
                partita.newGame(Maps.TheSevenSeas);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            //Crea utente
            TitleScreenState = 3;
            cambioMenuReset(5);
            return;
        }

        partita.statoPartita = StatoPartita.Playing;
        //partita.resetGame(); //TODO mettere bene qui il reset di tutti i tiles
        tileM.RiSetEnemici();
        tileM.RiSetPowerUps();

    }

    private void drawScreenGameOver(Graphics2D g2) {

        try {

            //Background image
            drawFullImage(g2, ImageIO.read(new File("src/view/res/common/GameOver2.png")));

            //Bomb menu
            var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


            //Continue
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 550, 305, 27, 37, this);
            }

//            //Save game
//            if (commandNum == 1) {
//                g2.drawImage(bombMenuImage, 550, 320, 27, 37, this);
//            }

            //Quit
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 550, 370, 27, 37, this);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawScreenWin(Graphics2D g2) {

        try {
            drawFullImage(g2, ImageIO.read(new File("src/view/res/common/Win3.png")));


            //Bomb menu
            var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


            //Continue
            if (commandNum == 0) {
                g2.drawImage(bombMenuImage, 550, 350, 27, 37, this);
            }

//            //Save game
//            if (commandNum == 1) {
//                g2.drawImage(bombMenuImage, 550, 370, 27, 37, this);
//            }

            //Quit
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 550, 420, 27, 37, this);
            }

            // }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawScreenWinNonContinue(Graphics2D g2) {

        try {
            drawFullImage(g2, ImageIO.read(new File("src/view/res/common/WinNoContinue.png")));


            //Bomb menu
            var bombMenuImage = ImageIO.read(new File("src/view/res/TitleScreen/BombMenu.png"));


            //Continue
//            if (commandNum == 0) {
//                g2.drawImage(bombMenuImage, 550, 350, 27, 37, this);
//            }

//            //Save game
//            if (commandNum == 1) {
//                g2.drawImage(bombMenuImage, 550, 370, 27, 37, this);
//            }

            //Quit
            if (commandNum == 1) {
                g2.drawImage(bombMenuImage, 550, 420, 27, 37, this);
            }

            // }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawInfoGame(Graphics2D g2) throws IOException {


        Color c = new Color(0, 0, 0f, .4f);
        g2.setColor(c);
        g2.fillRect(0, 0, 900, 30);

        var gestioneUtente = new GestioneUtente();

        BufferedImage AvatarIcon;
        if (partita.utente == null) {

            //Avatar di default Bomberman
            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(Avatar.Bomberman)));
        } else {
            AvatarIcon = ImageIO.read(new File(gestioneUtente.getPathAvatarIcon(partita.utente.avatar)));
        }

        int totvite = player.vite;

        g2.setColor(Color.white);
        g2.drawString("Lifes: ", 170, 20);
        int x_spacebetween = 200;
        for (int i = 0; i < totvite; i++) {
            g2.drawImage(AvatarIcon, x_spacebetween, -8, 55, 45, this);
            x_spacebetween += 40;
        }

        g2.setColor(Color.white);
        g2.drawString("Points: ", 500, 20);
        g2.drawString(String.valueOf(partita.points), 560, 20);

    }
}
