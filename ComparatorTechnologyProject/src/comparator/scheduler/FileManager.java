package comparator.scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


/**La clase FileManager se encarga de leer y escribir los ficheros de
 * entrada y salida. Algunos métodos de esta clase son versiones adapatadas
 * de los métodos de la clase Lectura.java implementada por Carlos Rey
 *
 * @author Ignacio Prieto
 * @author ignacio.p.e@gmail.com
 * @version 1.0 08-08-09
 *
 *
 */
public class FileManager {

    //Vector de equipos detectados en el fichero de RadioMobile
    private Vector<Equipo> equipos;
    //Nodo que ejerce de estación base
    private Subscriber baseStation = new Subscriber();

    /** Devuelve un vector con la informacion disponible de cada uno de los nodos definidos en el report de Radio Mobile.
     * @see Lectura#getFichero
     * @see Lectura#saltarLineas
     * @see Nodo#addNombre
     * @see Nodo#addX
     * @see Nodo#addY
     * @see Nodo#setCoord_Z
     * @return Vector con los nodos definidos en el report de Radio Mobile.
     */
    private Vector<Subscriber> importSubscribers(String path) throws IOException {
        // Indica si ya hemos leido los nodos
        boolean leidos = false;
        // Creamos las variables necesarias
        Vector<Subscriber> nodos = new Vector<Subscriber>();
        // Indicara el identificador del nodo en el orden en el que hayn sido defindos
        int id = 0;
        // Creamos los objetos necesarios para poder leer del fichero
        FileReader archivo = new FileReader(path);
        BufferedReader entrada = new BufferedReader(archivo);
        String linea = entrada.readLine();
        // Vamos leyendo el fichero linea a linea
        while (linea != null) {
            // Buscamos la linea "Active units information",
            // que nos indicara el comienzo del listado de nodos
            if (linea.compareTo(Messages.ActiveUnitsInformation) == 0) {
                // nos saltamos la linea de blanco que hay antes del listado de nodos
                linea = skipLine(4, entrada);
                // Recorremos el listado de nodos hasta la linea "---------------------------------------------------------------------------"
                // que nos indica el final del listado de nodos
                while (linea.compareTo("---------------------------------------------------------------------------") != 0) {
                    Subscriber nodo = new Subscriber();
                    // Si el nodo está mal definido, capturamos la excepcion que genere y lanzamos un mensaje
                    try {
                        // Comprobamos si está presente el caracter °. Ya que da problemas . Esto dependerá de la versión de la versión del compilador
                        char aux = linea.charAt(22);
                        // Si está lo eliminamos
                        if (linea.substring(22,23).equals("Â") && linea.substring(35,36).equals("Â")) {
                            linea = linea.substring(0, 22).concat(linea.substring(24, 35)).concat(linea.substring(37, linea.length()));
                            
                        }else if(Character.getNumericValue(aux) == -1)
                        {
                        	linea = linea.substring(0, 22).concat(linea.substring(23, 34)).concat(linea.substring(35, linea.length()));
                        }
                        // Vamos asignando para cada nodo ely valor para cada una de sus variables
                        int index = linea.indexOf("  ");
                        String nombre = linea.substring(0, index);
                        String x = linea.substring(20, 30);
                        String y = linea.substring(31, 40);
                        String z = linea.substring(63, linea.length() - 1);
                        // Introducimos el nodo en el vector
                        nodo = new Subscriber(nombre, x, y, z.replace(',', '.'));
                        nodos.addElement(nodo);
                        id = id + 1;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.err.println("ERROR: El nodo " + nodo.getName() + " no está bien definido.");
                    }
                    linea = entrada.readLine();
                }
                // Cuando terminamos de leer los nodos cambiamos el valor de leidos
                leidos = true;
            }
            // Si aun no hemos leido los nodos seguimos leyendo, si no, salimos del programa
            if (leidos == false) {
                linea = entrada.readLine();
            } // Si lo hemos hecho, salimos del metodo
            else {
                linea = null;
            }
        }
        entrada.close();
        // Cerramos el fichero del que estamos leyendo
        return nodos;
    }


