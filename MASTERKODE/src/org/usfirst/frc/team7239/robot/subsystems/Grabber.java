package org.usfirst.frc.team7239.robot.subsystems;

import org.usfirst.frc.team7239.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	
	Spark rGrab = new Spark(RobotMap.RIGHTGRABBER);
	Spark lGrab = new Spark(RobotMap.LEFTGRABBER);
	
	//DoubleSolenoid pneuGrab = new DoubleSolenoid(0,1);
	Solenoid pneuGrab1 = new Solenoid(0);
	Solenoid pneuGrab2 = new Solenoid(1);
	
	private static Grabber m_instance;
	public static synchronized Grabber getInstance() {
		if (m_instance == null){
			m_instance = new Grabber();
		}
		return m_instance;
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void move (double speed) {
    	rGrab.set(speed);
    	lGrab.set(speed);
    	
    	if(Elevator.getInstance().getBotSwitch() == true || speed != 0) {
    		pneuGrab1.set(false);
    		pneuGrab2.set(false);
    	} else {
    		pneuGrab1.set(true);
    		pneuGrab2.set(true);
    	}
    }
    
}

