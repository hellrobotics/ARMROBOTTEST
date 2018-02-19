package org.usfirst.frc.team7239.robot.commands;

import org.usfirst.frc.team7239.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGrabber extends Command {

	public enum State {in, out, grab;}
	private State state;
	private Grabber ssGrab;
	
    public AutoGrabber(State moveState, double seconds) {
    	ssGrab = Grabber.getInstance();
    	requires(ssGrab);
    	state = moveState;
    	setTimeout(seconds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
        return isTimedOut();
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
