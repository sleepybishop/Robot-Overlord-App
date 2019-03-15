package com.marginallyclever.robotOverlord.sixi2Robot;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.marginallyclever.convenience.SpringUtilities;
import com.marginallyclever.robotOverlord.CollapsiblePanel;
import com.marginallyclever.robotOverlord.HTMLDialogBox;
import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.commands.UserCommandSelectNumber;

public class Sixi2RobotControlPanel extends JPanel implements ActionListener, ChangeListener {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 257878994328366520L;

	private final double [] stepSizeOptions = {0.01, 0.05,
											0.1, 0.5, 
			                                1, 5, 
			                                10, 50};

	
	public JSlider fk0,fk1,fk2,fk3,fk4,fk5;
/*
	private JButton fk5pos, fk5neg;
	private JButton fk4pos, fk4neg;
	private JButton fk3pos, fk3neg;
	private JButton fk2pos, fk2neg;
	private JButton fk1pos, fk1neg;
	private JButton fk0pos, fk0neg;
*/
	private JButton ikXpos, ikXneg;
	private JButton ikYpos, ikYneg;
	private JButton ikZpos, ikZneg;
	private JButton ikUpos, ikUneg;
	private JButton ikVpos, ikVneg;
	private JButton ikWpos, ikWneg;
	
	public JLabel xPos,yPos,zPos,uPos,vPos,wPos;
	public JLabel angle5,angle4,angle3,angle2,angle1,angle0;
	private JLabel stepSizeNow;
	private JLabel uid;
	private JSlider stepSizeControl;
	private UserCommandSelectNumber feedRateControl;
	private UserCommandSelectNumber accelerationControl;
	
	private JButton runScript;
	private JButton showDebug;
	private JButton findHome;
	private JButton where;
	private JButton about;
	
	private Sixi2Robot robot=null;
	
	
	private JButton createButton(String name) {
		JButton b = new JButton(name);
		b.addActionListener(this);
		return b;
	}

	
	private JSlider createSlider() {
		JSlider b = new JSlider(JSlider.HORIZONTAL,-90,90,0);
		b.setMajorTickSpacing(45);
		b.setMinorTickSpacing(5);
		b.setPaintTicks(true);
		b.addChangeListener(this);
		return b;
	}

