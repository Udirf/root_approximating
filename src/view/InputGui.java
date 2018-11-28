package view;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import controller.Complex;

public class InputGui {

	static JPanel inputPanel;
	static JTextField inputPolynomialTextField;
	static JFormattedTextField inputErrorTextField;

	public static void createInputPanel(JFrame frame) {

		inputPanel = new JPanel(); // create main gui panel
		inputPanel.setBackground(new Color(249, 231, 159));
		inputPanel.setBounds(0, 0, 900, 600);
		frame.getContentPane().add(inputPanel);
		inputPanel.setLayout(null);
		inputPanel.setVisible(true);

		JLabel titleLabel = new JLabel("Approximating roots of polynomials with complex coefficients");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		titleLabel.setBounds(120, 50, 700, 30);
		inputPanel.add(titleLabel);

		JLabel inputPolynomialLabel = new JLabel(
				"<html>Please enter a polynomial in the form z<sup>n</sup> + a<sub>1</sub>z<sup>n-1</sup> + a<sub>2</sub>z<sup>n-2</sup> + ... + a<sub>n</sub>:</html>");
		inputPolynomialLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		inputPolynomialLabel.setBounds(100, 170, 600, 35);
		inputPanel.add(inputPolynomialLabel);

		inputPolynomialTextField = new JTextField("z^3 + (3 - i)z^2 + (2 - 3i)z + 6");
		inputPolynomialTextField.setBounds(100, 212, 500, 30);
		inputPanel.add(inputPolynomialTextField);

		JButton setDegree = new JButton("<html>z<sup>n</sup></html>");
		setDegree.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		setDegree.setBounds(600, 210, 45, 35);
		inputPanel.add(setDegree);
		setDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputPolynomialTextField.setText(inputPolynomialTextField.getText() + "z^n");
			}
		});

		JLabel inputErrorLabel = new JLabel("Please enter an error parameter:");
		inputErrorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		inputErrorLabel.setBounds(100, 280, 300, 30);
		inputPanel.add(inputErrorLabel);

		NumberFormat format = NumberFormat.getInstance(); // create formatter to check the input value
		NumberFormatter formatter = new NumberFormatter(format) {
			private static final long serialVersionUID = 1L; 
			public Object stringToValue(String string) throws ParseException { // override the stringToValue method to accept null
				if (string == null || string.length() == 0) {
					return null;
				}
				return super.stringToValue(string);
			}
		};
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);

		inputErrorTextField = new JFormattedTextField(formatter);
		inputErrorTextField.setText("5");
		inputErrorTextField.setBounds(100, 320, 300, 30);
		inputPanel.add(inputErrorTextField);

		JButton getSolutions = new JButton("Get the roots!");
		getSolutions.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getSolutions.setBounds(100, 450, 200, 50);
		inputPanel.add(getSolutions);
		getSolutions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String poly = inputPolynomialTextField.getText();
				String k = inputErrorTextField.getText();
				if (controller.Algorithm.check(poly, k)) { // check the input values
					inputPanel.setVisible(false);
					Integer n = controller.Algorithm.findDegree(poly);
					Complex[] root = controller.Algorithm.init(poly, n, Integer.parseInt(k)); // call the init method to start computing
					OutputGui.createOutputPanel(frame, n, root); // create gui panel for the solution
				} else {
					JOptionPane.showMessageDialog(null, "Please enter valid values!"); // alert if the input values are invalid
				}
			}
		});

	}

	public static void newPolynomial(JFrame frame) {

		inputPanel.setVisible(true);
		inputPolynomialTextField.setText("");
		inputErrorTextField.setText("");

	}

}
