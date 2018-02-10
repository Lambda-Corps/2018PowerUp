package org.usfirst.frc.team1895.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Gamepads
	public static final int GAMEPAD_PORT = 0;
	public static final int GAMEPAD2_PORT = 1;
	
	// CAN Bus
	public static final int LEFT_DT_MOTOR1_PORT = 1;
	public static final int LEFT_DT_MOTOR2_PORT = 2;
	public static final int RIGHT_DT_MOTOR1_PORT = 4;
	public static final int RIGHT_DT_MOTOR2_PORT = 5;
	public static final int CLIMBER_MOTOR_PORT = 3;
	public static final int WRIST_MOTOR_PORT = 6;
	public static final int CLAW_INTAKE_MOTOR1_PORT = 7;
	public static final int CLAW_INTAKE_MOTOR2_PORT = 8;
	public static final int LEFT_ARM_ROTATION_MOTOR_PORT = 9;
	public static final int LET_ARM_ROTATION_MOTOR_PORT = 10;
	public static final int LEFT_LOWER_INTAKE_MOTOR_PORT = 11;
	public static final int RIGHT_LOWER_INTAKE_MOTOR_PORT = 12;
	
	// Solenoids
	public static final int DRIVETRAIN_SOLENOID_A_PORT = 1;
	public static final int DRIVETRAIN_SOLENOID_B_PORT = 2;
	public static final int LOWER_INTAKE_SOLENOID_A_PORT = 3;
	public static final int LOWER_INTAKE_SOLENOID_B_PORT = 4;
	public static final int ARM_TELESCOPING_SOLENOID_A_PORT = 5;
	public static final int ARM_TELESCOPING_SOLENOID_B_PORT = 6;
	
	// Digital IO
	public static final int RIGHT_ENCODER_A_PORT = 0;
	public static final int RIGHT_ENCODER_B_PORT = 1;
	public static final int LEFT_ENCODER_A_PORT = 3;
	public static final int LEFT_ENCODER_B_PORT = 2;
	
	// Analog
	public static final int INTAKE_RANGEFINDER_PORT = 0;
	public static final int FRONT_RANGEFINDER_PORT = 3;
	public static final int LEFT_RANGEFINDER_PORT = 1;
	public static final int RIGHT_RANGEFINDER_PORT = 2;
	
	// I2C Ports, should only be accelerometer for now
	public static final int ACCELEROMETER_PORT = 0;
	
}
