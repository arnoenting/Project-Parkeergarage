package Parkeersimulator;

import java.awt.*;

import javax.swing.JPanel;

public class Circle extends JPanel{
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED); 
		g.drawRect(100, 10, 40, 30);
	}
	
}
