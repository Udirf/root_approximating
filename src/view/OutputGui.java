package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Solution;

public class OutputGui {

	public static void createOutputPanel(JFrame frame, Integer n, Solution[] solution, String poly, String k) {

		JPanel outputPanel = new JPanel(); // create gui panel for the solution
		outputPanel.setBackground(new Color(255, 234, 179));
		outputPanel.setBounds(0, 0, 1310, 680);
		frame.getContentPane().add(outputPanel);
		outputPanel.setLayout(null);
		outputPanel.setVisible(true);

		JLabel titleLabel = new JLabel("Solutions");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		titleLabel.setBounds(600, 30, 200, 30);
		outputPanel.add(titleLabel);
		
		DefaultListModel model = new DefaultListModel();
		JList solutionList = new JList(model);
		solutionList.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		solutionList.setBounds(350, 100, 600, 350);
		outputPanel.add(solutionList);
		
		for (int i = 0; i < solution.length; i++) {
			model.add(i, "Solution " + (i+1) + ": " + solution[i].getRoot().toString());
		}

		JButton animate = new JButton("Animate!");
		animate.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		animate.setBounds(700, 500, 200, 50);
		outputPanel.add(animate);
		animate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputPanel.setVisible(false);
				int index = solutionList.getSelectedIndex();
				AnimationGui animationPanel = new AnimationGui(frame, n, solution, index, poly, k);
				animationPanel.go();
				animationPanel.setBackground(new Color(255, 234, 179));
				animationPanel.setBounds(0, 0, 1310, 680);
				frame.getContentPane().add(animationPanel);
				animationPanel.setLayout(null);
				animationPanel.setVisible(true);
			}
		});
		
		JButton back = new JButton("Try another polynomial!");
		back.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		back.setBounds(400, 500, 200, 50);
		outputPanel.add(back);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputPanel.setVisible(false);
				InputGui.newPolynomial(frame);
			}
		});

	}

}
