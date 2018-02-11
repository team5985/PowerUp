package au.net.projectb;

import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.subsystems.Drivetrain.ThrottlePreset;
import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;
import au.net.projectb.subsystems.Lift.LiftPosition;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

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
		STOWED,
		SWITCH,
		SCALE_LO,
		SCALE_MI,
		SCALE_HI
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
				lift.actionMoveTo(LiftPosition.GROUND);
				lift.actionSetExtension(true);
				if (stick.getRawButton(1)) {
					intake.actionIntakeStandby();
				} else {
					if (intake.actionStowFromIntake()) {
						currentState = SuperstructureState.STOWED;
					}
				}
				break;
				
			case STOWED:
				// State
				lift.actionMoveTo(LiftPosition.GROUND);
				lift.actionSetExtension(true);
				intake.actionStow();
				// Transition
				currentState = desiredState;
				break;
			
			case SWITCH:
				// State
				lift.actionMoveTo(LiftPosition.SWITCH);
				lift.actionSetExtension(false);
				if (!stick.getRawButton(1)) {
					intake.actionStow();
				} else {
					intake.actionOpenWhileStowed();
				}
				// Transition
				currentState = desiredState;
				break;
			
			case SCALE_LO:
				lift.actionMoveTo(LiftPosition.SCALE_LO);
				lift.actionSetExtension(false); // TODO: Check if this is necessary or possible, but it shouldn't be a problem bc of the safety
				if (!stick.getRawButton(1)) {
					intake.actionStow();
				} else {
					intake.actionOpenWhileStowed();
				}
				// Transition
				currentState = desiredState;
				break;
			
			case SCALE_MI:
				lift.actionMoveTo(LiftPosition.SCALE_MI);
				lift.actionSetExtension(true);
				if (!stick.getRawButton(1)) {
					intake.actionStow();
				} else {
					intake.actionOpenWhileStowed();
				}
				// Transition
				currentState = desiredState;
				break;
			
			case SCALE_HI:
				lift.actionMoveTo(LiftPosition.SCALE_HI);
				lift.actionSetExtension(true); // TODO: Check if this is necessary or possible, but it shouldn't be a problem bc of the safety
				if (!stick.getRawButton(1)) {
					intake.actionStow();
				} else {
					intake.actionScoreCube();
				}
				// Transition
				currentState = desiredState;
				break;
			
			default:
				currentState = SuperstructureState.STOWED;
		}
		
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
		drive.arcadeDrive(-stick.getY(), -stick.getX(), (-stick.getThrottle() + 1) / 2);
	}
	
	/**
	 * Interprets what state the operators want the robot to be in.
	 * @return Desired state as read from controller bindings.
	 */
	private SuperstructureState handleInputs() {
		SuperstructureState retState = SuperstructureState.STOWED;
		if (xbox.getYButton()) {
			switch (xbox.getPOV()) {
			case 0:
				retState = SuperstructureState.SCALE_HI;
				break;
			case 90:
				retState = SuperstructureState.SCALE_MI;
				break;
			case 270:
				retState = SuperstructureState.SCALE_MI;
				break;
			case 180:
				retState = SuperstructureState.SCALE_LO;
				break;
			default:
				retState = SuperstructureState.SCALE_HI;
				break;
			}
		}
		
		if (xbox.getXButton()) {
			retState = SuperstructureState.SWITCH;
		}
		
		if (stick.getRawButton(1) && currentState == SuperstructureState.STOWED) { // Only start to intake if the arm is stow(ed/ing)
			retState = SuperstructureState.INTAKING;
		}
		
		return retState;
	}
}
