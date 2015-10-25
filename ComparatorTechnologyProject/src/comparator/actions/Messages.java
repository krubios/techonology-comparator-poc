package comparator.actions;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS{
	
	private static final String BUNDLE_NAME= "comparator.actions.messages";
	public static String ShowErrorToreadNetMessages;
	public static String ShowErroToReadFilerMessage;
	public static String SelectNetMessage;
	public static String SelectImporteNetMessage;
	public static String ShowErrorToLoadInformationNetMessage;
	
	static{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages(){
	}

}
