package au.net.projectb.auto;

import au.net.projectb.auto.modes.AutoMode;
import au.net.projectb.auto.modes.Default;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoController {
	SendableChooser<AutoMode> autoSelector;
	AutoMode selectedAuto;
	int currentStep;
	
	public AutoController() {
		autoSelector = new SendableChooser<AutoMode>();
		
		autoSelector.addDefault("Default", new Default());
		autoSelector.addObject("Example Mode", new Default());
		// Various auto modes
		SmartDashboard.putData("Auto Selector", autoSelector);
	}
	
	public void init() {
		selectedAuto = autoSelector.getSelected();
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
