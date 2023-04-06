package org.usfirst.frc.team7239.robot.commandgroup;

import org.usfirst.frc.team7239.robot.commands.AutoElevator;
import org.usfirst.frc.team7239.robot.commands.AutoGrabber;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLeftScale extends CommandGroup {

    public AutoLeftScale() {
    	String gameData;
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	addSequential(new TmACmdFollowTrajectory("LeftLScale1.csv",'t','R',false));
    	addParallel(new TmACmdFollowTrajectory("traj_scale.csv",'t','L',false));
    	addParallel(new AutoGrabber(AutoGrabber.State.grab, 0.5));
    	addSequential(new AutoElevator(45000, 2500));
    	addSequential(new AutoGrabber(AutoGrabber.State.out, 1.0, 't', 'L'));
    	//addParallel(new TmACmdFollowTrajectory("LeftLScale2.csv",'t','L',true));
    	//if(gameData.charAt(1) == 'L') {
    	//}
    }
}
