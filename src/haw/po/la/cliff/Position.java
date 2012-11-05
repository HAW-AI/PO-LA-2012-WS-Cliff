package haw.po.la.cliff;

public class Position {
	
	private int x;
	private int y;
	
	
    public Position(int x, int y) {
        this.x =x;
        this.y=y;
    }
    
    public int x() {
        return x;
    }
    
    public int y() {
        return y;
    }
    
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
    
    public int compareTo(Position other) {
    	int xCompare = Integer.valueOf(this.x()).compareTo(Integer.valueOf(other.x()));
    	int comp;
    	if( xCompare == 0 ){
    		comp = Integer.valueOf(this.y()).compareTo(Integer.valueOf(other.y()));
    	} else { comp = xCompare;}
    	return comp;
        }
    //contains in Position-List muss noch funktionieren
}
