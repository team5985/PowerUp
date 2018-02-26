package au.net.projectb.auto.modes;

import au.net.projectb.auto.AutoController.FieldPosition;
import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;
import au.net.projectb.subsystems.Lift.LiftPosition;
import edu.wpi.first.wpilibj.DriverStation;

public class Switch extends AutoMode {
	FieldPosition startPosition;  // Robot start position
	FieldPosition switchPosition;
	FieldPosition scalePosition;
	
	/**
	 * Set the starting position of the auto mode. (Left, Centre, Right)
	 */
	@Override
	public void setFieldPositions(FieldPosition startPos, FieldPosition switchPos, FieldPosition scalePos) {
		startPosition = startPos;
		switchPosition = switchPos;
		scalePosition = scalePos;
	}
	
	/**
	 * Run a given step in auto iteratively.
	 */
	@Override
	public void runStep(int stepIndex) {
		// Code here is step by step, and concurrent actions are possible if in the same step.
		switch (stepIndex) {
			case 0:
				Lift.getInstance().actionMoveTo(LiftPosition.GROUND);
				Intake.getInstance().actionStow();
				switch (startPosition) {
					case LEFT:
						if (switchPosition == FieldPosition.LEFT) {
							// do L-L
							Drivetrain.getInstance().setMotorPower(0.0, -0.0);
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do L-R
						}
						break;
					case CENTRE:
						if (switchPosition == FieldPosition.LEFT) {
							Drivetrain.getInstance().setMotorPower(0.3, -0.6);
						} else if (switchPosition == FieldPosition.RIGHT) {
							Drivetrain.getInstance().setMotorPower(0.6, -0.3);
						}
						break;
					case RIGHT:
						if (switchPosition == FieldPosition.LEFT) {
							// do R-L
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do R-R
							Drivetrain.getInstance().setMotorPower(0.0, -0.0);
						}
						break;
					default:
						break;
				}
				break;
				
			case 1:
				Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
				Intake.getInstance().actionStow();
				switch (startPosition) {
					case LEFT:
						if (switchPosition == FieldPosition.LEFT) {
							// do L-L
							Drivetrain.getInstance().setMotorPower(0.45, -0.22);
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do L-R
						}
						break;
					case CENTRE:
						if (switchPosition == FieldPosition.LEFT) {
							Drivetrain.getInstance().setMotorPower(0.6, -0.1);
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do C-R
							Drivetrain.getInstance().setMotorPower(0.1, -0.6);
						}
						break;
					case RIGHT:
						if (switchPosition == FieldPosition.LEFT) {
							// do R-L
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do R-R
							Drivetrain.getInstance().setMotorPower(0.22, -0.45);
						}
						break;
					default:
						break;
				}
				break;
				
			case 2:
				Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
				if (DriverStation.getInstance().getMatchTime() < 12) {
					Intake.getInstance().actionOpenWhileStowed();
				}
				switch (startPosition) {
					case LEFT:
						if (switchPosition == FieldPosition.LEFT) {
							// do L-L
							Drivetrain.getInstance().setMotorPower(0.0, -0.0);
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do L-R
						}
						break;
					case CENTRE:
						if (switchPosition == FieldPosition.LEFT) {
							Drivetrain.getInstance().setMotorPower(0.2, -0.2);
						} else if (switchPosition == FieldPosition.RIGHT) {
							Drivetrain.getInstance().setMotorPower(0.2, -0.2);
						}
						break;
					case RIGHT:
						if (switchPosition == FieldPosition.LEFT) {
							// do R-L
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do R-R
							Drivetrain.getInstance().setMotorPower(0.0, -0.0);
						}
						break;
					default:
						break;
				}
				break;
		}
		
	}
	
	/**
	 * Each step has an exit/finish condition, which is defined in this method.
	 * @return True if exit condition of a step is reached.
	 */
	@Override
	public boolean getStepIsCompleted(int stepIndex) {
		
		switch (stepIndex) {
			case 0:
				switch (startPosition) {
					case LEFT:
						if (switchPosition == FieldPosition.LEFT) {
							// do L-L
							return true;
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do L-R
							return false;
						} else {
							return true;
						}
					case CENTRE:
						if (switchPosition == FieldPosition.LEFT) {
							return Drivetrain.getInstance().getAngle() <= -45;
						} else if (switchPosition == FieldPosition.RIGHT) {
							return Drivetrain.getInstance().getAngle() >= 40;
						} else {
							return true;
						}
					case RIGHT:
						if (switchPosition == FieldPosition.LEFT) {
							// do R-L
							return false;
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do R-R
							return true; // skip first
						} else {
							return true;
						}
					default:
						return false;
				}
				
			case 1:
				switch (startPosition) {
					case LEFT:
						if (switchPosition == FieldPosition.LEFT) {
							// do L-L
							return DriverStation.getInstance().getMatchTime() < 13;
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do L-R
							return false;
						} else {
							return true;
						}
					case CENTRE:
						if (switchPosition == FieldPosition.LEFT) {
							return Drivetrain.getInstance().getAngle() >= -25;
						} else if (switchPosition == FieldPosition.RIGHT) {
							return Drivetrain.getInstance().getAngle() <= 25;
						} else {
							return true;
						}
					case RIGHT:
						if (switchPosition == FieldPosition.LEFT) {
							// do R-L
							return false;
						} else if (switchPosition == FieldPosition.RIGHT) {
							// do R-R
							return DriverStation.getInstance().getMatchTime() < 13;
						} else {
							return true;
						}
					default:
						return false;
				}
			default:
				return false;
		}
		
	}
}
