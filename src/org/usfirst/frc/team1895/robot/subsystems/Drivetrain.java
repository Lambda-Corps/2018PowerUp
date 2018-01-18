package org.usfirst.frc.team1895.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Drives around. Can gear shift into high and low gear. I am also including the Compressor here. 
 * Motors: 2 pistons (transmission), 4 motors (wheels)
 * Sensors: Encoders, gyro, rangefinders (4)
 * Last Updated: 1/13/2018 by Maddy
 */

public class Drivetrain extends Subsystem {
	
	// motors
    private TalonSRX left_dt_motor1;
    private TalonSRX left_dt_motor2;
    private TalonSRX right_dt_motor1;
    private TalonSRX right_dt_motor2;
    
    // pneumatics
    private final Compressor compressor;
    private final DoubleSolenoid transmission_solenoid;
    
    public Drivetrain() {
    	//motors
    	
    	//pneumatics
    	
    }

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
