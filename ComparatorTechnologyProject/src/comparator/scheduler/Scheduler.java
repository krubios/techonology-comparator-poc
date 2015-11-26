package comparator.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase Scheduler se encarga de realizar los cálculos de planificación 
 * 
 */
public class Scheduler {

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

	public int total_symbols;


	/**
	 * Construye un objeto Scheduler inicializando cada una de sus variables.
	 */
	public Scheduler() {
		
		this.listSubscriberNodes = new Vector<Subscriber>();
		this.numSS = 0;
		max_distance = 1;
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
			baseStation = input.getBaseStation();

			// Calcula el numero de SSs
			numSS = listSubscriberNodes.size();
			// Obtiene una lista con todos los nodos
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
	
	public float getMaxSize(){
		return max_distance;
	}
}
