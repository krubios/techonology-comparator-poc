package comparator.preferences.wifi.tdma;

import java.util.ArrayList;
import java.util.Arrays;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparator.scheduler.Auxiliary;
import comparator.scheduler.Scheduler;
import comparatortechnologyproject.Activator;

public class WifiTdmaCapacity {
	
	private float FRACTION_FRAME_TIME_DL;
	private float FRACTION_FRAME_TIME_UL;
	
	//Capacidad total
    public static float dl_totalWifiTdmaCapacity;
	public static float ul_totalWifiTdmaCapacity;
	
	//Modulación
	public static String max_modulation_dl;
	public static String max_modulation_ul;
	
	private static ArrayList<String> wifiTdmaModulationParamList = new ArrayList<String>(
			Arrays.asList(Messages.MCS_8, Messages.MCS_9, Messages.MCS_10, Messages.MCS_11, Messages.MCS_12, Messages.MCS_13,
					Messages.MCS_14, Messages.MCS_15));
	
	public WifiTdmaCapacity(){
		
		String fractionFrameDl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_DL);
		//Indicamos los valores de trama para el DL y UL
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_DL = Float.parseFloat(fractionFrameDl);
		}
		String fractionFrameUl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIFI_TDMA_FRACTION_FRAME_UL);
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_UL = Float.parseFloat(fractionFrameUl);
		}
	}
	
	public String wifiTdmaScheduler(Scheduler scheduler){
		
		String message = "Servicios Wifi Tdma creados";
		
		dl_totalWifiTdmaCapacity = getMaxThroughputDl(scheduler.getMaxSize());
		ul_totalWifiTdmaCapacity = getMaxThroughputUl(scheduler.getMaxSize());
		return message;
		
	}

	private float getMaxThroughputUl(float distance) {
		
		WifiTdmaData wifiTdmaData = new WifiTdmaData();
		ArrayList<WifiTdmaData> data = new ArrayList<WifiTdmaData>();
		float throughput = 0;
		float maxThroughput = 0;
		
		for (String modulation: wifiTdmaModulationParamList){
			if (modulation.equals("MCS8")){
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 75.7152)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS9")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 165.1968)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS10")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 261.33216)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS11")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 351.0432)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS12")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 526.5648)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS13")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 705.98688)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS14")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 787.89696)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS15")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 881.50848)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
			}
		}
		
		maxThroughput = getThroughputUl(data) * FRACTION_FRAME_TIME_UL;
		
		return maxThroughput;
	}

	private float getThroughputUl(ArrayList<WifiTdmaData> dataList) {
		float max = 0;
		for (WifiTdmaData data: dataList){
			if (data.getThroughput() > max){
				max = data.getThroughput();
				max_modulation_ul = data.getModulation();
			}
		}
		return max;
	}

	private float getMaxThroughputDl(float distance) {
		WifiTdmaData wifiTdmaData = new WifiTdmaData();
		ArrayList<WifiTdmaData> data = new ArrayList<WifiTdmaData>();
		float throughput = 0;
		float maxThroughput = 0;
		
		for (String modulation: wifiTdmaModulationParamList){
			if (modulation.equals("MCS8")){
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 75.7152)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS9")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 165.1968)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS10")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 261.33216)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS11")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 351.0432)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS12")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 526.5648)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS13")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 705.98688)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS14")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 787.89696)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
				
			}else if (modulation.equals("MCS15")){
				
				throughput = (float) (((4 * Math.pow(4, -6)) * distance + 881.50848)/10);
				wifiTdmaData.setModulation(modulation);
				wifiTdmaData.setThroughput(throughput);
				data.add(wifiTdmaData);
			}
		}
		
		maxThroughput = getThroughputDl(data) * FRACTION_FRAME_TIME_DL;
		
		return maxThroughput;
	}

	private float getThroughputDl(ArrayList<WifiTdmaData> dataList) {
		float max = 0;
		for (WifiTdmaData data: dataList){
			if (data.getThroughput() > max){
				max = data.getThroughput();
				max_modulation_dl = data.getModulation();
			}
		}
		return max;
	}

	public void setFractionFrameDl(float fractionFrameDl){
		this.FRACTION_FRAME_TIME_DL = fractionFrameDl;
	}
	
	public float getFractionFrameDl(){
		return FRACTION_FRAME_TIME_DL;
	}
	
	public void setFractionFrameUl(float fractionFrameUl){
		this.FRACTION_FRAME_TIME_UL = fractionFrameUl;
	}
	
	public float getFractionFrameUl(){
		return FRACTION_FRAME_TIME_UL;
	}
	
	public float getDlWifiTdmaCapacity(){
		return dl_totalWifiTdmaCapacity * 1000;
	}
	
	public float getUlWifiTdmaCapacity(){
		return ul_totalWifiTdmaCapacity * 1000;
	}
	
	public String getMaxModulationDl(){
		return max_modulation_dl;
	}
	
	public String getMaxModulationUl(){
		return max_modulation_ul;
	}
}
