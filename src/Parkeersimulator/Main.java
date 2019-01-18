package Parkeersimulator;

public class Main {
	public static void main(String[] args) {
		SimulatorView view = new SimulatorView(3, 6, 30);
		Simulator model = new Simulator(view);
		SimulatorController controller = new SimulatorController(model);
		
		controller.run();
	}
}
