package com.marginallyclever.robotOverlord.dhRobot;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Solves IK for a RTTRTR robot
 * @author Dan Royer
 */
public class DHIKSolveRTTRTR {
	public double theta0;
	public double alpha1;
	public double alpha2;
	// joint 3 is a dummy joint to draw the skeleton correctly.
	public double theta4;
	public double alpha5;
	public double theta6;
	
	public DHIKSolveRTTRTR() {}
	
	/**
	 * Starting from a known local origin and a known local hand position (link 6 {@DHrobot.endMatrix}), calculate the angles for the given pose.
	 * @param robot
	 */
	@SuppressWarnings("unused")
	public void solve(DHRobot robot) {
		DHLink link0 = robot.links.get(0);
		DHLink link1 = robot.links.get(1);
		DHLink link2 = robot.links.get(2);
		DHLink link3 = robot.links.get(3);
		DHLink link4 = robot.links.get(4);
		DHLink link5 = robot.links.get(5);
		DHLink link6 = robot.links.get(6);
		DHLink link7 = robot.links.get(7);

		//link7.poseCumulative.set(robot.endMatrix);
		
		Vector3d p7 = new Vector3d(
				link7.poseCumulative.m03,
				link7.poseCumulative.m13,
				link7.poseCumulative.m23);
		
		// Work backward to get link5 position
		Vector3d n7z = new Vector3d(
				link7.poseCumulative.m02,
				link7.poseCumulative.m12,
				link7.poseCumulative.m22);
		Point3d p5 = new Point3d(n7z);
		p5.scaleAdd(-link6.d,p7);

		// Work forward to get p1 position
		Point3d p1 = new Point3d(0,0,link0.d);

		Vector3d p5confirm = new Vector3d(
				link5.poseCumulative.m03,
				link5.poseCumulative.m13,
				link5.poseCumulative.m23);
		
		if(false) {
			System.out.println(
					"p7="+p7+"\t"+
					"n7z="+n7z+"\t"+
					"d6="+link6.d+"\t"+
					"p5="+p5+"\t"+
					"p5c="+p5confirm+"\t"+
					"p1="+p1+"\t"
					);
		}
		
		// (1) theta0 = atan(y07/x07);
		theta0 = Math.toDegrees(Math.atan2(p7.x,-p7.y));  // TODO explain why this isn't Math.atan2(p7.y,p7.x)
		if(false) System.out.println("t0="+theta0+"\t");
		
		// (2) C=z15
		double c = p5.z - p1.z;
		if(false) System.out.println("c="+c+"\t");
		
		// (3) 
		double x15 = p5.x-p1.x;
		double y15 = p5.y-p1.y;
		double d = Math.sqrt(x15*x15 + y15*y15);
		if(false) System.out.println("d="+d+"\t");
		
		// (4)
		double e = Math.sqrt(c*c + d*d);
		if(false) System.out.println("e="+e+"\t");

		// (5) phi = acos( (b^2 - a^2 - e^2) / (-2*a*e) ) 
		double a = link2.d;
		double b2 = link4.d+link5.d;
		double b1 = link3.d;
		double b = Math.sqrt(b2*b2+b1*b1);
		if(false) System.out.println("b="+b+"\t");
		
		double phi = Math.acos( (b*b-a*a-e*e) / (-2*a*e) );
		if(true) System.out.println("phi="+Math.toDegrees(phi)+"\t");
		
		// (6) rho = atan2(d,c)
		double rho = Math.atan2(d,c);
		if(false) System.out.println("rho="+Math.toDegrees(rho)+"\t");
		
		// (7) alpha1 = phi-rho
		alpha1 = Math.toDegrees(rho - phi);
		if(false) System.out.println("a1="+alpha1+"\t");
		
		// (8) omega = acos( (a^2-b^2-e^2) / (-2be) )
		double omega = Math.acos( (a*a-b*b-e*e) / (-2*b*e) );
		if(true) System.out.println("omega="+Math.toDegrees(omega)+"\t");

		// (9) phi3 = phi + omega
		double phi3 = phi+omega;
		if(true) System.out.println("phi3="+Math.toDegrees(phi3)+"\t");
				
		// angle of triangle j3-j2-j5 is ph4.
		// b2^2 = b^+b1^2-2*b*b1*cos(phi4)
		double phi4 = Math.acos( (b2*b2-b1*b1-b*b) / (-2*b1*b) );
		if(true) System.out.println("phi4="+Math.toDegrees(phi4)+"\t");
		
		// (10) alpha2 - phi3-phi4
		alpha2 = Math.toDegrees(phi3 - phi4);
		if(true) System.out.println("alpha2="+alpha2+"\t");
		
		/*
		//(1) theta0 = tan(x05/y05)
		double x05 = p5.x - p0.x;
		double y05 = p5.y - p0.y;
		theta0 = Math.toDegrees(Math.atan2(x05,-y05));  // TODO explain why this is -y05 instead of y05
		//System.out.println("t0="+theta0+"\t");
		
		//(2) A = sqrt(x12^2 + y12^2)
		double x12 = p2.x - p1.x;
		double y12 = p2.y - p1.y;
		double A = Math.sqrt(x12*x12 + y12*y12);
		// Correct sign of A with dot product
		double dot = x05*x12 + y05*x12;
		if(dot<0) A=-A;
		
		//(3) B = z12
		double B = p2.z-p1.z;

		//(4)
		alpha1 = Math.toDegrees(Math.atan2(A,B));
		//System.out.println("A="+A+"\t"+"B="+B+"\t"+"a1="+alpha1+"\t");
		
		//(5) C = z23
		double C = p3.z-p2.z; 

		//(6) D = sqrt(x23^2 + y23^2)
		double x23 = p3.x-p2.x;
		double y23 = p3.y-p2.y;
		double D = Math.sqrt(x23*x23 + y23*y23);
		// Correct sign of D
		dot = x05*x23 + y05*x23;
		if(dot<0) D=-D;
		
		//(7) phi1 = atan(C,D)
		double phi1 = Math.toDegrees(Math.atan2(D,C));

		//(8) alpha2 = alpha1 - phi1
		alpha2 = phi1-alpha1;	// TODO why is this backwards?
		//System.out.println("C="+C+"\t"+"D="+D+"\t"+"p1="+phi1+"\t"+"a1="+alpha1+"\t"+"a2="+alpha2+"\t");
		
		System.out.println("t0="+formatDouble(theta0)+"\ta1="+formatDouble(alpha1)+"\ta2="+formatDouble(alpha2)+"\t");
		*/
	}
	
