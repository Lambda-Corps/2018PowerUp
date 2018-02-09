package org.usfirst.frc.team1895.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * this class gets the input from pidcontroller and returns the output to the caller
 */


public class MyPIDOutput implements PIDOutput {
	private double pidOutput = 0.0;
	
    @Override
    public void pidWrite(double output) {
    	pidOutput = output;
    }
    
    public double get() {
    	return pidOutput;
    }    
    
}
