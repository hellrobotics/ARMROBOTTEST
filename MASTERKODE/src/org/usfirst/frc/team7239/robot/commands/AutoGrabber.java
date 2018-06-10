package org.usfirst.frc.team7239.robot.commands;

import org.usfirst.frc.team7239.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGrabber extends Command {

	public enum State {in, out, grab;}
	private State state;
	private Grabber ssGrab;
	private boolean runAuto;
	char switchSide = 't';
	char scaleSide = 't';
	
	DriverStation m_ds;
	
    public AutoGrabber(State moveState, double seconds) {
    	ssGrab = Grabber.getInstance();
    	requires(ssGrab);
    	state = moveState;
    	setTimeout(seconds);
    	m_ds = DriverStation.getInstance();
    }
    
    public AutoGrabber(State moveState, double seconds, char switchSide, char scaleSide) {
    	this(moveState, seconds);
    	this.switchSide = switchSide;
     	this.scaleSide  = scaleSide;
     	
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	runAuto = true;
     	String gameData = m_ds.getGameSpecificMessage();
        if(gameData.length() > 0) {
        	if(gameData.charAt(0) != switchSide && 
        		(switchSide == 'L' || switchSide == 'R')) {
        		System.out.println("Switchside != gamedata");
				runAuto = false;
        	} //else runAuto
        	
        	if(gameData.charAt(1) != scaleSide && 
        		(scaleSide == 'L' || scaleSide == 'R')) {
				runAuto = false;
				System.out.println("Scaleside != gamedata");
        	} //else runAuto        	
		}
        else { //don't run auto is no gameData available
        	runAuto = false;
        	System.out.println("No gamedata");
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(state == State.in) {
    		ssGrab.move(0.75);
    	} else if (state == State.out) {
    		ssGrab.move(-0.75);
    	} else if (state == State.grab) {
    		ssGrab.move(0.0);
    	} else {
    		ssGrab.move(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isTimedOut() == true || runAuto == false) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	ssGrab.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
