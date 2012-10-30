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
    
    public boolean equals (Position other){
    	return((this.x() == other.x()) && (this.y() == other.y()));
    }
    
    //contains in Position-List muss noch funktionieren
}
