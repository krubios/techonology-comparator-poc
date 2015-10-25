package comparator.preferences.wifi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import comparator.scheduler.Auxiliary;

public class WifiComboFieldEditor extends FieldEditor{

	private StringFieldEditor sensibilityFieldEditor;
	private StringFieldEditor powerFieldEditor;
	
	public static ArrayList<WifiPreferenceData> inputModified = new ArrayList<WifiPreferenceData>();
	
	public static Map<String,WifiPreferenceData> wifiInput;
	static
	{
		wifiInput = new HashMap<String,WifiPreferenceData>();
		wifiInput.put("00", Auxiliary.data0);
		wifiInput.put("01", Auxiliary.data1);
		wifiInput.put("02", Auxiliary.data2);
		wifiInput.put("03", Auxiliary.data3);
		wifiInput.put("04", Auxiliary.data4);
		wifiInput.put("05", Auxiliary.data5);
		wifiInput.put("06", Auxiliary.data6);
		wifiInput.put("07", Auxiliary.data7);
		wifiInput.put("08", Auxiliary.data8);
		wifiInput.put("09", Auxiliary.data9);
		wifiInput.put("10", Auxiliary.data10);
		wifiInput.put("11", Auxiliary.data11);
		wifiInput.put("12", Auxiliary.data12);
		wifiInput.put("13", Auxiliary.data13);
		wifiInput.put("14", Auxiliary.data14);
		wifiInput.put("15", Auxiliary.data15);
	}
	
	private Combo fCombo;
	private String fValue;
	private static String[][] fEntryNamesAndValues;
	
	public WifiComboFieldEditor(String name, String labelText,
			String[][] entryNamesAndValues, Composite parent) {
		init(name, labelText);
		Assert.isTrue(checkArray(entryNamesAndValues));
		fEntryNamesAndValues = entryNamesAndValues;
		createControl(parent);		
	}
	
	private boolean checkArray(String[][] table) {
		if (table == null) {
			return false;
		}
		for (int i = 0; i < table.length; i++) {
			String[] array = table[i];
			if (array == null || array.length != 2) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void adjustForNumColumns(int numColumns) {
		if ( numColumns <= 1 )
			return;
		int span = numColumns;
		Control control = getLabelControl();
		if (control != null) {
			((GridData)control.getLayoutData()).horizontalSpan = 1;
			--span;
		}
		((GridData)fCombo.getLayoutData()).horizontalSpan = span;
	}
	
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);
		control = getComboBoxControl(parent);
		gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);
	}

	@Override
	protected void doLoad() {
		updateComboForValue(getPreferenceStore().getString(getPreferenceName()));
	}
	
	@Override
	protected void doLoadDefault() {
		updateComboForValue(getPreferenceStore().getDefaultString(getPreferenceName()));
	}

	@Override
	protected void doStore() {
		if (fValue == null) {
			getPreferenceStore().setToDefault(getPreferenceName());
			return;
		}
		getPreferenceStore().setValue(getPreferenceName(), fValue);
	}

	@Override
	public int getNumberOfControls() {
		return 1;
	}

	protected Combo getComboBoxControl(Composite parent) {
		if (fCombo == null) {
			fCombo = new Combo(parent, SWT.READ_ONLY);
			for (int i = 0; i < fEntryNamesAndValues.length; i++) {
				fCombo.add(fEntryNamesAndValues[i][0], i);
			}
			
			fCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent evt) {
					String oldValue = fValue;
					String name = fCombo.getText();
					fValue = getValueForName(name);
					setPresentsDefaultValue(false);
					fireValueChanged(VALUE, oldValue, fValue);					
				}
			});
		}
		return fCombo;
	}
	
	protected Combo getComboBoxControl() {
	    return fCombo;
	}
	
	protected String getValueForName(String name) {
		for (int i = 0; i < fEntryNamesAndValues.length; i++) {
			String[] entry = fEntryNamesAndValues[i];
			if (name.equals(entry[0])) {
				return entry[1];
			}
		}
		return fEntryNamesAndValues[0][0];
	}
	
	protected void updateComboForValue(String value) {
		fValue = value;
		for (int i = 0; i < fEntryNamesAndValues.length; i++) {
			if (value.equals(fEntryNamesAndValues[i][1])) {
				fCombo.setText(fEntryNamesAndValues[i][0]);
				return;
			}
		}
		if (fEntryNamesAndValues.length > 0) {
			fValue = fEntryNamesAndValues[0][1];
		}
	}

	@Override
	protected void setPresentsDefaultValue( boolean b )
	{
		super.setPresentsDefaultValue( b );
	}

	protected void setEntries(String[][] entryNamesAndValues) {
		fEntryNamesAndValues = entryNamesAndValues;
		Combo combo = getComboBoxControl();
		// dispose old items.
		if (combo.getItemCount() > 0) {
			combo.removeAll();
		}
		//load values from contribution
		for (int i=0; i<fEntryNamesAndValues.length; ++i) {
			combo.add(fEntryNamesAndValues[i][0], i);
		}
		fCombo.select(0);
	}

	@Override
	protected void fireValueChanged(String property, Object oldValue,
			Object newValue) {
		super.fireValueChanged(property, oldValue, newValue);
		for (Map.Entry<String, WifiPreferenceData> entry: wifiInput.entrySet()){
			if (entry.getValue().getModulation().equals(newValue)){
				WifiSensibilityStringFieldEditor.setComboChange(true);
				WifiSensibilityStringFieldEditor.setIdModulation(entry.getValue().getId());
				sensibilityFieldEditor.setStringValue(String.valueOf(entry.getValue().getSensibility()));
				powerFieldEditor.setStringValue(String.valueOf(entry.getValue().getPowerTransmitted()));
			}
		}
	}

	public void setSensibilityFieldEditor(StringFieldEditor sensibilityEditor){
    	sensibilityFieldEditor = sensibilityEditor;
    }
	
	public void setPowerFieldEditor(StringFieldEditor powerEditor){
		powerFieldEditor = powerEditor;
	}
	public static String getSensitivityWifi(String modulation) {
		String sensibility = "";
		
		for(Map.Entry<String, WifiPreferenceData> entry: wifiInput.entrySet()){
			if(entry.getValue().getModulation().equals(modulation)){
				sensibility = String.valueOf(entry.getValue().getSensibility());
				break;
			}
		}
		return sensibility;
	}
	
	public static Map<String, WifiPreferenceData> getWifiPreferenceData(){
    	return wifiInput;
    }

	public static ArrayList<WifiPreferenceData> getInputModified() {
		return inputModified;
	}

	public static void setWifiPreferenceData(String id, WifiPreferenceData data) {
		for(Map.Entry<String, WifiPreferenceData> entry: wifiInput.entrySet() ){
			if(entry.getKey().equals(id)){
				entry.setValue(data);
				break;
			}
		}
	}

}
