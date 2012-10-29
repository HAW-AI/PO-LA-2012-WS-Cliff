package haw.po.la.cliff;

/**
 * 
 * @author Fenja Harbke
 *
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.applet.Applet;
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
	public List<Position> cliffList;
	public Position agentPos;
	
	public GUI(Environment env){
		g = getGraphics();
//		this.startField = env.startField;
//		this.endField = endField;
//		this.cliffList = cliffList;
//		this.width = env.envSizeX;
//		this.height = env.envSizeY;
//		this.agentPos = env.envAgentValue;
		frame = new Frame();
		frame.setResizable(true);
		frame.add(this);
		frame.pack();
		frame.setSize(width*fieldSize+(2*startx),height*fieldSize+(2*starty));
		draw(g);
		frame.setVisible(true);	
	}
	
//	public void init(){
//		//g = getGraphics();
//		//repaint();
//		//while(true){
//			//drawAgent();
//		//}
//	}
	
	private void draw (Graphics g){
		this.g = g;
		//Grid
		g.setColor(Color.black);
		g.drawLine(startx, starty, startx, starty+height*(fieldSize+1)); //waagerecht
		g.drawLine(startx, starty, startx+width*(fieldSize+1), starty); //senkrecht
		for(int i = 1; i <= height; i++){
			g.drawLine(startx, starty+((fieldSize+1)*i), startx+width*(fieldSize+1), starty+((fieldSize+1)*i));
		}
		for(int i = 1; i <= width; i++){
			g.drawLine(startx+((fieldSize+1)*i), starty, startx+((fieldSize+1)*i), starty+height*((fieldSize+1)));
		}
		//start & end field
		g.setColor(Color.lightGray);
		g.fillRect(startx+((fieldSize+1)*startField.x()+1), starty+((fieldSize+1)*startField.y()+1), fieldSize, fieldSize);
		g.fillRect(startx+((fieldSize+1)*endField.x()+1), starty+((fieldSize+1)*endField.y()+1), fieldSize, fieldSize);
		//cliff
		if(cliffList!=null){
			g.setColor(Color.black);
			for(Position p : cliffList){
				g.fillRect(startx+((fieldSize+1)*p.x()+1), starty+((fieldSize)*p.y()+1), fieldSize, fieldSize);
			}
		}
		drawAgent();
	}

	private void drawAgent(){
		g.setColor(Color.red);
		g.fillOval(startx+((fieldSize+1)*(agentPos.x()-1)+1), starty+((fieldSize+1)*(agentPos.y()-1)+1), fieldSize-1, fieldSize-1);
	}
	
	private void drawClear(){
		if(agentPos == startField){
			
		}else if(agentPos == endField){
			
		}else if(cliffList.contains(agentPos)){
			
		}
	}
	
    public void render(Position agentPos) {
    	if (this.agentPos != agentPos){
    		drawClear();
    		this.agentPos = agentPos;
    		drawAgent();
    	}
    }
}
