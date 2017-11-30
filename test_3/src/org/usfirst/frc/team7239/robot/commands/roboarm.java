package org.usfirst.frc.team7239.robot.commands;

import org.usfirst.frc.team7239.robot.OI;
import org.usfirst.frc.team7239.robot.subsystems.arma;
import org.usfirst.frc.team7239.robot.subsystems.driveTrain;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class roboarm extends Command {
	private arma ssArm;
	private OI oi;
    public roboarm() {
    	ssArm = arma.getInstance();
    	requires(ssArm);
    	oi = OI.getInstance();
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ssArm.MoveSpin(oi.stick.getZ() ,oi.stick.getRawAxis(3));
    	ssArm.MoveAngle1(oi.stick2.getRawAxis(4));
    	ssArm.Claw(oi.stick.getRawButton(1));
    	
    	
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

