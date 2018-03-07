package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLeftScale extends CommandGroup {

    public AutoLeftScale() {
    	addSequential(new TmACmdFollowTrajectory("LeftRScale1.csv",'t','R',true));
    	addSequential(new TmACmdFollowTrajectory("LeftLScale1.csv",'t','L',true));
    	addParallel(new TmACmdFollowTrajectory("LeftLScale2.csv",'t','L',true));
    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 0.5));
    	addSequential(new AutoElevator(45000, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    }
}
