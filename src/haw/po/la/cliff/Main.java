package haw.po.la.cliff;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		List<Position> cliffList = new ArrayList<Position>();
		cliffList.add(new Position(1,0));
		cliffList.add(new Position(2,0));
		cliffList.add(new Position(3,0));
		cliffList.add(new Position(4,0));
		cliffList.add(new Position(5,0));
		cliffList.add(new Position(6,0));
		
		Environment env = new Environment(3,8,new Position(0,0), new Position(7,0),cliffList);
		
		Simulation sim = new Simulation(env);
		Thread.sleep(100);//wait for x secs
		sim.step();
		
//		System.out.println("CLIFF\n");
//
//		// TEST ENVIRONMENT
//		Environment env = new Environment();
//		Position startPos = new Position(0, 0);
//		//1 step
//		System.out.println("startPos:(" + startPos.x() + " , " + startPos.y()+ ") , UP");
//		List<Direction> dirList = env.getPossibleDirections(startPos);
//
//			System.out.println("dirList: UP="+ (dirList.contains(Direction.UP))+"\tRIGHT="+ (dirList.contains(Direction.RIGHT)+"\tDOWN="+(dirList.contains(Direction.DOWN)+"\tLEFT="+(dirList.contains(Direction.LEFT)))));
//	
//		Pair<Position, Double> reward = env.nextState(startPos, Direction.UP);
//		System.out.println("newPos: (" + reward.first.x() + " , "+ reward.first.y() + ")\treward: " + reward.second);
//		startPos=reward.first;
//		System.out.println("\n");
//		//2 step
//		System.out.println("Pos:(" + startPos.x() + " , " + startPos.y()+ ") , UP");
//		dirList.clear();
//		dirList = env.getPossibleDirections(startPos);
//		
//			System.out.println("dirList: UP="+ (dirList.contains(Direction.UP))+"\tRIGHT="+ (dirList.contains(Direction.RIGHT)+"\tDOWN="+(dirList.contains(Direction.DOWN)+"\tLEFT="+(dirList.contains(Direction.LEFT)))));
//		
//		reward=null;
//		reward = env.nextState(startPos, Direction.UP);
//		System.out.println("newPos: (" + reward.first.x() + " , "+ reward.first.y() + ")\treward: " + reward.second);
//		System.out.println("\n");
		
		
		
	}

}
