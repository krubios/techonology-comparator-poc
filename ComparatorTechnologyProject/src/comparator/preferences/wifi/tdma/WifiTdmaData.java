package comparator.preferences.wifi.tdma;

public class WifiTdmaData {
	
	public String modulation;
	public float throughput;
	
	
	public WifiTdmaData(String modulation, float throughput) {
		this.modulation = modulation;
		this.throughput = throughput;
	}
	
	public WifiTdmaData() {
	}

	public String getModulation() {
		return modulation;
	}

	public void setModulation(String modulation) {
		this.modulation = modulation;
	}

	public float getThroughput() {
		return throughput;
	}

	public void setThroughput(float throughput) {
		this.throughput = throughput;
	}

}
