package Parkeersimulator;

import java.awt.*;
import javax.swing.*;

public class BarGraph extends JPanel {
		
		private double moneyEarnedPerDay;
		private int currentDay;
		
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
			g.fillRect(20, 20, 410, 195);
			
			g.setColor(Color.pink);
			
			for (int i = 0; i < 7; i++) {
				
			
			g.fillRect(80+50*i, 80 - (int)moneyEarnedPerDay, 40, 150);
			
			}
		}
		
		public void calculateBars()
		{
			
		}
		
		public void setData(double moneyEarnedPerDay, int currentDay)
		{
			System.out.println("Dit word aangeroepen");
			this.moneyEarnedPerDay = moneyEarnedPerDay;
			this.currentDay = currentDay;
		}
		
		
}
