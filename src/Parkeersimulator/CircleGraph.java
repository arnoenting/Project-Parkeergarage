package Parkeersimulator;

import javax.swing.*;
import java.awt.*;


public class CircleGraph extends JPanel{
	private int totalAdHocCar;
	private int totalParkingPassCar;
	private int totalHandicapCar;
	private int totalReservationCar;
	private int totalCars;
	
	public CircleGraph() {
		
	}
	
	public void setData(int totalAdHocCar, int totalParkingPassCar, int totalHandicapCar, int totalReservationCar, int totalCars) {
		this.totalAdHocCar = totalAdHocCar;
		this.totalParkingPassCar = totalParkingPassCar;
		this.totalHandicapCar = totalHandicapCar;
		this.totalReservationCar = totalReservationCar;
		
		this.totalCars = totalCars;
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.gray); // background
		g.fillRect(0,0,440,265); 
		
		g.setColor(Color.white);
		g.fillArc(130, 25, 180, 180, 360, 360);
		//System.out.println(totalCars + " is totalcars" + totalAdHocCar + " is total adhoc");
		
		if (totalCars != 0) {
			int startAngle = 0;
		    double partje1 = (double)totalAdHocCar/(double)totalCars * 360;
			g.setColor(Color.decode("#F28E37"));
			g.fillArc(130,25,180,180, startAngle,(int) partje1);

			startAngle += partje1;
			double partje2 = (double)totalParkingPassCar/(double)totalCars * 360;
			g.setColor(Color.decode("#73D2DE"));
			g.fillArc(130,25,180,180, startAngle,(int) partje2);

			startAngle += partje2;
			double partje3 = (double)totalHandicapCar/(double)totalCars * 360;
			g.setColor(Color.decode("#EFD843"));
			g.fillArc(130,25,180,180, startAngle,(int) partje3);	

			startAngle += partje3;
			double partje4 = (double)totalReservationCar/(double)totalCars * 360;
			g.setColor(Color.decode("#28bf29"));
			g.fillArc(130,25,180,180, startAngle,(int) partje4);
			
			
		}
	}
}
