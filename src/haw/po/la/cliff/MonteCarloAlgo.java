package haw.po.la.cliff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private List<Position> statelist;
	private int episodeCounter;
	private int count;

	/**
	 * default epsilon = 0.1
	 * 
	 * @param env
	 *            Environment
	 */
	public MonteCarloAlgo(Environment env) {
		this(0.4, env);
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

		for (int w = 0; w < this.env.getWidth(); w++) {
			for (int h = 0; h < this.env.getHeigth(); h++) {
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
	@Override
	public void startEpisode() {
		this.pairList = new ArrayList<Pair<Position, Direction>>();
		this.statelist = new ArrayList<Position>();
	}

	/**
	 * use this method to declare the end of an episode
	 */
	@Override
	public void endEpisode() {
		
		this.episodeCounter++;
		
		System.out.println("EPSIODE: "+this.episodeCounter+" ended");
		int indexOfOptimusA =10;
		double[] sortedActionEvaluation =new double[4];

		
		for (int i = 0; i < this.pairList.size(); i++) {

			Position initialPos = new Position(this.pairList.get(i).first().x(), this.pairList.get(i).first().y());
			Direction dir = this.pairList.get(i).second();
			
			// /fuer C) for each state s in the episode
			if (!this.statelist.contains(initialPos)) {
				this.statelist.add(initialPos);
			}

			// B) set Q(s,a) <-- avg returns(s,a)
			this.q[initialPos.x()][initialPos.y()][dir.ordinal()] = getAvg(this.returns[initialPos
					.x()][initialPos.y()][dir.ordinal()]);

		}

		// C) for each state in episode:
		for (int i = 0; i < this.statelist.size(); i++) {

			// for each s in the episode
			// save  action  evaluations  of  q(s,a)
			double[] actionEvaluations = this.q[this.statelist.get(i).x()][this.statelist.get(i).y()];

			

			sortedActionEvaluation = actionEvaluations.clone(); 

			Arrays.sort(sortedActionEvaluation);
			double highestEva=sortedActionEvaluation[3];
			
			// check each action evaluation of q(s,a) and save best rated action
			for (int j = 0; j < actionEvaluations.length; j++) {
				if (actionEvaluations[j] == highestEva) {
					// set index of best action a*
					indexOfOptimusA = j;
					j=actionEvaluations.length;
				}
					
			}
			
		

			// update policy of all actions of s by explore,
			// policy(s,a) <- epsilon/A(s), if a!=a*
			for (int a = 0; a < this.policy[this.statelist.get(i).x()][this.statelist.get(i).y()].length; a++) {
				this.policy[this.statelist.get(i).x()][this.statelist.get(i).y()][a] = this.explore;
			}
			// update policy of best evaluated action of s,
			// policy(s,a) <- 1-epsilon+epsilon/A(s), if a==a*
			this.policy[this.statelist.get(i).x()][this.statelist.get(i).y()][indexOfOptimusA] = this.exploit;

		}

		this.pairList = null;
		this.statelist=null;
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	@Override
	public void learn(Position initialPos, Direction dir, Position resultingPos, Double reward) {
		Pair<Position, Direction> firstPair = new Pair<Position, Direction>(initialPos, dir);
		// for each pair (s,a) in episode
		// first occurrence
		if (!this.pairList.contains(firstPair)) {
			this.pairList.add(firstPair);
			// Append r to returns(s,a)
			this.returns[initialPos.x()][initialPos.y()][dir.ordinal()].add(reward);
			System.out.println("\t\t\tREWARD: "+reward);
		}
	}

	@Override
	public Direction getDirection(Position pos) {
		
		this.count++;
		System.out.println("\t\t\t\t"+this.episodeCounter+"\t\t\t"+this.count);
		
		Direction returnDir;
		int exploitEnumIndex = 0;
		int explorerEnumIndex = 0;
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
			if ((int) (Math.random() * 11) < 9) {// if policy contains exploit
													// use exploit to 90 %
				returnDir = Direction.values()[exploitEnumIndex];
				System.out.println("\tDIR 	90	Exploit"+ returnDir);
			} else {
				
				do {
					// and 10% use  an explore action
					explorerEnumIndex = (int) (Math.random() * 4);
				} while (exploitEnumIndex == explorerEnumIndex);
				returnDir = Direction.values()[explorerEnumIndex];
				System.out.println("\tDIR 	10	Explore"+ returnDir);
			}
		} else {
			// if policy don't contains an  exploit, then random choice
			explorerEnumIndex = (int) (Math.random() * 4);
			returnDir = Direction.values()[explorerEnumIndex];
			System.out.println("\tDIR 	Random"+ returnDir);
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
		
		for (Double double1 : arrayList) {
			avg +=	double1;
			counter++;
		}

		avg = avg / counter;
		return avg;
	}

}
