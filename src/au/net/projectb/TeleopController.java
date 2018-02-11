package au.net.projectb;

import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.subsystems.Drivetrain.ThrottlePreset;
import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
	
	SuperstructureState currentState;
	
	private enum SuperstructureState {
		INTAKING,
		STOWED
	}
	
	TeleopController() {
		currentState = SuperstructureState.STOWED;
		
		intake = Intake.getInstance();
		lift = Lift.getInstance();
		drive = Drivetrain.getInstance();
		
		stick = new Joystick(0);
		xbox = new XboxController(1);
	}
	
	/**
	 * Called by teleopPeriodic in Robot
	 */
	void run() {
		SuperstructureState desiredState = handleInputs();
		
		switch (currentState) {
			case INTAKING:
				// State and transition are together
				if (stick.getRawButton(1)) {
					if (!stick.getRawButton(3)) {
						intake.actionIntakeClose();
					} else {
						intake.actionIntakeStandby();
					}
					
				} else {
					if (intake.actionStowFromIntake()) {
						currentState = SuperstructureState.STOWED;
					}
				}
				break;
				
			case STOWED:
				// State
				if (stick.getRawButton(3)) {
					intake.actionOpenWhileStowed();
				} else {
					intake.actionStow();
				}
				// Transition
				currentState = desiredState;
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
	 */
	private SuperstructureState handleInputs() {
		SuperstructureState retState = SuperstructureState.STOWED;
		if (stick.getRawButton(1) && currentState == SuperstructureState.STOWED) { // Only start to intake if the arm is stow(ed/ing)
			retState = SuperstructureState.INTAKING;
		}
		
		return retState;
	}
}
