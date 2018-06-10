package org.usfirst.frc.team7239.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	
	//CAN!!!!!!!!!
	public static int LEFTMOTOR1 = 2;
	public static int LEFTMOTOR2 = 3;
	public static int RIGHTMOTOR1 = 0;
	public static int RIGHTMOTOR2 = 1;
	
	//PWM!!!!!
	public static int MEATSPIN = 5;
	public static int ANGLE1 = 6;
	public static int ANGLE2 = 0; //VIPPETROLL

	//DIO!!!!!
	
	public static int LIMIT = 5;
	
	public static int CLAW = 9;
	

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
