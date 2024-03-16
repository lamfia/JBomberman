package view;

import javax.swing.JFrame;


import controller.KeyHandler;
import model.Partita;

import java.awt.*;


/**
 * Questa classe rappresenta la finestra principale del gioco
 * Estende la classe JFrame e contiene il pannello di gioco GamePanel.
 * Gestisce la configurazione della finestra, la posizione sulla schermata
 * e l'aggiunta del GamePanel.
 *
 * @author Gabriel Guerra
 */
public class Finestra extends JFrame {
    private GamePanel gamePanel;
    private static void showFrameOnScreen(Window frame, int screen) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
        GraphicsDevice graphicsDevice = (screen > -1 && screen < graphicsDevices.length) ? graphicsDevices[screen] : graphicsDevices.length > 0 ? graphicsDevices[0] : null;
        if (graphicsDevice == null) {
            throw new RuntimeException("There are no screens !");
        }
        Rectangle bounds = graphicsDevice.getDefaultConfiguration().getBounds();
        // frame.setSize(bounds.width, bounds.height);
        frame.setLocationRelativeTo(null);
        frame.setLocation(bounds.x + 500, bounds.y + 200);
    }

    /**
     * Costruisce una nuova Finestra del gioco.
     *
     * @param dimensionWidht  La larghezza del pannello di gioco.
     * @param dimensionHeight L'altezza del pannello di gioco.
     * @param partita         L'istanza della partita in corso.
     * @param keyHandler      Il gestore degli eventi da tastiera.
     */
    public Finestra(int dimensionWidht, int dimensionHeight, Partita partita, KeyHandler keyHandler) {

        //Construttore
        super("JBomberman");

        //Configurazione schermo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);
        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(null); //location schermo centro dello schermo
        setResizable(false); //disable resize dello schermo

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        //this.setFocusTraversalKeysEnabled(false);

        setVisible(true);

        //GamePanel
        gamePanel = new GamePanel(dimensionWidht, dimensionHeight, partita);
        add(gamePanel);
        gamePanel.setBounds(0, 0, 800, 600);
        gamePanel.setFocusable(true);

        //Questo serve per mettere nel primo schermo il jframe
        showFrameOnScreen(this, 1);
    }

    /**
     * Restituisce il pannello di gioco associato a questa finestra.
     *
     * @return GamePanel.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

}
