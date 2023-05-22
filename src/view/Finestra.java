package view;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Finestra extends JFrame {


    public Finestra() {

        super("Esempio MVC OO SWING");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 600);
        setLayout(null);
        //pannelloSpaziale=new PannelloSpaziale();
        // add(pannelloSpaziale);
        // pannelloSpaziale.setBounds(20,20,180,100);
        JButton bInc = new JButton("+");
        add(bInc);
        bInc.setBounds(20, 140, 40, 40);
        //  bInc.addActionListener(new IncrementoListener());
        setVisible(true);
    }

}
