package au.net.projectb;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Contains all the ports and gains for organisation
 */
public class Constants {
	// CAN ID
	public static final int kLeftDriveMaster = 1;
	public static final int kLeftDriveSlaveA = 2;
	public static final int kLeftDriveSlaveB = 3;
	
	public static final int kRightDriveMaster = 4;
	public static final int kRightDriveSlaveA = 5;
	public static final int kRightDriveSlaveB = 6;
	
	public static final int kBobcatMotor = 7;
	public static final int kWristMotor = 8;
	
	public static final int kPdp = 63;
	public static final int kPcm = 11;
	
	// PCM Ports	
	public static final int kIntakeClawReverse = 0;	// Open
	public static final int kIntakeClawForward = 1;	// Closed
	
	
	// Intake Wrist
	// Encoder is on output shaft. Positive is outwards.
	public static int kWristUpPosition = 0;
	public static int kWristDnPosition = 1000;
	
	public static double kPWrist = 1.2;
	public static double kIWrist = 0.0;
	public static double kDWrist = 0.0;
	
	public static int kWristErrorWindow = 60; // TODO: Check this
	public static double kWristMoveDelay = 0.1; // Delay from moving the claw to moving the wrist (or vice-versa), in seconds
	
	public static double kWristMaxVoltage = 12.0;
	
	// Lift Elbow
	// Has a 84:18 gear reduction after encoder. Positive is outwards.
	public static int kElbowCountsPerRev = 19115; // Actually 19114.666...
	
	public static int kElbowGroundPosition = 0; // TODO: Check this
	public static int kElbowSwitchPosition = 1;
	public static int kElbowScaleLoPosition = 2;
	public static int kElbowScaleMiPosition = 3;
	public static int kElbowScaleHiPosition = 6322;
	
	public static double kPElbow = 0.85; // Theoretical value
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
