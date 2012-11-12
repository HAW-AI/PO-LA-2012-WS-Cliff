package haw.po.la.cliff;

public interface Algo {
	
    /**
     * Learn from a state and action combination and the resulting position and reward.
     * @param initialPos   initial position (state)
     * @param dir          direction (action) taken
     * @param resultingPos resulting position (state)
     * @param reward       reward from carrying out the action
     */
	public void learn(Position initialPos, Direction dir, Position resultingPos, Double reward);
		
	
	/**
	 * Get the "best" direction for the next move
	 * @param pos the actual position of the agent
	 * @return the best direction to addiction of parameter Position 
	 */
	public Direction getDirection(Position pos);
	
	/**
	 * Only for episode based algorithms. Signal the start of a new episode.
	 */
	public void startEpisode();

    /**
     * Only for episode based algorithms. Signal the end of an episode.
     */
	public void endEpisode();
	
}
