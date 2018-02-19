package org.usfirst.frc.team7239.robot.commands;

import org.usfirst.frc.team7239.robot.subsystems.DriveTrain;
import org.usfirst.frc.team7239.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoElevator extends Command {

	private Elevator ssElevator;
	int pos;
	double tolerance;
	
    public AutoElevator(int Pos, double Tolerance) {
    	ssElevator = Elevator.getInstance();
    	requires(ssElevator);
    	pos = Pos;
    	tolerance = Tolerance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ssElevator.autoMove(pos, tolerance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Math.abs(DriveTrain.getInstance().getElevatorPos() - pos) < 250) {
        	return true;
        } else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	ssElevator.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
