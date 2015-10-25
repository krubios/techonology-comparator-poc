package comparator.preferences.wifi;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparator.scheduler.Auxiliary;
import comparatortechnologyproject.Activator;


/**
 * Página de preferencias que permite configurar la potencia y sensibilidad según la modulación empleada
 * @author Karla
 * @Fecha de creación 20/04/2015
 */
public class WifiPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	
	private String[][] modulations = new String[][]{};
	WifiComboFieldEditor wifiCombo;
	
	
	public WifiPreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	@Override
	public void init(IWorkbench arg0) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.WifiPreferencePage_infoMessage);
	}

	@Override
	protected void createFieldEditors() {
		
		modulations = Auxiliary.getWifiElementsModulation();
		BooleanFieldEditor booleanMimo = new BooleanFieldEditor(PreferenceConstants.MIMO_WIFI, Messages.WifiPreferencePage_mimo, getFieldEditorParent());
	
		booleanMimo.setPropertyChangeListener(this);
		
		wifiCombo  = new WifiComboFieldEditor(PreferenceConstants.MODULATION_WIFI_COMBO, Messages.PreferencePage_modulation, 
				modulations, getFieldEditorParent());
		
		StringFieldEditor wifiPower = new StringFieldEditor(PreferenceConstants.POWER_WIFI, Messages.PreferencePage_power,
		        getFieldEditorParent());
		
		WifiSensibilityStringFieldEditor wifiSensibility = new WifiSensibilityStringFieldEditor(PreferenceConstants.SENSIBILITY_WIFI, Messages.PreferencePage_sensibility,
		        getFieldEditorParent());
		
		BooleanFieldEditor booleanRts = new BooleanFieldEditor(PreferenceConstants.RTS_CAPACITY_WIFI, Messages.WifiPreferencePage_RtsCapacity, getFieldEditorParent());
		
		wifiCombo.setSensibilityFieldEditor(wifiSensibility);
		wifiCombo.setPowerFieldEditor(wifiPower);
		
		addField(booleanMimo);
		addField(wifiCombo);
		addField(wifiPower);
	    addField(wifiSensibility);
	    addField(booleanRts);
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		String[][] mimoModulations = Auxiliary.getWifiMimoElementsModulation();
		if(event.getSource() instanceof BooleanFieldEditor && 
				((BooleanFieldEditor)event.getSource()).getPreferenceName().equals(PreferenceConstants.MIMO_WIFI)){
			Auxiliary.setMimoModulation((boolean)event.getNewValue());
			boolean isCkeckMimoBox = (boolean) event.getNewValue();
			if(isCkeckMimoBox){
				wifiCombo.setEntries(mimoModulations);
			}else{
				wifiCombo.setEntries(modulations);
			}
		}
		super.propertyChange(event);
	}
}
