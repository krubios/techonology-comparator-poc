package comparator.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import comparatortechnologyproject.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer{

	public PreferenceInitializer(){
	}
	
	public void initializeDefaultPreferences() {
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		//Wimax
		store.setDefault(PreferenceConstants.MODULATION_WIMAX_COMBO, Messages.BPSK_1_2_PROVIDER);
		store.setDefault(PreferenceConstants.SENSIBILITY_WIMAX,Messages.BPSK_1_2_VALUE);
		
		//Wifi
		store.setDefault(PreferenceConstants.MIMO_WIFI, false);
		store.setDefault(PreferenceConstants.MODULATION_WIFI_COMBO, Messages.MCS_0);
		store.setDefault(PreferenceConstants.SENSIBILITY_WIFI, Messages.MCS_0_SENSIBILITY);
		store.setDefault(PreferenceConstants.POWER_WIFI, Messages.MCS_0_POWER_VALUE);
		store.setDefault(PreferenceConstants.RTS_CAPACITY_WIFI, false);
		
		//Capacidad Wimax
		store.setDefault(PreferenceConstants.CYCLIX_PREFIX, "1/32");
		store.setDefault(PreferenceConstants.BAND_WIDTH, "10");
		store.setDefault(PreferenceConstants.FRAME_DURATION, "20");
		store.setDefault(PreferenceConstants.WIMAX_FRACTION_FRAME_DL, "0.51");
		store.setDefault(PreferenceConstants.WIMAX_FRACTION_FRAME_UL, "0.49");
		
		//Capacidad Wifi
		store.setDefault(PreferenceConstants.COMBO_SIFS_RIFS, "2");
		store.setDefault(PreferenceConstants.BLOCK_ACK_ON_OFF, 1);
		store.setDefault(PreferenceConstants.NUM_PACKAGES, 42);
		store.setDefault(PreferenceConstants.PACKAGES_SIZE, 12000);
		store.setDefault(PreferenceConstants.WIFI_FRACTION_FRAME_DL, "0.5");
		store.setDefault(PreferenceConstants.WIFI_FRACTION_FRAME_UL, "0.5");
	}

}
