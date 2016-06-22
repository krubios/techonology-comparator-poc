package comparator.preferences.wifi.tdma;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparator.scheduler.Auxiliary;
import comparatortechnologyproject.Activator;

/**
 * P�gina de preferencias que permite configurar la potencia y sensibilidad seg�n la modulaci�n empleada
 * @author Karla
 *
 */
public class WifiTdmaPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private String[][] modulations = new String[][]{};
	WifiTdmaComboFieldEditor wifiTdmaCombo;
	
	public WifiTdmaPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.WifiTdmaPreferencePage_infoMessage);
	}

	@Override
	protected void createFieldEditors() {
		modulations = Auxiliary.getWifiTdmaElementsModulation();
		
		wifiTdmaCombo = new WifiTdmaComboFieldEditor(PreferenceConstants.MODULATION_WIFI_TDMA_COMBO, Messages.PreferencePage_modulation,
				modulations, getFieldEditorParent());
		
		StringFieldEditor wifiTdmaPower = new StringFieldEditor(PreferenceConstants.POWER_WIFI_TDMA, Messages.PreferencePage_power,
		        getFieldEditorParent());
		
		WifiTdmaSensibilityStringFieldEditor wifiTdmaSensibility = new WifiTdmaSensibilityStringFieldEditor(PreferenceConstants.SENSIBILITY_WIFI_TDMA, 
				Messages.PreferencePage_sensibility,
		        getFieldEditorParent());
		
		wifiTdmaCombo.setSensibilityFieldEditor(wifiTdmaSensibility);
		wifiTdmaCombo.setPowerFieldEditor(wifiTdmaPower);
		
		addField(wifiTdmaCombo);
		addField(wifiTdmaPower);
	    addField(wifiTdmaSensibility);
	}
}
