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
    	addSequential(new TmACmdFollowTrajectory("MidRSwitch.csv",'R','t',true));
    	addSequential(new TmACmdFollowTrajectory("MidLSwitch.csv",'L','t',true));
    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 0.5));
    	addSequential(new AutoElevator(15000, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    }
}
