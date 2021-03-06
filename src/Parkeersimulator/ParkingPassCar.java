package Parkeersimulator;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.decode("#73D2DE");
	
    public ParkingPassCar(double hourModifier) {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60 + (hourModifier * 60));
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
