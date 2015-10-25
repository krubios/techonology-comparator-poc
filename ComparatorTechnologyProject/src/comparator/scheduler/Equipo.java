
package comparator.scheduler;

/** La clase equipo modela los equipos definidos por Radio Mobile.
 * Un equipo se caracteriza por su nombre, su potencia transmitida, sus perdidas, su umbral de recepcion y su ganancia.
 * 
 * @author Carlos Rey
 * @author gitanillo@gmail.com
 * @version 1.0 30-5-07
 *
 */

public class Equipo {
	
	/** Almacena el nombre del equipo. */
	private String nombre;
	/** Almacena la potencia de transmision del equipo en W. */
	private double pwr_tx;
	/** Almacena las perdidas del equipo en dB. */
	private double loss;
	/** Almacena el umbral de recepcion del equipo en dBm. */
	private double rx_thr;
	/** Almacena la ganancia de la antena dBi. */
	private double ant_g;
	/** Indica si el equipo es utilizado. Por defecto no lo es. */
	private boolean utilizado;
	
	//Figura de ruido de los equipos en dB
    private static float noiseFigure;
    // Ancho de banda
    private static float bandwidth;
	
	/** Construye un objeto equipo por defecto, con todos sus atributos a 0.0. */
	public Equipo(){
		this.pwr_tx = 0.0;
		this.loss = 0.0;
		this.rx_thr = 0.0;
		this.ant_g = 0.0;
		this.nombre = null;
		Equipo.bandwidth = 10;
		utilizado=false;
	}
	
	/** Devuelve el nombre del equipo.
	 * @return El nombre del equipo
	 */
	public String getNombre() {
		return nombre;
	}
	
	/** Modifica el nombre del equipo.
	 * @param nombre nuevo nombre del equipo
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/** Devuelve la potencia transmitida por la interfaz.
	 * @return la potencia transmitida por la interfaz
	 */
	public double getPwr_tx() {
		return pwr_tx;
	}
	
	/** Modifica la potencia transmitida por el equipo.
	 * @param pwr_tx potencia transmitida por el equipo
	 */
	public void setPwr_tx(double pwr_tx) {
		this.pwr_tx = pwr_tx;
	}
	
	/** Devuelve la perdidas del equipo.
	 * @return Las perdidas del equipo.
	 */
	public double getLoss() {
		return loss;
	}
	
	/** Modifica las perdidas del equipo.
	 * @param loss Perdidas del equipo.
	 */
	public void setLoss(double loss) {
		this.loss = loss;
	}
	
	/** Devuelve el valor del umbral de recepcion del equipo.
	 * @return El valor del umbral del equipo.
	 */
	public double getRx_thr() {
		return rx_thr;
	}
	
	/** Modifica el valor del umbral de rececion del equipo.
	 * @param rx_thr Umbral de recepcion del equipo.
	 */
	public void setRx_thr(double rx_thr) {
		this.rx_thr = rx_thr;
	}
	
	/** Devuelve el valor de la ganancia del equipo.
	 * @return El valor de la ganancia del equipo.
	 */
	public double getAnt_g() {
		return ant_g;
	}
	
	/** Modifica el valor de la ganancia del equipo.
	 * @param ant_g Valor de la ganancia del equipo.
	 */
	public void setAnt_g(double ant_g) {
		this.ant_g = ant_g;
	}
	
	/** Indica si el equipo es utilizado.
	 * @return El estado de la utilizacion del equipo.
	 */
	public boolean isUtilizado() {
		return utilizado;
	}
	
	/** Modifica el estado de la utilizacion del equipo.
	 * @param utilizado El estado de la utilizacion del equipo.
	 */
	public void setUtilizado(boolean utilizado) {
		this.utilizado = utilizado;
	}
	
