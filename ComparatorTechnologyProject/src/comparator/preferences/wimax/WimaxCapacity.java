package comparator.preferences.wimax;

import comparator.preferences.Messages;
import comparator.preferences.PreferenceConstants;
import comparator.scheduler.Auxiliary;
import comparator.scheduler.Scheduler;
import comparatortechnologyproject.Activator;
import comparatortechnologyproject.InformationView;

/**
 * Configuración de Wimax para obtener los símbolos y calcular la capacidad ofrecida.
 * @author Karla
 *
 */
public class WimaxCapacity {
	
	//Parámetros del escenario
	private float DISTANCE;
	private float FRECUENCY;
	private int SUBSCRIBER_NUMBER;
	private float PROPAGATION_TIME;
	
	//Parametro OFDM
	private int N_FFT;
	private float BANDWIDTH;
	private float SAMPLING_FACTOR;
	private float CYCLIC_PREFIX;
	private int N_DATA;
	private float SAMPLING_FRECUENCY;
	
	//Parametros temporales de la capa física
	private float FRAME_DURATION;
	private float TTG_MAX;
	private float RTG_MAX;
	private float TTG;
	private float RTG;
	private float FRACTION_FRAME_TIME_DL;
	private float FRACTION_FRAME_TIME_UL;
	
	//Parametros de trama de la capa física
	private int BURST_NUMBER_DL;
	private int BURST_NUMBER_UL;
	private int VARIABLE_DCD_LENGTH;
	private int VARIABLE_UCD_LENGTH;
	private float DCD_FRECUENCY;
	private float UCD_FRECUENCY;
	private int RANGING_OPORTUNITIES_NUMBER;
	private int REQUEST_BW_NUMBER;
	
	
	//Parametros de la capa MAC
	private int MAXIMUM_TRANSMISSION_UNIT;
	private int PACKING_SUBHEADER_LENGTH;
	
	//Simbolos de datos
    private int dl_data_symbols,ul_data_symbols,dl_overHead,ul_overHead, dl_totalsymbols, ul_totalsymbols,
    			bits_Phy_Ul, bits_Phy_Dl, mac_overHead;
    
    //Capacidad total
    public static float dl_totalWimaxCapacity;
	public static float ul_totalWimaxCapacity;
    
