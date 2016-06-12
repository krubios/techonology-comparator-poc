
package comparator.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import comparator.actions.OpenFileAction;
import comparator.preferences.Messages;
import comparator.preferences.wifi.WifiPreferenceData;


/** La clase Auxiliary almacena constantes y metodos auxiliares que
 * se emplean en el resto de clases.
 */
public final class Auxiliary {
	
	private static float max_distance = 0;
	private static Subscriber baseStation = new Subscriber();
	
	private static final String[] MCS_0 = {"MCS0", "MCS0"};
	private static final String[] MCS_1 = {"MCS1", "MCS1"};
	private static final String[] MCS_2 = {"MCS2", "MCS2"};
	private static final String[] MCS_3 = {"MCS3", "MCS3"};
	private static final String[] MCS_4 = {"MCS4", "MCS4"};
	private static final String[] MCS_5 = {"MCS5", "MCS5"};
	private static final String[] MCS_6 = {"MCS6", "MCS6"};
	private static final String[] MCS_7 = {"MCS7", "MCS7"};
	private static final String[] MCS_8 = {"MCS8", "MCS8"};
	private static final String[] MCS_9 = {"MCS9", "MCS9"};
	private static final String[] MCS_10 = {"MCS10", "MCS10"};
	private static final String[] MCS_11 = {"MCS11", "MCS11"};
	private static final String[] MCS_12 = {"MCS12", "MCS12"};
	private static final String[] MCS_13 = {"MCS13", "MCS13"};
	private static final String[] MCS_14 = {"MCS14", "MCS14"};
	private static final String[] MCS_15 = {"MCS15", "MCS15"};
	
	public static WifiPreferenceData data0 = new WifiPreferenceData("00", Messages.MCS_0, Float.parseFloat(Messages.MCS_0_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_0_POWER_VALUE), (float)6.5);
	public static WifiPreferenceData data1 = new WifiPreferenceData("01", Messages.MCS_1, Float.parseFloat(Messages.MCS_1_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_1_POWER_VALUE), (float)13);
	public static WifiPreferenceData data2 = new WifiPreferenceData("02", Messages.MCS_2, Float.parseFloat(Messages.MCS_2_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_2_POWER_VALUE), (float)19.5);
	public static WifiPreferenceData data3 = new WifiPreferenceData("03", Messages.MCS_3, Float.parseFloat(Messages.MCS_3_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_3_POWER_VALUE), (float)26);
	public static WifiPreferenceData data4 = new WifiPreferenceData("04", Messages.MCS_4, Float.parseFloat(Messages.MCS_4_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_4_POWER_VALUE), (float)39);
	public static WifiPreferenceData data5 = new WifiPreferenceData("05", Messages.MCS_5, Float.parseFloat(Messages.MCS_5_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_5_POWER_VALUE), (float)52);
	public static WifiPreferenceData data6 = new WifiPreferenceData("06", Messages.MCS_6, Float.parseFloat(Messages.MCS_6_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_6_POWER_VALUE), (float)58.5);
	public static WifiPreferenceData data7 = new WifiPreferenceData("07", Messages.MCS_7, Float.parseFloat(Messages.MCS_7_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_7_POWER_VALUE), (float)65);
	public static WifiPreferenceData data8 = new WifiPreferenceData("08", Messages.MCS_8, Float.parseFloat(Messages.MCS_8_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_8_POWER_VALUE), (float)13);
	public static WifiPreferenceData data9 = new WifiPreferenceData("09", Messages.MCS_9, Float.parseFloat(Messages.MCS_9_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_9_POWER_VALUE), (float)26);
	public static WifiPreferenceData data10 = new WifiPreferenceData("10", Messages.MCS_10, Float.parseFloat(Messages.MCS_10_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_10_POWER_VALUE), (float)39);
	public static WifiPreferenceData data11 = new WifiPreferenceData("11", Messages.MCS_11, Float.parseFloat(Messages.MCS_11_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_11_POWER_VALUE), (float)52);
	public static WifiPreferenceData data12 = new WifiPreferenceData("12", Messages.MCS_12, Float.parseFloat(Messages.MCS_12_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_12_POWER_VALUE), (float)78);
	public static WifiPreferenceData data13 = new WifiPreferenceData("13", Messages.MCS_13, Float.parseFloat(Messages.MCS_13_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_13_POWER_VALUE), (float)104);
	public static WifiPreferenceData data14 = new WifiPreferenceData("14", Messages.MCS_14, Float.parseFloat(Messages.MCS_14_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_14_POWER_VALUE), (float)117);
	public static WifiPreferenceData data15 = new WifiPreferenceData("15", Messages.MCS_15, Float.parseFloat(Messages.MCS_15_SENSIBILITY), 
			Float.parseFloat(Messages.MCS_15_POWER_VALUE), (float)130);
	
	
	
