package haw.po.la.cliff;

import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("CLIFF\n");

		// TEST ENVIRONMENT
		Environment env = new Environment();
		Position startPos = new Position(0, 0);
		//gui
		GUI gui = new GUI(env);
		//1 step
		System.out.println("startPos:(" + startPos.x() + " , " + startPos.y()+ ") , UP");
		List<Direction> dirList = env.getPossibleDirections(startPos);

			System.out.println("dirList: UP="+ (dirList.contains(Direction.UP))+"\tRIGHT="+ (dirList.contains(Direction.RIGHT)+"\tDOWN="+(dirList.contains(Direction.DOWN)+"\tLEFT="+(dirList.contains(Direction.LEFT)))));
	
		Pair<Position, Double> reward = env.nextState(startPos, Direction.UP);
		System.out.println("newPos: (" + reward.first.x() + " , "+ reward.first.y() + ")\treward: " + reward.second);
		startPos=reward.first;
		System.out.println("\n");
		//2 step
		System.out.println("Pos:(" + startPos.x() + " , " + startPos.y()+ ") , UP");
		dirList.clear();
		dirList = env.getPossibleDirections(startPos);
		
			System.out.println("dirList: UP="+ (dirList.contains(Direction.UP))+"\tRIGHT="+ (dirList.contains(Direction.RIGHT)+"\tDOWN="+(dirList.contains(Direction.DOWN)+"\tLEFT="+(dirList.contains(Direction.LEFT)))));
		
		reward=null;
		reward = env.nextState(startPos, Direction.UP);
		System.out.println("newPos: (" + reward.first.x() + " , "+ reward.first.y() + ")\treward: " + reward.second);
		System.out.println("\n");
		
		//gui
		gui.render(new Position(0,1));
		
		
		
	}

}
