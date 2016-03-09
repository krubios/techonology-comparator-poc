package comparatortechnologyproject;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TreeItem;

import comparator.preferences.wifi.WifiComboFieldEditor;
import comparator.preferences.wifi.tdma.WifiTdmaCapacity;
import comparator.preferences.wimax.WimaxComboFieldEditor;
import comparator.scheduler.Auxiliary;
import comparator.scheduler.Subscriber;

/**
 * POJO para los elementos de configuración de WImax y Wifi
 * @author Karla
 *
 */
public class ConfigurationProperties {
	
	private static ArrayList<String> wimaxModulationParamList = new ArrayList<String>(
			Arrays.asList("64 QAM 3/4", "64 QAM 2/3", "16 QAM 3/4",
					"16 QAM 1/2", "QPSK 3/4", "QPSK 1/2", "BPSK 1/2"));
	
	private static String higthWimaxModulationUL = "";
	private static String higthWimaxModulationDL = "";
	private static String higthWifiModulationDL = "";
	private static String higthWifiModulationUL = "";
	
	private float frameDuration;
	private float wimaxFractionFrameDl;
	private float wimaxFractionFrameUl;
	private float bandWidth;
	private float cyclixPrefix;
	private float sifsRifsTime;
	private float blockACKOnOff;
	private float numPackages;
	private float packageSize;
	
	private String rtsCapacityFlag;
	private float wifiFractionFrameDl;
	private float wifiFractionFrameUl;
	
	private float wifiTdmaFractionFrameDl;
	private float wifiTdmaFractionFrameUl;
	
	private boolean frameDurationChange;
	private boolean wimaxFractionFrameDlChange;
	private boolean wimaxFractionFrameUlChange;
	private boolean bandWidthChange;
	private boolean cyclixPrefixChange;
	private boolean sifsRifsTimeChange;
	private boolean blockACKOnOffChange;
	private boolean numPackagesChange;
	private boolean packageSizeChange;
	private boolean rtsCapacityFlagChange;
	private boolean wifiFractionFrameDlChange;
	private boolean wifiFractionFrameUlChange;
	private boolean senbilityWimaxChange;

	private boolean wifiTdmaFractionFrameDlChange;
	private boolean wifiTdmaFractionFrameUlChange;
	private boolean mimoOrSensibilityWifiChange;
	