	public Sixi2RobotControlPanel(RobotOverlord gui,Sixi2Robot robot) {
		super();

		JPanel p;
		
		this.robot = robot;

		this.setBorder(new EmptyBorder(0,0,0,0));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints con1 = new GridBagConstraints();
		con1.gridx=0;
		con1.gridy=0;
		con1.weightx=1;
		con1.weighty=1;
		con1.fill=GridBagConstraints.HORIZONTAL;
		//con1.anchor=GridBagConstraints.CENTER;

		CollapsiblePanel speedPanel = createSpeedPanel(gui);
		this.add(speedPanel,con1);
		con1.gridy++;

		CollapsiblePanel stepSizePanel = createStepSizePanel();
		this.add(stepSizePanel,con1);
		con1.gridy++;

		// used for fk 
		CollapsiblePanel fkPanel = new CollapsiblePanel("Forward Kinematics");
		this.add(fkPanel,con1);
		con1.gridy++;
		p = new JPanel(new SpringLayout());
		fkPanel.getContentPane().add(p);
		//p.add(fk5pos = createButton("A5+"));		p.add(angle5 = new JLabel("0.00"));		p.add(fk5neg = createButton("A5-"));
		//p.add(fk4pos = createButton("A4+"));		p.add(angle4 = new JLabel("0.00"));		p.add(fk4neg = createButton("A4-"));
		//p.add(fk3pos = createButton("A3+"));		p.add(angle3 = new JLabel("0.00"));		p.add(fk3neg = createButton("A3-"));
		//p.add(fk2pos = createButton("A2+"));		p.add(angle2 = new JLabel("0.00"));		p.add(fk2neg = createButton("A2-"));
		//p.add(fk1pos = createButton("A1+"));		p.add(angle1 = new JLabel("0.00"));		p.add(fk1neg = createButton("A1-"));
		//p.add(fk0pos = createButton("A0+"));		p.add(angle0 = new JLabel("0.00"));		p.add(fk0neg = createButton("A0-"));
		p.add(new JLabel("J0"));		p.add(fk0=createSlider());		p.add(angle0 = new JLabel("0.00"));
		p.add(new JLabel("J1"));		p.add(fk1=createSlider());		p.add(angle1 = new JLabel("0.00"));
		p.add(new JLabel("J2"));		p.add(fk2=createSlider());		p.add(angle2 = new JLabel("0.00"));
		p.add(new JLabel("J3"));		p.add(fk3=createSlider());		p.add(angle3 = new JLabel("0.00"));
		p.add(new JLabel("J4"));		p.add(fk4=createSlider());		p.add(angle4 = new JLabel("0.00"));
		p.add(new JLabel("J5"));		p.add(fk5=createSlider());		p.add(angle5 = new JLabel("0.00"));
		SpringUtilities.makeCompactGrid(p,
                6, 3, //rows, cols
                5, 5, //initialX, initialY
                5, 5);//xPad, yPad
		
		// used for ik 
		CollapsiblePanel ikPanel = new CollapsiblePanel("Inverse Kinematics");
		this.add(ikPanel, con1);
		con1.gridy++;
		p = new JPanel(new GridLayout(6,3));
		ikPanel.getContentPane().add(p);
		
		p.add(ikXpos = createButton("X+"));		p.add(xPos = new JLabel("0.00"));		p.add(ikXneg = createButton("X-"));
		p.add(ikYpos = createButton("Y+"));		p.add(yPos = new JLabel("0.00"));		p.add(ikYneg = createButton("Y-"));
		p.add(ikZpos = createButton("Z+"));		p.add(zPos = new JLabel("0.00"));		p.add(ikZneg = createButton("Z-"));
		p.add(ikUpos = createButton("U+"));		p.add(uPos = new JLabel("0.00"));		p.add(ikUneg = createButton("U-"));
		p.add(ikVpos = createButton("V+"));		p.add(vPos = new JLabel("0.00"));		p.add(ikVneg = createButton("V-"));
		p.add(ikWpos = createButton("W+"));		p.add(wPos = new JLabel("0.00"));		p.add(ikWneg = createButton("W-"));

		CollapsiblePanel miscPanel = new CollapsiblePanel("Misc");
		this.add(miscPanel, con1);
		con1.gridy++;
		p = new JPanel(new GridLayout(5,1));
		miscPanel.getContentPane().add(p);
		
		p.add(runScript = createButton("Run script"));
		p.add(showDebug = createButton("Toggle debug view"));
		p.add(findHome = createButton("Find Home"));
		p.add(where = createButton("Where"));
		p.add(about = createButton("About this robot"));
	}
	
	protected CollapsiblePanel createSpeedPanel(RobotOverlord gui) {
		CollapsiblePanel speedPanel = new CollapsiblePanel("Limits");
		
		GridBagConstraints con2 = new GridBagConstraints();
		con2.gridx=0;
		con2.gridy=0;
		con2.fill=GridBagConstraints.HORIZONTAL;
		con2.anchor=GridBagConstraints.NORTHWEST;
		con2.weighty=1;
		con2.weightx=0.25;

		feedRateControl = new UserCommandSelectNumber(gui,"Speed",(float)robot.getFeedRate());
		feedRateControl.addChangeListener(this);
		speedPanel.getContentPane().add(feedRateControl,con2);
		con2.gridy++;

		accelerationControl = new UserCommandSelectNumber(gui,"Acceleration",(float)robot.getAcceleration());
		accelerationControl.addChangeListener(this);
		speedPanel.getContentPane().add(accelerationControl,con2);
		con2.gridy++;
		
		return speedPanel;
	}
	
	
	protected CollapsiblePanel createStepSizePanel() {
		CollapsiblePanel stepSizePanel = new CollapsiblePanel("Step size");
		
		GridBagConstraints con2 = new GridBagConstraints();
		con2.gridx=0;
		con2.gridy=0;
		con2.fill=GridBagConstraints.HORIZONTAL;
		con2.anchor=GridBagConstraints.NORTHWEST;
		con2.weighty=1;
		con2.weightx=0.25;
		
		double stepSize = robot.getStepSize();
		int stepSizeIndex;
		for(stepSizeIndex=0;stepSizeIndex<stepSizeOptions.length;++stepSizeIndex) {
			if( stepSizeOptions[stepSizeIndex] >= stepSize )
				break;
		}
		stepSizeNow = new JLabel(Double.toString(stepSizeOptions[stepSizeIndex]),JLabel.CENTER);
		java.awt.Dimension dim = stepSizeNow.getPreferredSize();
		dim.width = 50;
		stepSizeNow.setPreferredSize(dim);
		stepSizePanel.getContentPane().add(stepSizeNow,con2);

		stepSizeControl = new JSlider(0,stepSizeOptions.length-1,stepSizeIndex);
		stepSizeControl.addChangeListener(this);
		stepSizeControl.setMajorTickSpacing(stepSizeOptions.length-1);
		stepSizeControl.setMinorTickSpacing(1);
		stepSizeControl.setPaintTicks(true);
		con2.anchor=GridBagConstraints.NORTHEAST;
		con2.fill=GridBagConstraints.HORIZONTAL;
		con2.weightx=0.75;
		con2.gridx=1;
		stepSizePanel.getContentPane().add(stepSizeControl,con2);
		
		return stepSizePanel;
	}

