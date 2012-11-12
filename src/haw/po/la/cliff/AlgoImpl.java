package haw.po.la.cliff;

import java.util.HashMap;
import java.util.Map;

public class AlgoImpl implements Algo{

	protected Map<Pair<Position,Direction>, Double> brain;
	
	@Override
	public String toString(){
		String str = "Brain:\n";
		
		for (Map.Entry<Pair<Position, Direction>, Double> entry : brain.entrySet()) {
			str += entry.getKey() + " => " + entry.getValue() + "\n";
		}
		
		return str;
	}
	
	public AlgoImpl(){
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
	}
	
	@Override
	public void learn(Position initialPos, Direction dir, Position resultingPos, Double reward) {
		Pair<Position, Direction> key = new Pair<Position, Direction>(initialPos, dir);
		if(brain.containsKey(key)){
			brain.put(key, (brain.get(key) + reward)/2);
		}else{
			brain.put(key, reward);
		}
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
		
		int probability = (int)(Math.random()*10)+1;  //1-10
		if(probability == 1){
			dir = dir.leftOfThis() ;
		}else if(probability == 10){
			dir = dir.rightOfThis();
		}
		return dir;
	}

	@Override
	public void startEpisode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endEpisode() {
		// TODO Auto-generated method stub
		
	}

}
