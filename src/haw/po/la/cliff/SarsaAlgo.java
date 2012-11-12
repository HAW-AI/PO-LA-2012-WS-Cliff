package haw.po.la.cliff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SarsaAlgo implements Algo{

	//On-policy
	protected Map<Pair<Position,Direction>, Double> brain;
	private double alpha; //Lernrate
	public Position startPos = new Position(0,0);
	//TODO get startPos etc
	private List<Pair<Pair<Position,Direction>,Double>> episode = new ArrayList<Pair<Pair<Position,Direction>,Double>>();
	
	public SarsaAlgo(double alpha){
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
		this.alpha = alpha;
	}
	
	public SarsaAlgo(){
		this.brain = new HashMap <Pair<Position,Direction>, Double>();
		this.alpha = 0.2;
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
	public void learn(Position initialPos, Direction dir,Position resultingPos, Double reward) {
		//idea: hold list with steps = episode
		episode.add(new Pair(new Pair(initialPos, dir), reward));
		
		if(resultingPos == startPos){
			//start new episode
			episode.add(new Pair(new Pair(resultingPos,null),null));
			learnEpisode();
			
			episode.clear();
		}//else: add to running episode
	}
			
	private void learnEpisode(){	
		
		for(int i = 0; i < episode.size()-2; i++){
			Pair<Position,Direction> key = episode.get(i).first();
			if(brain.containsKey(episode.get(i).first())){
				brain.put(key, (double)Math.round((brain.get(key) + alpha * (episode.get(i).second() + 0.8 * episode.get(i+1).second() - brain.get(key)))*10)/10 );
			}else{
				brain.put(key, episode.get(i).second());
			}
		}
		brain.put(episode.get(episode.size()-2).first(), episode.get(episode.size()-2).second()); //last step to goal/cliff
		
//		double nextReward = Double.MIN_VALUE;
//		for(Direction d : Direction.values()){
//			if(brain.containsKey(new Pair<Position, Direction>(resultingPos,d))){
//				nextReward = Math.max(brain.get(new Pair<Position, Direction>(resultingPos,d)), nextReward);
//			}else{
//				brain.put(new Pair<Position,Direction>(resultingPos,d),-1.0);
//			}
//		}
//		if(nextReward == Double.MIN_VALUE){ nextReward = -1.0;}
//		
//		Pair<Position, Direction> key = new Pair<Position, Direction>(initialPos, dir);
//		if(brain.containsKey(key)){
//			brain.put(key, (double)Math.round((brain.get(key) + alpha * (reward + 0.8 * nextReward - brain.get(key)))*10)/10 );
//		}else{
//			brain.put(key, reward);
//		}
		
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