    //Booleanos que identificana cada sentido
    public static boolean DOWNLINK = true;
    public static boolean UPLINK = false;

    //Enteros que identifican al sentido del throughput
    public static int UL_RATE = 0;
    public static int DL_RATE = 1;
    
    //Mimo modulation
    public static boolean mimoModulation;


       /**
     * Busca un SS en funci�n del nombre que se pasa como par�metro
     * @param name nombre del SS que se busca
     * @return objeto SS con el nombre buscado
     */
    static Subscriber searchSubscriber(String name,Vector<?> vector){
        //Recorre el vector de SSs
        Iterator<?> it = vector.iterator();
        while (it.hasNext()) {
            Subscriber current = (Subscriber) (it.next());
            //Si el nombre de un SS coincide con el parametro "name"
            //devuelve el objeto SS
            if (current.getName().equals(name)) {
                return current;
            }
        }
        return null;

    }

    /** Comprueba si los datos introducidos por el usuario son
     * valores num�ricos
     * @param data datos introducidos por el usuario
     * @return si el valor es num�rico o no
     */
    public static boolean isNumber(String data) {
        //Comprueba que el campo no est� vacio
        if (data.equals("")) {
            String message = "Por favor, rellene todos los campos";
            showErrorMessage(null,message);
            return false;
        }
        //Comprueba que todos los caracteres son num�ricos o un punto
        for (int i = 0; i < data.length(); i++) {
            char z = data.charAt(i);
            if ((z < '0' || z > '9') && z != '.') {
                String message = '\"' + data + '\"' + " No es un valor numérico";
                showErrorMessage(null, message);
                return false;
            }
        }
        if (new Float(data) < 0) {
            String message = "No se admiten valores negativos en este campo";
            showErrorMessage(null,message);
            return false;
        }

        return true;
    }
    
    
    
    public static String isNumberWithErrorMessage(String data) {
        //Comprueba que el campo no est� vacio
        if (data.equals("")) {
        	String message = "El nombre del sistema no existe";
            showErrorMessage(OpenFileAction.getShell(),message);
            throw new RuntimeException();
        }
        
        String number = data.substring(data.length() -1);
        for (int i = 0; i < number.length();) {
            char z = number.charAt(i);
            if (z < '0' || z > '9') {
                return data;
            }else{
            	String message = "El nombre del sistema es muy grande, el tamaño máximo permitido es de 19 caracteres";
                showErrorMessage(OpenFileAction.getShell(),message);
                throw new RuntimeException();
            }
        }

        return data;
    }
    
    /** Abre una ventana con un mensaje de error
     * @param shell 
     * @param mensaje que se desea mostrar
     */
    public static void showErrorMessage(Shell shell,String message) {

    	MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setText(Messages.ErrorMessage);
		messageBox.setMessage(message);
		messageBox.open();
    }
    
    /** Abre una ventana con un mensaje de informaci�n
     * @param shell 
     * @param mensaje que se desea mostrar
     */
    public static void showInfoMessage(Shell shell,String message) {

    	MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setText(Messages.InformationMessage);
		messageBox.setMessage(message);
		messageBox.open();
    }
    
    public static String[][] getWifiElementsModulation() {
		return new String[][]{
				MCS_0, MCS_1,MCS_2,MCS_3,MCS_4,MCS_5,MCS_6,MCS_7 	
			};
	}
	
	public static String[][] getWifiMimoElementsModulation(){
		return new String[][]{
				MCS_0, MCS_1,MCS_2,MCS_3,MCS_4,MCS_5,MCS_6,MCS_7,
				MCS_8, MCS_9,MCS_10,MCS_11,MCS_12,MCS_13,MCS_14,MCS_15 	
			};
	}

	public static void setMimoModulation(boolean newValue) {
		mimoModulation = newValue;
	}
	
