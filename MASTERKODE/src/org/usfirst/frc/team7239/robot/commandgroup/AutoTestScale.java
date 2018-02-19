package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTestScale extends CommandGroup {

    public AutoTestScale() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new TmACmdFollowTrajectory("traj2m.csv",'t','t',true));
    	addParallel(new TmACmdFollowTrajectory("traj90r.csv",'t','t',true));
    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 1));
    	addSequential(new AutoElevator(45000, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    }
}
