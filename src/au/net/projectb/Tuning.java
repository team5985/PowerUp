package au.net.projectb;

import au.net.projectb.Constants;
import au.net.projectb.subsystems.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * For tuning the PID controllers and setting robot configurations from Shuffleboard
 */
public class Tuning {
	Joystick stick; 
	XboxController xbox;
	
	public Tuning() {
		stick = new Joystick(0);
		xbox = new XboxController(1);
	}
	
	void run() {
		// Bobcat
		// Inputs
		SmartDashboard.setDefaultNumber("Max Voltage", 0.0);
		Constants.kElbowMaxVoltage = SmartDashboard.getNumber("Max Voltage", 0.0);
		
		SmartDashboard.setDefaultNumber("Kp, Elbow", 0);
		Constants.kPElbow = SmartDashboard.getNumber("Kp, Elbow", 0);
		Constants.kIElbow = SmartDashboard.getNumber("Ki, Elbow", 0);
		Constants.kDElbow = SmartDashboard.getNumber("Kd, Elbow", 0);
		
		Constants.kElbowErrorWindow = (int) SmartDashboard.getNumber("Elbow Error Window", 0);
		
		Constants.kElbowIllegalPosLowerBound = (int) SmartDashboard.getNumber("Retract Position, Lower Bound", 0);
		Constants.kElbowIllegalPosUpperBound = (int) SmartDashboard.getNumber("Retract Position, Upper Bound", 0);
		
		SmartDashboard.setDefaultNumber("Elbow Setpoint", 0);
		int elbowSetpoint = (int) SmartDashboard.getNumber("Elbow Setpoint", 0);
		
		// Outputs
		SmartDashboard.putNumber("Lift Position", Lift.getInstance().getElbowPosition());
		
		// Setting motors
		Lift.getInstance().updateConstants();
		Lift.getInstance().setElbowPosition(elbowSetpoint);
		
		
//		Drivetrain.getInstance().arcadeDrive(stick.getX(), -stick.getY(), (-stick.getThrottle() + 1) / 2);
//		Lift.getInstance().setElbowPower(xbox.getY(Hand.kLeft));
//		
//		SmartDashboard.setDefaultBoolean("Close Claw", false);
//		boolean closeClaw = SmartDashboard.getBoolean("Close Claw", false);
//		Intake.getInstance().setClawPosition(closeClaw);
//		
//		/* Wrist */
//		// Inputs
//		SmartDashboard.setDefaultNumber("Max Voltage", 5.0);
//		Constants.kWristMaxVoltage = SmartDashboard.getNumber("Max Voltage", 5.0);
//		
//		SmartDashboard.setDefaultNumber("Kp, Wrist", 0);
//		SmartDashboard.setDefaultNumber("Kd, Wrist", 0);
//		Constants.kPWrist = SmartDashboard.getNumber("Kp, Wrist", 0);
//		Constants.kIWrist = SmartDashboard.getNumber("Ki, Wrist", 0);
//		Constants.kDWrist = SmartDashboard.getNumber("Kd, Wrist", 0);
//		
//		Constants.kWristErrorWindow = (int) SmartDashboard.getNumber("Wrist Error Window", 0);
//		
//		SmartDashboard.setDefaultNumber("Wrist Setpoint", 0);
//		int wristSetpoint = (int) SmartDashboard.getNumber("Wrist Setpoint", 0);
//		
//		// Outputs
//		SmartDashboard.putNumber("Wrist Position", Intake.getInstance().getWristPosition());
//		
//		// Setting motor
//		Intake.getInstance().updateConstants();
//		Intake.getInstance().setWristPosition(wristSetpoint);
	}
}
