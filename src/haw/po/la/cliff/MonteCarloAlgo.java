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
	private double policyInit;

	private ArrayList<Pair<Position, Direction>> pairList;
	private List<Position> statelist;
	private int episodeCounter;
	private int lost;
	private int win;

	/**
	 * default epsilon = 0.2
	 * 
	 * @param env
	 *            Environment
	 */
	public MonteCarloAlgo(Environment env) {
		this(0.2, env);
	}

	public MonteCarloAlgo(double epsilon, Environment env) {
		this.epsilon = epsilon;
		this.env = env;
		this.explore = this.epsilon / Direction.values().length;
		this.exploit = getExploit();
		this.policyInit = 1.0 / Direction.values().length;
		
	this.lost=0;
	this.win=0;
		
		initArrays();
	}
	

	/**
	 * use this method before a new episode is starting
	 */
	@Override
	public void startEpisode() {
		this.pairList = new ArrayList<Pair<Position, Direction>>();
		this.statelist = new ArrayList<Position>();
		System.out.println("\n\n\t\tNEW EPSIODE\n\n");
	}

	
	/**
	 * use this method to declare the end of an episode
	 */
	@Override
	public void endEpisode() {
		this.episodeCounter++;
		System.out.println("\nPOLICY\n");
		showPolicy();
		System.out.println("\n\n\t\tEND EPSIODE " + this.episodeCounter	+ "\n\tWIN: "+this.win+"\tLOST: "+this.lost+"\tW/L-RATIO: "+(double)((double)this.win/(double)this.lost)+"\n");
		
		String output="";
		for(int i=0;i<this.statelist.size();i++){
			output= output+"   ("+statelist.get(i).x()+","+statelist.get(i).y()+") ";
		}
		System.out.println("\nSTATES: "+output+"\n");
		
		//for each s in episode
		for(int s=0;s<this.statelist.size();s++){
			int[] actionsEqualsArgMaxA=new int[Direction.values().length];
			int actionsEqualsArgMaxAIndex=0;
			Position state = this.statelist.get(s);
			//arg_max_a Q(s)
				//Q(s)
			double[] qActions=this.q[state.x()][state.y()].clone();
			Arrays.sort(qActions);
			//arg_MAX_A		
			int qActionIndex=qActions.length-1;
			//ARG_MAX_A	
			double arg_max_a=qActions[qActionIndex];
			//for all a elements of A(s)
			for(int d=0;d<Direction.values().length;d++){
				double policyActions = this.q[state.x()][state.y()][d];
				
				//Policy(s,a)  <-- if(a==arg_max_A) this.policy[state.x()][state.y()][d]==this.exploit 
				// 					else			this.policy[state.x()][state.y()][d]==this.explore
				if(arg_max_a==policyActions){
					//save the index of actions, for all actions that fulfill a==arg_max_A
					actionsEqualsArgMaxA[actionsEqualsArgMaxAIndex]=d;
					actionsEqualsArgMaxAIndex++;
				}else{
					this.policy[state.x()][state.y()][d]=this.explore;
				}
			}
			//set random which action, that fulfill a==arg_max_a, is exploit , others explore  
			int numActionsEqualsArgMaxA=actionsEqualsArgMaxAIndex;
			int random = (int) (Math.random()*numActionsEqualsArgMaxA);
			for(int i=0;i<numActionsEqualsArgMaxA;i++){
				if(i==random){
					this.policy[state.x()][state.y()][actionsEqualsArgMaxA[i]]=this.exploit;
				}else{
					this.policy[state.x()][state.y()][actionsEqualsArgMaxA[i]]=this.explore;
				}
			}
		
		}
		System.out.println("NEW POLICY");
		showPolicy();
	}

	@Override
	public Direction getDirection(Position pos) {
		Direction returnDir = null;
		double rand = Math.random();
		double policyValue=0.0;
		int dirIndex=0;
		
		for(int d=0;d<Direction.values().length;d++){
			policyValue+=this.policy[pos.x()][pos.y()][d];
			if(policyValue>rand){
				dirIndex=d;
				d=Direction.values().length;
				
			}
		}
		
		returnDir=Direction.values()[dirIndex];
		return returnDir;
	}

	@Override
	public void learn(Position initialPos, Direction dir,Position resultingPos, Double reward) {
		Pair<Position, Direction> newPair = new Pair<Position, Direction>(initialPos, dir);
		
		if(this.env.getFinishPosition().equals(resultingPos)){
			this.win++;
		}
		if(this.env.getCliffPositions().contains(resultingPos)){
			this.lost++;
		}
		
		// for each pair (s,a) in episode
		// first occurrence
		if (!this.pairList.contains(newPair)) {
			this.pairList.add(newPair);
		
			// Append r to returns(s,a), add r to all states before
			for(int s=0;s<this.pairList.size();s++){
				this.returns[this.pairList.get(s).first().x()][this.pairList.get(s).first().y()][this.pairList.get(s).second().ordinal()].add(reward);
			}
									
			//Q(s,a)<--avg(Returns(s,a))		
			this.q[initialPos.x()][initialPos.y()][dir.ordinal()]=getAvg(this.returns[initialPos.x()][initialPos.y()][dir.ordinal()]);
		}
		
		// save all s ---> for each s in episode 			??????initPos  or resultingPos
		Position newState = new Position(initialPos.x(), initialPos.y());
		if (!this.statelist.contains(newState)) {
			this.statelist.add(newState);
		}
	}
	
	/*
	 * -----------------HELPER-------------------------
	 */

	/**
	 * get average value over all values of arrayList
	 * 
	 * @param arrayList
	 *            an arrayList contains value of type double
	 * @return an average value of all values are in parameter arrayList
	 */
	private double getAvg(ArrayList<Double> arrayList) {
		double avg = 0.0;
	
		for (Double rewardsOfStateActionPair : arrayList) {
			avg += rewardsOfStateActionPair;
		}

		avg = avg / arrayList.size();
		return avg;
	}




	
	private double getExploit() {
		double expl =0.001+(1 - this.epsilon)+ (this.epsilon / Direction.values().length);
		int exploitValue =(int) (1000*expl);
		return (double)exploitValue/1000;
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
					policy[w][h][a] = this.policyInit;// 0,25 --> 25%
				}
			}
		}
	}
	
	
	private void showPolicy() {
		for(int y=0;y<this.policy[0].length;y++){
			System.out.println("-------------------------------------------------------------------------------------------------------------------------");
			System.out.println("\t"+this.policy[0][y][2]+"\t\t|\t"+this.policy[1][y][2]+"\t\t|\t"+this.policy[2][y][2]+"\t\t|\t"+this.policy[3][y][2]+"\t\t|\t"+this.policy[4][y][2]+"\t\t|");
			System.out.println(""+this.policy[0][y][0]+"\t (0,"+y+")\t"+this.policy[0][y][1]+"\t|"+this.policy[1][y][0]+"\t (1,"+y+")\t"+this.policy[1][y][1]+"\t|"+this.policy[2][y][0]+"\t (2,"+y+")\t"+this.policy[2][y][1]+"\t|"+this.policy[3][y][0]+"\t (3,"+y+")\t"+this.policy[3][y][1]	+"\t|"+	this.policy[4][y][0]+"\t (4,"+y+")\t"+this.policy[4][y][1]+"\t|");
			System.out.println("\t"+this.policy[0][y][3]+"\t\t|\t"+this.policy[1][y][3]+"\t\t|\t"+this.policy[2][y][3]+"\t\t|\t"+this.policy[3][y][3]+"\t\t|\t"+this.policy[4][y][3]+"\t\t|");
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
	}
}
