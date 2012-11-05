package haw.po.la.cliff;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;
    public Direction leftOfThis(){
    	Direction dir = this;
    	switch (this){
    	case LEFT: dir = DOWN; break;
    	case UP: dir = LEFT; break;
    	case RIGHT: dir = UP; break;
    	case DOWN: dir = RIGHT; break;
    	}
    	return dir;
    }
    public Direction rightOfThis(){
    	Direction dir = this;
    	switch (this){
    	case LEFT: dir = UP; break;
    	case UP: dir = RIGHT; break;
    	case RIGHT: dir = DOWN; break;
    	case DOWN: dir = LEFT; break;
    	}
    	return dir;
    }
    public static Direction randomDirection(){
    	return Direction.values()[(int)(Math.random()*4)];
    }
}


