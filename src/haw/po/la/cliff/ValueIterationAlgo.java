package haw.po.la.cliff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValueIterationAlgo implements Algo {
    
    private static double DEFAULT_VALUE = 0.0;
    private static double DEFAULT_DISCOUNT_FACTOR = 0.9;
    private static double DEFAULT_DISTANCE_THRESHOLD = 0.1;
        
    private double discountFactor;
    private double distanceThreshold;
        
    Environment environment;
    Map<Position, Pair<Double, Direction>> values;
    
    public ValueIterationAlgo(Environment environment) {
        this(environment, DEFAULT_DISCOUNT_FACTOR);
    }
    
    public ValueIterationAlgo(Environment environment, double discountFactor) {
        this(environment, discountFactor, DEFAULT_DISTANCE_THRESHOLD);
    }
    
    public ValueIterationAlgo(Environment environment, double discountFactor, double distanceThreshold) {
        this.environment = environment;
        this.discountFactor = discountFactor;
        this.distanceThreshold = distanceThreshold;
        
        this.values = new HashMap<Position, Pair<Double,Direction>>(environment.getWidth() * environment.getHeigth());
        
        init();
    }
    
    private void init() {
        double distance = 0.0;
        
        do {
            distance = 0.0;

            for (Position state : possibleStates()) {
                double v = value(state);
                values.put(state, calculateNewValueAndArgument(state));
                distance = Math.max(distance, Math.abs(v - value(state)));
            }
        } while (distance > distanceThreshold);
    }

    @Override
    public void learn(Position initialPos, Direction dir, Position resultingPos, Double reward) {
    }

    @Override
    public Direction getDirection(Position state) {
        return policy(state);
    }
    
    @Override
    public String toString() {
        String str = "ValueIterationAlgo:\n";
        str += "Initial Position => (Value, Taken Direction)\n";
        
        for (Position state : possibleStates()) {
            str += state + "   => " + (values.containsKey(state) ? values.get(state) : "()") + "\n";
        }
        
        return str;
    }
    
    private Set<Position> possibleStates() {
        Set<Position> s = new HashSet<Position>(environment.getWidth() * environment.getHeigth());
        
        for (int i = 0; i < environment.getWidth(); ++i) {
            for (int j = 0; j < environment.getHeigth(); ++j) {
                s.add(new Position(i, j));
            }
        }
        
        return s;
    }
    
    private double reward(Position state, Direction action) {
        return environment.nextState(state, action).second();
    }
    
    private Double probability(Position initialState, Position resultingState, Direction action) {
        return environment.nextState(initialState, action).first().equals(resultingState) ? 1.0 : 0.0;
    }
    
    private Pair<Double, Direction> calculateNewValueAndArgument(Position state) {
        List<Pair<Double, Direction>> sums = new ArrayList<Pair<Double, Direction>>(Direction.values().length * possibleStates().size());
        Position initialState = state;
        
        for (Direction action : Direction.values()) {
            double sum = 0.0;
            
            for (Position resultingState : possibleStates()) {
                sum += probability(initialState, resultingState, action) * (reward(initialState, action) + discountFactor * value(resultingState));
            }
            
            sums.add(new Pair<Double, Direction>(sum, action));
        }
        
        if (!sums.isEmpty()) {
            Collections.shuffle(sums);
            
            return Collections.max(sums, new Comparator<Pair<Double, Direction>>() {

                @Override
                public int compare(Pair<Double, Direction> left, Pair<Double, Direction> right) {
                    return left.first().compareTo(right.first());
                }
                
            });
        } else {
            return new Pair<Double, Direction>(Double.NaN, Direction.DOWN);
        }
    }
    
    private double value(Position state) {
        return values.containsKey(state) ? values.get(state).first() : DEFAULT_VALUE;
    }
    
    private Direction policy(Position state) {
        return values.containsKey(state) ? values.get(state).second() : Direction.randomDirection();
    }

    @Override
    public void startEpisode() {
    }

    @Override
    public void endEpisode() {
    }
}
