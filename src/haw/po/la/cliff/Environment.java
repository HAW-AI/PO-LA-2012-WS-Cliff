package haw.po.la.cliff;

public class Environment {
    public Pair<Position, Double> nextState(Position pos, Direction dir) {
        return new Pair<Position, Double>(new Position(-1, -1), 0.0);
    }
}