	/**
	 * Assuming all link poses are known, calculate the angles for a given pose.
	 * @param robot
	 */
	public void solveOld(DHRobot robot) {
		DHLink link0 = robot.links.get(0);
		DHLink link1 = robot.links.get(1);
		DHLink link2 = robot.links.get(2);
		DHLink link3 = robot.links.get(3);
		//DHLink link4 = robot.links.get(4);
		DHLink link5 = robot.links.get(5);
		DHLink link6 = robot.links.get(6);

		Point3d p0 = new Point3d();
		Point3d p1 = new Point3d();
		Point3d p2 = new Point3d();
		Point3d p3 = new Point3d();
		//Point3d p4 = new Point3d();
		Point3d p5 = new Point3d();
		link0.poseCumulative.transform(new Point3d(0,0,0),p0);
		link1.poseCumulative.transform(new Point3d(0,0,0),p1);
		link2.poseCumulative.transform(new Point3d(0,0,0),p2);
		link3.poseCumulative.transform(new Point3d(0,0,0),p3);
		//link4.poseCumulative.transform(new Point3d(0,0,0),p4);
		link5.poseCumulative.transform(new Point3d(0,0,0),p5);
		//System.out.println("p0="+p0+"\tp1="+p1+"\tp2="+p2+"\tp4="+p4+"\tp5="+p5);
		
		//(1) theta0 = tan(x05/y05)
		double x05 = p5.x - p0.x;
		double y05 = p5.y - p0.y;
		theta0 = Math.toDegrees(Math.atan2(x05,-y05));  // TODO explain why this is -y05 instead of y05
		//System.out.println("t0="+theta0+"\t");
		
		//(2) A = sqrt(x12^2 + y12^2)
		double x12 = p2.x - p1.x;
		double y12 = p2.y - p1.y;
		double A = Math.sqrt(x12*x12 + y12*y12);
		// Correct sign of A with dot product
		double dot = x05*x12 + y05*x12;
		if(dot<0) A=-A;
		
		//(3) B = z12
		double B = p2.z-p1.z;

		//(4)
		alpha1 = Math.toDegrees(Math.atan2(A,B));
		//System.out.println("A="+A+"\t"+"B="+B+"\t"+"a1="+alpha1+"\t");
		
		//(5) C = z23
		double C = p3.z-p2.z; 

		//(6) D = sqrt(x23^2 + y23^2)
		double x23 = p3.x-p2.x;
		double y23 = p3.y-p2.y;
		double D = Math.sqrt(x23*x23 + y23*y23);
		// Correct sign of D
		dot = x05*x23 + y05*x23;
		if(dot<0) D=-D;
		
		//(7) phi1 = atan(C,D)
		double phi1 = Math.toDegrees(Math.atan2(D,C));

		//(8) alpha2 = alpha1 - phi1
		alpha2 = phi1-alpha1;	// TODO why is this backwards?
		//System.out.println("C="+C+"\t"+"D="+D+"\t"+"p1="+phi1+"\t"+"a1="+alpha1+"\t"+"a2="+alpha2+"\t");
		
		System.out.println("t0="+formatDouble(theta0)+"\ta1="+formatDouble(alpha1)+"\ta2="+formatDouble(alpha2)+"\t");
	}
	
	protected String formatDouble(double arg0) {
		//return Float.toString(roundOff(arg0));
		return String.format("%.3f", arg0);
	}
}
