package haw.po.la.cliff;

public class SimulationImpl implements Simulation {
	
	private GuiImpl gui;
	private EnvironmentImpl env;
	private AgentImpl agent;
	
	public SimulationImpl(EnvironmentImpl env){
		this.env = env;
		this.gui = new GuiImpl(env);
		Algo algo = new RandomAlgo();
		this.agent = new AgentImpl(env, algo);
	}
    
    @Override
    public void step() {
    	Position pos = agent.act();
    	gui.render(pos);
    	System.out.println("Reward: "+agent.getReward());
    }
}
