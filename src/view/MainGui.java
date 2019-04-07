package view;

import javax.swing.*;

public class MainGui {

	public static JFrame myFrame;

	public static void createFrame() {

		myFrame = new JFrame("Root approximating"); // create application window
		myFrame.setBounds(20, 20, 1320, 700); 
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		myFrame.getContentPane().setLayout(null); 

		InputGui.createInputPanel(myFrame); // initialize main gui panel

		myFrame.setVisible(true); 

	}

}
