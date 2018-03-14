package org.usfirst.frc.team1895.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ArmTestSuite extends CommandGroup {

    public ArmTestSuite() {
    	addSequential(new PrintCommand("Bring Arm to Zero"));
    	addSequential(new RotateArmToZero());
    	addSequential(new WaitCommand(1));
    	addSequential(new PrintCommand("Bring Arm to Switch Pos"));
    	addSequential(new RotateArm_SwitchPos());
    	addSequential(new WaitCommand(1));
    	addSequential(new PrintCommand("Bring Arm to Scale High"));
    	addSequential(new RotateArm_Scale_High());
    	addSequential(new WaitCommand(1));
    	addSequential(new PrintCommand("Bring Arm to Scale Low"));
    	addSequential(new RotateArm_Scale_Low());
    	addSequential(new WaitCommand(1));
    	addSequential(new PrintCommand("Bring Arm to Scale Mid"));
    	addSequential(new RotateArm_Scale_Mid());
    	addSequential(new WaitCommand(1));
    }
}
