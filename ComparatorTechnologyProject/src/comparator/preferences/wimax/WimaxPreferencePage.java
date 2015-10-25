package comparator.preferences.wimax;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparatortechnologyproject.Activator;



/**
 * Página de preferencias. Se permite configurar la potencia transmitida y la sensibilidad
 * según la modulación empleada
 * @Fecha de creación 20/04/2015
 * @author Karla
 *
 */
public class WimaxPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private static final String[] BPSK_1_2_PROVIDER = {"BPSK 1/2", "BPSK 1/2"};
	private static final String[] QPSK_1_2_PROVIDER = {"QPSK 1/2", "QPSK 1/2"};
	private static final String[] QPSK_3_4_PROVIDER = {"QPSK 3/4", "QPSK 3/4"};
	private static final String[] QAM16_1_2_PROVIDER = {"16 QAM 1/2", "16 QAM 1/2"};
	private static final String[] QAM16_3_4_PROVIDER = {"16 QAM 3/4", "16 QAM 3/4"};
	private static final String[] QAM64_2_3_PROVIDER = {"64 QAM 2/3", "64 QAM 2/3"};
	private static final String[] QAM64_3_4_PROVIDER = {"64 QAM 3/4", "64 QAM 3/4"};
	
	
	
	public WimaxPreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	protected void createFieldEditors() {
		
		String[][] modulations = getWimaxElementsModulation();
		
		WimaxComboFieldEditor wimaxCombo = new WimaxComboFieldEditor(PreferenceConstants.MODULATION_WIMAX_COMBO, Messages.PreferencePage_modulation, 
				modulations, getFieldEditorParent());
		
		StringFieldEditor powerWimax = new StringFieldEditor(PreferenceConstants.POWER_WIMAX, Messages.PreferencePage_power,
		        getFieldEditorParent());
		
		WimaxStringFieldEditor sensibilityWimax = new WimaxStringFieldEditor(PreferenceConstants.SENSIBILITY_WIMAX, Messages.PreferencePage_sensibility,
		        getFieldEditorParent());
		
		
		wimaxCombo.setSensibilityFieldEditor(sensibilityWimax);
	
		addField(wimaxCombo);
		
		addField(powerWimax);
		
	    addField(sensibilityWimax);
		
	}
	
	public void init(IWorkbench arg0) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	    setDescription(Messages.WimaxPreferencePage_infoMessage);
	}
	
	public  String[][] getWimaxElementsModulation()
	{
		return  new String[][] {
				BPSK_1_2_PROVIDER, QPSK_1_2_PROVIDER, QPSK_3_4_PROVIDER, QAM16_1_2_PROVIDER, QAM16_3_4_PROVIDER, QAM64_2_3_PROVIDER, QAM64_3_4_PROVIDER
		};
	}
}
