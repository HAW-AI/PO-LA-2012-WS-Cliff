package haw.po.la.cliff;

public interface IAlgo {
	
	/**
	 * Set an initial-pair of position and direction and an result-pair of actual position and got reward
	 * @param initial the pair which the agent use by environment
	 * @param result the pair of result got by environment
	 */
	public void learn(Position initialPos, Direction dir, Pair<Position, Double> result);
		
	
	/**
	 * Get the "best" direction for the next move
	 * @param pos the actual position of the agent
	 * @return the best direction to addiction of parameter Position 
	 */
	public Direction getDirection(Position pos);
}