	/** Fija el nombre del equipo dejandolo sin espacios al final.
	 * @param nombre_equipo Nombre del equipo con espacios al final.
	 * @see Equipo#setNombre
	 */
	public void addNombre(String nombre_equipo){
		// Buscamos la posicion en la que encontramos vaios espacios consecutivos que nos indicaran el final del nombre.
		// Cogemos 5 porque en distintos ejemplos hemos encontrado nombres compuestos con hasta 4 espacios entre ambos
		String espacio="    ";
		int posicion=nombre_equipo.indexOf(espacio);
		//	Fijamos la el nombre del equipo sin espacios
		setNombre(nombre_equipo.substring(0,posicion));		
	}
	
	/** Fija el valor de potencia transmitida por el equipo.
	 * Parsea un String que contiene espacios y el caracter que indica que viene en watios y se queda solo con el numero.
	 * @param pwr_tx Potencia transmiida por el equipo sin parsear.
	 * @see Equipo#setPwr_tx
	 */
	public void addPwrTx(String pwr_tx){
		// Buscamos el caracter W 
		String watios="W";
		int posicion=pwr_tx.indexOf(watios);
		// Nos quedamos con lo que hay antes, que nos indicara el final del numero que indica la potencia
		double potencia=Double.valueOf(pwr_tx.substring(0,posicion)).doubleValue();
		// Fijamos el valor de la potencia transmitida
		setPwr_tx(potencia);
		
	}
	
	/** Fija el valor de las perdidas del equipo.
	 * Parsea un String que contiene espacios y el caracter que indica que viene en dB y se queda solo con el numero.
	 * @param loss Perdidas del equipo sin parsear.
	 * @see Equipo#setLoss
	 */
	public void addLoss(String loss){
		// Buscamos los caracteres dB y actuamos igual que la potencia transmitida 
		String dB="dB";
		int posicion=loss.indexOf(dB);
		double perdidas=Double.valueOf(loss.substring(0,posicion)).doubleValue();
		setLoss(perdidas);
		
	}
	
	/** Fija el valor del umbral de recepcion del equipo.
	 * Parsea un String que contiene espacios y el caracter que indica que viene en dBm y se queda solo con el numero.
	 * @param rx_thr Umbral de recepcion del equipo sin parsear.
	 * @see Equipo#setRx_thr
	 */
	public void addRxThr(String rx_thr){
		// Buscamos los caracteres dBm y actuamos igual que la potencia transmitida 
		String dBm="dBm";
		int posicion=rx_thr.indexOf(dBm);
		double umbral=Double.valueOf(rx_thr.substring(0,posicion)).doubleValue();
		setRx_thr(umbral);
		
	}
	
	/** Fija el valor de la ganancia del equipo.
	 * Parsea un String que contiene espacios y el caracter que indica que viene en dBi y se queda solo con el numero.
	 * @param antG Ganacia del equipo sin parsear.
	 * @see Equipo#setAnt_g
	 */
	public void addAntG(String antG){
		// Buscamos los caracteres dBi y actuamos igual que la potencia transmitida 
		String dBi="dBi";
		int posicion=antG.indexOf(dBi);
		double ganancia=Double.valueOf(antG.substring(0,posicion)).doubleValue();
		setAnt_g(ganancia);
	}
	
	
	/** Calcula el nivel de ruido (dBm) del sistema
     * @return nivel de ruido en (dBm)
     */
    public float noiseFloor(){
        double noise;
        //cte de bolzman por Temperatura en dBm
        double KT = -174;
        //Ancho de banda en dB = 10*log(10E6)
        double BW_dBm = 10*Math.log10(bandwidth)+60;
        noise = KT +noiseFigure + BW_dBm;
        return (float)noise;
    }
    
    /**
     * Obtiene el ancho de banda del canal.
     */
    
    public float getBandWidth(){
    	return bandwidth;
    }
    
    /**
     * Modifica el ancho de banda del sistema
     * @param newBandWidth
     */
    public void setBandWidth(float newBandWidth)
    {
    	Equipo.bandwidth = newBandWidth;
    }
}
