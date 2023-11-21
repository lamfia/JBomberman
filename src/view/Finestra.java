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

    public Finestra() {

        //Construttore
        super("JBomberman");

        //Configurazione schermo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 600);
        setLayout(null);
        setSize(1200,800);
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

        //pannello
        gamePanel = new GamePanel(Color.gray);
        add(gamePanel);
        gamePanel.setBounds(20, 20,1100,600);
    }

    public GamePanel getPannelloSpaziale() {
        return gamePanel;
    }

}
