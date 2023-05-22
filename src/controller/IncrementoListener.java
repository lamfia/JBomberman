package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncrementoListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ApplicationManager.modelInstance.inc();
		
	}

}
