package com.marginallyclever.robotOverlord.dhRobot;

import javax.vecmath.Matrix4d;

import com.marginallyclever.robotOverlord.modelInWorld.ModelInWorld;
import com.marginallyclever.robotOverlord.physicalObject.PhysicalObject;

/**
 * DHTool is a model that has a DHLink equivalence.
 * In this way it can perform transforms and have sub-links.
 * @author Dan Royer
 *
 */
public class DHTool extends ModelInWorld {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A DHLink representation of this tool for kinematic solving.
	 */
	public DHLink dhLinkEquivalent;
	
	/**
	 * A PhysicalObject, if any, being held by the tool.  Assumes only one object can be held.
	 */
	public PhysicalObject subjectBeingHeld;
	
	
	public DHTool() {
		dhLinkEquivalent = new DHLink();
		dhLinkEquivalent.rangeMin=0;
		dhLinkEquivalent.rangeMax=0;
		dhLinkEquivalent.flags = DHLink.READ_ONLY_D | DHLink.READ_ONLY_THETA |	DHLink.READ_ONLY_R	| DHLink.READ_ONLY_ALPHA;
		dhLinkEquivalent.refreshPoseMatrix();
		setDisplayName("No Tool");
	}
	
	public void set(DHTool b) {
		dhLinkEquivalent = new DHLink(b.dhLinkEquivalent);
		setDisplayName(b.getDisplayName());
	}
	
	/**
	 * use the keyState to control the tool.
	 * @return true if the robot's pose has been affected.
	 */
	public boolean directDrive() {
		return false;		
	}
	
	public void refreshPose(Matrix4d endMatrix) {
		// update matrix
		dhLinkEquivalent.refreshPoseMatrix();
		// find cumulative matrix
		endMatrix.mul(dhLinkEquivalent.pose);
		dhLinkEquivalent.poseCumulative.set(endMatrix);
	}
	
	public double getAdjustableValue() {
		return dhLinkEquivalent.getAdjustableValue();
	}

	public String generateGCode() {
		return "";
	}
	
	public void parseGCode(String str) {}
	
	public void interpolate(double dt) {}
}
