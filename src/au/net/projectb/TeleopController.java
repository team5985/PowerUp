package au.net.projectb;

import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.subsystems.Drivetrain.ThrottlePreset;
import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Coordinates each Subsystem according to operator inputs.
 */
public class TeleopController {
	Joystick stick;	// Driver's joystick
	XboxController xbox;	// Operator's XBox Controller
	
	Intake intake;
	Lift lift;
	Drivetrain drive;
	
	UsbCamera driveCamera;
	UsbCamera wristCamera;
	CvSink driveSink;
	CvSink wristSink;
	VideoSink cameraServer;
	
	SuperstructureState currentState;
	
	private enum SuperstructureState {
		INTAKING,
		STOWED,
		RAISED
	}
	
	TeleopController() {
		currentState = SuperstructureState.STOWED;
		
		intake = Intake.getInstance();
		lift = Lift.getInstance();
		drive = Drivetrain.getInstance();
		
		stick = new Joystick(0);
		xbox = new XboxController(1);
		
		driveCamera = CameraServer.getInstance().startAutomaticCapture(0);
		
		wristCamera = CameraServer.getInstance().startAutomaticCapture(1);
		cameraServer = CameraServer.getInstance().getServer();
		
		driveSink = new CvSink("DriverCam");
		driveSink.setSource(driveCamera);
		driveSink.setEnabled(true);
		
		wristSink = new CvSink("DriverCam");
		wristSink.setSource(wristCamera);
		wristSink.setEnabled(true);
	}
	
	/**
	 * Called by teleopPeriodic in Robot
	 */
	void run() {
//		SuperstructureState desiredState = handleInputs();
		
		switch (currentState) {
			case INTAKING:
				// State
//				cameraServer.setSource(wristCamera);
				
				if (stick.getRawButton(1)) {
					intake.actionIntakeStandby();
				} else {
					intake.actionIntakeClose();
				}
				
				//Transition
				if (stick.getRawButtonPressed(3)) {
					currentState = SuperstructureState.STOWED;
				}
				if (lift.getArmIsInIllegalPos()) {
					currentState = SuperstructureState.RAISED;
				}
				break;
				
			case STOWED:
				// State
//				cameraServer.setSource(driveCamera);
				
				if (stick.getRawButton(1)) {
					intake.actionOpenWhileStowed();
				} else {
					intake.actionStow();
				}
				
				// Transition
				if (stick.getRawButtonPressed(3)) {
					currentState = SuperstructureState.INTAKING;
				}
				if (lift.getArmIsInIllegalPos()) {
					currentState = SuperstructureState.RAISED;
				}
				break;
				
			case RAISED:
				// State
//				cameraServer.setSource(driveCamera);
				
				if (stick.getRawButton(1)) {
					intake.actionOpenWhileStowed();
				} else {
					intake.actionStow();
				}
				
				// Transition
				if (lift.getArmIsInIllegalPos()) {
					currentState = SuperstructureState.RAISED;
				} else {
					currentState = SuperstructureState.STOWED;
				}
				break;
				
			default:
				currentState = SuperstructureState.STOWED;
		}
		
		// Lift Control
		lift.setElbowPower(-xbox.getY(Hand.kLeft));
		
		// Driver's buttons
		if (stick.getRawButtonPressed(2)) {
			drive.reverseDirection();
		}
		if (stick.getRawButtonPressed(10)) {
			drive.setThrottlePreset(ThrottlePreset.LOW);
		}
		if (stick.getRawButtonPressed(7)) {
			drive.setThrottlePreset(ThrottlePreset.MID);
		}
		if (stick.getRawButtonPressed(8)) {
			drive.setThrottlePreset(ThrottlePreset.HIGH);
		}
		drive.arcadeDrive(stick.getX(), -stick.getY(), (-stick.getThrottle() + 1) / 2);
	}
	
	/**
	 * Interprets what state the operators want the robot to be in.
	 * @return Desired state as read from controller bindings.
	 * @deprecated
	 */
	private SuperstructureState handleInputs() {
		SuperstructureState retState = SuperstructureState.STOWED;
		if (stick.getRawButton(1) && currentState == SuperstructureState.STOWED) { // Only start to intake if the arm is stow(ed/ing)
			retState = SuperstructureState.INTAKING;
		}
		
		return retState;
	}
}
