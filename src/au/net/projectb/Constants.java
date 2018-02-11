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
	public static final int kIntakeClawReverse = 1;	// Open
	public static final int kIntakeClawForward = 0;	// Closed
	
	
	// Intake Wrist
	// Encoder is on output shaft. Positive is outwards.
	public static int kWristUpPosition = 0;
	public static int kWristDnPosition = 1100;
	
	public static double kPWrist = 1.0;
	public static double kIWrist = 0.0;
	public static double kDWrist = 0.0;
	
	public static int kWristErrorWindow = 60; // TODO: Check this
	public static int kWristMoveDelay = 10; // Delay from moving the claw to moving the wrist (or vice-versa), in 0.02 seconds (50hz)
	
	public static double kWristMaxVoltage = 8.0;
	
	// Lift Elbow
	// Has a 84:18 gear reduction after encoder. Positive is outwards.
	public static int kElbowCountsPerRev = 19115; // Actually 19114.666...
	
	public static int kElbowGroundPosition = 0;
	public static int kElbowSwitchPosition = 1;
	public static int kElbowScaleLoPosition = 2;
	public static int kElbowScaleMiPosition = 3;
	public static int kElbowScaleHiPosition = 4;
	
	public static double kPElbow = 0.85; // Theoretical value
	public static double kIElbow = 0.0;
	public static double kDElbow = 0.0;
	
	public static int kElbowErrorWindow = 60; // TODO: Check this
	
	public static int kElbowIllegalPosLowerBound = 5;
	public static int kElbowIllegalPosUpperBound = 6;
	
	public static double kElbowMaxVoltage = 12.0; // +/- of 0
	
	// Drive Settings
	public static double kDriveVoltageRamp = 0.25;
	public static NeutralMode kDriveNeutralMode = NeutralMode.Brake;
	
	// System Attributes
	public static int kMagEncoderCountsPerRev = 4096;
}
