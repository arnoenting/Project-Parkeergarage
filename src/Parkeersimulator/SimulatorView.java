package Parkeersimulator;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private SimulatorController controller;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private JButton testButton;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        testButton = new JButton("Dit is een testknop.");
        testButton.addActionListener(e -> {
			controller.testButtonFunctie();
		});
        testButton.setBounds(10,5,70,20);
        
        carParkView = new CarParkView();
        
        // Define the panel to hold the buttons
        JPanel simulatorPannel = new JPanel();
        simulatorPannel.setPreferredSize(new Dimension(300, 300));
        simulatorPannel.setBackground(Color.green);
        simulatorPannel.add(carParkView);
        
        // Define the panel to hold the buttons
        JPanel buttonPannel = new JPanel();
        buttonPannel.setSize(200,200);
        buttonPannel.setBackground(Color.yellow);
        buttonPannel.add(testButton);
        
        
        
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.blue);
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
        contentPane.setPreferredSize(new Dimension(820,700));
        contentPane.add(simulatorPannel);
        contentPane.add(buttonPannel);
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
    
    public Location getFirstFreeLocationSpecified(int floorStart, int floorEnd, int rowStart, int rowEnd, int placeStart, int placeEnd)
    {
    	for (int floor = floorStart; floor < floorEnd; floor++) {
            for (int row = rowStart; row < rowEnd; row++) {
                for (int place = placeStart; place < placeEnd; place++) {
                	Location location = new Location(floor, row, place);
                	if (getCarAt(location) == null) {
                		return location;
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
            return new Dimension(850, 420);
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
            				if (floor == 0 && row < 2) {
            					if (place < 4) {
            					Location location = getFirstFreeLocation();
	                    		Car car = getCarAt(location);
	                    		Color color = car == null ? Color.black : car.getColor();
	                    		drawPlace(graphics, location, color);
                    		} else {
                    			Location location = new Location(floor, row, place);
	                    		Car car = getCarAt(location);
	                    		Color color = car == null ? Color.magenta : car.getColor();
	                    		drawPlace(graphics, location, color);	
                    		}
                    	} else { 
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
