package haw.po.la.cliff;

import java.util.LinkedList;
import java.util.List;

public class SimulationImpl implements Simulation {
	
	private GuiImpl gui;
	private EnvironmentImpl env;
	private AgentImpl agent;
	private Algo algo;
	private boolean isRunning;
	private Position position;
	
	private long stepTime = 50;
	private List<SimulationObserver> observers;

	public SimulationImpl(EnvironmentImpl env){
		this.env = env;
//		this.algo = new ValueIterationAlgo(env);
		this.algo = new MonteCarloAlgo(env);
		this.agent = new AgentImpl(env, algo);
		this.isRunning = false;
		
		this.observers = new LinkedList<SimulationObserver>();
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
        notifyObservers(position);

        if (shouldEndEpisode()) {
            endEpisode();
        }
    }
    
    public void stop(){
        System.out.println("stop");
        isRunning = false;
    }
    
    public void start() {
        System.out.println("start");
        isRunning = true;
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (isRunning) {
                    step();
                    try {
                        Thread.sleep(stepTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.println("exit run loop");
            }
            
        }).start();
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
    
    
    public void addObserver(SimulationObserver observer) {
        observers.add(observer);
    }
    
    private void notifyObservers(Position position) {
        for (SimulationObserver observer : observers) {
            observer.updatePosition(position);
        }
    }
}
