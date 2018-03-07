
package org.usfirst.frc.team7239.robot;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team7239.robot.commandgroup.AutoLeftScale;
import org.usfirst.frc.team7239.robot.commandgroup.AutoMidSwitch;
import org.usfirst.frc.team7239.robot.commandgroup.AutoTestScale;
import org.usfirst.frc.team7239.robot.commandgroup.TestCmdG;
import org.usfirst.frc.team7239.robot.commands.ArcadeDrive;
import org.usfirst.frc.team7239.robot.commands.AutonomTest;
import org.usfirst.frc.team7239.robot.commands.ElevatorControl;
import org.usfirst.frc.team7239.robot.commands.ExampleCommand;
import org.usfirst.frc.team7239.robot.commands.GrabController;
import org.usfirst.frc.team7239.robot.commands.TmACmdFollowTrajectory;
import org.usfirst.frc.team7239.robot.subsystems.ExampleSubsystem;





public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	Command arcadeDrive = new ArcadeDrive();
	Command elevatorControl = new ElevatorControl();
	Command gControl = new GrabController();
	Thread visionThread;
	

	@Override
	public void robotInit() {
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		chooser.addObject("Auto Mid Switch", new AutoMidSwitch());
		chooser.addObject("Auto Left Scale", new AutoLeftScale());
		chooser.addObject("Auto Tmp Test", new TmACmdFollowTrajectory("LeftLScale2.csv",'t','t',true));
		/*
		chooser.addObject("Test Auto", new TmACmdFollowTrajectory("traj.csv",'t','t',true));
		chooser.addObject("Test Turn", new TmACmdFollowTrajectory("trajturn.csv",'t','t',true));
		chooser.addObject("Test 3+2m", new TestCmdG());
		chooser.addObject("Test Scale", new AutoTestScale());
		*/
		SmartDashboard.putData("Auto selector", chooser);
		
		
	}
	

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
			
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		arcadeDrive.start();
		elevatorControl.start();
		gControl.start();
	}


	@Override
	public void teleopPeriodic() {
		
		Scheduler.getInstance().run();
		arcadeDrive.start();
	}


	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
