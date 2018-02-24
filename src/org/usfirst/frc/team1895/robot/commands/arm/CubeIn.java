package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.GrabCube_LowerIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeIn extends CommandGroup {

    public CubeIn() {
        
    	//steps 1-3
    	/* (lower intake is extended & up, arm is retracted)
    	 * Retract lower intake, go down
    	 * Arm extends
    	 * Run lower intake motors in until cube is present
    	 */
    	
    	addSequential(new ExtendLowerIntake());
    	addSequential(new ExtendTelescopingPart());
    	addSequential(new GrabCube_LowerIntake());
    	
    }
}