	public ConfigurationProperties(){
		
	}
	public float getFrameDuration() {
		return frameDuration;
	}
	
	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}
	
	public float getWimaxFractionFrameDl() {
		return wimaxFractionFrameDl;
	}
	
	public void setWimaxFractionFrameDl(float wimaxFractionFrameDl) {
		this.wimaxFractionFrameDl = wimaxFractionFrameDl;
	}
	
	public float getWimaxFractionFrameUl() {
		return wimaxFractionFrameUl;
	}
	
	public void setWimaxFractionFrameUl(float wimaxFractionFrameUl) {
		this.wimaxFractionFrameUl = wimaxFractionFrameUl;
	}
	
	public float getBandWidth() {
		return bandWidth;
	}
	
	public void setBandWidth(float bandWidth) {
		this.bandWidth = bandWidth;
	}
	
	public float getCyclixPrefix() {
		return cyclixPrefix;
	}
	
	public void setCyclixPrefix(float cyclixPrefix) {
		this.cyclixPrefix = cyclixPrefix;
	}
	
	public float getSifsRifsTime() {
		return sifsRifsTime;
	}
	
	public void setSifsRifsTime(float sifsRifsTime) {
		this.sifsRifsTime = sifsRifsTime;
	}
	
	public float getBlockACKOnOff() {
		return blockACKOnOff;
	}
	
	public void setBlockACKOnOff(float blockACKOnOff) {
		this.blockACKOnOff = blockACKOnOff;
	}
	
	public float getNumPackages() {
		return numPackages;
	}
	
	public void setNumPackages(float numPackages) {
		this.numPackages = numPackages;
	}
	
	public String getRtsCapacityFlag() {
		return rtsCapacityFlag;
	}
	
	public void setRtsCapacityFlag(String rtsCapacityFlag) {
		this.rtsCapacityFlag = rtsCapacityFlag;
	}
	
	public float getWifiFractionFrameDl() {
		return wifiFractionFrameDl;
	}
	
	public void setWifiFractionFrameDl(float wifiFractionFrameDl) {
		this.wifiFractionFrameDl = wifiFractionFrameDl;
	}
	
	public float getWifiFractionFrameUl() {
		return wifiFractionFrameUl;
	}
	
	public void setWifiFractionFrameUl(float wifiFractionFrameUl) {
		this.wifiFractionFrameUl = wifiFractionFrameUl;
	}
	
	public void setFrameDurationChange(boolean frameDurationChange) {
		this.frameDurationChange = frameDurationChange;
	}
	
	public boolean isFrameDurationChange() {
		return frameDurationChange;
	}
	
	public void setWimaxFractionFrameDlChange(boolean wimaxFractionFrameDlChange) {
		this.wimaxFractionFrameDlChange = wimaxFractionFrameDlChange;
	}
	
	public void setWimaxFractionFrameUlChange(boolean wimaxFractionFrameUlChange) {
		this.wimaxFractionFrameUlChange = wimaxFractionFrameUlChange;
	}
	
	public boolean isWimaxFractionFrameDlChange() {
		return wimaxFractionFrameDlChange;
	}
	public boolean isWimaxFractionFrameUlChange() {
		return wimaxFractionFrameUlChange;
	}
	public void setBandWidthChange(boolean bandWidthChange) {
		this.bandWidthChange = bandWidthChange;
	}
	
	public boolean isBandWidthChange() {
		return bandWidthChange;
	}
	public void setCyclixPrefixChange(boolean cyclixPrefixChange) {
		this.cyclixPrefixChange = cyclixPrefixChange;
	}
	
	public boolean isCyclixPrefixChange() {
		return cyclixPrefixChange;
	}
	
	public void setSifsRifsTimeChange(boolean sifsRifsTimeChange) {
		this.sifsRifsTimeChange = sifsRifsTimeChange;
	}
	
	public boolean isSifsRifsTimeChange() {
		return sifsRifsTimeChange;
	}
	
	public void setBlockACKOnOffChange(boolean blockACKOnOffChange) {
		this.blockACKOnOffChange =blockACKOnOffChange;
	}
	
	public boolean isBlockACKOnOffChange() {
		return blockACKOnOffChange;
	}
	
	public void setNumPackagesChange(boolean numPackagesChange) {
		this.numPackagesChange = numPackagesChange;
	}
	
	public boolean isNumPackagesChange() {
		return numPackagesChange;
	}
	public void setPackagesSize(float packageSize) {
		this.packageSize = packageSize;
	}
	
	public float getPackagesSize() {
		return packageSize;
	}
	
	public void setPackagesSizeChange(boolean packageSizeChange) {
		this.packageSizeChange = packageSizeChange;
	}
	
	public boolean isPackagesSizeChange() {
		return packageSizeChange;
	}
	
	public void setRtsCapacityFlagChange(boolean rtsCapacityFlagChange) {
		this.rtsCapacityFlagChange = rtsCapacityFlagChange;
	}
	
	public boolean isRtsCapacityFlagChange() {
		return rtsCapacityFlagChange;
	}

	public void setWifiFractionFrameDlChange(boolean wifiFractionFrameDlChange) {
		this.wifiFractionFrameDlChange = wifiFractionFrameDlChange;
	}
	
	public boolean isWifiFractionFrameDlChange() {
		return wifiFractionFrameDlChange;
	}

	public void setWifiFractionFrameUlChange(boolean wifiFractionFrameUlChange) {
		this.wifiFractionFrameUlChange = wifiFractionFrameUlChange;
	}
	
	public boolean isWifiFractionFrameUlChange() {
		return wifiFractionFrameUlChange;
	}

	public void showInformationSuscriber(TableViewer tableViewer,
			TreeItem grandChildWimaxDL, TreeItem grandChildWimaxUL,
			TreeItem grandChildWifiDL, TreeItem grandChildWifiUL, TreeItem grandChildWifiTdmaDl, TreeItem grandChildWifiTdmaUl) {

		Subscriber subscriber = NavigationView.getSelectedSubscriber();
		if ( subscriber != null){
			
			tableViewer.getTable().clearAll();
			ModelProvider.INSTANCE.clearAll();
			ModelProvider.INSTANCE.setSubscriber(subscriber);
			tableViewer.setInput(ModelProvider.INSTANCE.getSubscriber());

			grandChildWimaxDL.setText(showBestWimaxDLModulation(subscriber.getPotenciaRecibida_Dl()));
			grandChildWimaxUL.setText(showBestWimaxULModulation(subscriber.getPotenciaRecibida_Ul()));

			grandChildWifiDL.setText(showBestWifiDlModulation(subscriber.getPotenciaRecibida_Dl()));
			grandChildWifiUL.setText(showBestWifiUlModulation(subscriber.getPotenciaRecibida_Ul()));
			
			grandChildWifiTdmaDl.setText(WifiTdmaCapacity.max_modulation_dl);
			grandChildWifiTdmaUl.setText(WifiTdmaCapacity.max_modulation_ul);
		}
	}
	
	private String showBestWimaxULModulation(float potenciaMinRecibida_Ul) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		for (String modulationParam : wimaxModulationParamList) {
			sensitivityParam = WimaxComboFieldEditor
					.getSensibilityWimaxModulation(modulationParam);
			if ((potenciaMinRecibida_Ul > Float.parseFloat(sensitivityParam))
					|| potenciaMinRecibida_Ul == Float
							.parseFloat(sensitivityParam)) {
				modulation.add(modulationParam);
			}
		}
		String higthModulation = Auxiliary.getWimaxHightModulation(modulation);
		setHightWimaxModulationUL(higthModulation);
		return higthModulation;
	}

	private String showBestWimaxDLModulation(float potenciaMinRecibida_Dl) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		for (String modulationParam : wimaxModulationParamList) {
			sensitivityParam = WimaxComboFieldEditor
					.getSensibilityWimaxModulation(modulationParam);

			if ((potenciaMinRecibida_Dl > Float.parseFloat(sensitivityParam))
					|| potenciaMinRecibida_Dl == Float
							.parseFloat(sensitivityParam)) {
				modulation.add(modulationParam);
			}
		}

		String higthModulation = Auxiliary.getWimaxHightModulation(modulation);
		setHightWimaxModulationDL(higthModulation);
		return higthModulation;
	}

	private String showBestWifiDlModulation(float potenciaMinRecibida_Dl) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		String[][] modulationsList = Auxiliary.getWifiElementsModulation();
		if (Auxiliary.getMimoModulation()){
			modulationsList = Auxiliary.getWifiMimoElementsModulation();
		}
		for (String[] modulationParams : modulationsList) {
			for(String modulationParam: modulationParams){
				sensitivityParam = WifiComboFieldEditor
						.getSensitivityWifi(modulationParam);
	
				if ((potenciaMinRecibida_Dl > Float.parseFloat(sensitivityParam))
						|| potenciaMinRecibida_Dl == Float
								.parseFloat(sensitivityParam)) {
					modulation.add(modulationParam);
				}
				break;
			}
		}
		
		String higthModulation = Auxiliary.getWifiHightModulation(modulation);
		setHightWifiModulationDL(higthModulation);
		
		return higthModulation;
	}

	private String showBestWifiUlModulation(float potenciaMinRecibida_Ul) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		String[][] modulationsList = Auxiliary.getWifiElementsModulation();
		if (Auxiliary.getMimoModulation()){
			modulationsList = Auxiliary.getWifiMimoElementsModulation();
		}
		for (String[] modulationParams : modulationsList) {
			for(String modulationParam: modulationParams){
				sensitivityParam = WifiComboFieldEditor
						.getSensitivityWifi(modulationParam);
	
				if ((potenciaMinRecibida_Ul > Float.parseFloat(sensitivityParam))
						|| potenciaMinRecibida_Ul == Float
								.parseFloat(sensitivityParam)) {
					modulation.add(modulationParam);
				}
				break;
			}
		}
		String higthModulation = Auxiliary.getWifiHightModulation(modulation);
		setHightWifiModulationUL(higthModulation);
		
		return higthModulation;
	}
	
	public String getHightWimaxModulationUL() {
		return higthWimaxModulationUL;
	}
	
	public void setHightWimaxModulationUL(String modulation) {
		higthWimaxModulationUL = modulation;
	}
	
	public String getHightWimaxModulationDL() {
		return higthWimaxModulationDL;
	}

	public void setHightWimaxModulationDL(String modulation) {
		higthWimaxModulationDL = modulation;
	}

	public String getHightWifiModulationDL() {
		return higthWifiModulationDL;
	}

	public String getHightWifiModulationUL() {
		return higthWifiModulationUL;
	}

	public void setHightWifiModulationDL(String modulation) {
		higthWifiModulationDL = modulation;
	}

	public void setHightWifiModulationUL(String modulation) {
		higthWifiModulationUL = modulation;
	}
	
	public void setSenbilityWimaxChange(boolean senbilityWimaxChange) {
		this.senbilityWimaxChange = senbilityWimaxChange;
	}
	
	public boolean isSenbilityWimaxChange() {
		return senbilityWimaxChange;
	}

	public void setMimoOrSensibilityWifiChange(boolean mimoOrSensibilityWifiChange) {
		this.mimoOrSensibilityWifiChange = mimoOrSensibilityWifiChange;
	}
	
	public boolean isMimoOrSensibilityWifiChange() {
		return mimoOrSensibilityWifiChange;
	}
	public boolean isWifiTdmaFractionFrameDlChange() {
		return wifiTdmaFractionFrameDlChange;
	}
	public float getWifiTdmaFractionFrameDl() {
		return wifiTdmaFractionFrameDl;
	}
	public float getWifiTdmaFractionFrameUl() {
		return wifiTdmaFractionFrameUl;
	}
	public boolean isWifiTdmaFractionFrameUlChange() {
		return wifiTdmaFractionFrameUlChange;
	}
	
	public void setWifiTdmaFractionFrameDlChange(boolean wifiTdmaFractionFrameDlChange) {
		this.wifiTdmaFractionFrameDlChange = wifiTdmaFractionFrameDlChange;
	}
	
	public void setWifiTdmaFractionFrameUlChange(boolean wifiTdmaFractionFrameUlChange) {
		this.wifiTdmaFractionFrameUlChange = wifiTdmaFractionFrameUlChange;
	}
	public void setWifiTdmaFractionFrameDl(float wifiTdmaFractionFrameDl) {
		this.wifiTdmaFractionFrameDl = wifiTdmaFractionFrameDl;
	}
	public void setWifiTdmaFractionFrameUl(float wifiTdmaFractionFrameUl) {
		this.wifiTdmaFractionFrameUl = wifiTdmaFractionFrameUl;
	}
}
