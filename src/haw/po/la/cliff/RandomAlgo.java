package haw.po.la.cliff;

public class RandomAlgo implements Algo{
	
	public RandomAlgo(){}
	
	@Override
	public void learn(Position initialPos, Direction dir, Pair<Position, Double> result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Direction getDirection(Position pos) {
		return Direction.randomDirection();
	}
	

}
