package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RetractLowerIntake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_SwitchPos extends CommandGroup {

    public RotateArm_SwitchPos() {
    	
    	addSequential(new ExtendLowerIntake());
    	addSequential(new RaiseLowerIntake());
    	addSequential(new RotateArm(10));  //need to find out num degrees here
    	
    }
}