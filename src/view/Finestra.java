package view;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.IncrementoListener;



/**
 * Finestra principale del gioco
 * @author Gabriel Guerra
 */
public class Finestra extends JFrame {

    private PannelloSpaziale pannelloSpaziale;

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

        //pannello
        pannelloSpaziale = new PannelloSpaziale();
        add(pannelloSpaziale);
        pannelloSpaziale.setBounds(20, 20, 180, 100);

        JButton bInc = new JButton("PROVA BUTTON");
        add(bInc);
        bInc.setBounds(200, 140, 40, 40);
        bInc.addActionListener(new IncrementoListener());
        setVisible(true);
    }

    public PannelloSpaziale getPannelloSpaziale() {
        return pannelloSpaziale;
    }

}