	protected void setSpeed(double speed) {
		robot.setStepSize(speed);
		stepSizeNow.setText(Double.toString(robot.getStepSize()));
	}
	
	public int drivenIndex=-1;
	
	public void stateChanged(ChangeEvent e) {
		Object subject = e.getSource();
		if( subject == stepSizeControl ) {
			int i=stepSizeControl.getValue();
			setSpeed(stepSizeOptions[i]);
		}
		if( subject == feedRateControl ) {
			robot.setFeedRate(feedRateControl.getValue());
		}
		if( subject == accelerationControl ) {
			robot.setAcceleration(accelerationControl.getValue());
		}
		
		{
			if( subject == fk0 ) {
				robot.setFKAxis(0,fk0.getValue());
			}
			if( subject == fk1 ) {
				robot.setFKAxis(1,fk1.getValue());
			}
			if( subject == fk2 ) {
				robot.setFKAxis(2,fk2.getValue());
			}
			if( subject == fk3 ) {
				robot.setFKAxis(3,fk3.getValue());
			}
			if( subject == fk4 ) {
				robot.setFKAxis(4,fk4.getValue());
			}
			if( subject == fk5 ) {
				robot.setFKAxis(5,fk5.getValue());
			}
		}
	}
	
	
	// arm5 controls
	public void actionPerformed(ActionEvent e) {
		Object subject = e.getSource();			
		/*
		if( subject == fk5pos ) robot.moveA(1);
		if( subject == fk5neg ) robot.moveA(-1);
		if( subject == fk4pos ) robot.moveB(1);
		if( subject == fk4neg ) robot.moveB(-1);
		if( subject == fk3pos ) robot.moveC(1);
		if( subject == fk3neg ) robot.moveC(-1);
		if( subject == fk2pos ) robot.moveD(1);
		if( subject == fk2neg ) robot.moveD(-1);
		if( subject == fk1pos ) robot.moveE(1);
		if( subject == fk1neg ) robot.moveE(-1);
		if( subject == fk0pos ) robot.moveF(1);
		if( subject == fk0neg ) robot.moveF(-1);
		*/
		if( subject == ikXpos ) robot.moveX(1);
		if( subject == ikXneg ) robot.moveX(-1);
		if( subject == ikYpos ) robot.moveY(1);
		if( subject == ikYneg ) robot.moveY(-1);
		if( subject == ikZpos ) robot.moveZ(1);
		if( subject == ikZneg ) robot.moveZ(-1);
		
		if( subject == ikUpos ) robot.moveU(1);
		if( subject == ikUneg ) robot.moveU(-1);
		if( subject == ikVpos ) robot.moveV(1);
		if( subject == ikVneg ) robot.moveV(-1);
		if( subject == ikWpos ) robot.moveW(1);
		if( subject == ikWneg ) robot.moveW(-1);
		
		if( subject == findHome ) robot.findHome();
		if( subject == runScript ) runScript();
		if( subject == showDebug ) robot.toggleDebug();
		if( subject == where ) doWhere();
		if( subject == about ) doAbout();
	}
	
	protected void runScript() {
		// TODO list script files on SD card, let user pick one?
		// TODO auto-select the last used script?
		robot.sendLineToRobot("D4 ACT0.NGC");
	}
	
	protected void doWhere() {
		robot.sendLineToRobot("M114");
	}
	
	protected void doAbout() {
		HTMLDialogBox box = new HTMLDialogBox();
		box.display(this.getRootPane(), "<html><body>"
				+"<h1>SIXI 2 Robot Arm</h1>"
				+"<p>Created by Dan Royer (dan@marginallyclever.com).</p><br>"
				+"<p>A six axis manipulator.</p><br>"
				+"</body></html>", "About "+this.robot.getDisplayName());
	}
	
	
	public void setUID(long id) {
		if(uid!=null) {
			uid.setText("Evil Minion #"+Long.toString(id));
		}
	}
}