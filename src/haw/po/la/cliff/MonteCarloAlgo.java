package haw.po.la.cliff;

import java.util.ArrayList;

/**
 * 
 * @author Bjoern Kulas
 * 
 */

public class MonteCarloAlgo implements Algo {

	/*
	 * Untested class
	 * 
	 * TODO If algo works, then combine arrays and use a map
	 * 
	 * ATTENTION: The class, that is using an object of this class, is to be in
	 * charge of control the start and end of each episode
	 */
	private Environment env;
	private double epsilon;

	/*
	 * x,y -> Pos a -> action
	 */
	private double[][][] q;// [x][y][a]
	private ArrayList<Double>[][][] returns;// [x][y][a]
	private double[][][] policy;// [x][y][a]

	private double explore;
	private double exploit;

	private ArrayList<Pair<Position, Direction>> pairList;

	/**
	 * default epsilon = 0.1
	 * 
	 * @param env
	 *            Environment
	 */
	public MonteCarloAlgo(Environment env) {
		this(0.1, env);
	}

	public MonteCarloAlgo(double epsilon, Environment env) {
		this.epsilon = epsilon;
		this.env = env;
		this.explore = this.epsilon / Direction.values().length;
		this.exploit = (1 - this.epsilon)
				+ (this.epsilon / Direction.values().length);

		initArrays();
	}

	@SuppressWarnings("unchecked")
	/**
	 * create and initialize the arrays: q, returns and policy
	 */
	private void initArrays() {
		this.q = new double[this.env.getWidth()][this.env.getHeigth()][Direction
				.values().length];
		this.returns = new ArrayList[this.env.getWidth()][this.env.getHeigth()][Direction
				.values().length];
		this.policy = new double[this.env.getWidth()][this.env.getHeigth()][Direction
				.values().length];

		for (int w = 0; w <= this.env.getWidth(); w++) {
			for (int h = 0; h <= this.env.getHeigth(); h++) {
				for (int a = 0; a < Direction.values().length; a++) {
					q[w][h][a] = 0.0;// arbitrary value = 0.0
					returns[w][h][a] = new ArrayList<Double>();// empty list
					policy[w][h][a] = this.explore;/*
													 * an arbitrary epsilon-soft
													 * policy = explore (
													 * epsilon/A(s) )
													 */
				}
			}
		}
	}

	/**
	 * use this method before a new episode is starting
	 */
	public void startEpsiode() {
		this.pairList = new ArrayList<Pair<Position, Direction>>();
	}

	/**
	 * use this method to declare the end of an episode
	 */
	public void endEpisode() {
		this.pairList = null;
	}

	@Override
	public void learn(Position initialPos, Direction dir,
			Position resultingPos, Double reward) {
		Pair<Position, Direction> firstPair = new Pair<Position, Direction>(
				initialPos, dir);
		int indexOfOptimusA = 0;
		double bestEvaluation = 0.0;

		// for each pair (s,a) in episode
		// first occurrence
		if (!this.pairList.contains(firstPair)) {
			this.pairList.add(firstPair);
			// Append r to returns(s,a)
			this.returns[initialPos.x()][initialPos.y()][dir.ordinal()]
					.add(reward);
			// set Q(s,a) <-- avg returns(s,a)
			this.q[initialPos.x()][initialPos.y()][dir.ordinal()] = getAvg(this.returns[initialPos
					.x()][initialPos.y()][dir.ordinal()]);

		}

		// for each s in the episode
		double[] actionEvaluations = this.q[initialPos.x()][initialPos.y()];// save
																			// action
																			// evaluations
																			// of
																			// q(s,a)

		bestEvaluation = actionEvaluations[indexOfOptimusA]; // set startValue:
																// bestEvaluation
																// = 0
		// check each action evaluation of q(s,a) and save best rated action
		for (int i = 0; i < actionEvaluations.length; i++) {
			if (actionEvaluations[i] > bestEvaluation) {
				// set index of action a*
				indexOfOptimusA = i;
			}
		}

		// update policy of all actions of s by explore,
		// policy(s,a) <- epsilon/A(s), if a!=a*
		for (int a = 0; a < this.policy[initialPos.x()][initialPos.y()].length; a++) {
			this.policy[initialPos.x()][initialPos.y()][a] = this.explore;
		}
		// update policy of best evaluated action of s,
		// policy(s,a) <- 1-epsilon+epsilon/A(s), if a==a*
		this.policy[initialPos.x()][initialPos.y()][indexOfOptimusA] = this.exploit;
	}

	@Override
	public Direction getDirection(Position pos) {
		Direction returnDir;
		int exploitEnumIndex = 0;
		int explorerEnumIndex=0;
		boolean containsExploit = false;
		// check policy contains exploit
		for (int i = 0; i < this.policy[pos.x()][pos.y()].length; i++) {
			if (this.policy[pos.x()][pos.y()][i] == this.exploit) {
				containsExploit = true;
				exploitEnumIndex = i;
				i = this.policy[pos.x()][pos.y()].length;
			}
		}
		// if policy contains exploit then choose exploit by probability 90% 
		if (containsExploit) {
			if ((int) (Math.random() * 100) < 90) {//if policy contains exploit  use exploit to 90 %
				returnDir = Direction.values()[exploitEnumIndex];
			} else {
				do{
					explorerEnumIndex = (int) (Math.random() * 4);//and 10% use an explore action
				}while(exploitEnumIndex==explorerEnumIndex);
				returnDir = Direction.values()[explorerEnumIndex];
			}
		} else {
			exploitEnumIndex = (int) (Math.random() * 4);// if policy don't contains an exploit, then random choice
			returnDir = Direction.values()[exploitEnumIndex];
		}

		return returnDir;
	}

	/**
	 * get average value of all values are in parameter arrayList
	 * 
	 * @param arrayList
	 *            an arrayList contains value of type double
	 * @return an average value of all values are in parameter arrayList
	 */
	private double getAvg(ArrayList<Double> arrayList) {
		double avg = 0.0;
		int counter = 0;

		while (arrayList.iterator().hasNext()) {
			if (arrayList.iterator().next() != null) {
				counter++;
				avg += arrayList.iterator().next();
			}
		}

		avg = avg / counter;
		return avg;
	}

}
