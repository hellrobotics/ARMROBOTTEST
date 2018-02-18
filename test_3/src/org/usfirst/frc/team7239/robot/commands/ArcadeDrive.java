package org.usfirst.frc.team7239.robot.commands;

import org.usfirst.frc.team7239.robot.OI;
import org.usfirst.frc.team7239.robot.subsystems.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDrive extends Command {
	private driveTrain ssTrain;
	private OI oi;
    public ArcadeDrive() {
    	ssTrain = driveTrain.getInstance();
    	requires(ssTrain);
    	oi = OI.getInstance();
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ssTrain.Arcade(oi.stick.getY(), oi.stick.getX(), oi.stick.getRawAxis(3));
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
