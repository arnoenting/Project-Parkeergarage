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
		
		private JLabel monday = new JLabel("mon");
		private JLabel tuesday = new JLabel("tue");
		private JLabel wednesday = new JLabel("wed");
		private JLabel thursday = new JLabel("thu");
		private JLabel friday = new JLabel("fri");
		private JLabel saturday = new JLabel("sat");
		private JLabel sunday = new JLabel("sun");
		
		private JLabel highestLabel = new JLabel("");
		private JLabel highestLabel2 = new JLabel("");
		private JLabel highestLabel3 = new JLabel("");
		
		public BarGraph() {
			add(monday);
			add(tuesday);
			add(wednesday);
			add(thursday);
			add(friday);
			add(saturday);
			add(sunday);
			add(highestLabel);
			add(highestLabel2);
			add(highestLabel3);
			monday.setBounds(10, 150, 30, 100);
			tuesday.setBounds(80, 150, 30, 10);
			wednesday.setBounds(140, 150, 30, 10);
			thursday.setBounds(210, 150, 30, 10);
			friday.setBounds(133, 85, 30, 10);
			saturday.setBounds(160, 150, 30, 10);
			sunday.setBounds(190, 150, 3000, 10);
			highestLabel.setBounds(225, 5, 100, 10);
			highestLabel2.setBounds(225, 42, 100, 10);
			highestLabel3.setBounds(225, 75, 100, 10);
			
			
		}
		// x vanaf links, y vanaf boven, breedte, hoogte
		public void paintComponent(Graphics g) {
			g.setColor(Color.gray); // background
			g.fillRect(0,0,440,265); 


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
