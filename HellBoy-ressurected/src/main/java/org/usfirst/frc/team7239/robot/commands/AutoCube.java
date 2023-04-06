package org.usfirst.frc.team7239.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.subsystems.DriveTrain;
import org.usfirst.frc.team7239.robot.subsystems.Elevator;
import org.usfirst.frc.team7239.robot.subsystems.Grabber;
import org.usfirst.frc.team7239.robot.Robot;
import org.usfirst.frc.team7239.robot.RobotMap;
import org.usfirst.frc.team7239.robot.commands.ArcadeDrive;

/**
 *
 */
public class AutoCube extends Command {
	
	private DriveTrain ssTrain;
	private Elevator ssElevator;
	private Grabber ssGrab;
	
	private DigitalInput qbSwitch = new DigitalInput(RobotMap.LIMITSWITCHCUBE);
	
	double xCoord;
	//SerialPort sIn;
	
	int qbRange = 500;
	boolean looking4Qb;
	boolean seesQb;
	boolean hasQb;
	

    public AutoCube() {
    	ssTrain = DriveTrain.getInstance();
    	requires(ssTrain);
    	ssElevator = Elevator.getInstance();
    	requires(ssElevator);
    	ssGrab = Grabber.getInstance();
    	requires(ssGrab);
    	//sIn = Robot.serialRange;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	looking4Qb = true;
    	seesQb = false;
    	hasQb = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	xCoord = Robot.cubeCoord;
    	/*
    	String rangeString = sIn.readString();
    	//sIn.flush();
    	 if(rangeString.length() >= 1) {
    		if(rangeString.charAt(0) == 'R') {
    			rangeString = rangeString.replaceAll("R", "");
    			rangeString = rangeString.replaceAll("\\r", "");
    			qbRange = Integer.parseInt(rangeString);
    			System.out.println("Range = " + rangeString + " raw = " + qbRange);
    			
    		}
    	}*/
    	
    	/*if(qbSwitch.get() == false) {
    		hasQb = true;
    		looking4Qb = false;
    	} else {
    		hasQb = false;
    		looking4Qb = true;
    	}*/
    	/*
    	if(hasQb) {
    		deliverQb();
    	} else*/ 
    	if(looking4Qb) {
    		look4Qb();
    	}
    	
    }
    
    
    void look4Qb () {
    	double speed = 0;
    	if(ssElevator.getBotSwitch() == false) {
    		ssElevator.autoMove(0, 200);
    	}
    	if (xCoord >= 0) {
    		double error = 160.0-xCoord;
			double pk = 0.2/50.0;
			
			seesQb = true;
			if(qbRange < 430) {
				//ssGrab.move(0.75);
				speed = 0;
			} else {
				ssGrab.move(0);
				speed = -0.4;
			}
			
			ssTrain.Arcade(speed, error*pk*-1);
			//System.out.println("Error = " + error + " Out = " + (error*pk));
    	} 
    	    }
    
    void deliverQb () {
    	ssElevator.autoMove(15000, 200);
    	if(ssTrain.getElevatorPos() <= 15000+20 ) {
    		ssGrab.move(-0.75);
    	} else {
    		ssGrab.move(0);
    	}
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
