package Parkeersimulator;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
	private Color COLOR = Color.decode("#CDEDA3");
    private int stayMinutes;
	
    public ReservationCar() {
    	Random random = new Random();
    	stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
    
    @Override
    public void tick() {
    	this.setMinutesLeft(this.getMinutesLeft()-1);
    	if ((stayMinutes - this.getMinutesLeft()) > 15)
    	{
    		COLOR = Color.decode("#28bf29");
    	}
    }
}
