package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRun extends CommandGroup {

    public AutoRun() {
    	addSequential(new TmACmdFollowTrajectory("LeftLScale1.csv",'t','t',true));
    }
}
