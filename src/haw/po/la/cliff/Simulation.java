package haw.po.la.cliff;

public class Simulation implements ISimulation {
	
	private GUI gui;
	private Environment env;
	private Agent agent;
	
	public Simulation(Environment env){
		this.env = env;
		this.gui = new GUI(env);
		//IAlgo algo = new IAlgo();
		this.agent = new Agent(env, null);
	}
    
    @Override
    public void step() {
    	Position pos = agent.act();
    	gui.render(pos);
    }
}
