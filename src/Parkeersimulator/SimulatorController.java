package Parkeersimulator;

import javax.swing.*;

public class SimulatorController {
	Simulator model;
	
	public SimulatorController(Simulator model) {
		this.model = model;
	}
	
	//Methode tick() van (Simulator)model is wat de view update. 
	public void run() {
		model.run();
	}
	
	public void testButtonFunctie() {
		System.out.println("Hallo wereld.");
	}
}
