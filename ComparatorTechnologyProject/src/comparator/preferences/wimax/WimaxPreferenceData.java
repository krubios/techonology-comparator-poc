package comparator.preferences.wimax;

public class WimaxPreferenceData {

	private String id;
	private String modulation;
	private float sensibility;
	
	
	public WimaxPreferenceData(String id, String modulation, float sensibility) {
		this.id = id;
		this.modulation = modulation;
		this.sensibility = sensibility;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id= id;
	}
	
	public String getModulation(){
		return modulation;
	}
	
	public void setModulation(String modulation){
		this.modulation = modulation;
	}

	public float getSensibility(){
		return this.sensibility;
	}
	
	public void setSensibility(float sensibility){
		this.sensibility = sensibility;
	}
}
