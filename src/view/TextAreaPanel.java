package view;

import javax.swing.*;
import java.awt.*;


public class TextAreaPanel extends JPanel {
    private JTextArea textArea;
    public TextAreaPanel(){
        textArea= new JTextArea();

        //Layout solo del jpanel
        setLayout(new BorderLayout());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void aggiungiTesto(String testo){
        textArea.append(testo);
    }

}
