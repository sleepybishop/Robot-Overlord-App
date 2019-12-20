package com.marginallyclever.robotOverlord.dhRobot.robots;

import java.util.Iterator;

import javax.vecmath.Vector3d;

import com.jogamp.opengl.GL2;
import com.marginallyclever.robotOverlord.dhRobot.DHLink;
import com.marginallyclever.robotOverlord.dhRobot.DHRobot;
import com.marginallyclever.robotOverlord.dhRobot.solvers.DHIKSolver_SCARA;
import com.marginallyclever.robotOverlord.material.Material;
import com.marginallyclever.robotOverlord.model.ModelFactory;

/**
 * FANUC cylindrical coordinate robot GMF M-100
 * @author Dan Royer
 *
 */
public class DHRobot_SCARA_NM extends DHRobot {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isFirstTime;

	public DHRobot_SCARA_NM() {
		super(new DHIKSolver_SCARA());
		setDisplayName("SCARA NM");
		isFirstTime=true;
	}
	
	@Override
	protected void setupLinks(DHRobot robot) {
		robot.setNumLinks(5);

		// roll
		robot.links.get(0).setD(13.784);
		robot.links.get(0).setR(15);
		robot.links.get(0).flags = DHLink.READ_ONLY_D | DHLink.READ_ONLY_R | DHLink.READ_ONLY_ALPHA;
		robot.links.get(0).rangeMin=-40;
		robot.links.get(0).rangeMax=240;
		
		// roll
		robot.links.get(1).setR(13.0);
		robot.links.get(1).flags = DHLink.READ_ONLY_D | DHLink.READ_ONLY_R | DHLink.READ_ONLY_ALPHA;		
		robot.links.get(1).rangeMin=-120;
		robot.links.get(1).rangeMax=120;
		// slide
		robot.links.get(2).setD(-8);
		robot.links.get(2).flags = DHLink.READ_ONLY_THETA | DHLink.READ_ONLY_R | DHLink.READ_ONLY_ALPHA;
		robot.links.get(2).rangeMin=-10.92600+7.574;
		robot.links.get(2).rangeMax=-10.92600;//-18.5+7.574;
		// roll
		robot.links.get(3).flags = DHLink.READ_ONLY_D | DHLink.READ_ONLY_R | DHLink.READ_ONLY_ALPHA;
		robot.links.get(3).rangeMin=-180;
		robot.links.get(3).rangeMax=180;

		robot.links.get(4).flags = DHLink.READ_ONLY_D | DHLink.READ_ONLY_THETA | DHLink.READ_ONLY_R | DHLink.READ_ONLY_ALPHA;
		robot.links.get(4).rangeMin=0;
		robot.links.get(4).rangeMax=0;
		
		robot.refreshPose();
	}

	public void setupModels() {
		try {
			if(links.get(0).model==null) links.get(0).model = ModelFactory.createModelFromFilename("/SCARA_NM/Scara_base.stl",0.1f);
			if(links.get(1).model==null) links.get(1).model = ModelFactory.createModelFromFilename("/SCARA_NM/Scara_arm1.stl",0.1f);
			if(links.get(2).model==null) links.get(2).model = ModelFactory.createModelFromFilename("/SCARA_NM/Scara_arm2.stl",0.1f);
			if(links.get(4).model==null) links.get(4).model = ModelFactory.createModelFromFilename("/SCARA_NM/Scara_screw.stl",0.1f);
			
			links.get(0).model.adjustOrigin(new Vector3d(-8,0,0));
			links.get(1).model.adjustOrigin(new Vector3d(-15,8,-13.784));
			links.get(1).model.adjustRotation(new Vector3d(0,0,-90));

			links.get(2).model.adjustOrigin(new Vector3d(-13,8,-13.784));
			links.get(2).model.adjustRotation(new Vector3d(0,0,-90));

			links.get(4).model.adjustOrigin(new Vector3d(-8,0,-13.784));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(GL2 gl2) {
		links.get(2).rangeMax=-10.92600+7.574;
		links.get(2).rangeMin=-10.92600-0.5;//-18.5+7.574;
		if( isFirstTime ) {
			isFirstTime=false;
			setupModels();
		}
		
		gl2.glPushMatrix();
			Vector3d position = this.getPosition();
			gl2.glTranslated(position.x, position.y, position.z);
			
			// Draw models
			float r=0.5f;
			float g=0.5f;
			float b=0.5f;
			Material mat = new Material();
			mat.setDiffuseColor(r,g,b,1);
			mat.render(gl2);
			
			gl2.glPushMatrix();
				Iterator<DHLink> i = links.iterator();
				while(i.hasNext()) {
					DHLink link = i.next();
					link.renderModel(gl2);
				}
			gl2.glPopMatrix();
		gl2.glPopMatrix();
		
		super.render(gl2);
	}

	@Override
	public boolean canTargetPoseRotateX() {
		return false;
	}
	@Override
	public boolean canTargetPoseRotateY() {
		return false;
	}
}
