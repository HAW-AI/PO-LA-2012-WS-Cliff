package haw.po.la.cliff;

/**
 * @author Bjoern Kulas
 */
import java.util.ArrayList;
import java.util.List;

public class Environment implements IEnvironment {

	// dim of cliff environment
	private int envSizeX;
	private int envSizeY;

	// environment boundarys
	private int maxX;
	private int minX;
	private int maxY;
	private int minY;
	// playing field/environment
	private int[][] envField;
	// field description
	private final int envCliffValue;
	private final int envStartValue;
	private final int envFinishValue;
	private final int envFreeFieldValue;
	// agentPos
	private Position agentPos;
	// return Value of the new agentPos
	private int rewardValue;
	// bool to ask if round are running
	private boolean isRunning;

	/**
	 * Default Constructor
	 */
	public Environment() {
		// default envDim
		this(10, 10);
		this.envField = new int[this.envSizeX][this.envSizeY];

		initField();
	}
	
	/**
	 * Constructor whit default environmentValues and settable
	 * environmentDimension
	 * 
	 * @param x
	 *            sets dimension of environment x-axis , [0,x]
	 * @param y
	 *            sets dimension of environment y-axis , [0,y]
	 */
	public Environment(int x, int y) {
		// default description of fields......equivalent to reward?
		// _______ Clif, S, Fi, free
		this(x, y, -100, 0, 10, -1);

	}

	/**
	 * Constructor whit settable environmentValues and environmentDimension
	 * 
	 * @param x
	 *            sets dimension of environment x-axis , [0,x]
	 * @param y
	 *            sets dimension of environment y-axis , [0,y]
	 * @param cliffVal
	 *            value describes a CliffField in Environment in thousandth part
	 * @param startVal
	 *            value describes the StartField in Environment in thousandth
	 *            part
	 * @param finishVal
	 *            value describes the FinishField in Environment in thousandth
	 *            part
	 * @param freeFieldVal
	 *            value describes a free field in Environment in thousandth part
	 */
	public Environment(int x, int y, int cliffVal, int startVal, int finishVal,
			int freeFieldVal) {
		// env
		this.envSizeX = x;
		this.envSizeY = y;
		// fields

		this.envCliffValue = cliffVal;
		this.envStartValue = startVal;
		this.envFinishValue = finishVal;
		this.envFreeFieldValue = freeFieldVal;
		
	}

	/**
	 * initialize the environment a.k.a field
	 */
	private void initField() {
		// set min,max x-y-coordinates
		this.maxX = this.envField.length - 1;
		this.minX = 0;
		this.maxY = this.envField[0].length - 1;
		this.minY = 0;

		// set freeField, maybe freeFieldValue changes
		for (int x = this.minX; x < this.maxX; x++) {
			for (int y = this.minY + 1; y < this.maxY; y++) {
				this.envField[x][y] = this.envFreeFieldValue;
			}
		}
		// set start field
		this.envField[this.minX][this.minY] = this.envStartValue;
		// set finish field
		this.envField[this.maxX][this.minY] = this.envFinishValue;
		// set cliff fields
		for (int iX = (this.minX + 1); iX < this.maxX; iX++) {
			this.envField[iX][this.minY] = this.envCliffValue;
		}
	}

	public Pair<Position, Double> nextState(Position pos, Direction dir) {
		// create list, contains possible directions from pos
		List<Direction> possibleDir = new ArrayList<Direction>();

		// check agentPosition is int he space of environment
		if (checkPosInField(pos)) {
			this.agentPos = pos;

		} else {
			System.err
					.println("ERROR:Env - nextState - checkPosInField returns false");
		}
		// } else {
		// throw new Exception("pos is out of environment, pos(" + pos.x()
		// + " , " + pos.y() + ") not in env(" + this.minX + "..."
		// + this.maxX + " , " + this.minY + "..." + this.maxY + ")");
		// }

		// get all possible directions from agentpos
		possibleDir.addAll(getPossibleDirections(this.agentPos));

		// check new direction is possible
		if (possibleDir.contains(dir)) {
			setNewPosAndReward(dir);
		} else {
			// param NULL to get in default case
			setNewPosAndReward(null);

		}

		Double retVal = (double) (this.rewardValue);

		return new Pair<Position, Double>(new Position(this.agentPos.x(),
				this.agentPos.y()), retVal);
	}

