
package org.usfirst.frc.team7239.robot;


import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
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
import org.usfirst.frc.team7239.robot.commands.AutoCube;
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
	
	double dshExp = 50;
	
	public static SerialPort serialRange = new SerialPort(9600, SerialPort.Port.kOnboard, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne);
	
	public static int cubeCoord;

	@Override
	public void robotInit() {
		SmartDashboard.putNumber("Exposure", dshExp);
		oi = new OI();
		chooser.addDefault("Nothing", new ExampleCommand());
		chooser.addObject("Auto Mid Switch", new AutoMidSwitch());
		chooser.addObject("Auto Left Scale", new AutoLeftScale());
		chooser.addObject("Auto RUN", new TmACmdFollowTrajectory("LeftLScale1.csv",'t','t',false));
		chooser.addObject("CONFERANCE Cube Auto", new AutoCube());
		/*
		chooser.addObject("Test Auto", new TmACmdFollowTrajectory("traj.csv",'t','t',true));
		chooser.addObject("Test Turn", new TmACmdFollowTrajectory("trajturn.csv",'t','t',true));
		chooser.addObject("Test 3+2m", new TestCmdG());
		chooser.addObject("Test Scale", new AutoTestScale());
		*/
		SmartDashboard.putData("Auto selector", chooser);
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			
			
			// Set the resolution
			camera.setResolution(320, 240);
			camera.setFPS(30);
			
			int exp = (int)SmartDashboard.getNumber("Exposure", 50.0);
			//System.out.println(exp);
			camera.setExposureManual(exp);
			

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("CubeVision", 320, 240);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				/*
				int exp = (int)SmartDashboard.getNumber("Exposure", 50.0);
				//System.out.println(exp);
				camera.setExposureManual(exp);
				*/
				Mat filterOut = new Mat();
				Core.inRange(mat, new Scalar(0,240,0), new Scalar(255,255,255), filterOut);
				mat.release();
				
				final List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
				contours.clear();
				Mat hierarchy = new Mat();
				
				Imgproc.findContours(filterOut, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
				hierarchy.release();
				
				if(contours.size() != 0) {
					MatOfPoint biggestContour = contours.get(0);
				
					for(int i = 0; i < contours.size(); i++) {
						final MatOfPoint contour = contours.get(i);
						double area = Imgproc.contourArea(contour);
						double biggestArea = Imgproc.contourArea(biggestContour);
						if(Imgproc.boundingRect(contour).y > 70) {
							if (area > biggestArea) {
								biggestContour = contour;
							}
						}
						//contour.release();
						//contours.get(i).release();
					}
				
					//System.out.println("Storste objektomrade: " + Imgproc.contourArea(biggestContour));
					Imgproc.cvtColor(filterOut, filterOut, Imgproc.COLOR_GRAY2RGB, 3);
					Imgproc.rectangle(filterOut, new Point(0, 70), new Point(320, 200),new Scalar(255, 0, 0), 1);
					if (Imgproc.contourArea(biggestContour) > 20.0) {
						final Rect bb = Imgproc.boundingRect(biggestContour);
						cubeCoord = bb.x + (bb.width/2);
						// Put a rectangle on the image
						Imgproc.rectangle(filterOut, new Point(bb.x ,bb.y), new Point(bb.x + bb.width, bb.y + bb.height),new Scalar(0, 0, 255), 5);
						//Imgproc.putText(filterOut, "X= " + cubeCoord, new Point(bb.x, bb.y - 3), 0, 0.5, new Scalar(0, 0, 255), 2);
						//Imgproc.putText(filterOut, serialRange.readString(), new Point(bb.x, bb.y - 15), 0, 0.5, new Scalar(0, 0, 255), 2);
					} else {
						cubeCoord = -1;
						}
				}
				// Give the output stream a new image to display
				outputStream.putFrame(filterOut);
				//outputStream.putFrame(mat);
				filterOut.release();
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();

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
		
		SmartDashboard.putData("Auto selector", chooser);
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
