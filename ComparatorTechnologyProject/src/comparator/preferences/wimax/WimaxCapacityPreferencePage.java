package comparator.preferences.wimax;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import comparator.preferences.FloatFieldEditor;
import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparatortechnologyproject.Activator;

public class WimaxCapacityPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private static final String[] PARAM_1_4 = {"1/4", "1/4"};
	private static final String[] PARAM_1_8 = {"1/8", "1/8"};
	private static final String[] PARAM_1_16 = {"1/16", "1/16"};
	private static final String[] PARAM_1_32 = {"1/32", "1/32"};
	
	private static final String[] PARAM_2_5 = {"2.5", "2.5"};
	private static final String[] PARAM_5 = {"5", "5"};
	private static final String[] PARAM_10 = {"10", "10"};
	private static final String[] PARAM_20 = {"20", "20"};
	
	
	@Override
	public void init(IWorkbench arg0) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.WimaxCapacityPreferencePage_infoMessage);
	}

	@Override
	protected void createFieldEditors() {
		
		String[][] cyclixPrefixValues = getCyclixPrefixValues();
		
		ComboFieldEditor cyclixPrefix = new ComboFieldEditor(PreferenceConstants.CYCLIX_PREFIX, Messages.PreferencePage_CyclixPrefix, 
				cyclixPrefixValues, getFieldEditorParent());
		
		StringFieldEditor bandWidth = new StringFieldEditor(PreferenceConstants.BAND_WIDTH, Messages.PreferencePage_BandWidth,
		        getFieldEditorParent());
		
		ComboFieldEditor frameDuration = new ComboFieldEditor(PreferenceConstants.FRAME_DURATION, Messages.PreferencePage_FrameDuration,
		        getFrameDurationValues(), getFieldEditorParent());
//		
		FloatFieldEditor fractionFrameDl = new FloatFieldEditor(PreferenceConstants.WIMAX_FRACTION_FRAME_DL, Messages.PreferencePage_FractionFrameDl, 
				getFieldEditorParent());
		
		FloatFieldEditor fractionFrameUl = new FloatFieldEditor(PreferenceConstants.WIMAX_FRACTION_FRAME_UL, Messages.PreferencePage_FractionFrameUl, 
				getFieldEditorParent());
		
		fractionFrameDl.setWimaxFractionFrameUl(fractionFrameUl);
		fractionFrameUl.setWimaxFractionFrameDl(fractionFrameDl);
		
		addField(bandWidth);
		addField(cyclixPrefix);
		addField(frameDuration);
		addField(fractionFrameDl);
		addField(fractionFrameUl);
	}
	
	private String[][] getCyclixPrefixValues(){
		return new String[][]{
				PARAM_1_4,PARAM_1_8, PARAM_1_16, PARAM_1_32
			};
	}

	private String[][] getFrameDurationValues(){
		return new String[][]{
				PARAM_2_5,PARAM_5, PARAM_10,PARAM_20
		};
	}
}
