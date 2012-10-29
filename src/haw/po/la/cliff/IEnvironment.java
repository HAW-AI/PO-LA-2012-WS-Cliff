package haw.po.la.cliff;

import java.util.List;

public interface IEnvironment {

	
	/**
	 * do/play action
	 * @param pos Position of the agent
	 * @param dir Direction to be use
	 * @return Pair of new agent position and reward
	 */
	public Pair<Position, Double> nextState(Position pos, Direction dir);
	
	/**
	 * Get the start position 
	 * @return the position of the start field
	 */
	public Position getStartPosition();
	
	/**
	 * Get the finish position
	 * @return the position of the finish field
	 */
	public Position getFinishPosition();
	
	/**
	 * Get the cliff positions
	 * @return a list of the cliff field
	 */
	public List<Position> getCliffPositions();
	
	/**
	 * Get width of environment
	 * @return width of environment/number of fields on x-axis
	 */
	public int getWidth();
	
	/**
	 * Get height of environment
	 * @return height of environment/number of fields on y-axis
	 */
	public int getHeigth();
	
}
