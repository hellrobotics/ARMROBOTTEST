
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

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team7239.robot.commands.ArcadeDrive;
import org.usfirst.frc.team7239.robot.commands.ExampleCommand;
import org.usfirst.frc.team7239.robot.commands.roboarm;
import org.usfirst.frc.team7239.robot.subsystems.ExampleSubsystem;





public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	Command arcadeDrive = new ArcadeDrive();
	Command roboarm = new roboarm();
	Thread visionThread;
	

	@Override
	public void robotInit() {
		
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		//x/*
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			// Set the resolution
			camera.setResolution(640, 480);

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("CubeVision", 640, 480);

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
				Mat filterOut = new Mat();
				Core.inRange(mat, new Scalar(0,240,0), new Scalar(255,255,255), filterOut);
				mat.release();
				//filterOut.convertTo(filterOut, CvType.CV_32SC1);
				final List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
				contours.clear();
				Mat hierarchy = new Mat();
				//Imgproc.cvtColor(filterOut, filterOut, Imgproc.COLOR_RGB2GRAY, 1);
				//Imgproc.threshold(filterOut, filterOut, 50, 250, Imgproc.ADAPTIVE_THRESH_MEAN_C);
				Imgproc.findContours(filterOut, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
				hierarchy.release();
				System.out.println("Objekter: " + contours.size());
				if(contours.size() != 0) {
					MatOfPoint biggestContour = contours.get(0);
				
					for(int i = 0; i < contours.size(); i++) {
						final MatOfPoint contour = contours.get(i);
						double area = Imgproc.contourArea(contour);
						double biggestArea = Imgproc.contourArea(biggestContour);
						if (area > biggestArea) {
							biggestContour = contour;
						}
						//contour.release();
						//contours.get(i).release();
					}
				
					System.out.println("Storste objektomrade: " + Imgproc.contourArea(biggestContour));
					Imgproc.cvtColor(filterOut, filterOut, Imgproc.COLOR_GRAY2RGB, 3);
					if (Imgproc.contourArea(biggestContour) > 20.0) {
						final Rect bb = Imgproc.boundingRect(biggestContour);
						// Put a rectangle on the image
						Imgproc.rectangle(filterOut, new Point(bb.x ,bb.y), new Point(bb.x + bb.width, bb.y + bb.height),new Scalar(0, 0, 255), 5);
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
		//*/
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

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

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
		roboarm.start();
		//System.out.println("STARTER TELEOP");
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
