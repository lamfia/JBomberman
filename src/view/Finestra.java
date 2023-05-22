package view;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.IncrementoListener;

import java.io.IOException;

public class Finestra extends JFrame {

    private PannelloSpaziale pannelloSpaziale;

    public Finestra() {

        super("JBomberman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 600);
        setLayout(null);

        //pannello
        pannelloSpaziale = new PannelloSpaziale();
        add(pannelloSpaziale);
        pannelloSpaziale.setBounds(20, 20, 180, 100);

        JButton bInc = new JButton("+");
        add(bInc);
        bInc.setBounds(20, 140, 40, 40);
        bInc.addActionListener(new IncrementoListener());
        setVisible(true);
    }

    public PannelloSpaziale getPannelloSpaziale() {
        return pannelloSpaziale;
    }

}
