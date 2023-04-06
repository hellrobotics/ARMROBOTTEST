package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.DelayCmd;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestCmdG extends CommandGroup {

    public TestCmdG() {
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
    	//addSequential(new TmACmdFollowTrajectory("traj3m.csv",'t','t',true));
    	//addSequential(new DelayCmd(1.0));
    	//addSequential(new TmACmdFollowTrajectory("trajr.csv",'t','t',true));
    	addSequential(new AutoElevator(22500, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0));
    }
}
