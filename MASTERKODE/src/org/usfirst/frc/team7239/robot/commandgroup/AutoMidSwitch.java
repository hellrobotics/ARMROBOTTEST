package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoMidSwitch extends CommandGroup {

    public AutoMidSwitch() {
//    	addParallel(new TmACmdFollowTrajectory("MidRSwitch.csv",'R','t', false));
//    	addParallel(new TmACmdFollowTrajectory("MidLSwitch.csv",'L','t', false));
//    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 0.5));
//    	addSequential(new AutoElevator(15000, 2500));
//    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    	addSequential(new TmACmdFollowTrajectory("MidRSwitch.csv",'R','t', false));
    	addSequential(new TmACmdFollowTrajectory("MidLSwitch.csv",'L','t', false));
    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 0.5));
    	addSequential(new AutoElevator(20000, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    }
}
