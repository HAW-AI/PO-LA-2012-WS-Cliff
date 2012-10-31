package haw.po.la.cliff;

public interface Agent {

	/**
	 * Execute Agent action, one single step
	 * @return new agent position
	 */
	public Position act();
	
	/**
	 * Get the agent position
	 * @return agentPosition
	 */
	public Position getPosition();
	
}
