package haw.po.la.cliff;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GUI extends Applet{
	
	private int startx = 30;
	private int starty = 10;
	private int fieldSize = 30;
	private Graphics g;
	private Frame frame;
	

	//interface environment
	public int width = 8;
	public int height = 3;
	public Position startField = new Position(0,0);
	public Position endField = new Position(width-1,0);
	public List<Position> cliffList = new ArrayList<Position>();
	
//	public GUI(Position startField, Position endField, List<Position> cliffList){
//		this.startField = startField;
//		this.endField = endField;
//		this.cliffList = cliffList;
//		frame = new Frame();
//		frame.setResizable(true);
//		frame.add(this);
//		frame.pack();
//		frame.setSize(width*fieldSize+(2*startx),height*fieldSize+(2*starty));
//		init();
//		frame.setVisible(true);	
//	}
	
	public void init(){
		g = getGraphics();
		//repaint();
		//while(true){
			//drawAgent();
		//}
	}
	
	public void drawAgent(){
		Position pos = getAgentPos();
		g.setColor(Color.red);
		g.fillOval(startx+((fieldSize+1)*(pos.x()-1)+1), starty+((fieldSize+1)*(pos.y()-1)+1), fieldSize-1, fieldSize-1);
	}
	
	public Position getAgentPos(){
		return new Position(2,3);
	}
	
	public void paint (Graphics g){
		this.g = g;
		g.setColor(Color.black);
		g.drawLine(startx, starty, startx, starty+height*(fieldSize+1)); //waagerecht
		g.drawLine(startx, starty, startx+width*(fieldSize+1), starty); //senkrecht
		for(int i = 1; i <= height; i++){
			g.drawLine(startx, starty+((fieldSize+1)*i), startx+width*(fieldSize+1), starty+((fieldSize+1)*i));
		}
		for(int i = 1; i <= width; i++){
			g.drawLine(startx+((fieldSize+1)*i), starty, startx+((fieldSize+1)*i), starty+height*((fieldSize+1)));
		}
		g.setColor(Color.lightGray);
		g.fillRect(startx+((fieldSize+1)*startField.x()+1), starty+((fieldSize+1)*startField.y()+1), fieldSize, fieldSize);
		g.fillRect(startx+((fieldSize+1)*endField.x()+1), starty+((fieldSize+1)*endField.y()+1), fieldSize, fieldSize);
		
		drawAgent();
	}
	
    public void render(Environment env, Position pos) {
        // do smth
        // or change the interface
    }
}
