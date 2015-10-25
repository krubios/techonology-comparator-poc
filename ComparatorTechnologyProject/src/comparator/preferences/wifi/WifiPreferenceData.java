package comparator.preferences.wifi;

public class WifiPreferenceData {
	
	private String id;
	private String modulation;
	private float sensibility;
	private float powerTrans;
	private float bitRate;
	
	public WifiPreferenceData(String id, String modulation, float sensibility, float powerTrans, float bitRate){
		this.id = id;
		this.modulation = modulation;
		this.sensibility = sensibility;
		this.powerTrans = powerTrans;
		this.bitRate = bitRate;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getModulation(){
		return modulation;
	}
	
	public void setModulation(String modulation){
		this.modulation = modulation;
	}
	
	public float getSensibility(){
		return sensibility;
	}
	
	public void setSensibility(float sensibility){
		this.sensibility = sensibility;
	}
	
	public float getPowerTransmitted(){
		return powerTrans;
	}
	
	public void setPowerTransmitted(float power){
		this.powerTrans = power;
	}
	
	public float getbitRate(){
		return bitRate;
	}
	
	public void setBitRate(float bitRate){
		this.bitRate = bitRate;
	}
}
