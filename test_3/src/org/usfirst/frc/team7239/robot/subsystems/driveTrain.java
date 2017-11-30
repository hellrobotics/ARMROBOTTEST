package org.usfirst.frc.team7239.robot.subsystems;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team7239.robot.*;
/**
 *
 */
public class driveTrain extends Subsystem {
	VictorSP left1 = new VictorSP(RobotMap.LEFTMOTOR1);
	VictorSP left2 = new VictorSP(RobotMap.LEFTMOTOR2);
	VictorSP right1 = new VictorSP(RobotMap.RIGHTMOTOR1);
	VictorSP right2 = new VictorSP(RobotMap.RIGHTMOTOR2);
	RobotDrive Drive = new RobotDrive(left1, left2, right1, right2);
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static driveTrain m_instance;
	public static synchronized driveTrain getInstance() {
		if (m_instance == null){
			m_instance = new driveTrain();
		}
		return m_instance;
		
	}
	
	private driveTrain() {
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void Arcade(double moveValue, double rotateValue) {
		Drive.arcadeDrive(moveValue, rotateValue * -1, true);
		
	}
}

