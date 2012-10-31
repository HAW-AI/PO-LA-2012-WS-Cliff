package haw.po.la.cliff;

public class AgentImpl {
	
	private EnvironmentImpl env;
	private Algo algo;
	public Position agentPos;
	
	public AgentImpl(EnvironmentImpl env, Algo algo){
		this.env = env;
		this.algo = algo;
		agentPos = env.getStartPosition();
	}
	
    public Position act() {
    	Direction dir = algo.getDirection(agentPos);
        Pair<Position, Double> posAndReward = env.nextState(agentPos, dir);
        agentPos = posAndReward.first();
        algo.learn(agentPos, dir, posAndReward);
        return posAndReward.first();
    }
    
    public Position getPosition(){
    	return agentPos;
    }
}
