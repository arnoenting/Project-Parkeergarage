package Parkeersimulator;

import java.awt.Dimension;

import javax.swing.*;

import Controllers.SimulatorController;
import Models.Simulator;
import Views.SimulatorView;

public class Main {
	public static void main(String[] args) {
		//Maak de view aan
		SimulatorView view = new SimulatorView(3, 6, 30); //aantal floors, rows, places
		
		//Maak het model aan
		Simulator model = new Simulator(view);
		
		//Maak de controller aan
		SimulatorController controller = new SimulatorController(model);
		
		//Voeg de controller toe aan de view
		view.initController(controller);
	}
}