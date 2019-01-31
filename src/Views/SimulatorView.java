package Views;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import Controllers.SimulatorController;
import Parkeersimulator.Car;
import Parkeersimulator.CarQueue;
import Parkeersimulator.Location;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SimulatorView extends JFrame {
	private SimulatorController controller;
	
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JPanel legendaPanel;
	private CarParkView carParkView;
	private JPanel infoPanel;
	private JPanel buttonPanel;
	
	private JPanel leftInfoPanel;
	private JPanel rightInfoPanel;
	
	private CircleGraph circleGraphPanel;
	private JPanel lineGraphPanel;
	private BarGraph barGraphPanel;
    
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
    private JLabel carsPassedLabel;
    


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
        
        moneyLabel = new JLabel("Money earned: € 0.0",  SwingConstants.LEFT);
        
        carEnteringLabel = new JLabel("Cars parked: 0", SwingConstants.LEFT);
        
        speedLabel = new JLabel("The speed is: 100", SwingConstants.LEFT);
        
        carQueueLabel = new JLabel("The queue counts: 0 cars", SwingConstants.LEFT);
        
        carsPassedLabel = new JLabel("Cars passed because of a long queue: 0", SwingConstants.LEFT);
        
        
        // Border van de graphPanel
        //Border borderGraphPanel = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.decode("#5f5f5f"));
        
        //Border borderInfoPanel = BorderFactory.createMatteBorder(6, 6, 6, 4, Color.decode("#5f5f5f")); 
        
        mainPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        
        legendaPanel = new JPanel();
        carParkView = new CarParkView();
        infoPanel = new JPanel();
        buttonPanel = new JPanel();
        
        circleGraphPanel = new CircleGraph();
        lineGraphPanel = new JPanel();
        barGraphPanel = new BarGraph();
        
        leftInfoPanel = new JPanel();
        rightInfoPanel = new JPanel();
        
        
        mainPanel.setBackground(Color.decode("#4b4b4b"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(1280, 800));

        leftPanel.setBackground(Color.decode("#4b4b4b"));
        leftPanel.setMaximumSize(new Dimension(840, 800));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        rightPanel.setBackground(Color.decode("#4b4b4b"));
        rightPanel.setMaximumSize(new Dimension(440, 800));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        legendaPanel.setBackground(Color.darkGray);
        legendaPanel.setMaximumSize(new Dimension(840, 50));
        legendaPanel.add(timeLabel);
        
        carParkView.setMaximumSize(new Dimension(840, 420));
        
        leftInfoPanel.setBackground(Color.white);
        leftInfoPanel.setPreferredSize(new Dimension(410, 170));
        leftInfoPanel.setLayout(new GridLayout(0, 1));
        leftInfoPanel.add(moneyLabel);
        leftInfoPanel.add(carEnteringLabel);
        leftInfoPanel.add(carQueueLabel);
        leftInfoPanel.add(carsPassedLabel);
        leftInfoPanel.add(speedLabel);
        
        
        rightInfoPanel.setBackground(Color.white);
        rightInfoPanel.setPreferredSize(new Dimension(410, 170));
        rightInfoPanel.setLayout(new GridLayout(0, 1));
        //rightInfoPanel.add();
        
        infoPanel.setBackground(Color.gray);
        infoPanel.setMaximumSize(new Dimension(840, 180));
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(leftInfoPanel);
        infoPanel.add(rightInfoPanel);
        
        buttonPanel.setBackground(Color.decode("#4b4b4b"));
        buttonPanel.setMaximumSize(new Dimension(840, 150));
        buttonPanel.add(playPauseButton);
        buttonPanel.add(fasterButton);
        buttonPanel.add(slowerButton);
        buttonPanel.add(skipHourButton);
        buttonPanel.add(skipDayButton);
        buttonPanel.add(skipWeekButton);
        
        
        barGraphPanel.setBackground(Color.gray);
        barGraphPanel.setMaximumSize(new Dimension(440, 265));
        
        circleGraphPanel.setBackground(Color.gray);
        circleGraphPanel.setMaximumSize(new Dimension(440, 265));

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        leftPanel.add(legendaPanel);
        leftPanel.add(carParkView);
        leftPanel.add(infoPanel);
        leftPanel.add(buttonPanel);
        
        rightPanel.add(barGraphPanel);
        rightPanel.add(circleGraphPanel);
        
        
        Container contentPane = getContentPane();
        contentPane.add(mainPanel);
        //contentPane.setBackground(Color.blue);
        //contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
        //contentPane.setPreferredSize(new Dimension(1280,800));
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updateView();
    }
    
    public void initController(SimulatorController controller) {
    	this.controller = controller;
    }

    public void updateView() {
        carParkView.updateView();
    }
    
    public void updateGraph(int day, double moneyEarnedPerDay, double moneyEarnedPerWeek, int totalAdHocCar, int totalParkingPassCar, int totalHandicapCar, int totalReservationCar, int totalCars ) {
   	circleGraphPanel.setData(totalAdHocCar, totalParkingPassCar, totalHandicapCar, totalReservationCar, totalCars);
		barGraphPanel.setData(moneyEarnedPerDay,moneyEarnedPerWeek, day);
    }

    public void updateMoney(double moneyEarned) {
    	moneyLabel.setText("Money earned: € " + moneyEarned);
    }

    public void updateTime(int minute, int hour, String day) {
    	timeLabel.setText("Time: " + displayTime(hour) + ":" + displayTime(minute) + " Day: " + day);
    }
    
    public void updateCarQueue(CarQueue queue, int carsPassed) {
    	carQueueLabel.setText("The queue counts " + queue.carsInQueue() + " cars, ");
    	carsPassedLabel.setText("Cars passed because of a long queue: " + carsPassed);
    }
    
    public void updatePlayPauseButton(boolean isRunning) {
    	playPauseButton.setText("Play");
    	
    	if(isRunning) {
    		playPauseButton.setText("Pause");
    	}
    }
    
    public void updateCarsEntering (int spotsTaken) {
    	carEnteringLabel.setText("Cars parked: " + spotsTaken);
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
        private BufferedImage carParkBuffer = new BufferedImage(840, 500, BufferedImage.TYPE_INT_RGB);    
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
            return new Dimension(840, 250);
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
            	//g.setColor(Color.decode("#4b4b4b"));
            	//g.fillRect(0, 0, 800, 250);
                //g.drawImage(lagenda, 400 - lagenda.getWidth()/2, 25, null);
                g.drawImage(carParkBuffer, 0, 0, null);
                
            }
            else {
                // Rescale the previous image.
            	//g.setColor(Color.decode("#4b4b4b"));
            	//g.fillRect(0, 0, currentSize.width, currentSize.height);
                //g.drawImage(lagenda, 400 - lagenda.getWidth()/2, 25,currentSize.width,currentSize.height, null);
                g.drawImage(carParkBuffer, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
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
