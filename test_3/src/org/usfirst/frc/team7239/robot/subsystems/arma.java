package org.usfirst.frc.team7239.robot.subsystems;

import org.usfirst.frc.team7239.robot.RobotMap;
import org.usfirst.frc.team7239.robot.commands.roboarm;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class arma extends Subsystem {
	VictorSP spin = new VictorSP(RobotMap.MEATSPIN);
	Encoder spinCoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static arma m_instance;
	public static synchronized arma getInstance() {
		if (m_instance == null){
			m_instance = new arma();
		}
		return m_instance;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void MoveSpin(double movePos, double moveNeg) {
    	double moveValue = movePos - moveNeg;
    	spin.setSpeed(moveValue);
    	int count = spinCoder.get();
    	System.out.println("SpinCoder Count = " + count);
    }
}

