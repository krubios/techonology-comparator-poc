package comparator.scheduler;

import org.eclipse.osgi.util.NLS;


public class Messages {
	
	private static final String BUNDLE_NAME= "comparator.scheduler.messages";
	
	public static String NetSelected;
	public static String ComboAceptMessage;
	public static String ActiveUnitsInformation;
	public static String Pad;
	public static String Role;
	public static String System;
	public static String Antenna;
	public static String Node;
	public static String Master;
	public static String Systems;
	public static String ActiveNetsInformation;
	public static String Quality;
	
	
	static{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages(){
		
	}

}
