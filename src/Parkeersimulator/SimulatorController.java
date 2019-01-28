package Parkeersimulator;

public class SimulatorController {
	Simulator model;
	
	public SimulatorController(Simulator model) {
		this.model = model;
	}
	
	public void playPauseSimulation() {
		//model.initRun();
		model.playPause();
	}
	
	/*public void pauseSimulation() {
		model.pause();
	}*/
	
	public String getDay() {
		return model.getDay();
	}
	public void speedUpSimulation() {
		model.adjustSpeed(-25);
	}

	public void slowDownSimulation() {
		model.adjustSpeed(25);
	}
	
	public void skipTimeSimulation(int skipAmount) {
		model.skipTime(skipAmount);
	}
}
