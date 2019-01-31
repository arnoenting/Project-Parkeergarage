package Views;

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
		
		private String midLabel ="";
		private String highLabel ="";
	
		public BarGraph() {

		}
		// x vanaf links, y vanaf boven, breedte, hoogte
		public void paintComponent(Graphics g) {
			g.setColor(Color.decode("#4b4b4b"));; // background
			g.fillRect(0,0,440,265); 
			g.setColor(Color.white);
			
			
			g.drawString("De titel van deze graph", 60, 20); // The title of the graph
			
			// legend of BarGraph, all the days of the week
			g.drawString("mon", 90, 230);
			g.drawString("tue", 145, 230);
			g.drawString("wed", 190, 230);
			g.drawString("thu", 240, 230);
			g.drawString("fri", 295, 230);
			g.drawString("sat", 340, 230);
			g.drawString("sun", 390, 230);
			
			g.drawString("0%", 36, 190);
			g.drawString(midLabel + "%", 36, 134);
			g.drawString(highLabel + "%", 36, 78);
			
			// The X-as and the Y-as
			g.setColor(Color.BLACK);
			g.fillRect(60, 30, 2, 170);
			g.fillRect(60, 200, 370, 2);

			g.setColor(Color.red);
			
			for (int i = 0; i < 7; i++) 
			{
				//System.out.println("dag: " + i +" = " + (int)perdayProcent[i]*100);
				g.fillRect(80+50*i, 190 , 40, -(int)(120*perdayProcent[i]/highestEarningDay));
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
				highestEarningDay = 0;
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
			
			midLabel= "" + Math.round(((highestEarningDay*100) / 2));
			highLabel = "" + Math.round((highestEarningDay*100));
		}
		
		
}