    /**
     * Carga la información relativa a una red de un fichero de RadioMobile
     * @param path ruta al fichero de RadioMobile
     * @param netName red que se desea cargar
     * @param noisePower nivel de ruido
     * @return Vector con los nodos definidos en la red
     * @throws IOException
     */
    public Vector<Subscriber> importFile(String path, String netName, float noisePower) throws IOException {

        //Carga las redes disponibles
        loadNet(path);


        // En este array guardaremos las potencias que cada slave recibe del master, en la posicion 0 del primer slave, y asi sucesivamente
        int[] potencias_slave = null;// = new int[num_users - 1];
        // En este array guardaremos las potencias que el master recibe de cada slave
        int[] potencias_master = null;// = new int[num_users - 1];
        // En este array guardaremos los nombres de los nodos del enlace, en la posicion 0 irá el master, y en las sucesivas los slaves
        String[] nodos_enlace = null;// = new String[num_users];
        // En este array guardaremos los nombres de los equipos de cada nodo, en la posicion 0 irá el del master, y en las sucesivas los slaves
        String[] equipos_enlace = null;// = new String[num_users];
        // Lee la lista de equipos del fichero de RadioMobile
        equipos = leerEquipos(path);

        boolean foundNet = false;

        //Lee la lista de estaciones definidas en el archivo de RadioMobile
        Vector<Subscriber> subscriberList = importSubscribers(path);

        // Creamos los objetos necesarios para poder leer del fichero
        FileReader archivo = new FileReader(path);
        BufferedReader entrada = new BufferedReader(archivo);
        // Almacenaremos hasta dos lineas anteriores a la actual
        String linea = entrada.readLine();
        // Vamos leyendo el fichero linea a linea
        while (linea != null && foundNet == false) {
            // Buscamos la linea donde comienza la definicion de cada enlace, en la cual nos indica en que frecuencia se comunican en el
            if (linea.indexOf(netName) != -1) {

                foundNet = true;
                linea = skipLine(2, entrada);
                if (linea.indexOf("Hz to") != -1) {

                    // Comprobamos que los enlaces están definidos como nosotros esperamos
                    try {
                        // Nos saltamos las lineas que no nos interesan hasta la definicion de los miembros del enlace
                        linea = skipLine(7, entrada);
                        // En la linea en la que nos encontramos buecamos el simbolo # y la palabra Role
                        int posicion_aux = linea.indexOf(Messages.Pad);
                        int posicion_Role = linea.indexOf(Messages.Role);
                        // Si a las dos posiciones obtenidas anteriormente le restamos 3 (los caracteres desde la posicion del asterisco hasta el primer numero)
                        // Y lo dividimos entre tres (cada numero consta de dos cifras mas el espacio entre numeros)
                        // Obtenemos el numero de nodos del enlace
                        int num_users = ((posicion_Role - posicion_aux - 3) / 3);
                        // Almacenamos las posiciones donde estan las palabras System y Antenna que nos servirán para obetener el nombre del equipo
                        int posicion_System = linea.indexOf(Messages.System);
                        int posicion_Antenna = linea.indexOf(Messages.Antenna);
                        // En este array guardaremos las potencias que cada slave recibe del master, en la posicion 0 del primer slave, y asi sucesivamente
                        potencias_slave = new int[num_users - 1];
                        // En este array guardaremos las potencias que el master recibe de cada slave
                        potencias_master = new int[num_users - 1];
                        // En este array guardaremos los nombres de los nodos del enlace, en la posicion 0 irá el master, y en las sucesivas los slaves
                        nodos_enlace = new String[num_users];
                        // En este array guardaremos los nombres de los equipos de cada nodo, en la posicion 0 irá el del master, y en las sucesivas los slaves
                        equipos_enlace = new String[num_users];
                        // Indica la posicion en el array de interfaces;
                        int puntero = 0;
                        // Indica la posicion en el array de potencias_slave
                        int contador = 0;
                        // Indica la posicion en el array de potencias_slave
                        linea = entrada.readLine();
                        // Buscamos la linea en blanco que es donde acaba la definicion de nodos de cada enlace
                        // Recorremos los nodos de cada enlace...
                        while (linea.compareTo("") != 0) {
                            // Cogemos el nombre del nodo
                            String nombre_nodo = linea.substring(0, linea.indexOf("    "));
                            // Cogemos el nombre del equipo
                            String equipo_aux = linea.substring(posicion_System, posicion_Antenna).concat("    ");
                            String nombre_equipo = equipo_aux.substring(0, equipo_aux.indexOf("    "));
                            // Buscamos si ese nodo es el nodo Master....
                            if (linea.indexOf(Messages.Node) != -1 || linea.indexOf(Messages.Master) != -1) {
                                // Cogemos la linea de la matriz de potencias desde despues del numero de linea de la matriz hasta la palabra que encontramos la palabra Master
                                String linea_potencias = linea.substring(posicion_aux + 2, posicion_Role);
                                // Encontramos el comienzo de la primera potencia
                                int inicio_potencias = findNumber(linea_potencias);
                                // Asignamos cada potencia a una posicion del array
                                for (int i = 0; i < potencias_master.length; i++) {
                                    // Obtenemos la posicion inicio de la potencia saltándonos el espacio entre
                                    int inicio = inicio_potencias;
                                    // Obtenemos la posicion del final de la potencia
                                    int fin = linea_potencias.indexOf(" ", inicio);
                                    // la guardamos
                                    int potencia = Integer.valueOf(linea_potencias.substring(inicio, fin)).intValue();
                                    potencias_master[i] = potencia;
                                    // Nos quedamos con el resto de la linea, a partir de la potencia que ya encontramos
                                    linea_potencias = linea_potencias.substring(fin, linea_potencias.length());
                                    // Seguimos leyendo la linea
                                    inicio_potencias = findNumber(linea_potencias);
                                }
                                // Comprobamos si el el array de nombres de nodos esta vacio
                                if (nodos_enlace[0] == null) {
                                    // Y lo almacenamos en el primer sitio del vector
                                    nodos_enlace[0] = nombre_nodo;
                                    // Hacemos lo propio con los equipos
                                    equipos_enlace[0] = nombre_equipo;
                                } else {
                                    // Si ya hubieramos almacenado algun nodo en la primera posicion del vector,
                                    // desplazamos los valores una posicion, y en la primera almacenamos los valores del master
                                    for (int p = puntero; p > 0; p--) {
                                        nodos_enlace[p] = nodos_enlace[p - 1];
                                        equipos_enlace[p] = equipos_enlace[p - 1];
                                    }
                                    nodos_enlace[0] = nombre_nodo;
                                    equipos_enlace[0] = nombre_equipo;
                                }
                            } // En el caso de que sea slave
                            else {
                                // Almacenamos el nombre del nodo en la posicion correspodiente del array que contiene el nombre de los nodos del enlace
                                nodos_enlace[puntero] = nombre_nodo;
                                equipos_enlace[puntero] = nombre_equipo;
                                // Para las potencias realizamos la misma operacion que en el caso del nodo Master
                                // teniendo en cuenta que solo habra una potencia por linea
                                String linea_potencias = linea.substring(posicion_aux + 2, posicion_Role);
                                int inicio_potencias = findNumber(linea_potencias);
                                int inicio = inicio_potencias;
                                int fin = linea_potencias.indexOf(" ", inicio);
                                int potencia = Integer.valueOf(linea_potencias.substring(inicio, fin)).intValue();
                                potencias_slave[contador] = potencia;
                                // Aumentamos contador para no pisar la informacion de esa posicon del array de potencias_slave
                                contador++;
                            }
                            // Aumentamos puntero para no pisar la informacion de esa posicon de los arrays de nombres
                            puntero++;
                            // Continuamos leyendo el enlace
                            linea = entrada.readLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Error al leer los usuarios de radiomobile");

                    }
                }
            }
            linea = entrada.readLine();
        }

        entrada.close();

        return listSubscribers(potencias_slave, potencias_master, nodos_enlace, noisePower, equipos_enlace, subscriberList);
    }


	/** Salta un numero de linea de la variable entrada indicado en num_linea.
     * @param num_lineas Numero de linea que saltaremos.
     * @param entrada Fichero del que saltaremos las lineas.
     * @return La linea en la que continuamos leyendo.
     * @throws IOException
     */
    private String skipLine(int num_lineas, BufferedReader entrada) throws IOException {
        String linea = null;
        // Leemos tantas lineas como indica num_lineas
        for (int i = 1; i <= num_lineas; i++) {
            linea = entrada.readLine();
        }
        return linea;
    }

    /** Devuelve el indice del primer numero de una linea.
     * @param linea linea en la cual buscar el primer numero
     * @return el indice en la linea del primer numero que aparece
     */
    private int findNumber(String linea) {
        int fin = -1;
        // Recorremos la linea
        for (int i = 0; i < linea.length(); i++) {
            // Buscamos un numero
            if (Character.isDigit(linea.charAt(i)) == true) {
                // Si lo encontrmos, lo asignamos a la variable que devuelve el metodo
                fin = i;
                // Aumentamos index para dejar de buscar
                i = linea.length();
            }
        }
        return fin;
    }

    /**
     * Devuelve un vector con las estaciones subscriptoras definidas en el fichero de RadioMobile
     * @param potencias_slave array de potencias que reciben las subscriptoras de la BS
     * @param potencias_master array de potencias que recibe la BS de las subscriptoras
     * @param nodos_enlace array con los nombres de las subscriptoras definidas en el fichero
     * @param noisePower nivel de ruido
     * @param equipos_enlace lista de los equipos definidos en el fichero
     * @param subscriberList Vector con las estaciones subscriptoras definidas en el fichero
     * @return
     */
    private Vector<Subscriber> listSubscribers(int[] potencias_slave, int[] potencias_master, String[] nodos_enlace, float noisePower, String[] equipos_enlace, Vector<Subscriber> subscriberList){

        //Vector de estaciones subscriptoras
        Vector<Subscriber> subscribers = new Vector<Subscriber>();
        //Numero de estaciones subscriptoras
        int leng = potencias_slave.length;
        //Distancia por defecto
        float distance = 1;
        //SNR de uplink y downlink
        float snr_ul, snr_dl;
        float potencia_recibida_ul,potencia_recibida_dl;

        //Equipo definido como BS
        Equipo baseDevice = searchDevice(equipos_enlace[0]);
        baseStation = Auxiliary.searchSubscriber(nodos_enlace[0], subscriberList);
        //Umbral de la bS
        double baseThreshold = baseDevice.getRx_thr();
        
        //Recorre los arrays recopilando los datos de cada enlace e incorporandolos
        //al vector de subscriptoras
        for (int index = 0;index<leng; index++){
            Subscriber subs = Auxiliary.searchSubscriber(nodos_enlace[index+1],subscriberList);
            //Calcula la distancia a la BS
            distance = getDistance(subs.getX(), subs.getY(), subs.getZ());
            Equipo device = searchDevice(equipos_enlace[index+1]);
            snr_ul=(float) (potencias_master[index] + baseThreshold - 50 - noisePower);
            snr_dl=(float) (potencias_slave[index] + device.getRx_thr() - 50 - noisePower);
            
            potencia_recibida_ul = (float) (potencias_master[index] + baseThreshold - 50);
            potencia_recibida_dl = (float) (potencias_slave[index] + device.getRx_thr() -50);
            
            //Inserta el nombre las SNR y la distancia.
            subs.modify(nodos_enlace[index+1], distance, snr_ul, snr_dl, potencia_recibida_ul, potencia_recibida_dl);
            subscribers.add(subs);
        }

        return subscribers;
    }

	/**
     * Busca un Equipo en función del nombre que se pasa como parámetro
     * @param name nombre del Equipo que se busca
     * @return objeto Equipo con el nombre buscado
     */
    public Equipo searchDevice(String name) {
        //Recorre el vector de SSs
        Iterator<Equipo> it = equipos.iterator();
        while (it.hasNext()) {
            Equipo current = (Equipo) (it.next());
            //Si el nombre de un SS coincide con el parametro "name"
            //devuelve el objeto SS
            if (current.getNombre().equals(name)) {
                return current;
            }
        }
        return null;
    }

    /**
     * Calcula la distancia de un punto a la estacion base
     * @param coordX latitud
     * @param coordY longitud
     * @param coordZ altura
     * @return distancia en Km a la estación base
     */
    public float getDistance(float coordX, float coordY, float coordZ) {

        float longitud = Math.abs(coordX - baseStation.getX());
        float latitude = Math.abs(coordY - baseStation.getY());
        float d1 = (float) Math.sqrt(Math.pow(longitud, 2) + Math.pow(latitude, 2));
        float altitude = Math.abs(coordZ - baseStation.getZ());
        float distance = (float) Math.sqrt(Math.pow(altitude, 2) + Math.pow(d1, 2));
        //Distancia en km
        return distance/1000;
    }

    /** Devuelve un vector con la informacion disponible de cada uno de los equipos definidos en Radio Mobile.
     * @see Lectura#getFichero
     * @see Lectura#saltarLineas
     * @see Equipo#addNombre
     * @see Equipo#addPwrTx
     * @see Equipo#addLoss
     * @see Equipo#addRxThr
     * @see Equipo#addAntG
     * @return Vector con los equipos definidos en el report de Radio Mobile.
     */
    public Vector<Equipo> leerEquipos(String path) throws IOException {
        // Nos indica si hemos leido los equipos definidos en radio mobile
        boolean leidos = false;
        equipos = new Vector<Equipo>();
        // Creamos los objetos necesarios para poder leer del fichero
        FileReader archivo = new FileReader(path);
        BufferedReader entrada = new BufferedReader(archivo);
        String linea = entrada.readLine();
        // Vamos leyendo el fichero linea a linea
        while (linea != null) {
            // La linea "Systems" nos indicará el comienzo de la definición de equipos
            if (linea.compareTo(Messages.Systems) == 0) {
                // Nos situamos en la linea que define al primer equipo
                linea = skipLine(4, entrada);
                // la linea "---------------------------------------------------------------------------" indica el final de la definicion de equipos
                while (linea.compareTo("---------------------------------------------------------------------------") != 0) {
                    Equipo equipo = new Equipo();
                    // Si el equipo está mal definido capturamos la excepción y lanzamos un mensaje
                    try {
                        // Vamos asignando para cada equipo el valor para cada una de sus variables
                        String nombre = linea.substring(0, 20);
                        nombre = nombre.concat("    ");
                        equipo.addNombre(nombre);
                        String pwr_tx = linea.substring(20, 30);
                        equipo.addPwrTx(pwr_tx.replace(',', '.'));
                        String loss = linea.substring(30, 36);
                        equipo.addLoss(loss.replace(',', '.'));
                        String rx_thr = linea.substring(46, 56);
                        equipo.addRxThr(rx_thr.replace(',', '.'));
                        String ant_g = linea.substring(56, 64);
                        // Cambiamos los puntos por comas
                        equipo.addAntG(ant_g.replace(',', '.'));
                        // Introducimos el nodo en el vector
                        equipos.addElement(equipo);
                    } catch (Exception e) {
                        System.err.println("El equipo " + equipo.getNombre() + " no está bien definido y no será tenido en cuenta.");
                    }
                    linea = entrada.readLine();
                }
                // si ya hemos leido los equipos, cambiamos el valor de leido
                leidos = true;
            }
            // Si aun no hemos leido los equipos, seguimos leyendo
            if (leidos == false) {
                linea = entrada.readLine();
            } // Si lo hemos hecho, dejamos de leer
            else {
                linea = null;
            }
        }
        entrada.close();
        return equipos;
    }


    /**
     * Busca los nombres de las redes definidas en un fichero de
     * RadioMobile
     * @param path ruta al fichero de RadioMobile
     * @return array con los nombres de las redes
     * @throws java.io.IOException
     */
    public String[] loadNet(String path) throws IOException {

        //lista de redes detectadas en el fichero
        ArrayList<String> netList = new ArrayList<String>();

        // Creamos los objetos necesarios para poder leer del fichero
        FileReader archivo = new FileReader(path);
        BufferedReader entrada = new BufferedReader(archivo);
        String linea = entrada.readLine();
        // Vamos leyendo el fichero linea a linea
        while (linea != null) {
            // Buscamos la linea "Active nets information",
            // que nos indicara el comienzo del listado de nodos
            if (linea.compareTo(Messages.ActiveNetsInformation) == 0) {
                // salta hasta la linea donde se define la red
                linea = skipLine(3, entrada);
                //Almacena el nombre de la red en el vector quitándole
                //los espacios que tenga al principio o al final del nombre
                
                netList.add(linea.trim());
                linea = entrada.readLine();
            } else if (linea.contains(Messages.Quality)) {
                //Dos lineas despues de Quality o hay una nueva red o se acaba el fichero
                linea = skipLine(3, entrada);
                if (linea != null) {
                    //Almacena el nombre de la red en el vector quitándole
                    //los espacios que tenga al principio o al final del nombre
                    netList.add(linea.trim());
                    linea = entrada.readLine();
                }

            } else {
                linea = entrada.readLine();
            }
        }
        // Cerramos el fichero del que estamos leyendo
        entrada.close();

        //Convierte el vector en un Array
        String[] list = new String[netList.size()];
        list = netList.toArray(list);

        return list;

    }
    
    public Subscriber getBaseStation(){
    	return baseStation;
    }

}
