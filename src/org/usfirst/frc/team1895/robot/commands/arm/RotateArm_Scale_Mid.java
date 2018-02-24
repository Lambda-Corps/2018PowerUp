package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.RetractLowerIntake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_Scale_Mid extends CommandGroup {

    public RotateArm_Scale_Mid() {

    	addSequential(new RetractTelescopingPart());
    	addSequential(new RetractLowerIntake());
    	addSequential(new RotateArm(10));

    }
}
