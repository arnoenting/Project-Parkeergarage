package Parkeersimulator;

public class SimulatorController {
	Simulator model;
	
	public SimulatorController(Simulator model) {
		this.model = model;
	}
	
	public void startSimulation() {
		model.run();
	}
	
	public void pauseSimulation() {
		model.pause();
	}
	
<<<<<<< HEAD
	public void getDay() {
		System.out.println("Deze dag is een" + model.getDay());
=======
	public void speedUpSimulation() {
		model.adjustSpeed(-25);
	}

	public void slowDownSimulation() {
		model.adjustSpeed(25);
>>>>>>> 9749fa5d61ed955b5d8dd4240bc372f0313cdfff
	}
}
