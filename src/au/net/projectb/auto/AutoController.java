package au.net.projectb.auto;

import au.net.projectb.auto.modes.AutoMode;
import au.net.projectb.subsystems.Drivetrain;
import au.net.projectb.auto.modes.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoController {
	SendableChooser<FieldPosition> startSelector;
	SendableChooser<AutoMode> autoSelector;
	AutoMode selectedAuto;
	int currentStep;
	
	public enum FieldPosition {
		LEFT,
		CENTRE,
		RIGHT,
		DEFAULT
	}
	
	public AutoController() {
		startSelector = new SendableChooser<FieldPosition>();
		startSelector.addDefault("Select Starting Position", FieldPosition.DEFAULT);
		startSelector.addObject("Centre", FieldPosition.CENTRE);
		startSelector.addObject("Left", FieldPosition.LEFT);
		startSelector.addObject("Right", FieldPosition.RIGHT);
		SmartDashboard.putData("Start Position Selector", startSelector);
		
		autoSelector = new SendableChooser<AutoMode>();		
		autoSelector.addDefault("Default", new Default());
		autoSelector.addObject("Switch", new Switch());
		autoSelector.addObject("Scale", new Scale());
		// Various auto modes
		SmartDashboard.putData("Auto Selector", autoSelector);
	}
	
	public void init() {
		Drivetrain.getInstance().zeroGyro();
		
		selectedAuto = autoSelector.getSelected();
		
		FieldPosition switchPosition;
		FieldPosition scalePosition;
		
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			switchPosition = FieldPosition.LEFT;
		} else {
			switchPosition = FieldPosition.RIGHT;
		}		
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			scalePosition = FieldPosition.LEFT;
		} else {
			scalePosition = FieldPosition.RIGHT;
		}
		
		selectedAuto.setFieldPositions(startSelector.getSelected(), switchPosition, scalePosition);
		currentStep = 0;
	}
	
	public void run() {
		// getStepIsCompleted default returns false.
		if (!selectedAuto.getStepIsCompleted(currentStep)) {
			selectedAuto.runStep(currentStep);
		} else {
			currentStep++;
		}
	}
}
