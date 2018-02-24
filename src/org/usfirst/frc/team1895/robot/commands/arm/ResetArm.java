package org.usfirst.frc.team1895.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ResetArm extends CommandGroup {

    public ResetArm() {

    	addSequential(new RetractTelescopingPart());
    	addSequential(new RotateArm(0));
    	
    }
}