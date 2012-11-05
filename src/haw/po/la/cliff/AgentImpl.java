package haw.po.la.cliff;

import java.util.Map;

public class AgentImpl {
	
	private EnvironmentImpl env;
	private Algo algo;
	private double reward;
	public Position agentPos;
	
	public AgentImpl(EnvironmentImpl env, Algo algo){
		this.env = env;
		this.algo = algo;
		agentPos = env.getStartPosition();
		reward = 100;
	}
	
    public Position act() {
    	Direction dir = algo.getDirection(agentPos);
        Pair<Position, Double> posAndReward = env.nextState(agentPos, dir);
        agentPos = posAndReward.first();
        algo.learn(agentPos, dir, posAndReward);
        reward += posAndReward.second();
        return posAndReward.first();
    }
    
    public double getReward(){ return reward; }
    public Position getPosition(){ return agentPos;}
}
