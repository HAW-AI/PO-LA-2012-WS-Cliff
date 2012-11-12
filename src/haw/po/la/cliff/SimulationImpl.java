package haw.po.la.cliff;

public class SimulationImpl implements Simulation {
	
	private GuiImpl gui;
	private EnvironmentImpl env;
	private AgentImpl agent;
	private Algo algo;
	private boolean isRunning;
	private Position position;

	public SimulationImpl(EnvironmentImpl env){
		this.env = env;
		this.gui = new GuiImpl(env);
//		this.algo = new ValueIterationAlgo(env);
		this.algo = new MonteCarloAlgo(env);
		this.agent = new AgentImpl(env, algo);
		this.isRunning = false;
	}
    
    @Override
    public void step() {
        if (shouldStartEpisode()) {
            startEpisode();
        }

        position = agent.act();
        gui.render(position);

        if (shouldEndEpisode()) {
            endEpisode();
        }
    }
    
    private boolean shouldStartEpisode() {
        return !isRunning();
    }
    
    private boolean shouldEndEpisode() {
        return isRunning() && env.isGameEndingPosition(position);
    }
    
    private void startEpisode() {
        agent.setPosition(env.getStartPosition());
        position = env.getStartPosition();
        algo.startEpisode();
        isRunning = true;
    }
    
    private void endEpisode() {
        algo.endEpisode();
        isRunning = false;
    }
    
    public boolean isRunning() {
        return isRunning;
    }
}
