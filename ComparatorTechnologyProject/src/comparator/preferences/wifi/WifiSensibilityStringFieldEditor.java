package comparator.preferences.wifi;

import java.util.Map;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class WifiSensibilityStringFieldEditor extends StringFieldEditor{
	
	public Map<String, WifiPreferenceData> input = WifiComboFieldEditor.wifiInput;
	private static boolean isComboModification= false;
	private static  String id= "";
	
	public WifiSensibilityStringFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}
	
	@Override
	protected void fireValueChanged(String property, Object oldValue,
			Object newValue) {
		super.fireValueChanged(property, oldValue, newValue);
		if (!isComboModification){
			float newSensibility = Float.parseFloat(newValue.toString());
			for(Map.Entry<String, WifiPreferenceData> entry: WifiComboFieldEditor.getWifiPreferenceData().entrySet()){
				if(entry.getKey().equals(id)){
					WifiPreferenceData data = new WifiPreferenceData(entry.getKey(), entry.getValue().getModulation(), 
							newSensibility, entry.getValue().getPowerTransmitted(), entry.getValue().getbitRate());
					WifiComboFieldEditor.setWifiPreferenceData(entry.getKey(), data);
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
