package comparator.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import comparator.preferences.Messages;
import comparator.preferences.wifi.WifiCapacity;
import comparator.preferences.wimax.WimaxCapacity;
import comparatortechnologyproject.InformationView;

/**
 * La clase Scheduler se encarga de realizar los cálculos de planificación 
 * 
 */
public class Scheduler {

	// Contiene la configuración de WiMAX
	public static WimaxCapacity wimaxConfig;
	
	//Contiene la configuración de wifi
	public static WifiCapacity wifiConfig;

	// Contiene la lista de todas las estaciones
	private ArrayList<Subscriber> listNodes;
	// Contiene la lista de Estaciones subscriptoras (SS)
	private Vector<Subscriber> listSubscriberNodes;

	private Subscriber baseStation;
	// Contiene el número de estaciones subscriptoras (SS) conectadas a la BS
	private int numSS;
	// Distancia a la SS más alejada
	private float max_distance;

	public int dl_Data_Symbols;
	public int ul_Data_Symbols;

	public int dl_overhead;
	public int ul_overhead;

	public static float dl_totalWimaxCapacity;
	public static float ul_totalWimaxCapacity;
	public int total_symbols;

	private float dl_totalWifiCapacity;
	private float ul_totalWifiCapacity;

	/**
	 * Construye un objeto Scheduler inicializando cada una de sus variables.
	 */
	public Scheduler() {

		wimaxConfig = new WimaxCapacity();
		wifiConfig = new WifiCapacity();
		
		this.listNodes = new ArrayList<Subscriber>();
		this.listSubscriberNodes = new Vector<Subscriber>();
		this.numSS = 0;
		max_distance = 1;
	}

	public String wimaxScheduler() {
		String message = "Servicios Wimax Creados!";

		dl_totalWimaxCapacity = 0;
		ul_totalWimaxCapacity = 0;
		int dlBitBySymbols = 0;
		float dlCodeRate = 0;
		int ulBitBySymbols = 0;
		float ulCodeRate = 0;
		
		// Calcula la distancia máxima
		setMaxDistance();

		//Throughput Wimax
		// Calcula los simbolos de la trama
		wimaxConfig.calculateFrameSymbols(numSS, max_distance);
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

		float capacityDlPhy = wimaxConfig.getCapacityDL_PHY(dlBitBySymbols, dlCodeRate);
		float capaciyUlPhy = wimaxConfig.getCapacityUL_PHY(ulBitBySymbols, ulCodeRate);

		if (capacityDlPhy != 0){
			dl_totalWimaxCapacity = wimaxConfig.getCapabilityDL_MAC();
		}
		
		if (capaciyUlPhy != 0){
			ul_totalWimaxCapacity = wimaxConfig.getCapabilityUL_MAC();
		}
		
		return message;
	}
	
	public String wifiScheduler(){
		
		String message = "Servicios Wifi Creados!";
		
		// Calcula la distancia máxima
		setMaxDistance();
		
		//Throughput Wifi
		wifiConfig.calculateSaturationThroughput(numSS, max_distance);
		//Obtenemos la modulación para el DL
		String dlWifiModulation = InformationView.configurationProperties.getHightWifiModulationDL();
		
		if (wifiConfig.getRtsCapacityFlag()){
			wifiConfig.getRtsThroughputSaturation(dlWifiModulation);
			dl_totalWifiCapacity = wifiConfig.getRtsThroughputSaturationDl();
		}else{
			wifiConfig.getBasicThroughputSaturation(dlWifiModulation);
			dl_totalWifiCapacity = wifiConfig.getBasicThroughputSaturationDl();
		}
				
		//Obtenemos la modulación para el UL
		String ulWifiModulation = InformationView.configurationProperties.getHightWifiModulationUL();
		
		if (wifiConfig.getRtsCapacityFlag()){
			wifiConfig.getRtsThroughputSaturation(ulWifiModulation);
			ul_totalWifiCapacity = wifiConfig.getRtsThroughputSaturationUl();
		}else{
			wifiConfig.getBasicThroughputSaturation(ulWifiModulation);
			ul_totalWifiCapacity = wifiConfig.getBasicThroghputSaturationUl();
		}
		
		return message;
	}

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

	/**
	 * Recorre el vector de usuarios para encontrar el usuario a mayor distancia
	 */
	public void setMaxDistance() {
		// Inicializa a 0 el valor de max_distance
		max_distance = 0;
		// Recorre el vector de usuarios
		for (Iterator<Subscriber> it = listSubscriberNodes.iterator(); it
				.hasNext();) {
			Subscriber user = (Subscriber) it.next();
			// Si la distancia máxima del CPE supera a la anterior
			if (max_distance < user.getDistance()) {
				max_distance = user.getDistance();
			}
		}
	}


