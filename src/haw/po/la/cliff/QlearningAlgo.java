package haw.po.la.cliff;

import java.util.HashMap;
import java.util.Map;

public class QlearningAlgo implements Algo {

	//Off-policy
	protected Map<Pair<Position,Direction>, Double> brain;
	private double alpha; //Lernrate
	private double epsylon;
	
	public QlearningAlgo(double alpha, double epsylon) {
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
		this.alpha = alpha;
		this.epsylon = epsylon;
	}
	
	public QlearningAlgo(double alpha) {
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
		this.alpha = alpha;
		this.epsylon = 0.1;
	}
	
	public QlearningAlgo() {
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
		this.alpha = 0.2;
		this.epsylon = 0.1;
	}
	
	@Override
	public String toString(){
		String str = "Brain:\n";
		
		for (Map.Entry<Pair<Position, Direction>, Double> entry : brain.entrySet()) {
			str += entry.getKey() + " => " + entry.getValue() + "\n";
		}
		
		return str;
	}

	@Override
	public void learn(Position initialPos, Direction dir, Position resultingPos, Double reward) {
		
		double nextReward = Double.MIN_VALUE;
		for(Direction d : Direction.values()){
			if(brain.containsKey(new Pair<Position, Direction>(resultingPos,d))){
				nextReward = Math.max(brain.get(new Pair<Position, Direction>(resultingPos,d)), nextReward);
			}else{
				brain.put(new Pair<Position,Direction>(resultingPos,d),-1.0);
			}
		}
		if(nextReward == Double.MIN_VALUE){ nextReward = -1.0;}
		
		Pair<Position, Direction> key = new Pair<Position, Direction>(initialPos, dir);
		if(brain.containsKey(key)){
			brain.put(key, (double)Math.round((brain.get(key) + alpha * (reward + 0.8 * nextReward - brain.get(key)))*10)/10 );
		}else{
			brain.put(key, reward);
		}
		System.out.println(this);

	}

	@Override
	public Direction getDirection(Position pos) {
		Direction dir = Direction.randomDirection();
		double maxReward = Double.MIN_VALUE;
		for(Direction d : Direction.values()){
			if(brain.containsKey(new Pair<Position, Direction>(pos,d))){
				double oldReward = maxReward;
				maxReward = Math.max(brain.get(new Pair<Position, Direction>(pos,d)), maxReward);
				if(oldReward < maxReward){ dir = d;}
			}
		}
		
		int probability = (int)(Math.random()*1000)+1;  //1-10
		if(probability <= epsylon * 10){
			dir = Direction.randomDirection();
		}
		return dir;
	}

}
