package comparator.preferences.wifi;

import java.util.HashMap;
import java.util.Map;

import comparator.preferences.PreferenceConstants;
import comparator.scheduler.Auxiliary;
import comparatortechnologyproject.Activator;

/**
 * Esta clase es la encargada de calcular la capacidad de wifi
 * @author Karla
 * 
 */
public class WifiCapacity {
	
	
	private boolean rtsCapacity;
	//Valores por defecto de Tau
	private static Map<Integer, String> mapTauValue;
	static {
		mapTauValue = new HashMap<Integer, String>();
		mapTauValue.put(2, "0.109051");
		mapTauValue.put(3, "0.09620301");
		mapTauValue.put(4, "0.08591101");
		mapTauValue.put(5, "0.07766120");
		mapTauValue.put(6, "0.07098181");
		mapTauValue.put(7, "0.06549456");
		mapTauValue.put(8, "0.06091691");
		mapTauValue.put(9, "0.05704242");
		mapTauValue.put(10, "0.05372005");
		mapTauValue.put(11, "0.05083804");
		mapTauValue.put(12, "0.04831252");
		mapTauValue.put(13, "0.04607951");
		mapTauValue.put(14, "0.04408949");
		mapTauValue.put(15, "0.04230355");
	}
	
	
	private float MIN_MAIN_WINDOW, PACKAGES, PLCP_TIME, SLOT_TIME;
	private float R_P;
	private float SIFS_RIFS_TIME;
	private float DIFS_TIME;
	private float E_P_SIZE;
	private float R_BASIC;
	private float BLOCK_ACK_ON;
	private float ACK_TIME;
	private float ACK_BITS;
	private float RTS_TIME;
	private float RTS_BITS;
	private float BACK_EXT_TIME;
	private float NUM_PACKAGES;
	private float BAR_TIME;
	private float BAR_BITS;
	private float BACK_TIME;
	private float BACK_BITS;
	private float TAU_VALUE;
	private float CTS_TIME;
	private float CTS_BITS;
	private float FRACTION_FRAME_TIME_DL;
	private float FRACTION_FRAME_TIME_UL;
	private float success_probability, busy_probability,
			slot_time_with_distance, ts_basic, tc_basic, ts_rts, tc_rts, dl_basic_saturation, ul_basic_saturation,
			dl_rts_saturation, ul_rts_saturation, basic_saturation, rts_saturation;
	
	public WifiCapacity() {

		String sifs_rifs = Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.COMBO_SIFS_RIFS);
		
		float sifs_rifs_value = getSifsRifsValue(sifs_rifs);
		
