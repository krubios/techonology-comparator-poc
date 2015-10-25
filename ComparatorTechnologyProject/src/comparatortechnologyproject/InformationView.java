package comparatortechnologyproject;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import comparator.preferences.PreferenceConstants;
import comparator.preferences.wifi.WifiComboFieldEditor;
import comparator.preferences.wimax.WimaxComboFieldEditor;
import comparator.scheduler.Auxiliary;
import comparator.scheduler.Scheduler;
import comparator.scheduler.Subscriber;

public class InformationView extends ViewPart {

	public static final String ID = "ComparatorTechnologyProject.view";
	// Poner en negrita
	public Font boldFont = JFaceResources.getFontRegistry().getBold(
			JFaceResources.DEFAULT_FONT);

	public static ArrayList<String> wimaxModulationParamList = new ArrayList<String>(
			Arrays.asList("64 QAM 3/4", "64 QAM 2/3", "16 QAM 3/4",
					"16 QAM 1/2", "QPSK 3/4", "QPSK 1/2", "BPSK 1/2"));

	public static String higthWimaxModulationDL = "";
	public static String higthWimaxModulationUL = "";

	public static Text messageText;
	private static TableViewer viewer;

	public static TreeItem grandChildWifiDL;
	public static TreeItem grandChildWifiUL;

	public static TreeItem grandChildWimaxDL;
	public static TreeItem grandChildWimaxUL;
	public static Tree wifiTree;
	public static Tree wimaxTree;
	private static String higthWifiModulationDL = "";
	private static String higthWifiModulationUL = "";

