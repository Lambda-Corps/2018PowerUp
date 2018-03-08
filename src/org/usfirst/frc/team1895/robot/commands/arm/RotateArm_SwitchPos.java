package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_SwitchPos extends CommandGroup {

    public RotateArm_SwitchPos() {
    	
    	addSequential(new ExtendLowerIntake());
    	addSequential(new RaiseLowerIntake());
    	addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION)); 
    	addSequential(new DeployCube());
    	addSequential(new RetractTelescopingPart());
    	
    }
}