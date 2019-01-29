package Parkeersimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class CircleGraph extends JPanel{
	int totalAdHocCar;
	int totalParkingPassCar;
	int totalHandicapCar;
	int totalReservationCar;
	private BufferedImage circleBuffer = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);;
	
	public CircleGraph() {
		
	}
	
	public CircleGraph(int totalAdHocCar, int totalParkingPassCar, int totalHandicapCar, int totalReservationCar) {
		this.totalAdHocCar = totalAdHocCar;
		this.totalParkingPassCar = totalParkingPassCar;
		this.totalHandicapCar = totalHandicapCar;
		this.totalReservationCar = totalReservationCar;
		
		
	}
	
	public void paintComponent(Graphics g) {
		
		
		g.setColor(Color.decode("#bfbfbf"));
		g.fillRect(0, 0, 400, 400);
		g.setColor(Color.BLACK);
		g.fillRect(0, 50, 400, 400);
		g.drawImage(circleBuffer, 0, 0, null);
		
		int startAngle = 0;
		int intNumber = totalAdHocCar;
		g.setColor(Color.decode("#F28E37"));
		//g.fillArc(0,0,100,100, startAngle, intNumber);
		
		startAngle += intNumber;
		int intSecondNumber = totalParkingPassCar;
		g.setColor(Color.decode("#73D2DE"));
		//g.fillArc(0,0,100,100, startAngle, intSecondNumber);
		
		startAngle += intSecondNumber;
		int intThirdNumber = totalHandicapCar;
		g.setColor(Color.decode("#EFD843"));
		//g.fillArc(0,0,100,100, startAngle, intThirdNumber);	
		
		startAngle += intThirdNumber;
		int intFourthNumber = totalReservationCar;
		g.setColor(Color.decode("#28bf29"));
		//g.fillArc(0,0,100,100, startAngle, intFourthNumber);
	
	}
	
	public void updateGraph()
	{
		repaint();
	}
}
