package comparator.preferences.wifi;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparator.preferences.FloatFieldEditor;
import comparatortechnologyproject.Activator;

public class WifiCapacityPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private static final String[] SIFS_VALUE = {"10/16","10/16"};
	private static final String[] RIFS_VALUE = {"2","2"};
	
	@Override
	public void init(IWorkbench arg0) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.WifiCapacityPreferencePage_infoMessage);
	}
	
	@Override
	protected void createFieldEditors() {
		String[][] sifs_rifs_values = getSifsRifsValues();
		
		ComboFieldEditor sifs_rifs_combo = new ComboFieldEditor(PreferenceConstants.COMBO_SIFS_RIFS, Messages.PreferencePage_Sifs_Rifs,
				sifs_rifs_values, getFieldEditorParent());
		
		IntegerFieldEditor numPackages = new IntegerFieldEditor(PreferenceConstants.NUM_PACKAGES, Messages.PreferencePage_NumPackages, getFieldEditorParent());
		
		IntegerFieldEditor packages_size = new IntegerFieldEditor(PreferenceConstants.PACKAGES_SIZE, Messages.PreferencePage_PackagesSize, getFieldEditorParent());
		
		BooleanFieldEditor block_ack = new BooleanFieldEditor(PreferenceConstants.BLOCK_ACK_ON_OFF, Messages.PreferencePage_BlockACKOnOff, getFieldEditorParent());
		
		FloatFieldEditor wifiFractionFrameDl = new FloatFieldEditor(PreferenceConstants.WIFI_FRACTION_FRAME_DL, Messages.PreferencePage_FractionFrameDl, 
				getFieldEditorParent());
		
		FloatFieldEditor wifiFractionFrameUl = new FloatFieldEditor(PreferenceConstants.WIFI_FRACTION_FRAME_UL, Messages.PreferencePage_FractionFrameUl, 
				getFieldEditorParent());
		
		wifiFractionFrameDl.setWifiFractionFrameUl(wifiFractionFrameUl);
		wifiFractionFrameUl.setWifiFractionFrameDl(wifiFractionFrameDl);
		
		addField(sifs_rifs_combo);
		addField(numPackages);
		addField(packages_size);
		addField(block_ack);
		addField(wifiFractionFrameDl);
		addField(wifiFractionFrameUl);
	}

	private String[][] getSifsRifsValues() {
		return new String[][]{
				RIFS_VALUE, SIFS_VALUE
		};
	}

}
