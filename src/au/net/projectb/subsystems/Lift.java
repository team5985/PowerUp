package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Robot's lift system. Travels and holds at a range of heights, as well as extends and retracts. Carries the Intake.
 */
public class Lift extends Subsystem {
	private static Lift instance;
	
	public enum LiftPosition {
		GROUND,
		SWITCH,
		SCALE_LO,
		SCALE_MI,
		SCALE_HI
	}
	
	DoubleSolenoid pExtension;	
	TalonSRX mElbow;
	
	public static Lift getInstance() {
		if (instance == null) {
			instance = new Lift();
		}
		return instance;
	}
	
	private Lift() {
		pExtension = new DoubleSolenoid(Constants.kBobcatCylinderReverse, Constants.kBobcatCylinderForward);
		
		mElbow = new TalonSRX(Constants.kBobcatMotor);
		mElbow.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		mElbow.setSensorPhase(false); // Setting to true reverses sensor reading
		mElbow.config_kP(0, Constants.kPElbow, 0);
		mElbow.config_kP(0, Constants.kIElbow, 0);
		mElbow.config_kD(0, Constants.kDElbow, 0);
	}
	
	/**
	 * Set what height the lift aims to be in.
	 * @param setpoint
	 * @return True if within a range of the setpoint.
	 */
	public boolean actionMoveTo(LiftPosition setpoint) {
		switch (setpoint) {
			case GROUND:
				setElbowPosition(Constants.kElbowGroundPosition);
				break;
			case SWITCH:
				setElbowPosition(Constants.kElbowSwitchPosition);
				break;
			case SCALE_LO:
				setElbowPosition(Constants.kElbowScaleLoPosition);
				break;
			case SCALE_MI:
				setElbowPosition(Constants.kElbowScaleMiPosition);
				break;
			case SCALE_HI:
				setElbowPosition(Constants.kElbowScaleHiPosition);
				break;
		}
		return mElbow.getClosedLoopError(0) < Constants.kElbowErrorWindow;
	}
	
	/**
	 * Sets the position of the extension in the arm. True is out, false is in. Automatically contracts if the arm is within a range.
	 * @param extend
	 * @return True if action completed.
	 */
	public boolean actionSetExtension(boolean extend) {
		if (getArmIsInIllegalPos()) {
			pExtension.set(Value.kReverse);
			return extend == false;
		} else {
			if (extend) {
				pExtension.set(Value.kForward);
			} else {
				pExtension.set(Value.kReverse);
			}
			return true;
		}
	}
	
	public int getElbowPosition() {
		return mElbow.getSelectedSensorPosition(0);
	}
	
	/**
	 * 
	 * @return True if the arm would be outside the 16" rule if the intake and extension were deployed.
	 */
	public boolean getArmIsInIllegalPos() {
		return mElbow.getSelectedSensorPosition(0) > Constants.kElbowIllegalPosLowerBound && mElbow.getSelectedSensorPosition(0) < Constants.kElbowIllegalPosUpperBound;
	}
	
	/**
	 * Wrapper for moving the arm.
	 * @param setpoint
	 */
	private void setElbowPosition(double setpoint) {
		// Possible arm position safety
		mElbow.set(ControlMode.Position, setpoint);
	}
}
