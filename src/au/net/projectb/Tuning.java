package au.net.projectb;

import au.net.projectb.Constants;
import au.net.projectb.subsystems.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * For tuning the PID controllers and setting robot configurations from Shuffleboard
 */
public class Tuning {
	void run() {
		// Inputs
		Constants.kElbowMaxVoltage = SmartDashboard.getNumber("Max Voltage", 5.0);
		
		Constants.kPElbow = SmartDashboard.getNumber("Kp, Elbow", 0);
		Constants.kIElbow = SmartDashboard.getNumber("Ki, Elbow", 0);
		Constants.kDElbow = SmartDashboard.getNumber("Kd, Elbow", 0);
		
		Constants.kElbowErrorWindow = (int) SmartDashboard.getNumber("Elbow Error Window", 0);
		
		Constants.kElbowIllegalPosLowerBound = (int) SmartDashboard.getNumber("Retract Position, Lower Bound", 0);
		Constants.kElbowIllegalPosUpperBound = (int) SmartDashboard.getNumber("Retract Position, Upper Bound", 0);
		
		int elbowSetpoint = (int) SmartDashboard.getNumber("Elbow Setpoint", 0);
		
		// Outputs
		SmartDashboard.putNumber("Lift Position", Lift.getInstance().getElbowPosition());
		
		// Setting motors
		Lift.getInstance().updateConstants();
		Lift.getInstance().setElbowPosition(elbowSetpoint);
	}
}
