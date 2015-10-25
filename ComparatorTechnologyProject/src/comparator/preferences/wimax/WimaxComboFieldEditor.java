package comparator.preferences.wimax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

import comparator.preferences.Messages;

public class WimaxComboFieldEditor extends ComboFieldEditor{
	
	private static StringFieldEditor sensibilityFieldEditor;
	private static WimaxPreferenceData data1 = new WimaxPreferenceData("01",Messages.BPSK_1_2_PROVIDER.trim(), 
			Float.parseFloat(Messages.BPSK_1_2_VALUE));
	private static WimaxPreferenceData data2 =  new WimaxPreferenceData("02",Messages.QPSK_1_2_PROVIDER.trim(),
			Float.parseFloat(Messages.QPSK_1_2_VALUE));
	private static WimaxPreferenceData data3 = new WimaxPreferenceData("03", Messages.QPSK_3_4_PROVIDER.trim(),
			Float.parseFloat(Messages.QPSK_3_4_VALUE));
	private static WimaxPreferenceData data4 = new WimaxPreferenceData("04",Messages.QAM16_1_2_PROVIDER.trim(),
			Float.parseFloat(Messages.QAM16_1_2_VALUE));
	private static WimaxPreferenceData data5 = new WimaxPreferenceData("05",Messages.QAM16_3_4_PROVIDER.trim(), 
			Float.parseFloat(Messages.QAM16_3_4_VALUE));
	private static WimaxPreferenceData data6 = new WimaxPreferenceData("06",Messages.QAM64_2_3_PROVIDER.trim(), 
			Float.parseFloat(Messages.QAM64_2_3_VALUE));
	private static WimaxPreferenceData data7 = new WimaxPreferenceData("07",Messages.QAM64_3_4_PROVIDER.trim(),
			Float.parseFloat(Messages.QAM64_3_4_VALUE));
	
	public static Map<String,WimaxPreferenceData> input;
	static
	{
		input = new HashMap<String,WimaxPreferenceData>();
		input.put("01",data1);
		input.put("02",data2);
		input.put("03",data3);
		input.put("04",data4);
		input.put("05",data5);
		input.put("06",data6);
		input.put("07",data7);
	}
	
	public static ArrayList<WimaxPreferenceData> inputModified = new ArrayList<WimaxPreferenceData>();
													
	public WimaxComboFieldEditor(String name, String labelText,
			String[][] entryNamesAndValues, Composite parent) {
		super(name, labelText, entryNamesAndValues, parent);
	}
	
    @Override
    protected void fireValueChanged(String property, Object oldValue,
    		Object newValue) {
    	super.fireValueChanged(property, oldValue, newValue);
    	for (Map.Entry<String, WimaxPreferenceData> entry:input.entrySet()){
    		if (entry.getValue().getModulation().equals(newValue)){
    			WimaxStringFieldEditor.setComboChange(true);
    			WimaxStringFieldEditor.setIdModulation(entry.getValue().getId());
    			sensibilityFieldEditor.setStringValue(String.valueOf(entry.getValue().getSensibility()));
    		}
    	}
    }
    
    public static String getSensibilityFieldEditor(){
    	return sensibilityFieldEditor.getStringValue();
    }
    public void setSensibilityFieldEditor(StringFieldEditor editor){
    	sensibilityFieldEditor = editor;
    }
    
    public static String getSensibilityWimaxModulation(String modulation){
    	String sensibility ="";
    	for(Map.Entry<String, WimaxPreferenceData> entry: input.entrySet()){
    		if (entry.getValue().getModulation().equals(modulation.trim())){
    			sensibility = String.valueOf(entry.getValue().getSensibility());
    			break;
    		}
    	}
    	return sensibility;
    }
    
    public static Map<String, WimaxPreferenceData> getWimaxPreferenceData(){
    	return input;
    }
    
	public static ArrayList<WimaxPreferenceData> getInputModified() {
		return inputModified;
	}

	public static void setWimaxPreferenceData(String id, WimaxPreferenceData data) {
		for(Map.Entry<String, WimaxPreferenceData> entry:input.entrySet() ){
			if(entry.getKey().equals(id)){
				entry.setValue(data);
				break;
			}
		}
	}
}
