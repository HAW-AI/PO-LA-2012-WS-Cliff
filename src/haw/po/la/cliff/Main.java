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
		
		//EnvironmentImpl env = new EnvironmentImpl(3,8,new Position(0,0), new Position(7,0),cliffList);
		EnvironmentImpl env = new EnvironmentImpl(3,5);
		
		SimulationImpl sim = new SimulationImpl(env);
		for(int i = 0; i < 10000; i++){
		Thread.sleep(50);//wait for x secs
		sim.step();
		}
	}

}
