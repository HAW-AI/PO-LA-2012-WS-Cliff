package haw.po.la.cliff;

import java.util.HashMap;

public class AlgoImpl extends ReinforcementAlgo{

	
	public AlgoImpl(){
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
	}
	
	@Override
	public void learn(Position initialPos, Direction dir,
			Pair<Position, Double> result) {
		Pair<Position, Direction> key = new Pair<Position, Direction>(initialPos, dir);
		if(brain.containsKey(key)){
			brain.put(key, (brain.get(key) + result.second())/2);
		}else{
			brain.put(key,result.second());
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

}
