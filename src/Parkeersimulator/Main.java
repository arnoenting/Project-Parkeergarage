package Parkeersimulator;

public class Main {
	public static void main(String[] args) {
		Simulator model = new Simulator();
		SimulatorController controller = new SimulatorController(model);
		
		controller.run();
	}
}
