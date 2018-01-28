package au.net.projectb;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Contains all the ports and gains for organisation
 */
public class Constants {
	// CAN ID
	public static final int kLeftDriveMaster = 0;
	public static final int kLeftDriveSlaveA = 1;
	public static final int kLeftDriveSlaveB = 2;
	
	public static final int kRightDriveMaster = 3;
	public static final int kRightDriveSlaveA = 4;
	public static final int kRightDriveSlaveB = 5;
	
	public static final int kBobcatMotor = 6;
	public static final int kWristMotor = 7;
	
	public static final int kPdp = 10;
	public static final int kPcm = 11;
	
	// PCM Ports
	public static final int kBobcatCylinderReverse = 0;
	public static final int kBobcatCylinderForward = 1;
	
	public static final int kIntakeClawReverse = 5;	// Open
	public static final int kIntakeClawForward = 6;	// Closed
	
	
	// Intake Wrist
	public static double kPWrist = 0.0;
	public static double kIWrist = 0.0;
	public static double kDWrist = 0.0;
	
	public static double kWristErrorWindow = 0.5; // TODO: Check this
	public static final double kWristMoveDelay = 0; // Delay from closing the claw to raising the wrist
	
	// Lift Elbow
	public static double kPElbow = 0.0;
	public static double kIElbow = 0.0;
	public static double kDElbow = 0.0;
	
	public static double kElbowErrorWindow = 0.5; // TODO: Check this
	
	// Drive Settings
	public static double kDriveVoltageRamp = 0.25;
	public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
}
