package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.RetractLowerIntake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_SwitchPos extends CommandGroup {

    public RotateArm_SwitchPos() {
    	
    	addSequential(new RetractTelescopingPart());
    	addSequential(new RetractLowerIntake());
    	addSequential(new RotateArm(10));
    	
    }
}