	/**
	 * move the agent in space of environment and set the reward for the move
	 * 
	 * @param dir
	 *            is the agent move direction
	 */
	private void setNewPosAndReward(Direction dir) {
		Position newAgentPos = null;

		// set freeFieldValue on old agentPos
		this.envField[this.agentPos.x()][this.agentPos.y()] = this.envFreeFieldValue;
		// set newAgentPos
		switch (dir) {
		case UP:
			newAgentPos = new Position(agentPos.x(), (this.agentPos.y() + 1));
			break;
		case RIGHT:
			newAgentPos = new Position((agentPos.x() + 1), this.agentPos.y());
			break;
		case DOWN:
			newAgentPos = new Position(agentPos.x(), (this.agentPos.y() - 1));
			break;
		case LEFT:
			newAgentPos = new Position((agentPos.x() - 1), this.agentPos.y());
			break;
		default:
			newAgentPos = this.agentPos;
		}
		// save the value of the new agentPosition, it will returned
		this.rewardValue = this.envField[newAgentPos.x()][newAgentPos.y()];
		// set agentValue on new agentPos
		//		speichern nicht notwendig, this.envField[newAgentPos.x()][newAgentPos.y()] = this.envAgentValue;
		// update agentPos
		this.agentPos = newAgentPos;
		
		
		// set isRunning true or false. False if agent move into cliff or finish
		if(((this.rewardValue==this.envCliffValue) |(this.rewardValue==this.envFinishValue))){
		this.isRunning=false;	
		}else{
			this.isRunning=true;
		}
	}

	/**
	 * @param pos
	 *            agentPosition, Type Position
	 * @return a set of possible directions from param pos
	 */
	public List<Direction> getPossibleDirections(Position pos) {
		List<Direction> possibleDirections = new ArrayList<Direction>();

		// up
		if (pos.y() < this.maxY) {
			possibleDirections.add(Direction.UP);
		}
		// right
		if (pos.x() < this.maxX) {
			possibleDirections.add(Direction.RIGHT);
		}
		// down
		if (pos.y() > this.minY) {
			possibleDirections.add(Direction.DOWN);
		}
		// left
		if (pos.x() > this.minX) {
			possibleDirections.add(Direction.LEFT);
		}
		return possibleDirections;

	}

	/**
	 * @parm description of agentPosition, Type Position
	 * @return true if pos inside dim of field
	 */
	private boolean checkPosInField(Position pos) {
		boolean result = false;

		if ((pos.x() >= 0) & (pos.x() < (this.envField.length - 1))) {
			// X ok
			if ((pos.y() >= 0) & (pos.y() < (this.envField[0].length - 1))) {
				// X,Y ok
				result = true;
			}
		}
		return result;
	}

	/**
	 * Get list of field you search, your search will be set by a envValue, that
	 * represent start,finish,cliff or player
	 * 
	 * @param envDesriptionValue
	 * @return
	 */
	private List<Position> searchFieldOnEnv(int envDesriptionValue) {
		List<Position> foundList = new ArrayList<Position>();

		for (int x = 0; x < this.envSizeX; x++) {
			for (int y = 0; y < this.envSizeY; y++) {
				if (envField[x][y] == envDesriptionValue) {
					foundList.add(new Position(x, y));
				}
			}
		}

		return foundList;
	}

	@Override
	/**
	 * Get Position of startField
	 * @return position of start field or null if none or more than one was found
	 */
	public Position getStartPosition() {
		Position retpos = null;
		List<Position> startPos = searchFieldOnEnv(this.envStartValue);
		if (((!startPos.isEmpty()) & (startPos.size() < 2))) {
			retpos = startPos.get(0);
		} else {
			System.out
					.println("ERROR startfeld nicht gefunden oder mehrfach gefunden");
		}

		return retpos;
	}

	@Override
	/**
	 * Get Position of finishField
	 * @return position of finish field
	 */
	public Position getFinishPosition() {
		Position retpos = null;
		List<Position> finishPos = searchFieldOnEnv(this.envFinishValue);
		if (((!finishPos.isEmpty()) & (finishPos.size() < 2))) {
			retpos = finishPos.get(0);
		} else {
			System.out
					.println("ERROR finishField nicht gefunden oder mehrfach gefunden");
		}
		return retpos;
	}

	@Override
	/**
	 * Get List of all Positions that represent the cliff
	 * @return list of cliff positions
	 */
	public List<Position> getCliffPositions() {
		List<Position> retposList = null;
		List<Position> cliffPos = searchFieldOnEnv(this.envCliffValue);
		if (((!cliffPos.isEmpty()) & (cliffPos.size() < this.envSizeX
				* this.envSizeY))) {
			retposList = cliffPos;
		} else {
			System.out
					.println("ERROR cliff nicht gefunden oder zuviele gefunden");
		}
		return retposList;
	}

	@Override
	/**Get num of environment size/fields on x-axis
	 * @return the envSizeX
	 */
	public int getWidth() {
		return envSizeX;
	}

	@Override
	/**Get num of environment size/fields on y-axis
	 * @return the envSizeY
	 */
	public int getHeigth() {
		return envSizeY;
	}

	@Override
	public boolean isRunnig() {
			return isRunning;
	}

}
