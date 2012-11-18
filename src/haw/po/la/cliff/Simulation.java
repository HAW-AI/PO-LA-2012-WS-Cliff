package haw.po.la.cliff;

public interface Simulation {

	/**
	 * Perform a simulation step
	 * @return 
	 */
	public void step();

	public void setAlgo(Algorithm algo);

	public void stop();

	public void go(int i);
	
}
