package comparator.scheduler;

import java.math.BigDecimal;

/** La clase modela los SSs de la red WiMAX. Cada SS es una estación
 * subscriptora definido identificado por un nombre y_string caracterizado
 * por una distancia a la estación base (BS), una SNR en el uplink y_string una SNR en el
 * downlink.
 *
 */
public class Subscriber {

    /** Almacenan la snr en el uplink y_string la SNR en el downlink respectivamente. */
    private float ul_snr, dl_snr;
    /** Almacena la distancia a la BS. */
    private float distance;
    /** Almacena el nombre del SS. */
    private String name;
    /** Almacena las coordenadas */
    private float x, y, z;

    /**Almacena la potencia recibida del enlace*/
    private float potencia_recibida_ul, potencia_recibida_dl;
    
    /**Almacena la modulación empleada
    
    /** Construye un objeto equipo inicializando cada una de sus variables.
     * @param ul_snr SNR de uplink.
     * @param dl_snr SNR de downlink.
     * @param distance Distancia a la BS
     * @param name Nombre del SS
     */
    public Subscriber(float ul_snr, float dl_snr, float distance, String name) {
        this.ul_snr = ul_snr;
        this.dl_snr = dl_snr;
        this.distance = distance;
        this.name = name;
    }

    /**
     * Construye un objeto Subscriber dados un nombre y_string unas coordenadas
     * @param nombre nombre del Nodo
     * @param x_string latitud
     * @param y_string longitud
     * @param z altura
     */
    public Subscriber(String nombre, String x_string, String y_string, String z) {
        this.name = nombre;
        addX(x_string);
        addY(y_string);
        this.z = new Float(z);

        this.ul_snr = 0;
        this.dl_snr = 0;
        this.distance = 1;

    }

    public Subscriber(String name){
    	this.name = name;
    }
    public Subscriber() {
    }

    /************************************/
    /** Metodos que devuelven parámetros*/
    /************************************/
    /**
     * Devuelve la Latidud del punto
     * @return
     */
    float getX() {
        return x;
    }

    /**
     * Devuelve la longitud del punto
     * @return
     */
    float getY() {
        return y;
    }

    /**
     * Devuelve la altura del punto
     * @return
     */
    float getZ() {
        return z;
    }

    /** Devuelve la distancia en km del usuario a la estación base (BS)
     * @return la distancia a la BS.
     */
    public float getDistance() {
        return distance;
    }

    /** Devuelve el nombre del SS
     * @return nombre del SS.
     */
    public String getName() {
        return name;
    }

  

    /** Devuelve la SNR del enlace en funcion del sentido de la comunicación
     * @param direction sentido de la comunicación: uplink o downlink
     * @return SNR del enlace en dicho sentido
     */
    public String getSnr(boolean direction) {

        boolean dwlink = true;
        if (direction == dwlink) {
            return String.valueOf(dl_snr);
        } else {
            return String.valueOf(ul_snr);
        }

    }
    
    /**
     * Devuelve la capacidad del enlace
     */
    public float getPotenciaRecibida_Dl(){
    	return potencia_recibida_dl;
    }
    
    public float getPotenciaRecibida_Ul(){
    	return potencia_recibida_ul;
    }
    
    /**
     * Modifica los atributos de la subscriptroa
     */
    public void modify(String newName, float newDistance, float newUlSnr, float newDlSnr, float potencia_recibida_ul, float potencia_recibida_dl) {
        name = newName;
        distance = newDistance;
        this.potencia_recibida_ul = potencia_recibida_ul;
        this.potencia_recibida_dl = potencia_recibida_dl;
        
        //actualiza en los flujos la información relacionada con la SNR de uplink
        if (newUlSnr != ul_snr) {
            ul_snr = newUlSnr;
        }
        //actualiza en los flujos la información relacionada con la SNR de downlink
        if (newDlSnr != dl_snr) {
            dl_snr = newDlSnr;
        }
    }

