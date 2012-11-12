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
		this.algo = new ValueIterationAlgo(env);
		this.agent = new AgentImpl(env, algo);
		this.isRunning = false;
		System.out.println(algo);

        System.out.println("start: " + env.getStartPosition());
        System.out.println("finish: " + env.getFinishPosition());
		for (Position pos : env.getCliffPositions()) {
	        System.out.println("cliff: " + pos);
		}
	}
    
    @Override
    public void step() {
        if (shouldStartEpisode()) {
            startEpisode();
        }

        System.out.println("before act: " + position);
        position = agent.act();
        gui.render(position);
        System.out.println("after act: " + position);

        if (shouldEndEpisode()) {
            endEpisode();
        }
    }
    
    private boolean shouldStartEpisode() {
        return !isRunning();
    }
    
    private boolean shouldEndEpisode() {
        System.out.println("  position: " + position);
        System.out.println("  isRunning: " + isRunning());
        System.out.println("  is finish: " + env.getFinishPosition().equals(position));
        System.out.println("  is cliff: " + env.getCliffPositions().contains(position));
        return isRunning()
                && (env.getFinishPosition().equals(position) ||
                        env.getCliffPositions().contains(position));
    }
    
    private void startEpisode() {
        agent.setPosition(env.getStartPosition());
        position = env.getStartPosition();
        algo.startEpisode();
        isRunning = true;
        System.out.println("start episode");
    }
    
    private void endEpisode() {
        algo.endEpisode();
        isRunning = false;
        System.out.println("end episode");
    }
    
    public boolean isRunning() {
        return isRunning;
    }
}
