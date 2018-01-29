package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Robot's intake system. Intakes, stows, and scores power cubes.
 */
public class Intake extends Subsystem {
	private static Intake instance;
	
	DoubleSolenoid pClaw;	
	TalonSRX mWrist;
	
	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}
	
	private Intake() {
		pClaw = new DoubleSolenoid(Constants.kIntakeClawReverse, Constants.kIntakeClawForward);
		
		mWrist = new TalonSRX(Constants.kWristMotor);
		mWrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		mWrist.setSensorPhase(false); // Setting to true reverses sensor reading
		mWrist.config_kP(0, Constants.kPWrist, 0);
		mWrist.config_kP(0, Constants.kIWrist, 0);
		mWrist.config_kD(0, Constants.kDWrist, 0);
	}
	
	/**
	 * Closes the claw, then raises the wrist
	 * @return True when action is complete
	 */
	public boolean actionStowFromIntake() {
		boolean actionComplete = mWrist.getClosedLoopError(0) < Constants.kWristErrorWindow;
		
		pClaw.set(Value.kForward);
		// mysterious wait timer for Constants.kWristMoveDelay seconds
		setWristPosition(Constants.kWristUpPosition);
		
		return actionComplete;
	}
	
	/**
	 * Brings the intake down and open.
	 * @return True if action completed.
	 */
	public boolean actionIntakeStandby() {
		pClaw.set(Value.kReverse);
		setWristPosition(Constants.kWristDnPosition);
		return mWrist.getClosedLoopError(0) < Constants.kWristErrorWindow;
	}
	
	/**
	 * Keeps the intake up and closed.
	 * @return True if action completed.
	 */
	public boolean actionStow() {
		pClaw.set(Value.kForward);
		setWristPosition(Constants.kWristUpPosition);
		return mWrist.getClosedLoopError(0) < Constants.kWristErrorWindow;
	}
	
	/**
	 * Keeps the intake up and open.
	 * @return True if action completed.
	 */
	public boolean actionOpenWhileStowed() {
		pClaw.set(Value.kReverse);
		setWristPosition(Constants.kWristUpPosition);
		return mWrist.getClosedLoopError(0) < Constants.kWristErrorWindow;
	}
	
	/**
	 * Brings the intake down, waits, then opens.
	 * @return True if action completed.
	 */
	public boolean actionScoreCube() {
		setWristPosition(Constants.kWristDnPosition);
		// wait timer
		pClaw.set(Value.kReverse);
		return mWrist.getClosedLoopError(0) < Constants.kWristErrorWindow /* && timer is complete*/;
	}
	
	/**
	 * Get wrist position.
	 * @return True if wrist is more than halfway down.
	 */
	public boolean getWristIsDown() {
		return mWrist.getSelectedSensorPosition(0) > Constants.kWristDnPosition / 2;
	}
	
	private void setWristPosition(int setpoint) {
		if (Lift.getInstance().getArmIsInIllegalPos()) {
			mWrist.set(ControlMode.Position, Constants.kWristUpPosition);
		} else {
			mWrist.set(ControlMode.Position, setpoint);
		}
	}
}
