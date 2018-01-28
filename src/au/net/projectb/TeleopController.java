package au.net.projectb;

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
	
	SuperstructureState currentState;
	
	private enum SuperstructureState {
		INTAKING,
		STOWED,
		SWITCH,
		SCALE_LO,
		SCALE_MI,
		SCALE_HI,
		MOVING
	}
	
	TeleopController() {
		currentState = SuperstructureState.STOWED;
	}
	
	/**
	 * Called by teleopPeriodic in Robot
	 */
	void run() {
		SuperstructureState desiredState = handleInputs();
		switch (currentState) {
		case INTAKING:
			intake.action
		}
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
		
		if (stick.getRawButton(1)) {
			retState = SuperstructureState.INTAKING;
		}
		
		return retState;
	}
	
	/**
	 * Retract arm and raise wrist
	 */
	private void stowIntake() {
		
	}
}
