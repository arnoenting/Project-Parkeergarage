package Parkeersimulator;

public class Main {
	public static void main(String[] args) {
		//Maak de view aan
		SimulatorView view = new SimulatorView(3, 6, 30); //aantal floors, rows, places
		
		//Maak het model aan
		Simulator model = new Simulator(view);
		
		//Maak de controller aan
		SimulatorController controller = new SimulatorController(model);
		
		//Voeg de controller toe aan de view
		view.addController(controller);
		
		//controller.run();
	}
}