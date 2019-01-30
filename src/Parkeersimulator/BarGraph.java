package Parkeersimulator;

import java.awt.*;
import javax.swing.*;

public class BarGraph extends JPanel {

		public BarGraph() {
			/*setName("Graph");
			setSize(440, 265);
			setVisible(true);*/
			
		}
		// x vanaf links, y vanaf boven, breedte, hoogte
		public void paint(Graphics g) {
			g.setColor(Color.gray); // background
			g.fillRect(0,0,440,265); 
			
			g.setColor(Color.BLUE); // background graph
			g.fillRect(20, 50, 400, 195);
			
			g.setColor(Color.yellow);
			g.fillRect(30, 95, 50, 150);
			
			g.setColor(Color.pink);
			g.fillRect(90, 95, 50, 150);
			
			g.setColor(Color.orange);
			g.fillRect(360, 95, 50, 150);
		}
		
		
}
