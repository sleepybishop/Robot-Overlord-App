package com.marginallyclever.robotOverlord.robot;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jogamp.opengl.GL2;

/**
 * Move the entire robot relative to the world
 * @author dan royer
 *
 */
public class RobotInstructionTravel implements RobotInstruction {
	public String getName() {
		return "Travel";
	}

	@Override
	public JComponent getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(GL2 gl2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeForward(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeBackward(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getProgress() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
