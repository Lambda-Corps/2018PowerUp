package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.climbing.Default_ManuallyClimb;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Climbs up and down 
 * Motors: 1
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class Climber extends Subsystem {

    private TalonSRX climber_motor;
    
    public Climber() {
    	
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_ManuallyClimb());
    }
}

