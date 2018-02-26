package au.net.projectb.auto.modes;

import au.net.projectb.auto.AutoController.FieldPosition;
import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;
import au.net.projectb.subsystems.Lift.LiftPosition;
import edu.wpi.first.wpilibj.DriverStation;

public class CentreSwitchLeft extends AutoMode {
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
				Drivetrain.getInstance().setMotorPower(0.3, -0.6);
				break;
				
			case 1:
				Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
				Intake.getInstance().actionStow();
				Drivetrain.getInstance().setMotorPower(0.6, -0.1);
				break;
				
			case 2:
				Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
				if (DriverStation.getInstance().getMatchTime() < 12) {
					Intake.getInstance().actionOpenWhileStowed();
				}
				Drivetrain.getInstance().setMotorPower(0.2, -0.2);
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
				return Drivetrain.getInstance().getAngle() <= -45;
				
			case 1:
				return Drivetrain.getInstance().getAngle() >= -25;
				
			default:
				return false;
		}
		
	}
}
