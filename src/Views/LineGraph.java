package Views;

import java.awt.*;
import javax.swing.*;

public class LineGraph extends JPanel{
	
	public LineGraph() {
		
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.decode("#4b4b4b")); // background
		g.fillRect(0,0,440,265); 
		g.setColor(Color.white);
		
		
		g.drawString("De titel van deze graph", 60, 20); // The title of the graph
		
		// The X-as and the Y-as
		g.setColor(Color.BLACK);
		g.fillRect(60, 30, 2, 170);
		g.fillRect(60, 200, 370, 2);
		
	}

}
