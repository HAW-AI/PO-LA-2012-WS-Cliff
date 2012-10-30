package haw.po.la.cliff;

public class Simulation {
	
	private GUI gui;
	private Environment env;
	private Agent agent;
	
	public Simulation(Environment env){
		this.env = env;
		this.gui = new GUI(env);
		//IAlgo algo = new IAlgo();
		this.agent = new Agent(env, null);
	}
	
    public void run() {
    	Position newPos = agent.act();
    	gui.render(newPos);
        // do smth and return smth
    }
    
    // or should we add a method that only does one step and remove run()?
    // it would be easier to separate gui and game logic and still update the
    // gui after each step.
}
