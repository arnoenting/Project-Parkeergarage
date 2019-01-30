package Parkeersimulator;

import java.awt.*;
import javax.swing.*;

public class BarGraph extends JFrame {

		public BarGraph() {
			setName("Graph");
			setSize(440, 265);
			setVisible(true);
			
		}
		// x, y, breedte, hoogte
		public void paint(Graphics g) {
			g.setColor(Color.gray); // background
			g.fillRect(0,0,440,265); 
			
			g.setColor(Color.BLUE);
			g.fillRect(20, 50, 400, 195);
		}
		
		public static void main(String[] args) {
			BarGraph barGraph = new BarGraph();
			barGraph.paint(null);
		}
		
}
