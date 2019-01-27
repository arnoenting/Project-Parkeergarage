package Parkeersimulator;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.UnsupportedEncodingException;

public class SimulatorView extends JFrame {
	private SimulatorController controller;
    private CarParkView carParkView;
    private JPanel buttonPanel;
    private JPanel simulatorPanel;
    private JPanel graphPanel;
    private JPanel infoPanel;
    private CircleGraph totalCarGraph;
    
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    
    //Buttons hier
    private JButton startButton;
    private JButton pauseButton;

    private JButton fasterButton;
    private JButton slowerButton;
    
    private JButton skipDayButton;

    private JLabel timeLabel;
    private int minute;
    private int hour;
    private int day;
    
    // Counters voor totale aantal auto's per soort
    int totalAdHocCar ;
    int totalHandicapCar ;
    int totalParkingPassCar ;
    int totalReservationCar ;
    
    private JLabel moneyLabel;
    
    private JLabel carEnteringLabel;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
        	//Thread runnable aanmaken
        	Runnable runnable = () -> {
				controller.startSimulation();
        	};
        	
        	//Thread aanmaken met de juiste functie
        	Thread thread = new Thread(runnable);
        	
        	thread.start();
		});
        startButton.setBounds(10,5,70,20);
        
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.pauseSimulation();
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        fasterButton = new JButton("Faster");
        fasterButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.speedUpSimulation();
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        slowerButton = new JButton("Slower");
        slowerButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.slowDownSimulation();
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        skipDayButton = new JButton("Skip day");
        skipDayButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.skipTimeSimulation();
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        
        
        // Textlabels
        timeLabel = new JLabel("The time is: 00:00 on a: ");
        moneyLabel = new JLabel("Total money earned thus far: ");
        carEnteringLabel = new JLabel("Total cars in queue: ");
        
        
        // De kleur van het "carPark" gedeelte
        this.setBackground(Color.decode("#404040"));
        
        // Border van de graphPanel
        Border borderGraphPanel = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.decode("#5f5f5f"));
        
        Border borderInfoPanel = BorderFactory.createMatteBorder(6, 6, 6, 4, Color.decode("#5f5f5f")); 
        
        carParkView = new CarParkView();    
        buttonPanel = new JPanel();
        graphPanel = new JPanel();
        simulatorPanel = new JPanel();
        infoPanel = new JPanel();
        totalCarGraph = new CircleGraph();
        
        // Define the panel to hold the button
        simulatorPanel.setPreferredSize(new Dimension(400, 300));
        simulatorPanel.setBackground(Color.decode("#4b4b4b"));
        simulatorPanel.setLayout(new BoxLayout(simulatorPanel,BoxLayout.Y_AXIS));
        simulatorPanel.add(carParkView);
        simulatorPanel.add(infoPanel);
        simulatorPanel.add(buttonPanel);
        
        // Define the panel to hold the buttons
        buttonPanel.setSize(400,200);
        buttonPanel.setBackground(Color.decode("#4b4b4b"));
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);

        buttonPanel.add(fasterButton);
        buttonPanel.add(slowerButton);
        
        buttonPanel.add(skipDayButton);
        
        // Panel for the Graphs
        graphPanel.setSize(200,200);
        graphPanel.setBackground(Color.decode("#4b4b4b"));
        graphPanel.setBorder(borderGraphPanel);
        graphPanel.add(totalCarGraph);

        // Panel for the info about the parking garage
        infoPanel.setSize(200,200);
        infoPanel.setBackground(Color.white);
        infoPanel.setBorder(borderInfoPanel);
        infoPanel.add(timeLabel);
        infoPanel.add(moneyLabel);
        infoPanel.add(carEnteringLabel);
        
        
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.blue);
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
        contentPane.setPreferredSize(new Dimension(1280,800));
        contentPane.add(simulatorPanel);
        contentPane.add(graphPanel);
        pack();
        setVisible(true);

        updateView();
    }
    
    public void addController(SimulatorController controller) {
    	this.controller = controller;
    }

    public void updateView() {
        carParkView.updateView();
    }
    
    public void updateGraph(int totalAdHocCar, int totalParkingPassCar, int totalHandicapCar, int totalReservationCar ) {
    	this.totalAdHocCar = totalAdHocCar;
		this.totalParkingPassCar = totalParkingPassCar;
		this.totalHandicapCar = totalHandicapCar;
		this.totalReservationCar = totalReservationCar;
    }

    public void updateMoney(double moneyEarned) {
    	moneyLabel.setText("Total € earned: " + moneyEarned);
    }

    public void updateTime(int minute, int hour, String day) {
    	timeLabel.setText("The time is: " + displayTime(hour) + ":" + displayTime(minute) + " on a: " + day);
    }
    
    public void updateCarsEntering (int spotsTaken) {
    	carEnteringLabel.setText("Total cars in queue: " + spotsTaken);
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    
                    if (getCarAt(location) == null) {
                    	
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    public Location getFirstFreeLocationCarType (Car car)
    {
    	Location freeLocation = null;
    	switch (car.getClass().getName())
        {
            case "Parkeersimulator.ParkingPassCar":
            	freeLocation = getFirstFreeLocationSpecified(0, 0, 0, 1, 5, 29, 0);
            	break;
            case "Parkeersimulator.ReservationCar":
            	freeLocation = getFirstFreeLocationSpecified(0, getNumberOfFloors()-1, 2, getNumberOfRows()-1, 0, getNumberOfPlaces()-1, 0);
            	break;
            case "Parkeersimulator.HandicapCar":
            	freeLocation = getFirstFreeLocationSpecified(0, 0, 0, 1, 0, 4,0);
            	break;
            case "Parkeersimulator.AdHocCar":
            	freeLocation = getFirstFreeLocationSpecified(0, getNumberOfFloors()-1, 2, getNumberOfRows()-1, 0, getNumberOfPlaces()-1, 0);
            	break;
        }
    	
    	return freeLocation;
    }
    
    public Location getFirstFreeLocationSpecified(int floorStart, int floorEnd, int rowStart, int rowEnd, int placeStart, int placeEnd, int SpecificFloor)
    {
    	for (int floor = floorStart; floor <= floorEnd; floor++) {
    		if (SpecificFloor == floor || SpecificFloor < 0){
	            for (int row = rowStart; row <= rowEnd; row++) {
	                for (int place = placeStart; place <= placeEnd; place++) {
	                	Location location = new Location(floor, row, place);
	                	if (getCarAt(location) == null) {	
	                		return location;
	                	}
	                }
	            }
    		}
            else{
            	for (int row = 0; row < getNumberOfRows(); row++){
	                for (int place = 0; place < getNumberOfPlaces(); place++){
	                	Location location = new Location(floor, row, place);
	                	if (getCarAt(location) == null) {
	                		return location;
	                	}
	                }
	            }
            }
    	}
    	return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    /**
     * Methode om een int als "tijd string" te returnen (met altijd twee characters)
     */
    private String displayTime(int timeValue) {
    	String time = "";
    	
    	if(timeValue < 10) {
    		time += "0" + timeValue;
    	} else {
    		time += timeValue;
    	}
    	
    	return time;
    }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0); 
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(880, 250);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
    		for(int floor = 0; floor < getNumberOfFloors(); floor++){
    			for(int row = 0; row < getNumberOfRows(); row++){
    				for(int place = 0; place < getNumberOfPlaces(); place++){
    					{
            			Location location = new Location(floor, row, place);
                		Car car = getCarAt(location);
                		Color color = car == null ? Color.white : car.getColor();
                		drawPlace(graphics, location, color);	
    					}
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
