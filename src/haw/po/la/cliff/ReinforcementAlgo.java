package haw.po.la.cliff;

import java.util.Map;

public abstract class ReinforcementAlgo implements Algo{

	protected Map<Pair<Position,Direction>, Double> brain;
	
	@Override
	public String toString(){
		String str = "Brain:\n";
		
		for (Map.Entry<Pair<Position, Direction>, Double> entry : brain.entrySet()) {
			str += entry.getKey() + " => " + entry.getValue() + "\n";
		}
		
		return str;
	}
}
