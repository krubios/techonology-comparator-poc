package comparator.preferences.wifi.tdma;

import java.util.Map;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class WifiTdmaSensibilityStringFieldEditor extends StringFieldEditor{
	
	public Map<String, WifiTdmaPreferenceData> input = WifiTdmaComboFieldEditor.wifiTdmaInput;
	private static boolean isComboModification= false;
	private static  String id= "";
	
	public WifiTdmaSensibilityStringFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}
	
	@Override
	protected void fireValueChanged(String property, Object oldValue,
			Object newValue) {
		super.fireValueChanged(property, oldValue, newValue);
		if (!isComboModification){
			float newSensibility = Float.parseFloat(newValue.toString());
			for(Map.Entry<String, WifiTdmaPreferenceData> entry: WifiTdmaComboFieldEditor.getWifiTdmaPreferenceData().entrySet()){
				if(entry.getKey().equals(id)){
					WifiTdmaPreferenceData data = new WifiTdmaPreferenceData(entry.getKey(), entry.getValue().getModulation(), 
							newSensibility, entry.getValue().getPowerTx(), entry.getValue().getBitRate());
					WifiTdmaComboFieldEditor.setWifiTdmaPreferenceData(entry.getKey(), data);
					break;
				}
			}
		}
		isComboModification=false;
	}
	public static void setComboChange(boolean value) {
		isComboModification = value;
	}
	
	public static void setIdModulation(String value) {
		id = value;
	}

}
