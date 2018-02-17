package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.claw.Default_Claw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Can grab/release cubes Motors: 2 motors Sensors: Last Updated:
 * 1/13/2018 by Maddy
 */

public class Claw extends Subsystem {

	private TalonSRX claw_intake_motor1;
	private TalonSRX claw_intake_motor2;
	private static final double CLAW_SPEED = 0.4;
	
	private AnalogInput in_rangefinder;

	public Claw() {
		claw_intake_motor1 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR1_PORT);
		claw_intake_motor1.setInverted(true);
		claw_intake_motor2 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR2_PORT);
		claw_intake_motor2.follow(claw_intake_motor1);
		
		in_rangefinder = new AnalogInput(RobotMap.INTAKE_RANGEFINDER_PORT);
	}

	public void GrabCube_Claw() {
		double velocity = CLAW_SPEED;
	    if (velocity > 1.0) velocity = 1.0;
	    if (velocity <-1.0) velocity = -1.0;
	    claw_intake_motor1.set(ControlMode.PercentOutput, velocity);
	}

	public void DeployCube_Claw() {
		double velocity = -1;
	    if (velocity > 1.0) velocity = 1.0;
	    if (velocity <-1.0) velocity = -1.0;
	    claw_intake_motor1.set(ControlMode.PercentOutput, velocity);
	}
	
	public void stopClaw() {
		claw_intake_motor1.set(ControlMode.PercentOutput, 0);
	}
	
	public boolean cubeIsIn() {
		System.out.println(in_rangefinder.getAverageVoltage());
		if(in_rangefinder.getAverageVoltage()>2.0) {
			return true;
		} else {
			return false;
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Claw());
	}
}