	/**
	 * Devuelve el vector de usuarios
	 * 
	 * @return lista de CPEs conectados a la estación base
	 */
	public ArrayList<Subscriber> getSubscriberNodes() {
		return new ArrayList<Subscriber>(listSubscriberNodes);
	}

	/**
	 * Devuelve una lista con todas las estaciones definidas
	 */
	public ArrayList<Subscriber> getNodes() {
		return listNodes;
	}


	/**
	 * Devuelve el número de estaciones subscriptoras conectadas a la estación
	 * base
	 * 
	 * @return numero de estaciones subscriptoras
	 */
	public int getNumNodes() {
		if (numSS == 0) {
			// No tiene sentido realizar los cálculos para 0 usuarios
			return 1;
		}
		return numSS;
	}


	/**************************************/
	/** Metodos de gestion y planificación */
	/**************************************/

	/**
	 * Buscan la subscriptora con el nombre oldName y modifica sus atributos
	 * según con los parámetros que se pasan al método
	 * @return devuelve false si ya existe una subscriptora con el nuevo nombre
	 */
	public boolean modifySubscriber(String oldName, String newName,
			float newDistance, float newUlSnr, float newDlSnr,
			float potencia_recibida_min_ul, float potencia_recibida_min_dl,
			float potencia_recibida_ul, float potencia_recibida_dl) {
		// busca la subscriptora a modificar
		Subscriber subscriber = searchSubscriber(oldName, listSubscriberNodes);
		// Comprueba que el nuevo nombre no está en uso
		if (!oldName.equals(newName)
				&& (searchSubscriber(newName, listSubscriberNodes) != null)) {
			return false;
		}
		// modifica los atributos de la subscriptora
		subscriber.modify(newName, newDistance, newUlSnr, newDlSnr,
				potencia_recibida_ul, potencia_recibida_dl);
		return true;
	}

	/**
	 * Busca un SS en función del nombre que se pasa como parámetro
	 * 
	 * @param name
	 *            nombre del SS que se busca
	 * @return objeto SS con el nombre buscado
	 */
	public Subscriber searchSubscriber(String name, Vector<Subscriber> vector) {
		// Recorre el vector de SSs
		Iterator<Subscriber> it = vector.iterator();
		while (it.hasNext()) {
			Subscriber current = (Subscriber) (it.next());
			// Si el nombre de un SS coincide con el parametro "name"
			// devuelve el objeto SS
			if (current.getName().equals(name)) {
				return current;
			}
		}
		return null;

	}

	/**
	 * Importa los SSs de un fichero generado en RadioMobile
	 * 
	 * @param path
	 *            ruta al fichero de RadioMobile
	 * @param netName
	 *            nombre de la red en RadioMobile
	 * @return true si el fichero se carga correctamente
	 */
	public boolean loadNetFile(String path, String netName) {
		FileManager input = new FileManager();
		try {

			// Obtiene el vector de SSs
			Equipo equipo = new Equipo();
			listSubscriberNodes = input.importFile(path, netName,
					equipo.noiseFloor());
			baseStation = FileManager.getBaseStation();

			// Calcula el numero de SSs
			numSS = listSubscriberNodes.size();
			// Obtiene una lista con todos los nodos
			listNodes = input.getNodeList();
		} catch (IOException ex) {
			Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null,
					ex);
			return false;
		}
		return true;
	}

	/*************************************************************/
	/** Metodos relacionados con la gestión de archivos **/
	/*************************************************************/
	/**
	 * Metodo que identifica las redes definidas en un fichero de radio mobile
	 * 
	 * @param path
	 *            ruta al archivo de radioMobile
	 * @return array con los nombres de las redes del fichero
	 * @throws IOException
	 */
	public String[] loadNet(String path) throws IOException {
		// Crea un objeto FileManager
		FileManager mng = new FileManager();
		// Lee las redes del fichero
		String[] nets = mng.loadNet(path);
		// Devuelve los nombres de las redes
		return nets;
	}

	public Subscriber getBaseStation() {
		return baseStation;
	}
	
	public float getDlWimaxCapacity(){
		return dl_totalWimaxCapacity / 1000;
	}
	
	public float getUlWimaxCapacity(){
		return ul_totalWimaxCapacity /1000;
	}
	
	public float getDlWifiCapacity(){
		return dl_totalWifiCapacity * 1000;
	}
	
	public float getUlWifiCapacity(){
		return ul_totalWifiCapacity * 1000;
	}
}
