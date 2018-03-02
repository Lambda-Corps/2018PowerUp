package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RotateArm_Scale_Mid extends CommandGroup {

    public RotateArm_Scale_Mid() {

    	addSequential(new ExtendLowerIntake());
    	addSequential(new RaiseLowerIntake());
    	addSequential(new RotateArmToAngle(10));  //need to find out num degrees here
    	addSequential(new ExtendTelescopingPart()); //possibly?

    }
}
