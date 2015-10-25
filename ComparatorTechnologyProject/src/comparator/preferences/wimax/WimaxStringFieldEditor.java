package comparator.preferences.wimax;

import java.util.Map;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class WimaxStringFieldEditor extends StringFieldEditor{
	
	public Map<String, WimaxPreferenceData> input = WimaxComboFieldEditor.input;
	private static boolean isComboModification= false;
	private static  String id= "";
	
	public WimaxStringFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}
	
	
	@Override
	protected void fireValueChanged(String property, Object oldValue,
			Object newValue) {
		super.fireValueChanged(property, oldValue, newValue);
		if (!isComboModification){
			float newSensibility = Float.parseFloat(newValue.toString());
			for(Map.Entry<String, WimaxPreferenceData> entry: WimaxComboFieldEditor.getWimaxPreferenceData().entrySet()){
				if(entry.getKey().equals(id)){
					WimaxPreferenceData data = new WimaxPreferenceData(entry.getKey(), entry.getValue().getModulation(),
							newSensibility);
					WimaxComboFieldEditor.setWimaxPreferenceData(entry.getKey(), data);
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
