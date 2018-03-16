package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.LowerLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RetractLowerIntake;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestGrabCube;
import org.usfirst.frc.team1895.robot.subsystems.Arm;

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
    	//addSequential(new RotateArmToPosition(Arm.ARM_LOWEST_POSITION));
    	addSequential(new ExtendLowerIntake());
    	addSequential(new LowerLowerIntake());
    	addSequential(new RetractLowerIntake()); //IS THIS IN THE RIGHT PLACE
    	addSequential(new RotateArmToPosition(Arm.ARM_LOWEST_POSITION));
    	addSequential(new ExtendTelescopingPart());
    	addSequential(new TestGrabCube());
    	//addSequential(new GrabCube_LowerIntake());
    	addSequential(new RetractTelescopingPart());
    	addSequential(new DriveWristToMax());
    	addSequential(new ExtendLowerIntake());
    	addSequential(new RaiseLowerIntake());
    }
}