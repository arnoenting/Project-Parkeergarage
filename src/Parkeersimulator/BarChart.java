package Parkeersimulator;

import java.awt.*;
import javax.swing.*;

public class BarChart extends JPanel {
	
    private Dimension size;
    private Image barImage; 

		public BarChart() {
            size = new Dimension(0, 0); 
		}
		
        public Dimension getPreferredSize() {
            return new Dimension(880, 250);
        }
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.GREEN);
			g.drawRect(300, 300, 200, 200);
			g.setColor(Color.BLUE);
			g.drawRect(480, 480, 200, 100);
            if (barImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(barImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(barImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    	
}
