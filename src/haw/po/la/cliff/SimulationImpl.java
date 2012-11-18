package haw.po.la.cliff;

public class SimulationImpl implements Simulation {
	
	private GuiImpl gui;
	private EnvironmentImpl env;
	private AgentImpl agent;
	private Algo algo;
	private boolean isRunning;
	private Position position;

	public SimulationImpl(EnvironmentImpl env, GuiImpl gui){
		this.env = env;
		this.gui = gui;
//		this.algo = new ValueIterationAlgo(env);
		this.algo = new MonteCarloAlgo(env);
		this.agent = new AgentImpl(env, algo);
		this.isRunning = false;
	}
	
	public SimulationImpl(EnvironmentImpl env){
		this.env = env;
		this.gui = new GuiImpl(env);
//		this.algo = new ValueIterationAlgo(env);
		this.algo = new MonteCarloAlgo(env);
		this.agent = new AgentImpl(env, algo);
		this.isRunning = false;
	}
	
	public void setAlgo(Algorithm algorithm){
		Algo newAlgo = null;
		switch (algorithm){
		case RANDOM: newAlgo = new RandomAlgo(); break;
		case VALUE_ITERATION: newAlgo = new ValueIterationAlgo(env); break;
		case SARSA: newAlgo = new SarsaAlgo(); break;
		case Q_LEARNING: newAlgo = new QlearningAlgo(); break;
		case MONTE_CARLO: newAlgo = new MonteCarloAlgo(env); break;
		default: this.algo = new RandomAlgo();
		}
		this.algo = newAlgo;
		this.agent = new AgentImpl(env, algo);
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
    
    //private volatile Thread run = Thread.currentThread();
    public void stop(){
    	//run.interrupt();
    }
    
    public void go(int stepTime){
    	//TODO run and interrupt step-loop
//    	while (!run.isInterrupted()) {
//    	    try {
//    	        step();
//    	        Thread.sleep(stepTime);
//    	    } catch (InterruptedException ex) {
//    	        run.interrupt();
//    	    }
//    	    run.interrupt();
//    	}
    	
//    	while(run){
//    		step();
//    		try {
//				Thread.sleep(stepTime);
//				if(!run){break;}
//			} catch (InterruptedException e) {}
//    	}
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
