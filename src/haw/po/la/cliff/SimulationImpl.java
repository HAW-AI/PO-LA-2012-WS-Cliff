package haw.po.la.cliff;

public class SimulationImpl implements Simulation {
	
	private GuiImpl gui;
	private EnvironmentImpl env;
	private AgentImpl agent;
	private Algo algo;
	
	public SimulationImpl(EnvironmentImpl env){
		this.env = env;
		this.gui = new GuiImpl(env);
		this.algo = new SarsaAlgo();
		this.agent = new AgentImpl(env, algo);
	}
    
    @Override
    public void step() {
    	Position pos = agent.act();
    	gui.render(pos);
    }
}
