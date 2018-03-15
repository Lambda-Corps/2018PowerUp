package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.Default_LowerIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Intake system can retract and extend to wrap around cubes using a piston. Intake can also grab and 
 * release cubes. 
 * Motors: 2 pistons (retracting/extending), 2 motors (grabbing/releasing cubes)
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class LowerIntake extends Subsystem {

	// motors
	private TalonSRX left_lower_intake_motor;
	private TalonSRX right_lower_intake_motor;
	
	// pneumatics
	private final DoubleSolenoid lower_intake_solenoid_ExRe;
	private final DoubleSolenoid lower_intake_solenoid_UpDown;
	
	public static final double INTAKE_MOTOR_LEFT_SPEED = .4;
	public static final double INTAKE_MOTOR_RIGHT_SPEED = .6;

	public LowerIntake() {
		//motors
		left_lower_intake_motor = new TalonSRX(RobotMap.LEFT_LOWER_INTAKE_MOTOR_PORT);
		right_lower_intake_motor = new TalonSRX(RobotMap.RIGHT_LOWER_INTAKE_MOTOR_PORT);
		left_lower_intake_motor.setInverted(true);

		//pneumatics
		lower_intake_solenoid_ExRe = new DoubleSolenoid(RobotMap.LOWER_INTAKE_SOLENOID_EXRE_A_PORT, RobotMap.LOWER_INTAKE_SOLENOID_EXRE_B_PORT);
		lower_intake_solenoid_UpDown = new DoubleSolenoid(RobotMap.LOWER_INTAKE_SOLENOID_UPDOWN_A_PORT, RobotMap.LOWER_INTAKE_SOLENOID_UPDOWN_B_PORT);
	}
	
	//check signs
	public void setLowerIntakeMotors(double speed) {
		left_lower_intake_motor.set(ControlMode.PercentOutput, -speed);
		right_lower_intake_motor.set(ControlMode.PercentOutput, speed);
	}

//==Solenoids===================================================================================================
	public void toggleLowerIntakeArms() {
		DoubleSolenoid.Value val = lower_intake_solenoid_UpDown.get();
		if(val == DoubleSolenoid.Value.kForward) {
			lower_intake_solenoid_UpDown.set(DoubleSolenoid.Value.kReverse);
		} else {
			lower_intake_solenoid_UpDown.set(DoubleSolenoid.Value.kForward);
		}

	}
	
	public void extendLowerIntake() {
		lower_intake_solenoid_ExRe.set(DoubleSolenoid.Value.kForward);
		System.out.println("extend lower intake method");
	}
	
	public void retractLowerIntake() {
		lower_intake_solenoid_ExRe.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void raiseLowerIntake() {
		lower_intake_solenoid_UpDown.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lowerLowerIntake() {
		lower_intake_solenoid_UpDown.set(DoubleSolenoid.Value.kForward);
	}
	
	public boolean isExtended() {
		return lower_intake_solenoid_ExRe.get() == DoubleSolenoid.Value.kForward;
	}
	
	public boolean isLowered() {
		return lower_intake_solenoid_UpDown.get() == DoubleSolenoid.Value.kForward;
	}

	
	public void toggleLowerIntake() {
		DoubleSolenoid.Value val = lower_intake_solenoid_ExRe.get();
		if(val == DoubleSolenoid.Value.kForward) {
			lower_intake_solenoid_ExRe.set(DoubleSolenoid.Value.kReverse);
		} else {
			lower_intake_solenoid_ExRe.set(DoubleSolenoid.Value.kForward);
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_LowerIntake());
    }

	public void grabCube() {
		left_lower_intake_motor.set(ControlMode.PercentOutput, INTAKE_MOTOR_LEFT_SPEED);
		right_lower_intake_motor.set(ControlMode.PercentOutput, INTAKE_MOTOR_RIGHT_SPEED);	
	}
}

