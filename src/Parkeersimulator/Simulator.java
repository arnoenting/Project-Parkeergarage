package Parkeersimulator;

import java.util.*;
import java.util.Random;
//import java.util.concurrent.TimeUnit;

public class Simulator {
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private int tickPause = 100; // time between ticks (speed of the simulation)

    int weekDayArrivals= 85; // average number of arriving cars per hour - handicap & resv
    int weekendArrivals = 170; // average number of arriving cars per hour - idem
    
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 35; // average number of arriving cars per hour
    
    int weekDayHandArrivals = 25;
    int weekendHandArrivals = 100;
    
    int weekDayResvArrivals = 50;
    int weekendResvArrivals = 150;

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    double moneyEarned = 0.00;
    
    // Counters voor totale aantal auto's per soort
    int totalAdHocCar = 0;
    int totalHandicapCar = 0;
    int totalParkingPassCar = 0;
    int totalReservationCar = 0;
    
    private boolean isRunning = false;
    
    int carsPassed = 0;

    public Simulator(SimulatorView simulatorView) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.simulatorView = simulatorView;
    }

    public void playPause() {
    	// Zo weet de run functie of hij op pauze moet of juist niet.
    	isRunning = (!isRunning) ? true : false;
    	
    	simulatorView.updatePlayPauseButton(isRunning);
    	
		while(isRunning) {
			tick();
		}
    }
    
    // Obsolete method replaced by a play & pause method
    /*public void pause() {
    	if(isRunning) {isRunning = false;}
    }*/

    private void tick() {
    	advanceTime();
    	handleExit();
    	handleMoney();
    	updateViews();
    	// Pause between ticks.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }
    
    private void manualTick() {
    	advanceTime();
    	handleExit();
    	handleMoney();
    	manualUpdateViews();
    	handleEntrance();
    }
    
    public void skipTime(int amount){
    	for( int i = 0; i < amount; i++ ) {
    		manualTick();
    	}
    	//updateViews();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
            CarPayment(0, new ParkingPassCar(stayLeaveModifier(true)));
            
        }
    } 
    
    public String getDay() {
    	return daysOfTheWeek[day];
    }
    
    private void handleMoney() {
    	simulatorView.updateMoney(moneyEarned);
    }
    
    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	
    	//if(entranceCarQueue > 10)
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
        // Update the time.
        simulatorView.updateTime(minute, hour, getDay());
        //update the graph
        simulatorView.updateGraph(totalAdHocCar, totalParkingPassCar, totalHandicapCar, totalReservationCar);
        //update the parked cars
    	simulatorView.updateCarsEntering(countCars());
    }
    
    private void manualUpdateViews(){
    	simulatorView.tick();
    }
    
    // All the spots taken minus all the open spots
    private int countCars() {
    	int totalSpots = simulatorView.getNumberOfFloors() * simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfRows();
    	int totalSpotsTaken = totalSpots - simulatorView.getNumberOfOpenSpots();
    	return totalSpotsTaken;
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, new AdHocCar(stayLeaveModifier(true)));    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, new ParkingPassCar(stayLeaveModifier(true))); 
        numberOfCars=getNumberOfCars(weekDayHandArrivals, weekendHandArrivals);
        addArrivingCars(numberOfCars, new HandicapCar(stayLeaveModifier(true)));
        numberOfCars=getNumberOfCars(weekDayResvArrivals, weekendResvArrivals);
        addArrivingCars(numberOfCars, new ReservationCar(stayLeaveModifier(true)));
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = simulatorView.getFirstFreeLocationCarType(car);
            
            if (freeLocation != null)
            {
	            simulatorView.setCarAt(freeLocation, car);
            }
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
    	// Let cars pay.
		int i=0;
		while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
	    	System.out.println("payment queue size: " + paymentCarQueue.carsInQueue());
	        Car car = paymentCarQueue.removeCar();
        	CarPayment(car.getMinutesStayed(), car);
        	car.setIsPaying(true);
        	car.setHasToPay(true);
        	carLeavesSpot(car);
            i++;
        	//System.out.println(moneyEarned);
        }
        
        
	}
    
    private void CarPayment(int timeStayed, Car car)
    {
    	switch (car.getClass().getName())
        {
            case "Parkeersimulator.ParkingPassCar":
            	moneyEarned += (50 * 75);
            	break;
            case "Parkeersimulator.ReservationCar":
            	moneyEarned += (timeStayed / 60) * 2.50 + 5;
            	break;
            case "Parkeersimulator.HandicapCar":
            	moneyEarned += (4 * (timeStayed / 60));
            	break;
            case "Parkeersimulator.AdHocCar":
            	moneyEarned += (2.5 * (timeStayed / 60));
            	break;
        }
    }
    
    private void carsLeaving() {
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;
        
        averageNumberOfCarsPerHour *= stayLeaveModifier(false);
        
        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, Car car){
        // Add the cars to the back of the queue.
    	switch(car.getClass().getName()) {
    	case "Parkeersimulator.AdHocCar": 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar(stayLeaveModifier(true)));
            	totalAdHocCar ++;
            }
            break;
    	case "Parkeersimulator.ParkingPassCar":
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new ParkingPassCar(stayLeaveModifier(true)));
            	totalParkingPassCar ++;
            }
            break;
    	case "Parkeersimulator.HandicapCar":
    		for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new HandicapCar(stayLeaveModifier(true)));
            	totalHandicapCar ++;
            }
    		break;
    	case "Parkeersimulator.ReservationCar":
    		for (int i = 0; i < numberOfCars; i++) {
    			ReservationCar reservationCar = new ReservationCar(stayLeaveModifier(true));
    			moneyEarned += (((reservationCar.getMinutesLeft() / 60) * 2.50) + 5);
            	entranceCarQueue.addCar(reservationCar);
            	totalReservationCar ++;
            }
    		break;
    	}
    }
    
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }
    
    public void adjustSpeed(String adjustment) {
    	switch(adjustment)
    	{
    	case "-":
    		switch(tickPause)
			{
			case 400:
				tickPause = 400;
				break;
			case 200:
				tickPause = 400;
				break;
			case 100:
				tickPause = 200;
				break;
			case 50:
				tickPause = 100;
				break;
			case 25:
				tickPause = 50;
				break;
			case 12:
				tickPause = 25;
				break;
			case 6:
				tickPause = 12;
				break;
			case 3:
				tickPause = 6;
				break;
			case 1:
				tickPause = 3;
				break;
    		}
    		break;
    	case "+":
			System.out.println("speed going up");
			switch(tickPause)
			{
			case 400:
				tickPause = 200;
				break;
			case 200:
				tickPause = 100;
				break;
			case 100:
				tickPause = 50;
				break;
			case 50:
				tickPause = 25;
				break;
			case 25:
				tickPause = 12;
				break;
			case 12:
				tickPause = 6;
				break;
			case 6:
				tickPause = 3;
				break;
			case 3:
				tickPause = 1;
				break;
			case 1:
				tickPause = 1;
				break;
    		}
    		break;
    	}
    	System.out.println("The speed is now: " + tickPause);
    }
    
    public double stayLeaveModifier(boolean isArriving)
    {
    	double modifier = 0;
    	if (isArriving)
    	{
    		switch (hour)
	        {
	        case 0:
	        	modifier = 4;
	        	break;
	        case 1:
	        	modifier = 4;
	        	break;
	        case 2:
	        	modifier = 4;
	        	break;
	        case 3:
	        	modifier = 4;
	        	break;
	        case 4:
	        	modifier = 4;
	        	break;
	        case 5:
	        	modifier = 3;
	        	break;
	        case 6:
	        	modifier = 2;
	        	break;
	        case 7:
	        	modifier = 1;
	        	break;
	        case 8:
	        	modifier = 0;
	        	break;
	        case 9:
	        	modifier = 0;
	        	break;
	        case 10:
	        	modifier = 0;
	        	break;
	        case 11:
	        	modifier = 0;
	        	break;
	        case 12:
	        	modifier = 0;
	        	break;
	        case 13:
	        	modifier = 0;
	        	break;
	        case 14:
	        	modifier = 0;
	        	break;
	        case 15:
	        	modifier = 0;
	        	break;
	        case 16:
	        	modifier = 0;
	        	break;
	        case 17:
	        	modifier = 0;
	        	break;
	        case 18:
	        	modifier = 0;
	        	break;
	        case 19:
	        	modifier = 0;
	        	break;
	        case 20:
	        	modifier = 1.5;
	        	break;
	        case 21:
	        	modifier = 3;
	        	break;
	        case 22:
	        	modifier = 4;
	        	break;
	        case 23:
	        	modifier = 5;
	        	break; 
	        }
    	}
    	else
    	{
	    	switch (hour)
	        {
	        case 0:
	        	modifier = 0.4;
	        	break;
	        case 1:
	        	modifier = 0.2;
	        	break;
	        case 2:
	        	modifier = 0.05;
	        	break;
	        case 3:
	        	modifier = 0.05;
	        	break;
	        case 4:
	        	modifier = 0.2;
	        	break;
	        case 5:
	        	modifier = 0.4;
	        	break;
	        case 6:
	        	modifier = 0.7;
	        	break;
	        case 7:
	        	modifier = 0.9;
	        	break;
	        case 8:
	        	modifier = 1;
	        	break;
	        case 9:
	        	modifier = 1;
	        	break;
	        case 10:
	        	modifier = 1.1;
	        	break;
	        case 11:
	        	modifier = 1.1;
	        	break;
	        case 12:
	        	modifier = 1.1;
	        	break;
	        case 13:
	        	modifier = 1.2;
	        	break;
	        case 14:
	        	modifier = 1.2;
	        	break;
	        case 15:
	        	modifier = 1.0;
	        	break;
	        case 16:
	        	modifier = 1.0;
	        	break;
	        case 17:
	        	modifier = 1.0;
	        	break;
	        case 18:
	        	modifier = 1.2;
	        	break;
	        case 19:
	        	modifier = 1.2;
	        	break;
	        case 20:
	        	modifier = 1.2;
	        	break;
	        case 21:
	        	modifier = 1;
	        	break;
	        case 22:
	        	modifier = 0.8;
	        	break;
	        case 23:
	        	modifier = 0.6;
	        	break; 
	        	
	        }
    	}
    	return modifier;
    }

}
