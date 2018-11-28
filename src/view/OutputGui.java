package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Complex;

public class OutputGui {

	public static void createOutputPanel(JFrame frame, Integer n, Complex[] root) {

		JPanel outputPanel = new JPanel(); // create gui panel for the solution
		outputPanel.setBackground(new Color(249, 231, 159));
		outputPanel.setBounds(0, 0, 900, 600);
		frame.getContentPane().add(outputPanel);
		outputPanel.setLayout(null);
		outputPanel.setVisible(true);

		JLabel titleLabel = new JLabel("Solutions");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		titleLabel.setBounds(400, 30, 200, 30);
		outputPanel.add(titleLabel);
		
		DefaultListModel model = new DefaultListModel();
		JList solutionList = new JList(model);
		solutionList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		solutionList.setBounds(100, 100, 600, 350);
		outputPanel.add(solutionList);
		
		for (int i = 0; i < n; i++) model.add(i, "Solution " + (i+1) + ": " + root[i].toString());

		JButton animate = new JButton("Animate!");
		animate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		animate.setBounds(300, 500, 200, 30);
		outputPanel.add(animate);
		animate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton back = new JButton("Try another polynomial!");
		back.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		back.setBounds(50, 500, 200, 30);
		outputPanel.add(back);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputPanel.setVisible(false);
				InputGui.newPolynomial(frame);
			}
		});

	}

}
