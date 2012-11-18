package haw.po.la.cliff;

/**
 * 
 * @author Fenja Harbke
 *
 */

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GuiImpl extends Panel implements Gui{
	
	Simulation sim;
	
	//Grid-Params
	private int startx = 30;
	private int starty = 10;
	private int fieldSize = 30;
	private Graphics g;
	private JFrame frame;
	

	//interface environment
	public int width;
	public int height;
	public Position startField;
	public Position endField;
	public List<Position> cliffList;
	public Position agentPos;
	
	//Buttons etc.
	private String[] lernAlgoArr = {"Random","ValueIteration","SARSA","Q-Learning","Monte Carlo"};
	private JComboBox learningAlgos = new JComboBox(lernAlgoArr);
	private Button goButton = new Button("Go");
	private Button stopButton = new Button("Stop");
	
	public GuiImpl(EnvironmentImpl env){
		sim = new SimulationImpl(env,this);
		g = getGraphics();
		this.startField = env.getStartPosition();
		this.endField = env.getFinishPosition();
		this.cliffList = env.getCliffPositions();
		this.width = env.getWidth();
		this.height = env.getHeigth();
		this.agentPos = startField;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.add(this);
		frame.pack();
		frame.setSize(600,400);
		init();
		frame.setVisible(true);	
	}
	
	public void init() {
		g = getGraphics();
		final Panel panel = new Panel(new GridLayout(4,1));
		add(panel);
		panel.add(learningAlgos);
		goButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				goActionPerformed(evt);
			}
		});
		panel.add(goButton);
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				stopActionPerformed(evt);
			}
		});
		panel.add(stopButton);
	}
	
	private void goActionPerformed(ActionEvent evt){
		Algorithm algo = null;
		switch(learningAlgos.getSelectedIndex()){
		case 0: algo = Algorithm.RANDOM; break;
		case 1: algo = Algorithm.VALUE_ITERATION; break;
		case 2: algo = Algorithm.SARSA; break;
		case 3: algo = Algorithm.Q_LEARNING; break;
		case 4: algo = Algorithm.MONTE_CARLO; break;
		default: algo = Algorithm.RANDOM;
		}
		sim.setAlgo(algo);
		sim.go(50);
	}
	
	private void stopActionPerformed(ActionEvent evt){
		sim.stop();
	}
	
	public void paint (Graphics g){
	    if (System.getProperty("os.name").equals("Mac OS X")) {
	        this.g = g;
	    }
		
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
		fillField(startField, Color.lightGray);
		fillField(endField, Color.lightGray);
		
		//cliff
		if(cliffList!=null){
			for(Position p : cliffList){
				fillField(p,Color.black);
			}
		}
		
		drawAgent();
	}

	private void drawAgent(){
		g.setColor(Color.red);
		g.fillOval(startx+((fieldSize+1)*agentPos.x()+1), starty+((fieldSize+1)*agentPos.y()+1), fieldSize, fieldSize);
	}
	
	private void drawClear(){
		Color c;
		if(agentPos == startField || agentPos == endField){
			c = Color.lightGray;
		}else if(cliffList.contains(agentPos)){
			c = Color.black;
		}else{
			c = Color.white;
		}
		fillField(agentPos, c);
	}
	
	private void fillField(Position pos, Color c){
		g.setColor(c);
		g.fillRect(startx+((fieldSize+1)*pos.x()+1), starty+((fieldSize+1)*pos.y()+1), fieldSize, fieldSize);
	}
	
    public void render(Position agentPos) {
    	if (this.agentPos != agentPos){
    		drawClear();
    		this.agentPos = agentPos;
    		drawAgent();
    	}

        if (System.getProperty("os.name").equals("Mac OS X")) {
            setVisible(false);
            setVisible(true);
        }
    }
}
