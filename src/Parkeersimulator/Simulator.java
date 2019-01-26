package Parkeersimulator;

import java.util.*;
import java.util.Random;
//import java.util.concurrent.TimeUnit;

public class Simulator {

	//Auto types voor arriving cars
	private static final String FREEPARK = "1";
	private static final String ABO = "2";
	private static final String HAND = "3";
	private static final String RESV = "4";
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private String[] daysOfTheWeek = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    private int tickPause = 100;
    private int previousTickPause;

    int weekDayArrivals= 85; // average number of arriving cars per hour - handicap & resv
    int weekendArrivals = 170; // average number of arriving cars per hour - idem
    
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    
    int weekDayHandArrivals = 25;
    int weekendHandArrivals = 10;
    
    int weekDayResvArrivals = 50;
    int weekendResvArrivals = 150;

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    double moneyEarned = 0.00;
    
    private boolean isRunning = false;

    public Simulator(SimulatorView simulatorView) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.simulatorView = simulatorView;
    }

    public void run() {
    	if(!isRunning) {
    		isRunning = true;
    		if(tickPause > 99999999)tickPause = previousTickPause;
    		
			for(int i = 0; i < 999999999; i++) {
				tick();
			}
    	}
    }
    
    public void pause() {
    	previousTickPause = tickPause;
    	tickPause = 999999999;
    	isRunning = false;
    }

    private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        simulatorView.setTime(minute, hour, day);
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
        }
        
    }
    
    public String getDay() {
    	return daysOfTheWeek[day];
    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
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
        simulatorView.updateTime();
    }
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, FREEPARK);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, ABO); 
        numberOfCars=getNumberOfCars(weekDayHandArrivals, weekendHandArrivals);
        addArrivingCars(numberOfCars, HAND);
        numberOfCars=getNumberOfCars(weekDayResvArrivals, weekendResvArrivals);
        addArrivingCars(numberOfCars, RESV);
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
            Car car = paymentCarQueue.removeCar();

            //Handling payment through checking what kind of car it is and checking the time they stayed TODO quality check
            if(car.getHasToPay() && !(car instanceof ParkingPassCar)) {
            	
            	/* Test code om te laten zien wat voor auto betaald
            	if(car instanceof HandicapCar) System.out.println("Dit is een handicap auto");
            	if(car instanceof ReservationCar) System.out.println("Dit is een reservatie auto");
            	if(car instanceof AdHocCar) System.out.println("Dit is een vrije parkeer auto");
            	if(car instanceof ParkingPassCar) System.out.println("Dit is een abonnement auto");
            	*/
            	
            	car.setIsPaying(true);
            	car.setHasToPay(true);
            	moneyEarned += 2.5;
            	
            	//System.out.println(moneyEarned);
            }
            
            carLeavesSpot(car);
            i++;
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

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case FREEPARK: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case ABO:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;
    	case HAND:
    		for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new HandicapCar());
            }
    		break;
    	case RESV:
    		for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ReservationCar());
            }
    		break;
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }
    
    public void adjustSpeed(int adjustment) {
    	if(tickPause + adjustment > 0)tickPause += adjustment;
    	System.out.println("The speed is now: " + tickPause);
    }

}