    /** Convierte la coordenada X del nodo en metros.
     * Recibe la coordenada X del nodo en grados minutos y_string segundos y_string la convierte a metros utilizando la constante radio_tierra.
     * Para hacerlo, tenemos en cuenta que la posicion (0,0,0) en metros, es la (-90,-180,0) en grados, minutos segundos.
     * @param x_string Coordenada x_string del nodo en grados, minutos y_string segundos.
     * @see Nodo#setCoord_X
     */
    public void addX(String x_string) {
        double radio_tierra = 111194.926624;
        // Guardamos los grados
        double grados = Double.valueOf(x_string.substring(0, 2)).doubleValue();
        // Guardamos los minutos
        double minutos = Double.valueOf(x_string.substring(2, 4)).doubleValue();
        // Guardamos los segundos
        double segundos = Double.valueOf(x_string.substring(5, 7)).doubleValue();
        // Guardamos el hemisferio del nodo(puede ser S o N)
        char hemisferio = x_string.charAt(8);
        // Realizamos el cambio a nuestro nuevo sistema de coordanadas
        if (hemisferio == 'S') {
            // Si esta en el sur, a los 90 grados necesarios para llegar al ecuador, le restamos la posicion del nodo
            BigDecimal coord_x = new BigDecimal(radio_tierra * (90 - grados - (minutos / 60) - (segundos / 3600)));
            // Fijamos el numero de decimales a dos, ya que no es necesaria tanta precision
            // Para ello buscamos el punto y_string cogemos los dos primeros decimales
            String aux = coord_x.toString();
            int posicion = aux.indexOf(".");
            // Fijamos la posicion del nodo
            x = new Float(aux.substring(0, posicion + 3));
        } else {
            // Si esta en el norte, a los 90 grados necesarios para llegar al ecuador, le sumamos la posicion del nodo
            BigDecimal coord_x = new BigDecimal(radio_tierra * (90 + grados + (minutos / 60) + (segundos / 3600)));
            String aux = coord_x.toString();
            int posicion = aux.indexOf(".");
            x = new Float(aux.substring(0, posicion + 3));
        }
    }

    /** Convierte la coordenada Y del nodo en metros.
     * Recibe la coordenada Y del nodo en grados minutos y_string segundos y_string la convierte a metros utilizando la constante radio_tierra.
     * Para hacerlo, tenemos en cuenta que la posicion (0,0,0) en metros, es la (-90,-180,0) en grados, minutos segundos.
     * @param y_string Coordenada y_string del nodo en grados, minutos y_string segundos.
     * @see Nodo#setCoord_Y
     */
    public void addY(String y_string) {
        double radio_tierra = 111194.926624;
        // Sigue el mismo procedimento que añadirX
        double grados = Double.valueOf(y_string.substring(0, 2)).doubleValue();
        double minutos = Double.valueOf(y_string.substring(2, 4)).doubleValue();
        double segundos = Double.valueOf(y_string.substring(5, 7)).doubleValue();
        // Guardamos la zona del mundo en la que se encuentra el nodo
        char orientacion = y_string.charAt(8);
        if (orientacion == 'W') {
            // Si esta en el oeste, a los 180 grados necesarios para llegar al paralelo de Greenwich, le restamos la posicion del nodo
            BigDecimal coord_y = new BigDecimal(radio_tierra * (180 - grados - (minutos / 60) - (segundos / 3600)));
            String aux = coord_y.toString();
            int posicion = aux.indexOf(".");
            y = new Float(aux.substring(0, posicion + 3));
        } else {
            // Si esta en el este, a los 180 grados necesarios para llegar al paralelo de Greenwich, le sumamos la posicion del nodo
            BigDecimal coord_y = new BigDecimal(radio_tierra * (180 + grados + (minutos / 60) + (segundos / 3600)));
            String aux = coord_y.toString();
            int posicion = aux.indexOf(".");
            y = new Float(aux.substring(0, posicion + 3));
        }
    }
}