		String block_ack_on_off = Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.BLOCK_ACK_ON_OFF);
		String packages_size = Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.PACKAGES_SIZE);
		String num_packages = Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.NUM_PACKAGES);
		
		String rtsCapacity = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.RTS_CAPACITY_WIFI);
		setRtsCapacityFlag(rtsCapacity);

		String fractionFrameDl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIFI_FRACTION_FRAME_DL);
		String fractionFrameUl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIFI_FRACTION_FRAME_UL);
		
		this.MIN_MAIN_WINDOW = 15;
		this.PACKAGES = Float.valueOf(packages_size);
		this.PLCP_TIME = 20;
		this.SLOT_TIME = 9;
		this.R_P = 6;
		this.SIFS_RIFS_TIME = sifs_rifs_value;
		this.DIFS_TIME = SLOT_TIME * 2 + SIFS_RIFS_TIME;
		this.E_P_SIZE = (PACKAGES * MIN_MAIN_WINDOW) / (MIN_MAIN_WINDOW - 1);
		this.R_BASIC = (float) 6.5;
		this.BLOCK_ACK_ON = Float.valueOf(block_ack_on_off);
		this.NUM_PACKAGES = Float.valueOf(num_packages);
		this.BAR_BITS = 192;
		this.BACK_BITS = 1216;
		this.BAR_TIME = BAR_BITS / R_P;
		this.BACK_TIME = BACK_BITS / R_P;
		this.ACK_BITS = 112;
		this.ACK_TIME = ACK_BITS /R_BASIC + PLCP_TIME;
		this.RTS_BITS = 160;
		this.RTS_TIME = RTS_BITS /R_BASIC + PLCP_TIME;
		this.CTS_BITS = 112;
		this.CTS_TIME = (CTS_BITS / R_BASIC) + PLCP_TIME;
		this.BACK_EXT_TIME = BLOCK_ACK_ON * ((NUM_PACKAGES - 1) * (PLCP_TIME + ((PACKAGES + 224)/R_P) + SIFS_RIFS_TIME) + BAR_TIME 
				+ BACK_TIME + SIFS_RIFS_TIME);
		// Tomamos un valor de tau por defecto para una Wmin = 16 y 2
		// estaciones subscriptoras(n =2) conectadas punto a punto
		this.TAU_VALUE = (float) 0.10905100;
		
		//Indicamos los valores de trama para el DL y UL
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_DL = Float.parseFloat(fractionFrameDl);
		}
		
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_UL = Float.parseFloat(fractionFrameUl);
		}
	}

	public void calculateSaturationThroughput(int num_subscriber, float distance) {

		if (num_subscriber > 1) {
			TAU_VALUE = Float.parseFloat(mapTauValue.get(num_subscriber));
		}

		slot_time_with_distance = getSlotTime(distance);
		success_probability = getSuccessProbality(num_subscriber);
		busy_probability = getBusyProbability(num_subscriber);
		ts_basic = getBasicSuccessTransmission();
		tc_basic = getBasicCollisionTransmission();
		ts_rts = getRtsSuccessTransmission();
		tc_rts = getRtsCollisionTransmission();

//		// Saturacion
		basic_saturation = getBasicSaturation();
		rts_saturation = getRtsSaturation();
	}

	/**
	 * Devuelve el slot de tiempo dada la distancia
	 * 
	 * @param distance
	 * @return
	 */
	public float getSlotTime(double distance) {

		float slotTime = 0;
		if (distance == 0.0) {
			slotTime = 9;
		} else {
			slotTime = (float) Math.max((SLOT_TIME - 2) + (2 * distance) / 0.3,
					SLOT_TIME);
		}
		return slotTime;
	}

	public float getSuccessProbality(int num_subscriber) {

		return (float) (num_subscriber * TAU_VALUE * Math.pow(1 - TAU_VALUE,
				num_subscriber - 1));
	}

	public float getBusyProbability(int num_subscriber) {
		return (float) (1 - Math.pow(1 - TAU_VALUE, num_subscriber));
	}

	public float getBasicSuccessTransmission() {
		return ((slot_time_with_distance + (PLCP_TIME
				+ ((PACKAGES + 224) / R_P) + SIFS_RIFS_TIME + ACK_TIME
				+ BACK_EXT_TIME + DIFS_TIME + (slot_time_with_distance - SLOT_TIME)
				* (1 - BLOCK_ACK_ON))
				* MIN_MAIN_WINDOW) / MIN_MAIN_WINDOW);
	}

	public float getBasicCollisionTransmission() {
		return slot_time_with_distance
				+ (PLCP_TIME + ((PACKAGES + 224) / R_P) + SIFS_RIFS_TIME
						+ ACK_TIME + DIFS_TIME + ((slot_time_with_distance - SLOT_TIME) / 2));
	}

	public float getRtsSuccessTransmission() {

		return (slot_time_with_distance + (RTS_TIME + CTS_TIME + PLCP_TIME
				+ ((PACKAGES + 224) / R_P) + 3 * SIFS_RIFS_TIME
				+ (1 - BLOCK_ACK_ON) * ACK_TIME + BACK_EXT_TIME + DIFS_TIME + 2 * (slot_time_with_distance - SLOT_TIME))
				* MIN_MAIN_WINDOW)
				/ (MIN_MAIN_WINDOW - 1);
	}

	public float getRtsCollisionTransmission() {

		return (slot_time_with_distance + (RTS_TIME + SIFS_RIFS_TIME + CTS_TIME
				+ DIFS_TIME + ((slot_time_with_distance - SLOT_TIME) / 2)));
	}

	public float getBasicSaturation() {
		return (success_probability * ((E_P_SIZE + BLOCK_ACK_ON * PACKAGES
				* (NUM_PACKAGES - 1)) / (((1 - busy_probability) * slot_time_with_distance)
				+ (success_probability * ts_basic) + (busy_probability - success_probability)
				* tc_basic)));
	}

	public float getRtsSaturation() {
		return (success_probability * ((E_P_SIZE + BLOCK_ACK_ON * PACKAGES
				* (NUM_PACKAGES - 1)) / (((1 - busy_probability) * slot_time_with_distance)
				+ (success_probability * ts_rts) + (busy_probability - success_probability)
				* tc_rts)));
	}
	
	public float getRtsThroughputSaturation(String maxModulation) {
		
		float bitRate = getBitRate(maxModulation);
		float backAckExtTimeModulation = getBackAckExtTimeModulation(bitRate);
		
		float numValue = success_probability * (E_P_SIZE + BLOCK_ACK_ON * E_P_SIZE * (NUM_PACKAGES -1));
		float firstElementDenom = ((1- busy_probability) * slot_time_with_distance);
		
		float secondElementDenom = success_probability * (slot_time_with_distance + (RTS_TIME + + CTS_TIME + PLCP_TIME + ((PACKAGES + 224)/ bitRate) +
				3 * SIFS_RIFS_TIME + (1 - BLOCK_ACK_ON) * ACK_TIME + backAckExtTimeModulation + DIFS_TIME + 2 * (slot_time_with_distance - SLOT_TIME)) * 
				MIN_MAIN_WINDOW/(MIN_MAIN_WINDOW - 1));
		
		float thridElementDenom = busy_probability - success_probability;
		
		float fourthElementDenom = (slot_time_with_distance + (RTS_TIME + SIFS_RIFS_TIME + CTS_TIME +  DIFS_TIME +
					(slot_time_with_distance - SLOT_TIME) / 2));
		
		float denomTotal = (firstElementDenom + (secondElementDenom + ((thridElementDenom) * fourthElementDenom))); 
		
		rts_saturation = numValue / denomTotal;
		
		return rts_saturation;
	}
	
	public float getRtsThroughputSaturationDl(){
		dl_rts_saturation = rts_saturation * FRACTION_FRAME_TIME_DL;
		return dl_rts_saturation;
	}
	
	public float getRtsThroughputSaturationUl(){
		ul_rts_saturation = rts_saturation * FRACTION_FRAME_TIME_UL;
		return ul_rts_saturation;
	}
	
	
	public float getBasicThroughputSaturation(String maxModulation) {
		basic_saturation = 0;
		if(!maxModulation.equals("")){
			
			float bitRate = getBitRate(maxModulation);
			float backAckExtTimeModulation = getBackAckExtTimeModulation(bitRate);
			
			float numValue = success_probability * (E_P_SIZE + BLOCK_ACK_ON * E_P_SIZE * (NUM_PACKAGES -1));
			float firstElementDenom = ((1- busy_probability) * slot_time_with_distance);
			
			float secondElementDenom = (success_probability * (slot_time_with_distance + (PLCP_TIME + ((PACKAGES + 224)/ bitRate) +
					SIFS_RIFS_TIME + ACK_TIME + backAckExtTimeModulation + DIFS_TIME + (slot_time_with_distance - SLOT_TIME) * (1- BLOCK_ACK_ON)) *
					MIN_MAIN_WINDOW/(MIN_MAIN_WINDOW - 1)));
			
			float thridElementDenom = busy_probability - success_probability;
			float fourthElementDenom = (slot_time_with_distance + (PLCP_TIME + ((PACKAGES + 224)/ bitRate) + SIFS_RIFS_TIME + ACK_TIME + DIFS_TIME +
						(slot_time_with_distance - SLOT_TIME) / 2));
			
			basic_saturation = numValue / (firstElementDenom + secondElementDenom + (thridElementDenom * fourthElementDenom));
		}
		
		return basic_saturation;
	}
	
	public float getBasicThroughputSaturationDl(){
		dl_basic_saturation = basic_saturation * FRACTION_FRAME_TIME_DL;
		return dl_basic_saturation;
	}

	public float getBasicThroghputSaturationUl(){
		ul_basic_saturation = basic_saturation * FRACTION_FRAME_TIME_UL;
		return ul_basic_saturation;
	}

	private float getBitRate(String maxModulation) {
		float bitRate = 0;
		for (Map.Entry<String, WifiPreferenceData> entry: WifiComboFieldEditor.wifiInput.entrySet()){
			if (entry.getValue().getModulation().equals(maxModulation)){
				bitRate = entry.getValue().getbitRate();
			}
		}
		return bitRate;
	}
	
	private float getBackAckExtTimeModulation(float bitRate) {
		float value = 0;
		if (bitRate != 0 ){
			value = BLOCK_ACK_ON * ((NUM_PACKAGES - 1) * (PLCP_TIME + ((PACKAGES + 224)/bitRate) + SIFS_RIFS_TIME) +
					BAR_TIME + BACK_TIME + SIFS_RIFS_TIME);
		}
		return  value;
	}

	public float getSifsRifsValue(String sifs_rifs) {
		float sifs_rifs_value = 0;
		if (sifs_rifs.equals("10/16")) {
			sifs_rifs_value = (float) (10.0 / 16.0);
		} else if (sifs_rifs.equals("2")) {
			sifs_rifs_value = (float) (2.0);
		}
		return sifs_rifs_value;
	}
	
	public void setSifsRifs(float value){
		this.SIFS_RIFS_TIME = value;
	}
	
	public float getSifsRifsValue(){
		return SIFS_RIFS_TIME;
	}
	
	public void setBlockAck(float value){
		this.BLOCK_ACK_ON = value;
	}
	
	public float getBlockAckValue(){
		return BLOCK_ACK_ON;
	}
	
	public void setNumPackages(float numPackages){
		this.NUM_PACKAGES = numPackages;
	}
	
	public float getNumPackages(){
		return NUM_PACKAGES;
	}
	
	public void setPackagesSize(float packageValue){
		this.PACKAGES = packageValue;
	}
	
	public float getPackageSize(){
		return PACKAGES;
	}
	
	public void setRtsCapacityFlag(String value){
		rtsCapacity = Boolean.valueOf(value);
	}
	
	public boolean getRtsCapacityFlag(){
		return rtsCapacity;
	}

	public void setFractionFrameDl(float value) {
		this.FRACTION_FRAME_TIME_DL = value;
	}

	public void setFractionFrameUl(float value) {
		this.FRACTION_FRAME_TIME_UL = value;
	}
}
