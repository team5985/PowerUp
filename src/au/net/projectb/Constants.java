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
	// Encoder is on end effector shaft
	public static int kWristUpPosition = 0; // TODO: Check this
	public static int kWristDnPosition = 4096;
	
	public static double kPWrist = 0.0;
	public static double kIWrist = 0.0;
	public static double kDWrist = 0.0;
	
	public static int kWristErrorWindow = 60; // TODO: Check this
	public static final double kWristMoveDelay = 0.1; // Delay from moving the claw to moving the wrist (or vice-versa), in seconds
	
	public static final double kWristMaxVoltage = 12.0;
	
	// Lift Elbow
	// Has a 84:18 gear reduction after encoder
	public static int kElbowCountsPerRev = 19115; // Actually 19114.666...
	
	public static int kElbowGroundPosition = 0; // TODO: Check this
	public static int kElbowSwitchPosition = 1;
	public static int kElbowScaleLoPosition = 2;
	public static int kElbowScaleMiPosition = 3;
	public static int kElbowScaleHiPosition = 6322;
	
	public static double kPElbow = 0.85;
	public static double kIElbow = 0.0;
	public static double kDElbow = 0.0;
	
	public static int kElbowErrorWindow = 60; // TODO: Check this
	
	public static int kElbowIllegalPosLowerBound = 0;
	public static int kElbowIllegalPosUpperBound = 1;
	
	public static double kElbowMaxVoltage = 12.0; // +/- of 0
	
	// Drive Settings
	public static double kDriveVoltageRamp = 0.25;
	public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
	
	// System Attributes
	public static int kMagEncoderCountsPerRev = 4096;
}
