package haw.po.la.cliff;

public class Main {

	public static void main(String[] args) throws Exception {
		EnvironmentImpl env = new EnvironmentImpl(3,5);
		SimulationImpl sim = new SimulationImpl(env);
		GuiImpl gui = new GuiImpl(env, sim);
		sim.addObserver(gui);
	}

}
