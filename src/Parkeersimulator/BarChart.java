package Parkeersimulator;

import java.awt.*;
import javax.swing.JPanel;

public class BarChart extends JPanel {

		public BarChart() {
			setName("Barchart");
			setSize(960, 960);
			setVisible(true);
		}
		
		public void paint(Graphics g) {
			g.setColor(Color.GREEN);
			g.drawOval(300, 300, 200, 200);
			g.setColor(Color.BLUE);
			g.drawRect(480, 480, 200, 100);
		}
		
		public static void main(String[] args) {
			BarChart barChart = new BarChart();
			barChart.paint(null);
		}
		
}
