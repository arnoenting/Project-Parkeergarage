package Parkeersimulator;

import java.util.Random;
import java.awt.*;

public class HandicapCar extends Car {
	private static final Color COLOR=Color.decode("#EFD843");
	
    public HandicapCar(double hourModifier) {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60 /*+ (hourModifier * 60)*/);
    	System.out.println(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
