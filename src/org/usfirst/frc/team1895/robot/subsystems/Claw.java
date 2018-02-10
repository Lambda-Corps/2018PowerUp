package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.claw.Default_Claw;
import org.usfirst.frc.team1895.robot.oi.F310;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Can grab/release cubes  
 * Motors: 2 motors
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class Claw extends Subsystem {

	private TalonSRX claw_intake_motor1;
	private TalonSRX claw_intake_motor2;
	
	public Claw() {
		claw_intake_motor1 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR1_PORT);
		claw_intake_motor2 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR2_PORT);
	}
	
	public void GrabCube_Claw(double velocity) {
	    	if (velocity > 1.0) velocity = 1.0;
	    	if (velocity <-1.0) velocity = -1.0;
	}

	public void DeployCube_Claw(double velocity) {
	    	if (velocity > 1.0) velocity = 1.0;
	    	if (velocity <-1.0) velocity = -1.0;
	}
 
	 
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_Claw());
    }
}

