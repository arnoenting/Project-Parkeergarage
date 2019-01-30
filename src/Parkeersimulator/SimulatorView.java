package Parkeersimulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SimulatorView extends JFrame {
	private SimulatorController controller;
    private CarParkView carParkView;
    private JPanel buttonPanel;
    private JPanel simulatorPanel;
    private JPanel carParkViewStats;
    private JPanel carParkViewStatsR1;
    private JPanel carParkViewStatsR2;
    private JPanel graphPanel;
    private JPanel infoPanel;
    private CircleGraph totalCarGraph;
    private BarGraph barChart;
    
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    
    //Buttons hier
    private JButton playPauseButton;

    private JButton fasterButton;
    private JButton slowerButton;
    
    private JButton skipHourButton;
    private JButton skipDayButton;
    private JButton skipWeekButton;
        
    // Counters voor totale aantal auto's per soort
    int totalAdHocCar ;
    int totalHandicapCar ;
    int totalParkingPassCar ;
    int totalReservationCar ;
    
    //Text labels hier
    private JLabel moneyLabel;
    private JLabel carEnteringLabel;
    private JLabel speedLabel; 
    private JLabel timeLabel;
    private JLabel LegendaAdHocCar;
    private JLabel LegendaHandicapCar;
    private JLabel LegendaParkingPasCar;
    private JLabel LegendaReservationCar;
    private JLabel carQueueLabel;
    

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        super("title");
        //setLayout(new FlowLayout());
		//this.setPreferredSize(new Dimension(1280,800));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
       
        // Start knop aanmaken
        playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(e -> {
        	//Thread runnable aanmaken
        	Runnable runnable = () -> {
				controller.playPauseSimulation();
        	};
        	
        	//Thread aanmaken met de juiste functie
        	Thread thread = new Thread(runnable);
        	
        	thread.start();
		});
        playPauseButton.setBounds(10,5,70,20);
        
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
        
        skipHourButton = new JButton("Skip hour");
        skipHourButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.skipTimeSimulation(60);
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        skipDayButton = new JButton("Skip Day");
        skipDayButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.skipTimeSimulation(1440);
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        skipWeekButton = new JButton("Skip Week");
        skipWeekButton.addActionListener(e -> {
        	Runnable runnable = () -> {
        		controller.skipTimeSimulation(10080);
        	};
        	
        	Thread thread = new Thread(runnable);
        	 
        	thread.start();
        });
        
        
        
        // Textlabels
        timeLabel = new JLabel("Time: 00:00 Day: Monday");
        timeLabel.setForeground(Color.white);
        
        LegendaAdHocCar = new JLabel("AdHocCar:       ");
        LegendaAdHocCar.setForeground(Color.white);
        LegendaAdHocCar.setAlignmentX(0);
        LegendaAdHocCar.setAlignmentY(0);
        
        LegendaHandicapCar = new JLabel("HandicapCar:       ");
        LegendaHandicapCar.setForeground(Color.white);
        
        LegendaParkingPasCar = new JLabel("ParkingPasCar:        ");
        LegendaParkingPasCar.setForeground(Color.white);
        
        LegendaReservationCar = new JLabel("ReservationCar:        ");
        LegendaReservationCar.setForeground(Color.white);
        
        moneyLabel = new JLabel("Total money earned thus far: ");
        
        carEnteringLabel = new JLabel("Total cars parked: ");
        
        speedLabel = new JLabel("The speed is: ");
        
        carQueueLabel = new JLabel("The queue counts 0 cars, 0 cars have passed the garage.");
        
        
        // De kleur van het "carPark" gedeelte
        //this.setBackground(Color.decode("#101010"));
        
        // Border van de graphPanel
        Border borderGraphPanel = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.decode("#5f5f5f"));
        
        Border borderInfoPanel = BorderFactory.createMatteBorder(6, 6, 6, 4, Color.decode("#5f5f5f")); 
        
        carParkView = new CarParkView();    
        carParkViewStats = new JPanel();
        carParkViewStatsR1 = new JPanel();
        carParkViewStatsR2 = new JPanel();
        buttonPanel = new JPanel();
        graphPanel = new JPanel();
        simulatorPanel = new JPanel();
        infoPanel = new JPanel();
        totalCarGraph = new CircleGraph();
        barChart = new BarGraph();
       
        
        //Add stats that will be above the simulator to the carParkViewStats;
        carParkViewStatsR1.setBackground(Color.decode("#4b4b4b"));
        carParkViewStatsR1.add(timeLabel);
        carParkViewStatsR2.setBackground(Color.decode("#4b4b4b"));
        carParkViewStatsR2.add(LegendaAdHocCar);
        carParkViewStatsR2.add(LegendaHandicapCar);
        carParkViewStatsR2.add(LegendaParkingPasCar);
        carParkViewStatsR2.add(LegendaReservationCar);
        
        carParkViewStats.setLayout(new BoxLayout(carParkViewStats,BoxLayout.Y_AXIS));
        carParkViewStats.setBackground(Color.decode("#4b4b4b"));
        carParkViewStats.add(carParkViewStatsR1);
        //carParkViewStats.add(carParkViewStatsR2);
        // Add general info text to carParkView
        carParkView.add(carParkViewStats);
        
        // Define the panel to hold the button
        simulatorPanel.setSize(400, 300);
        simulatorPanel.setBackground(Color.decode("#4b4b4b"));
        simulatorPanel.setLayout(new BoxLayout(simulatorPanel,BoxLayout.Y_AXIS));
        simulatorPanel.add(carParkView);
        simulatorPanel.add(infoPanel);
        simulatorPanel.add(buttonPanel);
        
        // Define the panel to hold the buttons
        buttonPanel.setSize(400,200);
        buttonPanel.setBackground(Color.decode("#4b4b4b"));
        buttonPanel.add(playPauseButton);

        buttonPanel.add(fasterButton);
        buttonPanel.add(slowerButton);
        
        buttonPanel.add(skipHourButton);
        buttonPanel.add(skipDayButton);
        buttonPanel.add(skipWeekButton);
        
        // Panel for the Graphs

        graphPanel.setSize(500,500);
        graphPanel.setBackground(Color.decode("#4b4b4b"));
        graphPanel.setBorder(borderGraphPanel);
        graphPanel.add(totalCarGraph);
        graphPanel.add(barChart);

        // Panel for the info about the parking garage
        infoPanel.setSize(200,200);
        infoPanel.setBackground(Color.white);
        infoPanel.setBorder(borderInfoPanel);
        infoPanel.add(carQueueLabel);
        infoPanel.add(moneyLabel);
        infoPanel.add(carEnteringLabel);
        infoPanel.add(speedLabel);
        
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.blue);
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
        contentPane.setPreferredSize(new Dimension(1280,800));
        contentPane.add(simulatorPanel);
        contentPane.add(graphPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        updateView();
    }
    
    public void initController(SimulatorController controller) {
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
    	timeLabel.setText("Time: " + displayTime(hour) + ":" + displayTime(minute) + " Day: " + day);
    }
    
    public void updateCarQueue(CarQueue queue, int carsPassed) {
    	carQueueLabel.setText("The queue counts " + queue.carsInQueue() + " cars, " + carsPassed + " cars have passed the garage.");
    }
    
    public void updatePlayPauseButton(boolean isRunning) {
    	playPauseButton.setText("Play");
    	
    	if(isRunning) {
    		playPauseButton.setText("Pause");
    	}
    }
    
    public void updateCarsEntering (int spotsTaken) {
    	carEnteringLabel.setText("Total cars parked: " + spotsTaken);
    }
    
    public void updateSpeed(int speedUpAndDown) {
    	speedLabel.setText("The speed is: " + speedUpAndDown);
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
        private BufferedImage carParkBuffer = new BufferedImage(800, 500, BufferedImage.TYPE_INT_ARGB);    
        private BufferedImage lagenda;
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0); 
            
            try {
            	lagenda = ImageIO.read(getClass().getResourceAsStream("/lagenda.png"));
            }
            catch (IOException e)
            {
            	e.printStackTrace();
            }
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
        	
        	
        	
            if (carParkBuffer == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
            	g.setColor(Color.decode("#4b4b4b"));
            	g.fillRect(0, 0, 800, 250);
                g.drawImage(lagenda, 400 - lagenda.getWidth()/2, 25, null);
                g.drawImage(carParkBuffer, 0, 0, null);
                
            }
            else {
                // Rescale the previous image.
            	g.setColor(Color.decode("#4b4b4b"));
            	g.fillRect(0, 0, currentSize.width, currentSize.height);
                g.drawImage(lagenda, 400 - lagenda.getWidth()/2, 25,currentSize.width,currentSize.height, null);
                g.drawImage(carParkBuffer, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            }
            Graphics graphics = carParkBuffer.getGraphics();
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
