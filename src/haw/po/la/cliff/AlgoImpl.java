package haw.po.la.cliff;

public class AlgoImpl implements IAlgo{

	public AlgoImpl(){}
	
	@Override
	public void learn(Position initialPos, Direction dir, Pair<Position, Double> result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Direction getDirection(Position pos) {
		int act = (int)(Math.random()*4);
		Direction dir;
		switch (act){
		case 0: dir = Direction.UP; break;
		case 1: dir = Direction.LEFT; break;
		case 2: dir = Direction.DOWN; break;
		case 3: dir = Direction.RIGHT; break;	
		default: dir = Direction.DOWN; break;
		}
		return dir;
	}
	

}