	public static boolean getMimoModulation(){
		return mimoModulation;
	}
	
	/**
	 * Devuelve la mayor modulaci�nn wifi
	 * @param modulation
	 * @return
	 */
	public static String getWifiHightModulation(ArrayList<String> modulation) {
		String mayor = "";
		if (modulation.contains(Messages.MCS_15)) {
			mayor = Messages.MCS_15;
		} else if (modulation.contains(Messages.MCS_14)) {
			mayor = Messages.MCS_14;
		} else if (modulation.contains(Messages.MCS_13)) {
			mayor = Messages.MCS_13;
		} else if (modulation.contains(Messages.MCS_12)) {
			mayor = Messages.MCS_12;
		} else if (modulation.contains(Messages.MCS_11)) {
			mayor = Messages.MCS_11;
		} else if (modulation.contains(Messages.MCS_10)) {
			mayor = Messages.MCS_10;
		} else if (modulation.contains(Messages.MCS_9)) {
			mayor = Messages.MCS_9;
		} else if (modulation.contains(Messages.MCS_8)) {
			mayor = Messages.MCS_8;
		} else if (modulation.contains(Messages.MCS_7)) {
			mayor = Messages.MCS_7;
		} else if (modulation.contains(Messages.MCS_6)) {
			mayor = Messages.MCS_6;
		} else if (modulation.contains(Messages.MCS_5)) {
			mayor = Messages.MCS_5;
		} else if (modulation.contains(Messages.MCS_4)) {
			mayor = Messages.MCS_4;
		} else if (modulation.contains(Messages.MCS_3)) {
			mayor = Messages.MCS_3;
		} else if (modulation.contains(Messages.MCS_2)) {
			mayor = Messages.MCS_2;
		} else if (modulation.contains(Messages.MCS_1)) {
			mayor = Messages.MCS_1;
		} else {
			mayor = Messages.MCS_0;
		}
		return mayor;
	}
	
	/**
	 * Devuelve la mayor modulacion en Wimax
	 * @param modulations
	 * @return
	 */
	public static String getWimaxHightModulation(ArrayList<String> modulations) {
		String mayor = "";
		for (String modulation : modulations) {
			if (modulation.trim().equals(Messages.QAM64_3_4_PROVIDER.trim())) {
				mayor = Messages.QAM64_3_4_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.QAM64_2_3_PROVIDER.trim())) {
				mayor = Messages.QAM64_2_3_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.QAM16_3_4_PROVIDER.trim())) {
				mayor = Messages.QAM16_3_4_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.QAM16_1_2_PROVIDER.trim())) {
				mayor = Messages.QAM16_1_2_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.QPSK_3_4_PROVIDER.trim())) {
				mayor = Messages.QPSK_3_4_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.QPSK_1_2_PROVIDER.trim())) {
				mayor = Messages.QPSK_1_2_PROVIDER;
				break;
			} else if (modulation.trim().equals(
					Messages.BPSK_1_2_PROVIDER.trim())) {
				mayor = Messages.BPSK_1_2_PROVIDER;
			}
		}
		return mayor;
	}
	
	/**
	 * Recorre el vector de usuarios para encontrar el usuario a mayor distancia
	 */
	public static void setMaxDistance(ArrayList<Subscriber> listSubscriberNodes) {
		// Inicializa a 0 el valor de max_distance
		// Recorre el vector de usuarios
		max_distance = 0;
		for (Iterator<Subscriber> it = listSubscriberNodes.iterator(); it
				.hasNext();) {
			Subscriber user = (Subscriber) it.next();
			// Si la distancia m�xima del CPE supera a la anterior
			if (max_distance < user.getDistance()) {
				max_distance = user.getDistance();
			}
		}
	}
	
	public static float getMaxDistance(){
		return max_distance;
	}

	public static void setBaseStation(String name,
			Vector<Subscriber> vector) {
		Iterator<?> it = vector.iterator();
        while (it.hasNext()) {
            Subscriber current = (Subscriber) (it.next());
            //Si el nombre de un SS coincide con el parametro "name"
            //devuelve el objeto SS
            if (current.getName().equals(name)) {
            	baseStation = current;
            }
        }
	}
	
	public static Subscriber getBaseStation(){
		return baseStation;
	}
}
