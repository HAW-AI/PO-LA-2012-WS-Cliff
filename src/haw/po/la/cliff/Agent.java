package haw.po.la.cliff;

public class Agent {
	
	private Environment env;
	private AlgoImpl algo;
	public Position agentPos;
	
	public Agent(Environment env, AlgoImpl algo){
		this.env = env;
		this.algo = algo;
		agentPos = env.getStartPosition();
	}
	
    public Position act() {
    	Direction dir = Direction.DOWN;//algo.getDirection(agentPos);
        Pair<Position, Double> posAndReward = env.nextState(agentPos, dir);
        agentPos = posAndReward.first();
        //algo.learn(agentPos, dir, posAndReward);
        return posAndReward.first();
    }
    
    public Position getPosition(){
    	return agentPos;
    }
}
