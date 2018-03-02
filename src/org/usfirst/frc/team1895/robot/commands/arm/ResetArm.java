package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ResetArm extends CommandGroup {

    public ResetArm() {

    	addSequential(new RetractTelescopingPart());
    	addSequential(new RotateArmToPosition(Arm.ARM_LOWEST_POSITION));
    	addSequential(new ExtendTelescopingPart());
    	
    }
}