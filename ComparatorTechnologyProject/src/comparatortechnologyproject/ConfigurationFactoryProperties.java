package comparatortechnologyproject;

import org.eclipse.jface.util.PropertyChangeEvent;

import comparator.preferences.PreferenceConstants;
import comparator.preferences.wifi.WifiCapacity;
import comparator.preferences.wimax.WimaxCapacity;
import comparator.scheduler.Auxiliary;

/**
 * Clase que lee las propiedades de configuración tanto de Wimax como Wifi
 * @author Karla
 *
 */
public class ConfigurationFactoryProperties {

	private ConfigurationFactoryProperties(){
	}

	public static final ConfigurationProperties getInstance (PropertyChangeEvent event){
		
		ConfigurationProperties configurationProperties = new ConfigurationProperties();
		
		if(event.getProperty().equals(PreferenceConstants.FRAME_DURATION)){
			String frameDurationValue = event.getNewValue().toString();
			float frameDuration = (float) (Float.parseFloat(frameDurationValue)*Math.pow(10, -3));
			configurationProperties.setFrameDuration(frameDuration);
			configurationProperties.setFrameDurationChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_DL)){
			String fractionFrameDl = event.getNewValue().toString();
			if (Auxiliary.isNumber(fractionFrameDl)){
				configurationProperties.setWimaxFractionFrameDl(Float.parseFloat(fractionFrameDl));
				configurationProperties.setWimaxFractionFrameDlChange(true);
			}
		}else if (event.getProperty().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_UL)){
			String fractionFrameUl = event.getNewValue().toString();
			if (Auxiliary.isNumber(fractionFrameUl)){
				configurationProperties.setWimaxFractionFrameUl(Float.parseFloat(fractionFrameUl));
				configurationProperties.setWimaxFractionFrameUlChange(true);
			}
		}else if (event.getProperty().equals(PreferenceConstants.BAND_WIDTH)){
			String bandWidthValue = event.getNewValue().toString();
			float bandWidth = (float) (Float.parseFloat(bandWidthValue) * Math.pow(10, 6));
			configurationProperties.setBandWidth(bandWidth);
			configurationProperties.setBandWidthChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.CYCLIX_PREFIX)){
			String cyclixPrefix = event.getNewValue().toString();
			float cyclixPrefixValue = WimaxCapacity.getCyclixPrefixValue(cyclixPrefix);
			configurationProperties.setCyclixPrefix(cyclixPrefixValue);
			configurationProperties.setCyclixPrefixChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.COMBO_SIFS_RIFS)){
			String comboSifsDifsValue = event.getNewValue().toString();
			float comboValue = WifiCapacity.getSifsRifsValue(comboSifsDifsValue);
			configurationProperties.setSifsRifsTime(comboValue);
			configurationProperties.setSifsRifsTimeChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.BLOCK_ACK_ON_OFF)){
			String blockACKOnOff = event.getNewValue().toString();
			configurationProperties.setBlockACKOnOff(Float.parseFloat(blockACKOnOff));
			configurationProperties.setBlockACKOnOffChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.NUM_PACKAGES)){
			String numPackagesValue = event.getNewValue().toString();
			configurationProperties.setNumPackages(Float.parseFloat(numPackagesValue));
			configurationProperties.setNumPackagesChange(true);
			
		}else if (event.getProperty().equals(PreferenceConstants.PACKAGES_SIZE)){
			String packageSizeValue = event.getNewValue().toString();
			configurationProperties.setPackagesSize(Float.parseFloat(packageSizeValue));
		
		}else if (event.getProperty().equals(PreferenceConstants.RTS_CAPACITY_WIFI)){
			if (NavigationView.getSelectedSubscriber() != null){
				String isRts = event.getNewValue().toString();
				configurationProperties.setRtsCapacityFlag(isRts);
				configurationProperties.setRtsCapacityFlagChange(true);
			}
			
		}else if (event.getProperty().equals(PreferenceConstants.WIFI_FRACTION_FRAME_DL)){
			String fractionFrameDl = event.getNewValue().toString();
			if (Auxiliary.isNumber(fractionFrameDl)){
				configurationProperties.setWifiFractionFrameDl(Float.parseFloat(fractionFrameDl));
				configurationProperties.setWifiFractionFrameDlChange(true);
			}
			
		}else if (event.getProperty().equals(PreferenceConstants.WIFI_FRACTION_FRAME_UL)){
			String fractionFrameUl = event.getNewValue().toString();
			if (Auxiliary.isNumber(fractionFrameUl)){
				configurationProperties.setWifiFractionFrameUl(Float.parseFloat(fractionFrameUl));
				configurationProperties.setWifiFractionFrameUlChange(true);
			}
		}else if(event.getProperty().equals(PreferenceConstants.SENSIBILITY_WIMAX)){
			configurationProperties.setSenbilityWimaxChange(true);
		
		}else if (((event.getProperty().equals(PreferenceConstants.MIMO_WIFI)) ||
				(event.getProperty().equals(PreferenceConstants.SENSIBILITY_WIFI)))){
			configurationProperties.setMimoOrSensibilityWifiChange(true);
		}
		return configurationProperties;
	}
}
