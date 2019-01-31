package Views;

import javax.swing.*;
import java.awt.*;


public class CircleGraph extends JPanel{
	private int totalAdHocCar;
	private int totalParkingPassCar;
	private int totalHandicapCar;
	private int totalReservationCar;
	private int totalCars;
	
	public CircleGraph() {
		//setLayout(null);
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
		
		g.setColor(Color.decode("#4b4b4b")); // background
		g.fillRect(0,0,440,265); 
		
		g.setColor(Color.white);
		g.fillArc(60, 40, 200, 200, 360, 360);
		//System.out.println(totalCars + " is totalcars" + totalAdHocCar + " is total adhoc");
		
		g.drawString("Totaal soorten auto's geparkeerd over de hele simulatie", 60, 20); // The title of the graph
		
		// The legend with all the different type of cars
		g.setColor(Color.decode("#F28E37"));
		g.fillRect(300,80,10,10);
		g.setColor(Color.white);
		g.drawString("AdHocCar", 320, 89);
		
		g.setColor(Color.decode("#73D2DE"));
		g.fillRect(300,120,10,10);
		g.setColor(Color.white);
		g.drawString("ParkingPassCar", 320, 129);
		
		g.setColor(Color.decode("#EFD843"));
		g.fillRect(300,160,10,10);
		g.setColor(Color.white);
		g.drawString("HandicapCar", 320, 169);
		
		g.setColor(Color.decode("#28bf29"));
		g.fillRect(300,200,10,10);
		g.setColor(Color.white);
		g.drawString("ReservationCar", 320, 209);
		
		
		
		if (totalCars != 0) {
			int startAngle = 0;
		    double partje1 = (double)totalAdHocCar/(double)totalCars * 360;
			g.setColor(Color.decode("#F28E37"));
			g.fillArc(60,40,200,200, startAngle,(int) partje1);

			startAngle += partje1;
			double partje2 = (double)totalParkingPassCar/(double)totalCars * 360;
			g.setColor(Color.decode("#73D2DE"));
			g.fillArc(60,40,200,200, startAngle,(int) partje2);

			startAngle += partje2;
			double partje3 = (double)totalHandicapCar/(double)totalCars * 360;
			g.setColor(Color.decode("#EFD843"));
			g.fillArc(60,40,200,200, startAngle,(int) partje3);	

			startAngle += partje3;
			double partje4 = (double)totalReservationCar/(double)totalCars * 360;
			g.setColor(Color.decode("#28bf29"));
			g.fillArc(60,40,200,200, startAngle,(int) partje4);
			
			
		}
	}
}
