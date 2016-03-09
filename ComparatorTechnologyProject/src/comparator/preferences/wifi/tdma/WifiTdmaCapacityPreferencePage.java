package comparator.preferences.wifi.tdma;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.FloatFieldEditor;
import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparatortechnologyproject.Activator;

public class WifiTdmaCapacityPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{
	
	@Override
	public void init(IWorkbench arg0) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.WifiTdmaCapacityPreferencePage_infoMessage);
	}
	
	@Override
	protected void createFieldEditors() {
		
		FloatFieldEditor wifiTdmaFractionFrameDl = new FloatFieldEditor(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_DL, Messages.PreferencePage_FractionFrameDl, 
				getFieldEditorParent());
		
		FloatFieldEditor wifiTdmaFractionFrameUl = new FloatFieldEditor(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_UL, Messages.PreferencePage_FractionFrameUl, 
				getFieldEditorParent());
		
		wifiTdmaFractionFrameUl.setWifiFractionFrameDl(wifiTdmaFractionFrameDl);
		wifiTdmaFractionFrameDl.setWifiFractionFrameUl(wifiTdmaFractionFrameUl);
		
		addField(wifiTdmaFractionFrameDl);
		addField(wifiTdmaFractionFrameUl);
	}
}
