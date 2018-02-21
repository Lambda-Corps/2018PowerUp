package org.usfirst.frc.team1895.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;
/**
 *
 */
public class Default_Arm extends Command {

    public Default_Arm() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    //	Robot.arm.resetEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.arm.driveArm(Robot.oi.gamepad2.getAxis(F310.LY));
    		Robot.arm.driveWrist(Robot.oi.gamepad2.getAxis(F310.RX));
    		Robot.arm.getAllAxesString();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
