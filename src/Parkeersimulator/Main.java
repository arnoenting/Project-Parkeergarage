package Parkeersimulator;

import java.awt.Dimension;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		//Maak de view aan
		SimulatorView view = new SimulatorView(3, 6, 30); //aantal floors, rows, places
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//view.setPreferredSize(new Dimension(1280,800));
		//view.setVisible(true);
		
		//Maak het model aan
		Simulator model = new Simulator(view);
		
		//Maak de controller aan
		SimulatorController controller = new SimulatorController(model);
		
		//Voeg de controller toe aan de view
		view.initController(controller);
	}
}