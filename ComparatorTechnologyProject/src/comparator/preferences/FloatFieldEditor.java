package comparator.preferences;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class FloatFieldEditor extends StringFieldEditor {
	
	private FloatFieldEditor wimaxFractionFrameUl;
	private FloatFieldEditor wimaxFractionFrameDl;
	private FloatFieldEditor wifiFractionFrameUl;
	private FloatFieldEditor wifiFractionFrameDl;
	private FloatFieldEditor wifiTdmaFractionFrameUl;
	private FloatFieldEditor wifiTdmaFractionFrameDl;
	
    private double minValidValue = 0;
    private double maxValidValue = 1;
	
    private static final int DEFAULT_TEXT_LIMIT = 10;

    protected FloatFieldEditor() {
    }
   
    public FloatFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
    }


    public FloatFieldEditor(String name, String labelText, Composite parent,
            int textLimit) {
        init(name, labelText);
        setTextLimit(textLimit);
        setEmptyStringAllowed(false);
        setErrorMessage(JFaceResources
                .getString(Messages.FloatFieldEditorErrorMessage));//$NON-NLS-1$
        createControl(parent);
    }

    
    public void setValidRange(double min, double max) {
        minValidValue = min;
        maxValidValue = max;
        setErrorMessage(JFaceResources.format(
        		"IntegerFieldEditor.errorMessageRange", //$NON-NLS-1$
        		new Object[] { new Double(min), new Double(max) }));
    }

    protected boolean checkState() {

        Text text = getTextControl();

        if (text == null) {
			return false;
		}

        String numberString = text.getText();
        try {
            double number = Double.valueOf(numberString).doubleValue();
            if (number >= minValidValue && number <= maxValidValue) {
				clearErrorMessage();
				return true;
			}
            
			showErrorMessage();
			return false;
			
        } catch (NumberFormatException e1) {
            showErrorMessage();
        }

        return false;
    }

  
    protected void doLoad() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDouble(getPreferenceName());
            text.setText("" + value);//$NON-NLS-1$
            oldValue = "" + value; //$NON-NLS-1$
        }

    }

    protected void doLoadDefault() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDefaultDouble((getPreferenceName()));
            text.setText("" + value);//$NON-NLS-1$
        }
        valueChanged();
    }

    
    protected void doStore() {
        Text text = getTextControl();
        if (text != null) {
            Double i = new Double(text.getText());
            getPreferenceStore().setValue(getPreferenceName(), i.doubleValue());
        }
    }
   
    public double getdoubleValue() throws NumberFormatException {
        return new Double(getStringValue()).doubleValue();
    }
    
    @Override
    protected void fireValueChanged(String property, Object oldValue,
    		Object newValue) {
    	super.fireValueChanged(property, oldValue, newValue);
    	if (!newValue.toString().equals("0.")){
	    	double newValueDouble = Double.valueOf(newValue.toString());
	    	double value = (double)Math.round((1 - newValueDouble) * 100000) / 100000;
	    	if (this.getPreferenceName().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_DL)){
	    		wimaxFractionFrameUl.setStringValue(String.valueOf(value));
	    	}else if (this.getPreferenceName().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_UL)){
	    		wimaxFractionFrameDl.setStringValue(String.valueOf(value));
	    	}else if (this.getPreferenceName().equals(PreferenceConstants.WIFI_FRACTION_FRAME_UL)){
	    		wifiFractionFrameDl.setStringValue(String.valueOf(value));
	    	}else if (this.getPreferenceName().equals(PreferenceConstants.WIFI_FRACTION_FRAME_DL)){
	    		wifiFractionFrameUl.setStringValue(String.valueOf(value));
	    	}else if (this.getPreferenceName().equals(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_UL)){
	    		wifiTdmaFractionFrameDl.setStringValue(String.valueOf(value));
	    	}else if (this.getPreferenceName().equals(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_DL)){
	    		wifiTdmaFractionFrameUl.setStringValue(String.valueOf(value));
	    	}
    	}
    }

	public void setWimaxFractionFrameUl(FloatFieldEditor fractionFrameUlValue) {
		this.wimaxFractionFrameUl = fractionFrameUlValue;
	}
	
	public FloatFieldEditor getWifiTdmaFractionFrameUl() {
		return wifiTdmaFractionFrameUl;
	}

	public void setWifiTdmaFractionFrameUl(FloatFieldEditor wifiTdmaFractionFrameUl) {
		this.wifiTdmaFractionFrameUl = wifiTdmaFractionFrameUl;
	}

	public FloatFieldEditor getWifiTdmaFractionFrameDl() {
		return wifiTdmaFractionFrameDl;
	}

	public void setWifiTdmaFractionFrameDl(FloatFieldEditor wifiTdmaFractionFrameDl) {
		this.wifiTdmaFractionFrameDl = wifiTdmaFractionFrameDl;
	}

	public FloatFieldEditor getWimaxFractionFrameUl(){
		return wimaxFractionFrameUl;
	}
	
	public void setWimaxFractionFrameDl(FloatFieldEditor fractionFrameDlValue) {
		wimaxFractionFrameDl = fractionFrameDlValue;
	}
	
	public FloatFieldEditor getWimaxFractionFrameDl(){
		return wimaxFractionFrameDl;
	}

	public void setWifiFractionFrameUl(FloatFieldEditor wifiFractionFrameUlValue) {
		wifiFractionFrameUl = wifiFractionFrameUlValue;
	}
	
	public FloatFieldEditor getWifiFractionFrameUl(){
		return wifiFractionFrameUl;
	}
	
	public void setWifiFractionFrameDl(FloatFieldEditor wifiFractionFrameDlValue){
		wifiFractionFrameDl = wifiFractionFrameDlValue;
	}
	
	public FloatFieldEditor getWifiFractionFrameDl(){
		return wifiFractionFrameDl;
	}
	
	
}