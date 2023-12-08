package view;

import javax.swing.JButton;
import javax.swing.JFrame;


import controller.KeyHandler;

import java.awt.*;


/**
 * Finestra principale del gioco
 * @author Gabriel Guerra
 */
public class Finestra extends JFrame {

    private GamePanel gamePanel;



    public Finestra(int dimensionWidht, int dimensionHeight) {

        //Construttore
        super("JBomberman");

        //Configurazione schermo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);
        setLayout(null);
        setSize(800,600);
        setLocationRelativeTo(null); //location schermo centro dello schermo
        //setResizable(false); //disable resize dello schermo



//        JButton bInc = new JButton("PROVA BUTTON");
//        add(bInc);
//        bInc.setBounds(200, 140, 40, 40);
//        bInc.addActionListener(new IncrementoListener());


        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        setVisible(true);



        //GamePanel
        gamePanel = new GamePanel(Color.gray,dimensionWidht, dimensionHeight);

        add(gamePanel);
        gamePanel.setBounds(0, 0,800,600);
    }

    public GamePanel getPannelloSpaziale() {
        return gamePanel;
    }

}