	public WimaxCapacity(){
		String bw = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.BAND_WIDTH);
		String cp = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.CYCLIX_PREFIX);
		
		float cpValue = getCyclixPrefixValue(cp);
		String frameDuration = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.FRAME_DURATION);
		float frameDurationValue = getFrameDurationValue(frameDuration);
		String fractionFrameDl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIMAX_FRACTION_FRAME_DL);
		//Indicamos los valores de trama para el DL y UL
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_DL = Float.parseFloat(fractionFrameDl);
		}
		String fractionFrameUl = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.WIMAX_FRACTION_FRAME_UL);
		if (Auxiliary.isNumber(fractionFrameDl)){
			this.FRACTION_FRAME_TIME_UL = Float.parseFloat(fractionFrameUl);
		}
		
		this.FRECUENCY = (float) (5.8 *Math.pow(10, 9));
		this.SUBSCRIBER_NUMBER = 1;
		this.N_FFT = 256;
		this.BANDWIDTH = (float) (Float.parseFloat(bw)* Math.pow(10,6)); 
		this.CYCLIC_PREFIX = cpValue;
		this.N_DATA = 192;
		
		this.FRAME_DURATION = (float) (frameDurationValue * Math.pow(10, -3)); 
		this.TTG_MAX = (float) (100 * Math.pow(10, -6));
		this.RTG_MAX = (float) (100 * Math.pow(10, -6));
		this.TTG = TTG_MAX;
		this.RTG = RTG_MAX;
		
		this.BURST_NUMBER_DL = 1;
		this.BURST_NUMBER_UL = 1;
		this.VARIABLE_DCD_LENGTH = 0;
		this.VARIABLE_UCD_LENGTH = 0;
		this.RANGING_OPORTUNITIES_NUMBER = 1;
		this.REQUEST_BW_NUMBER = 2;
		
		this.MAXIMUM_TRANSMISSION_UNIT = 1400*8;
		this.PACKING_SUBHEADER_LENGTH = 2*8;
	}
	
	public String wimaxScheduler(Scheduler scheduler) {
		String message = "Servicios Wimax Creados!";

		dl_totalWimaxCapacity = 0;
		ul_totalWimaxCapacity = 0;
		int dlBitBySymbols = 0;
		float dlCodeRate = 0;
		int ulBitBySymbols = 0;
		float ulCodeRate = 0;
		
		// Calcula la distancia máxima
		Auxiliary.setMaxDistance(scheduler.getSubscriberNodes());

		//Throughput Wimax
		// Calcula los simbolos de la trama
		calculateFrameSymbols(scheduler.getNumNodes(), Auxiliary.getMaxDistance());
		// obtenemos la modulacion y codificacion para el DL
		String dlWimaxModulation = InformationView.configurationProperties.getHightWimaxModulationDL();
		if (!dlWimaxModulation.equals("")){
			dlBitBySymbols = getModulationBitBySymbol(dlWimaxModulation);
			dlCodeRate = getCodeRateModulation(dlWimaxModulation);
		}

		// obtenemos la modulacion y codificacion para el UL
		String UlWimaxModulation = InformationView.configurationProperties.getHightWimaxModulationUL();
		if (!UlWimaxModulation.equals("")){
			ulBitBySymbols = getModulationBitBySymbol(UlWimaxModulation);
			ulCodeRate = getCodeRateModulation(UlWimaxModulation);
		}

		float capacityDlPhy = getCapacityDL_PHY(dlBitBySymbols, dlCodeRate);
		float capaciyUlPhy = getCapacityUL_PHY(ulBitBySymbols, ulCodeRate);

		if (capacityDlPhy != 0){
			dl_totalWimaxCapacity = getCapabilityDL_MAC();
		}
		
		if (capaciyUlPhy != 0){
			ul_totalWimaxCapacity = getCapabilityUL_MAC();
		}
		
		return message;
	}
	
	private float getFrameDurationValue(String fd) {
		float frameDuration= 0;
		if (fd.equals("2.5")){
			frameDuration = (float) 2.5;
		}else if (fd.equals("5")){
			frameDuration = (float)5;
		}else if(fd.equals("10")){
			frameDuration = (float)10;
		}else if (fd.equals("20")){
			frameDuration = (float)20;
		}
		return frameDuration;
	}

	public static float getCyclixPrefixValue(String cp) {
		float cpValue= 0;
		if (cp.equals("1/32")){
			cpValue = (float) (1.0/32.0);
		}else if (cp.equals("1/4")){
			cpValue = (float)(1.0/4.0);
		}else if (cp.equals("1/8")){
			cpValue = (float)(1.0/8.0);
		}else if (cp.equals("1/16")){
			cpValue = (float)(1.0/16.0);
		}
		return cpValue;
	}

	public void calculateFrameSymbols(int num_subscribers, float max_distance){
		//Calcula el overhead en cada sentido
        ul_overHead = getUl_overHead(num_subscribers, max_distance);
        dl_overHead = getDl_overHead(num_subscribers);
        mac_overHead = getMac_overHead();
	}
	
	
	/**
	 * Calcula los símbolos que hay sobre la cabecera física en el Downlink
	 * @param num_subscribers
	 * @param max_distance
	 * @return
	 */
	private int getDl_overHead(int num_subscribers) {
		
		SUBSCRIBER_NUMBER = num_subscribers;
		//Preambulo y FCH
		int preambuleLength = 2;
		int controlFrameHeaders = 1;
		int shortPreambuleBurst = 2;
		
		//Broadcast
		int b_DLMAP = (int)(Math.ceil(64 + 32*(float)SUBSCRIBER_NUMBER));
		int b_ULMAP = (int)(Math.ceil(64 + 48*(float)SUBSCRIBER_NUMBER));
		
		//Albentia
		int n_burst_DCD = 7;
		int n_burst_UCD = 11;
		DCD_FRECUENCY= (float) (FRAME_DURATION / (1200 *Math.pow(10, -3)));
		UCD_FRECUENCY = (float) (FRAME_DURATION / (1200 * Math.pow(10, -3)));
		int b_DCD = (int) ((16 + 24 * n_burst_DCD + VARIABLE_DCD_LENGTH) * DCD_FRECUENCY);
		int b_UCD = (int) ((16 + 24 * n_burst_UCD + VARIABLE_UCD_LENGTH) * UCD_FRECUENCY);
		int b_others = 0; // bits para otros mensajes de broadcast
		int b_BC = Math.round(b_ULMAP + b_DLMAP + b_DCD + b_UCD + b_others); 
		int L_BC = (int) Math.ceil(b_BC/96);
		
		//Total
		int total = preambuleLength + controlFrameHeaders + L_BC + shortPreambuleBurst;
		return total;
	}
	
	/**
	 * Calcula la capacidad física de la red, asumimos sólo una rafaga
	 * @return
	 */
	public float getCapacityDL_PHY(int symbols, float r_DL){
		float capacityTotal= 0;
		if (symbols != 0 && r_DL != 0){
			int M_DL = (int) (Math.log(symbols)/ Math.log(2));
			float T_DL = (FRAME_DURATION - TTG - RTG) * FRACTION_FRAME_TIME_DL; //Duración de una subtrama
			dl_totalsymbols = (int) Math.floor(T_DL/getOFDMSymbolTime()); //numero de simbolos OFDM en el DL
			
			//Numero de simbolos OFDM en el DL para datos MAC
			if (dl_totalsymbols != 0){
				dl_data_symbols = dl_totalsymbols	 - dl_overHead;
				bits_Phy_Dl = (int) (dl_data_symbols * (N_DATA * M_DL * r_DL)); //bits de datos en el DL
				capacityTotal = bits_Phy_Dl / FRAME_DURATION; //los bits/s
			}
		}
		return capacityTotal;
	}
	
	/**
	 * Calcula los símbolos que hay sobre la cabecera física en el Uplink
	 * @param num_subscribers
	 * @param max_distance
	 * @return
	 */
	private int getUl_overHead(int num_subscribers, float max_distance) {
		DISTANCE = max_distance;
		PROPAGATION_TIME = (float) (DISTANCE / (3* Math.pow(10, 8)));
		int L_RANGING = (int) Math.ceil(Math.max(1, 2* PROPAGATION_TIME/getOFDMSymbolTime())) * RANGING_OPORTUNITIES_NUMBER; //numero de simbolos OFDM para el ranging
		int L_BR = 2 * REQUEST_BW_NUMBER; //Simbolos OFDM para peticiones BW
		int L_BURST_PRE = 1 * BURST_NUMBER_UL; //simbolos OFDM por preambulos de rafagas UL
		int total = L_RANGING + L_BR + L_BURST_PRE;
		return total;
	}
	
	/**
	 * Calcula la capacidad fisica de la red en el uplink
	 * @return
	 */
	public float getCapacityUL_PHY(int symbols, float r_UL){
		float capacityTotal= 0;
		if (symbols != 0 && r_UL != 0){
			int M_UL = (int) (Math.log(symbols)/ Math.log(2));
			float T_UL = (FRAME_DURATION - TTG - RTG) * FRACTION_FRAME_TIME_UL; //duracion de subtramas UL
			ul_totalsymbols = (int) Math.floor(T_UL/getOFDMSymbolTime()); //simbolos OFDM en el UL
			if (ul_totalsymbols != 0){
				ul_data_symbols = ul_totalsymbols - ul_overHead; //simbolos OFDM en el UL para datos MAC
				bits_Phy_Ul = (int)(ul_data_symbols * (N_DATA * M_UL * r_UL)); //bits de datos en el UL
				capacityTotal = bits_Phy_Ul / FRAME_DURATION;
			}
		}
		return capacityTotal;
	}
	
	/**
	 * Calcula los simbolos de overhead en la capa MAC. Asumimos una rafaga en cada direccion con solo un PDU
	 * @return
	 */
	public int getMac_overHead(){
		int b_PDU = (6+4) * 8;
		int b_other_sh = 32;
		int total = b_PDU + b_other_sh;
		return total;
	}
	
	/**
	 * Calcula la capacidad total tanto de la capa fisica como de la MAC en el downlink 
	 * @return
	 */
	public float getCapabilityDL_MAC(){
		
		int b_SDU = MAXIMUM_TRANSMISSION_UNIT + PACKING_SUBHEADER_LENGTH;
		int n_SDUS_DL = (int) Math.ceil((bits_Phy_Dl - mac_overHead)/b_SDU); //numero de SDUs en una trama
		int ob_PSH_DL = PACKING_SUBHEADER_LENGTH * n_SDUS_DL;
		float capacityTotal = (bits_Phy_Dl - mac_overHead - ob_PSH_DL)/ FRAME_DURATION;
		return capacityTotal;
	}
	
	/**
	 * Calcula la capacidad total tanto de la capa fisica como de la MAC en el uplink 
	 * @return
	 */
	public float getCapabilityUL_MAC(){
		
		int b_SDU = MAXIMUM_TRANSMISSION_UNIT + PACKING_SUBHEADER_LENGTH;
		int n_SDUS_UL = (int) Math.ceil((bits_Phy_Ul - mac_overHead)/b_SDU); //numero de SDUs en una trama
		int ob_PSH_UL = PACKING_SUBHEADER_LENGTH * n_SDUS_UL;
		float capacityTotal = (bits_Phy_Ul - mac_overHead - ob_PSH_UL)/ FRAME_DURATION;
		return capacityTotal;
	}
	
	/**
	 * Calcula el factor de muestreo, dependiendo del ancho de banda configurado
	 * @return
	 */
	public float getSamplingFactor(){
		 //El factor de muestreo depende del ancho de banda
        float n;
		if (BANDWIDTH%1.75==0){
        	n = ((float)8)/((float)7);
        }
        else if (BANDWIDTH%1.5==0){
            n = ((float)86)/((float)75);
        }
        else if (BANDWIDTH%1.25==0){
            n = ((float)144)/((float)125);
        }
        else if (BANDWIDTH%2.75==0){
            n = ((float)316)/((float)275);
        }
        else if (BANDWIDTH%2==0){
            n = ((float)57)/((float)50);
        }
        else {
            n = ((float)8)/((float)7);
        }
        //Devuelve la frecuencia de muestreo
        return n;
	}
	
	/**
	 * Devuelve la fecuencia de muestreo
	 * @return
	 */
	public float getSamplingFrecuency(){
		
		SAMPLING_FACTOR = getSamplingFactor();
		SAMPLING_FRECUENCY = (float) Math.floor((SAMPLING_FACTOR * BANDWIDTH)/8000)* 8000;
		return SAMPLING_FRECUENCY;
	}
	
	/**
	 * Calcula el tiempo de símbolo OFDM
	 * @return
	 */
	public float getOFDMSymbolTime(){
		float OfdmSymbolTime = (N_FFT * (1+ CYCLIC_PREFIX)) / getSamplingFrecuency();
		return OfdmSymbolTime;
	}
	
	/**
	 * Calcula la codificación segun la modulación empleada
	 * @param newValue
	 * @return
	 */
	private float getCodeRateModulation(String newValue) {
		float code = 0;
		if (newValue.trim().equals(Messages.QPSK_1_2_PROVIDER.trim())
				|| (newValue.trim().equals(Messages.QAM16_1_2_PROVIDER.trim()))) {
			code = (float) (1.0 / 2.0);
		} else if (newValue.trim().equals(Messages.QPSK_3_4_PROVIDER.trim())
				|| (newValue.trim().equals(Messages.QAM16_3_4_PROVIDER.trim()))
				|| (newValue.trim().equals(Messages.QAM64_3_4_PROVIDER.trim()))) {
			code = (float) (3.0 / 4.0);
		} else if (newValue.trim().equals(Messages.QAM64_2_3_PROVIDER.trim())) {
			code = (float) (2.0 / 3.0);
		} else if (newValue.trim().equals(Messages.BPSK_1_2_PROVIDER.trim())) {
			code = (float) (1.0 / 2.0);
		}
		return code;
	}

	/**
	 * Obtiene los bit por símbolos de modulación
	 * @param newValue
	 * @return
	 */
	private int getModulationBitBySymbol(String newValue) {
		int symbol = 0;
		if (newValue.trim().equals(Messages.QPSK_1_2_PROVIDER.trim())) {
			symbol = 4;
		} else if (newValue.trim().equals(Messages.QPSK_3_4_PROVIDER.trim())) {
			symbol = 4;
		} else if (newValue.trim().equals(Messages.QAM16_1_2_PROVIDER.trim())) {
			symbol = 16;
		} else if (newValue.trim().equals(Messages.QAM16_3_4_PROVIDER.trim())) {
			symbol = 16;
		} else if (newValue.trim().equals(Messages.QAM64_2_3_PROVIDER.trim())) {
			symbol = 64;
		} else if (newValue.trim().equals(Messages.QAM64_3_4_PROVIDER.trim())) {
			symbol = 64;
		} else if (newValue.trim().equals(Messages.BPSK_1_2_PROVIDER.trim())){
			symbol = 2;
		}
		return symbol;
	}
	
	public void setBandWidth(float bw){
		this.BANDWIDTH = bw;
	}
	
	public float getBandWidth(){
		return BANDWIDTH;
	}
	
	public void setCyclixPrefix(float cp){
		this.CYCLIC_PREFIX = cp;
	}
	
	public float getCyclixPrefix(){
		return CYCLIC_PREFIX;
	}
	
	public void setFrameDuration(float frameDuration){
		this.FRAME_DURATION = frameDuration;
	}
	
	public float getFrameDuration(){
		return FRAME_DURATION;
	}
	
	public float getFrecuency(){
		return FRECUENCY;
	}
	
	public Integer getBurstDL(){
		return BURST_NUMBER_DL;
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
	
	/**
	 * La capacidad viene en bps, lo pasamos a Mbps
	 * @return
	 */
	public float getDlWimaxCapacity(){
		return dl_totalWimaxCapacity / 1000000;
	}
	
	public float getUlWimaxCapacity(){
		return ul_totalWimaxCapacity /1000000;
	}

}
