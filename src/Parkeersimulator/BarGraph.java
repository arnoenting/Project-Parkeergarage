package Parkeersimulator;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;

import javax.swing.*;

public class BarGraph extends JPanel {
		
		private double moneyEarnedPerDay;
		private int currentDay;
		private int previousDay;
		
		private double[] earnedPerWeekDay = new double[7];
		private double[] perdayProcent = new double[7];
		private double total;
		private double highestEarningDay;
		
		
		public BarGraph() {
			
			
		}
		// x vanaf links, y vanaf boven, breedte, hoogte
		public void paintComponent(Graphics g) {
			g.setColor(Color.gray); // background
			g.fillRect(0,0,440,265); 
			g.setColor(Color.black);
			g.drawString("mon", 90, 180);
			g.drawString("tue", 145, 180);
			g.drawString("wed", 190, 180);
			g.drawString("thu", 240, 180);
			g.drawString("fri", 295, 180);
			g.drawString("sat", 340, 180);
			g.drawString("sun", 390, 180);
			
			g.fillRect(60, 200, 450, 1);
			g.fillRect(60, 200, 1, -400);

			g.setColor(Color.pink);
			
			for (int i = 0; i < 7; i++) 
			{
				//System.out.println("dag: " + i +" = " + (int)perdayProcent[i]*100);
				g.fillRect(80+50*i, 160 , 40, -(int)(75*perdayProcent[i]/highestEarningDay));
			}
		}
		
		
		public void setData(double moneyEarnedPerDay, double moneyEarnedPerWeek, int currentDay)
		{
			repaint();
			
			if (previousDay == 6 && currentDay == 0){
				for (int i = 0; i < earnedPerWeekDay.length; i++) {
					perdayProcent[i] = 0;
					earnedPerWeekDay[i]=0;
				}
				total = 0;
			}
			previousDay = currentDay;
			
			this.moneyEarnedPerDay = moneyEarnedPerDay;
			this.currentDay = currentDay;
			
			earnedPerWeekDay[currentDay] = moneyEarnedPerDay;
			
			highestEarningDay = 0;
			
			for (int i = 0; i < earnedPerWeekDay.length; i++) 
			{
				perdayProcent[i] = (earnedPerWeekDay[i]/moneyEarnedPerWeek);
				
				if (highestEarningDay < perdayProcent[i])
				{
					highestEarningDay = perdayProcent[i];
				}
			}
		}
		
		
}
