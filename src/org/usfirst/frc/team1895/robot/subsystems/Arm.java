package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.arm.Default_Arm;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Moves up and down to put cubes onto switch or scale. Can also extend for added height. Contains
 * latch for climbing. Includes wrist for claw. 
 * Motors: 1 piston (telescoping), 4-5 (1 wrist, 1-2 claw intake, 2 arm rotation)
 * Sensors: Encoders, ccelerometer
 * Last Updated: 1/13/2018 by Maddy
 */
public class Arm extends Subsystem {

	// motors
    private TalonSRX wrist_motor;
    private TalonSRX arm_intake1;
    private TalonSRX arm_intake2;
    private TalonSRX arm_rotation_motor1;
    private TalonSRX arm_rotation_motor2;
    
    // pneumatics
    private final DoubleSolenoid telescoping_solenoid;
    
    
    public Arm() {
    	// motors
    	
    	//pneumatics
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new Default_Arm());
    }
}

