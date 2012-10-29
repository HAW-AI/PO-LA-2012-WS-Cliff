package haw.po.la.cliff;

import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("CLIFF");
        
     
        //TEST ENVIRONMENT
		Environment env = new Environment();
    	Position startPos =new Position(0, 0);
    	System.out.println("startPos:("+startPos.x()+" , "+startPos.y()+")");
    	List<Direction> dirList =env.getPossibleDirections(startPos);
      	if(!dirList.isEmpty()){
      		System.out.println("dirList: UP="+(dirList.get(0)==Direction.UP)+" RIGHT="+(dirList.get(1)==Direction.RIGHT));
      	}
    	Pair<Position, Double> reward = env.nextState(startPos, Direction.UP);
    	System.out.println("newPos: ("+reward.first.x()+" , "+reward.first.y()+")\treward: "+reward.second);
    }

}
