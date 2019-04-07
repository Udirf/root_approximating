package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Solution;
import controller.Complex;

public class AnimationGui extends JPanel {

    Integer degree;
    Solution[] solutions;
    Integer index;
    Integer num;
    Complex[] circleCenters;
    Double[] circleRadius;
    Double ratio;
    String poly;
    String k;
    JLabel iterationLabel;
    JLabel centerTitleLabel;
    JLabel centerLabel;
    JLabel radiusTitleLabel;
    JLabel radiusLabel;
    JFrame topFrame;

    public AnimationGui(JFrame frame, Integer n, Solution[] sol, int in, String poly, String k) {

        this.degree = n;
        this.solutions = sol;
        this.index = in;
        this.num = 0;
        this.circleCenters = new Complex[degree];
        this.circleRadius = new Double[degree];
        sol[in].getCircles(circleCenters, circleRadius);
        this.ratio = 250 / circleRadius[0];
        this.poly = poly;
        this.k = k;
        this.topFrame = frame;

    }

    public void go() {

        JLabel titleLabel = new JLabel("Animation");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		titleLabel.setBounds(600, 30, 200, 30);
        add(titleLabel);

        JLabel solutionLabel = new JLabel("Solution: " + solutions[index].getRoot().toString());
		solutionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		solutionLabel.setBounds(400, 70, 500, 30);
        add(solutionLabel);

        iterationLabel = new JLabel();
		iterationLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		iterationLabel.setBounds(900, 250, 200, 30);
        add(iterationLabel);

        centerTitleLabel = new JLabel("Coordinates of the center point:");
        centerTitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		centerTitleLabel.setBounds(900, 300, 400, 30);
        add(centerTitleLabel);

        centerLabel = new JLabel();
		centerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		centerLabel.setBounds(900, 330, 400, 30);
        add(centerLabel);

        radiusTitleLabel = new JLabel("Radius of the circle:");
		radiusTitleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		radiusTitleLabel.setBounds(900, 360, 400, 30);
        add(radiusTitleLabel);

        radiusLabel = new JLabel();
		radiusLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		radiusLabel.setBounds(900, 390, 400, 30);
        add(radiusLabel);

        JButton next = new JButton("Next step");
		next.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		next.setBounds(900, 150, 100, 30);
        add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(num < degree) {
                    repaint();
                    num++;
                }
			}
        });

        JButton back = new JButton("Animate another solution!");
		back.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		back.setBounds(900, 550, 200, 50);
        add(back);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                setVisible(false);
                OutputGui.createOutputPanel(topFrame, degree, solutions, poly, k);
			}
        });

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        setBackground(new Color(255, 234, 179)); 

        if(num < degree) {

            g.fillRect(120, 368, 500, 4);
            g.fillRect(368, 120, 4, 500);
                
            for (int i = 1; i < 8*degree*degree; i++) {
                int grid = (int) (120 + (double) (i * ((500 * 1.0) / (8 * degree * degree))));
                g.drawLine(grid, 120, grid, 620);
                g.drawLine(120, grid, 620, grid);
            }

            g.setColor(Color.RED);
            int x = (int) (circleCenters[0].re() * ratio) + 370;
            int y = (int) (-(circleCenters[0].im() * ratio)) + 370;
            int rad = (int) (Math.round(circleRadius[0]) * ratio);
            g.fillOval(x-3, y-3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawOval(x-rad, y-rad, 2*rad, 2*rad);

            for (int i = 1; i <= num; i++) {

                g.setColor(Color.RED);
                x = (int) (circleCenters[i].re() * ratio) + 370;
                y = (int) (-(circleCenters[i].im() * ratio)) + 370;
                rad = (int) (Math.round(circleRadius[i]) * ratio);
                g.fillOval(x-3, y-3, 6, 6);
                g.setColor(Color.BLACK);
                g.drawOval(x-rad, y-rad, 2*rad, 2*rad);

            }

            iterationLabel.setText("Iteration "+(num+1));
            centerLabel.setText(circleCenters[num].toString());
            radiusLabel.setText(circleRadius[num].toString());

        } else {

            g.fillRect(120, 368, 500, 4);
            g.fillRect(368, 120, 4, 500);
                
            for (int i = 1; i < 8*degree*degree; i++) {
                int grid = (int) (120 + (double) (i * ((500 * 1.0) / (8 * degree * degree))));
                g.drawLine(grid, 120, grid, 620);
                g.drawLine(120, grid, 620, grid);
            }

            g.setColor(Color.RED);
            int x = (int) (circleCenters[0].re() * ratio) + 370;
            int y = (int) (-(circleCenters[0].im() * ratio)) + 370;
            int rad = (int) (Math.round(circleRadius[0]) * ratio);
            g.fillOval(x-3, y-3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawOval(x-rad, y-rad, 2*rad, 2*rad);

            for (int i = 1; i < degree; i++) {
    
                    g.setColor(Color.RED);
                    x = (int) (circleCenters[i].re() * ratio) + 370;
                    y = (int) (-(circleCenters[i].im() * ratio)) + 370;
                    rad = (int) (Math.round(circleRadius[i]) * ratio);
                    g.fillOval(x-3, y-3, 6, 6);
                    g.setColor(Color.BLACK);
                    g.drawOval(x-rad, y-rad, 2*rad, 2*rad);
    
                }

            g.setColor(Color.GREEN);
            x = (int) (solutions[index].getRoot().re() * ratio) + 370;
            y = (int) (-(solutions[index].getRoot().im() * ratio)) + 370;
            g.fillOval(x-3, y-3, 6, 6);

            iterationLabel.setText("Root:");
            centerTitleLabel.setText(solutions[index].getRoot().toString());
            centerLabel.setText("Polynomial:");
            radiusTitleLabel.setText(poly);
            radiusLabel.setText("Error parameter: " + k);

        }

    }

}
