package org.usfirst.frc.team7239.robot.subsystems;
import edu.wpi.first.wpilibj.SensorBase;
import org.usfirst.frc.team7239.robot.RobotMap;
import org.usfirst.frc.team7239.robot.commands.roboarm;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;


public class arma extends Subsystem {
	
	double armLimiter = 0.35;
	
	VictorSP spin = new VictorSP(RobotMap.MEATSPIN);
	VictorSP angle1 = new VictorSP(RobotMap.ANGLE1);
	VictorSP angle2 = new VictorSP(RobotMap.ANGLE2);
	Encoder spinCoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder arm1Coder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	DigitalOutput clawPort = new DigitalOutput(RobotMap.CLAW);
	
	
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
    
    public void MoveAngle1(double moveValue) {
    	angle1.setSpeed(moveValue*armLimiter*-1);
    	
    }
    
    public void MoveSpin(double movePos, double moveNeg) {
    	double moveValue = movePos - moveNeg;
    	spin.setSpeed(moveValue*-1);
    	int count = spinCoder.get();
    	System.out.println("SpinCoder Count = " + count);
    }
    
    public void Claw(boolean close) {
    		clawPort.set(close);   	
    }
    
    public void SnopDispensser(boolean on, boolean no) {
    	if(on) {
    		angle2.setSpeed(-0.5);
    	} else if(no) {
    		angle2.setSpeed(0.5);
    	} else {
    		angle2.setSpeed(0);
    	}
    }
    
    public void SetZero(boolean reset) {
    	if(reset) {
    	spinCoder.reset();
    	arm1Coder.reset();
    	}
    }
    
    public void ReturnToZero(boolean retur) {
    	if(retur) {
    		double temp = spinCoder.get();
    		if(spinCoder.get() > 0 && spinCoder.get() < 150) {
    			spin.setSpeed(-0.6*(temp/150.0));
    			System.out.println("Hastighet = -0.6 * " + spinCoder.get() + " / 150 = " + (temp/150.0));
    		} else if(spinCoder.get() > 0) {
    			spin.setSpeed(-0.6);
    		}
    		
    		if(spinCoder.get() < 0 && spinCoder.get() > -150) {
    			spin.setSpeed(0.6*(temp/-150.0));
    		} else if(spinCoder.get() < 0) {
    			spin.setSpeed(0.6);
    		}
    			
    	} else if (spinCoder.get() == 0) {
    		spin.setSpeed(0);
    	}
    	
    	
    }
  
}