	public void createPartControl(Composite parent) {

		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		GridData data = new GridData();
		data.widthHint = 700;
		data.heightHint = 100;
		top.setLayout(layout);
		top.setLayoutData(data);

		// top banner
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(data);
		layout = new GridLayout();
		banner.setLayout(layout);

		Group groupTechnology = new Group(top, SWT.NONE);
		groupTechnology.setLayout(new GridLayout());
		groupTechnology.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupTechnology.setText("Información Technologia Usada:");

		Group groupRadioMobile = new Group(banner, SWT.NONE);
		groupRadioMobile.setLayout(new GridLayout());
		groupRadioMobile.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupRadioMobile.setText("Información Radio Mobile: ");

		createRadioMobileViewer(groupRadioMobile);
		createTechnologyUsedViewer(groupTechnology);
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if(event.getProperty().equals(PreferenceConstants.FRAME_DURATION)){
						String frameDurationValue = event.getNewValue().toString();
						float frameDuration = (float) (Float.parseFloat(frameDurationValue)*Math.pow(10, -3));
						Scheduler.wimaxConfig.setFrameDuration(frameDuration);
						refreshWimaxCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_DL)){
						String fractionFrameDl = event.getNewValue().toString();
						if (Auxiliary.isNumber(fractionFrameDl)){
							Scheduler.wimaxConfig.setFractionFrameDl(Float.parseFloat(fractionFrameDl));
							refreshWimaxCapacity();
						}
					}else if (event.getProperty().equals(PreferenceConstants.WIMAX_FRACTION_FRAME_UL)){
						String fractionFrameUl = event.getNewValue().toString();
						if (Auxiliary.isNumber(fractionFrameUl)){
							Scheduler.wimaxConfig.setFractionFrameUl(Float.parseFloat(fractionFrameUl));
							refreshWimaxCapacity();
						}
					}else if (event.getProperty().equals(PreferenceConstants.BAND_WIDTH)){
						String bandWidthValue = event.getNewValue().toString();
						float bandWidth = (float) (Float.parseFloat(bandWidthValue) * Math.pow(10, 6));
						Scheduler.wimaxConfig.setBandWidth(bandWidth);
						refreshWimaxCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.CYCLIX_PREFIX)){
						String cyclixPrefix = event.getNewValue().toString();
						float cyclixPrefixValue = Scheduler.wimaxConfig.getCyclixPrefixValue(cyclixPrefix);
						Scheduler.wimaxConfig.setCyclixPrefix(cyclixPrefixValue);
						refreshWimaxCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.COMBO_SIFS_RIFS)){
						String comboSifsDifsValue = event.getNewValue().toString();
						float comoValue = Scheduler.wifiConfig.getSifsRifsValue(comboSifsDifsValue);
						Scheduler.wifiConfig.setSifsRifs(comoValue);
						refreshWifiCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.BLOCK_ACK_ON_OFF)){
						String blockACKOnOff = event.getNewValue().toString();
						Scheduler.wifiConfig.setBlockAck(Float.parseFloat(blockACKOnOff));
						refreshWifiCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.NUM_PACKAGES)){
						String numPackagesValue = event.getProperty().toString();
						Scheduler.wifiConfig.setNumPackages(Float.parseFloat(numPackagesValue));
						refreshWifiCapacity();
						
					}else if (event.getProperty().equals(PreferenceConstants.PACKAGES_SIZE)){
						String packageSizeValue = event.getProperty().toString();
						Scheduler.wifiConfig.setPackagesSize(Float.parseFloat(packageSizeValue));
						refreshWifiCapacity();
						
					}else if(event.getProperty().equals(PreferenceConstants.SENSIBILITY_WIMAX)){
						if (NavigationView.getSelectedSubscriber() != null){
							InformationView.showInformationSuscriber(NavigationView.getSelectedSubscriber());
							refreshWimaxCapacity();
						}
					}else if (((event.getProperty().equals(PreferenceConstants.MIMO_WIFI)) ||
							(event.getProperty().equals(PreferenceConstants.SENSIBILITY_WIFI)))){
						
						if (NavigationView.getSelectedSubscriber() != null){
							InformationView.showInformationSuscriber(NavigationView.getSelectedSubscriber());
							refreshWifiCapacity();
						}
					}else if (event.getProperty().equals(PreferenceConstants.RTS_CAPACITY_WIFI)){
						if (NavigationView.getSelectedSubscriber() != null){
							String isRts = event.getNewValue().toString();
							Scheduler.wifiConfig.setRtsCapacityFlag(isRts);
							InformationView.showInformationSuscriber(NavigationView.getSelectedSubscriber());
							refreshWifiCapacity();
						}
					}else if (event.getProperty().equals(PreferenceConstants.WIFI_FRACTION_FRAME_DL)){
						String fractionFrameDl = event.getNewValue().toString();
						if (Auxiliary.isNumber(fractionFrameDl)){
							Scheduler.wifiConfig.setFractionFrameDl(Float.parseFloat(fractionFrameDl));
							refreshWifiCapacity();
						}
					}else if (event.getProperty().equals(PreferenceConstants.WIFI_FRACTION_FRAME_UL)){
						String fractionFrameUl = event.getNewValue().toString();
						if (Auxiliary.isNumber(fractionFrameUl)){
							Scheduler.wifiConfig.setFractionFrameUl(Float.parseFloat(fractionFrameUl));
							refreshWifiCapacity();
						}
					}
				}
			});

	}

	protected void refreshWimaxCapacity() {
		ApplicationWorkbenchWindowAdvisor.scheduler.wimaxScheduler();
		InformationView.showWimaxCapacityInformation();
	}
	
	protected void refreshWifiCapacity() {
		ApplicationWorkbenchWindowAdvisor.scheduler.wifiScheduler();
		InformationView.showWifiCapacityInformation();
	}

	private void createTechnologyUsedViewer(Composite parent) {
		GridData gridData = new GridData();
		gridData.widthHint = 700;
		gridData.heightHint = 200;
		parent.setLayoutData(gridData);
		parent.setLayout(new GridLayout(3, true));
		wimaxTree = new Tree(parent, SWT.MULTI | SWT.FULL_SELECTION);
		createColumns(wimaxTree);
		fillTree(wimaxTree, "Wimax");

		wifiTree = new Tree(parent, SWT.MULTI | SWT.FULL_SELECTION);
		createColumns(wifiTree);
		fillTree(wifiTree, "Wifi");
	}

	private void createColumns(Tree tree) {
		int columnCount = 2;
		for (int i = 0; i < columnCount; i++) {
			TreeColumn column = new TreeColumn(tree, SWT.NONE);
			column.setText("Column " + i);
			column.setWidth(150);
		}
	}

	private void fillTree(Tree tree, String name) {
		GridData data = new GridData(GridData.FILL, GridData.FILL, false, true);
		tree.setLayoutData(data);
		tree.setRedraw(false);

		// Create five root items
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText(name);
		item.setFont(boldFont);

		// Create three children below the root
		TreeItem childDL = new TreeItem(item, SWT.NONE);
		childDL.setText("DL");
		TreeItem childUL = new TreeItem(item, SWT.NONE);
		childUL.setText("UL");

		if (name.equals("Wimax")) {
			grandChildWimaxDL = new TreeItem(childDL, SWT.NONE);
			grandChildWimaxDL.setText("");
			grandChildWimaxDL.setText(1, "");

			grandChildWimaxUL = new TreeItem(childUL, SWT.NONE);
			grandChildWimaxUL.setText("");
			grandChildWimaxUL.setText(1, "");

		} else if (name.equals("Wifi")) {
			grandChildWifiDL = new TreeItem(childDL, SWT.NONE);
			grandChildWifiDL.setText("");
			grandChildWifiDL.setText(1,"");

			grandChildWifiUL = new TreeItem(childUL, SWT.NONE);
			grandChildWifiUL.setText("");
			grandChildWifiUL.setText(1, "");
		}

		tree.setRedraw(true);
	}

	private void createRadioMobileViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(ModelProvider.INSTANCE.getSubscriber());
		// make the selection available to other views
		getSite().setSelectionProvider(viewer);

		// define layout for the viewer
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		gridData.horizontalSpan = 2;
		viewer.getControl().setLayoutData(gridData);
	}

	public static TableViewer getViewer() {
		return viewer;
	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "SNR UL(dB)", "SNR DL(dB)", "Distancia(Km)",
				"Potencia recibida UL(dBm)", "Potencia recibidad DL(dBm)" };
		int[] bounds = { 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return p.getSnr(false);
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return p.getSnr(true);
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getDistance());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getPotenciaRecibida_Ul());
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getPotenciaRecibida_Dl());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.pack();
		return viewerColumn;
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void showInformationSuscriber(Subscriber _selectedSubscriber) {

		viewer.getTable().clearAll();
		ModelProvider.INSTANCE.clearAll();
		ModelProvider.INSTANCE.setSubscriber(_selectedSubscriber);
		viewer.setInput(ModelProvider.INSTANCE.getSubscriber());

		grandChildWimaxDL.setText(showBestWimaxDLModulation(_selectedSubscriber
				.getPotenciaRecibida_Dl()));
		grandChildWimaxUL.setText(showBestWimaxULModulation(_selectedSubscriber
				.getPotenciaRecibida_Ul()));

		grandChildWifiDL.setText(showBestWifiDlModulation(_selectedSubscriber
				.getPotenciaRecibida_Dl()));
		grandChildWifiUL.setText(showBestWifiUlModulation(_selectedSubscriber
				.getPotenciaRecibida_Ul()));
	}

	private static String showBestWimaxULModulation(float potenciaMinRecibida_Ul) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		for (String modulationParam : wimaxModulationParamList) {
			sensitivityParam = WimaxComboFieldEditor
					.getSensibilityWimaxModulation(modulationParam);
			if ((potenciaMinRecibida_Ul > Float.parseFloat(sensitivityParam))
					|| potenciaMinRecibida_Ul == Float
							.parseFloat(sensitivityParam)) {
				modulation.add(modulationParam);
			}
		}
		String higthModulation = Auxiliary.getWimaxHightModulation(modulation);
		setHightWimaxModulationUL(higthModulation);
		return higthModulation;
	}

	private static String showBestWimaxDLModulation(float potenciaMinRecibida_Dl) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		for (String modulationParam : wimaxModulationParamList) {
			sensitivityParam = WimaxComboFieldEditor
					.getSensibilityWimaxModulation(modulationParam);

			if ((potenciaMinRecibida_Dl > Float.parseFloat(sensitivityParam))
					|| potenciaMinRecibida_Dl == Float
							.parseFloat(sensitivityParam)) {
				modulation.add(modulationParam);
			}
		}

		String higthModulation = Auxiliary.getWimaxHightModulation(modulation);
		setHightWimaxModulationDL(higthModulation);
		return higthModulation;
	}

	private static String showBestWifiDlModulation(float potenciaMinRecibida_Dl) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		String[][] modulationsList = Auxiliary.getWifiElementsModulation();
		if (Auxiliary.getMimoModulation()){
			modulationsList = Auxiliary.getWifiMimoElementsModulation();
		}
		for (String[] modulationParams : modulationsList) {
			for(String modulationParam: modulationParams){
				sensitivityParam = WifiComboFieldEditor
						.getSensitivityWifi(modulationParam);
	
				if ((potenciaMinRecibida_Dl > Float.parseFloat(sensitivityParam))
						|| potenciaMinRecibida_Dl == Float
								.parseFloat(sensitivityParam)) {
					modulation.add(modulationParam);
				}
				break;
			}
		}
		
		String higthModulation = Auxiliary.getWifiHightModulation(modulation);
		setHightWifiModulationDL(higthModulation);
		
		return higthModulation;
	}

	private static String showBestWifiUlModulation(float potenciaMinRecibida_Ul) {

		ArrayList<String> modulation = new ArrayList<String>();
		String sensitivityParam = "";
		String[][] modulationsList = Auxiliary.getWifiElementsModulation();
		if (Auxiliary.getMimoModulation()){
			modulationsList = Auxiliary.getWifiMimoElementsModulation();
		}
		for (String[] modulationParams : modulationsList) {
			for(String modulationParam: modulationParams){
				sensitivityParam = WifiComboFieldEditor
						.getSensitivityWifi(modulationParam);
	
				if ((potenciaMinRecibida_Ul > Float.parseFloat(sensitivityParam))
						|| potenciaMinRecibida_Ul == Float
								.parseFloat(sensitivityParam)) {
					modulation.add(modulationParam);
				}
				break;
			}
		}
		String higthModulation = Auxiliary.getWifiHightModulation(modulation);
		setHightWifiModulationUL(higthModulation);
		
		return higthModulation;
	}

	public static void clearTree() {
		grandChildWifiDL.setText("");
		grandChildWifiUL.setText("");
		grandChildWifiDL.setText(1,"");
		grandChildWifiUL.setText(1,"");
		grandChildWimaxDL.setText("");
		grandChildWimaxUL.setText("");
		grandChildWimaxDL.setText(1, "");
		grandChildWimaxUL.setText(1, "");
	}

	public static String getHightWimaxModulationDL() {
		return higthWimaxModulationDL;
	}

	public static String getHightWimaxModulationUL() {
		return higthWimaxModulationUL;
	}

	public static void setHightWimaxModulationDL(String modulation) {
		higthWimaxModulationDL = modulation;
	}

	public static void setHightWimaxModulationUL(String modulation) {
		higthWimaxModulationUL = modulation;
	}

	public static void showWimaxCapacityInformation() {
		grandChildWimaxDL.setText(
				1,
				Float.toString(ApplicationWorkbenchWindowAdvisor.scheduler
						.getDlWimaxCapacity()) + " Kbps");
		grandChildWimaxUL.setText(
				1,
				Float.toString(ApplicationWorkbenchWindowAdvisor.scheduler
						.getUlWimaxCapacity()) + " Kbps");
	}
	
	public static String getHightWifiModulationDL() {
		return higthWifiModulationDL;
	}

	public static String getHightWifiModulationUL() {
		return higthWifiModulationUL;
	}

	public static void setHightWifiModulationDL(String modulation) {
		higthWifiModulationDL = modulation;
	}

	public static void setHightWifiModulationUL(String modulation) {
		higthWifiModulationUL = modulation;
	}

	public static void showWifiCapacityInformation() {
		grandChildWifiDL.setText(
				1,
				Float.toString(ApplicationWorkbenchWindowAdvisor.scheduler
						.getDlWifiCapacity()) + " Kbps");
		grandChildWifiUL.setText(
				1,
				Float.toString(ApplicationWorkbenchWindowAdvisor.scheduler
						.getUlWifiCapacity()) + " Kbps");
	}
}
