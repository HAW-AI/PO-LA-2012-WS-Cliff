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
    
    public boolean equals(Object obj) {
    	if (this == obj)
                return true;
    	if (obj == null)
    	    return false;
    	if (!(obj instanceof Position))
    	    return false;
    	Position other = (Position) obj;
    	    return compareTo(other) == 0;
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
