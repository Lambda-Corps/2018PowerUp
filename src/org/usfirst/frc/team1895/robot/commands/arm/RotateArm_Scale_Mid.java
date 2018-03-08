package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_Scale_Mid extends CommandGroup {

    public RotateArm_Scale_Mid() {

    	addSequential(new ExtendLowerIntake());
    	addSequential(new RaiseLowerIntake());
    	addSequential(new RotateArmToPosition(Arm.ARM_SCALE_MID_POSITION));
//    	addSequential(new ExtendTelescopingPart()); //possibly?
    	addSequential(new DeployCube());
    	addSequential(new RetractTelescopingPart());
    	
    }
}
