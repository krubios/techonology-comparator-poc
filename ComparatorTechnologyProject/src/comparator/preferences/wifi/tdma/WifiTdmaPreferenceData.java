package comparator.preferences.wifi.tdma;

public class WifiTdmaPreferenceData {
	
	private String id;
	private String modulation;
	private float sensibility;
	private float powerTx;
	private float bitRate;
	
	public WifiTdmaPreferenceData(String id, String modulation, float sensibility, float powerTrans, float bitRate){
		this.id = id;
		this.modulation = modulation;
		this.sensibility = sensibility;
		this.powerTx = powerTrans;
		this.bitRate = bitRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModulation() {
		return modulation;
	}

	public void setModulation(String modulation) {
		this.modulation = modulation;
	}

	public float getSensibility() {
		return sensibility;
	}

	public void setSensibility(float sensibility) {
		this.sensibility = sensibility;
	}

	public float getPowerTx() {
		return powerTx;
	}

	public void setPowerTx(float powerTx) {
		this.powerTx = powerTx;
	}

	public float getBitRate() {
		return bitRate;
	}

	public void setBitRate(float bitRate) {
		this.bitRate = bitRate;
	}

}
