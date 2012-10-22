package haw.po.la.cliff;

import static haw.po.la.cliff.Direction.*;

public class Agent {
    public Position act(Environment env, Position pos) {
        Pair<Position, Double> posAndReward = env.nextState(pos, LEFT);
        return pos;
    }
}
