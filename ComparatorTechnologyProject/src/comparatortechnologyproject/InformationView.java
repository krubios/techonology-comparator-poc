package comparatortechnologyproject;

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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import comparator.actions.OpenFileAction;
import comparator.scheduler.Scheduler;
import comparator.scheduler.Subscriber;

public class InformationView extends ViewPart {

	public static final String ID = "ComparatorTechnologyProject.view";

	private String[] titles =  { "SNR UL(dB)", "SNR DL(dB)", "Distancia(Km)",
			"Potencia recibida UL(dBm)", "Potencia recibida DL(dBm)" };
	
	public TableViewer tableViewer;

	public static TreeItem grandChildWifiDL;
	public static TreeItem grandChildWifiUL;

	public static TreeItem grandChildWimaxDL;
	public static TreeItem grandChildWimaxUL;

	public static ConfigurationProperties configurationProperties = new ConfigurationProperties();
	
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

		//Se crea la vista de información del report.xml
		createRadioMobileViewer(groupRadioMobile);

		//Se crea la vista de información de la capacidad de las tecnologias
		createTechnologyUsedViewer(groupTechnology);
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					configurationProperties = ConfigurationFactoryProperties.getInstance(event);
					
					if(configurationProperties.isFrameDurationChange()){
						Scheduler.wimaxConfig.setFrameDuration(configurationProperties.getFrameDuration());
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isWimaxFractionFrameDlChange()){
						Scheduler.wimaxConfig.setFractionFrameDl(configurationProperties.getWifiFractionFrameDl());
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isWimaxFractionFrameUlChange()){
						Scheduler.wimaxConfig.setFractionFrameUl(configurationProperties.getWifiFractionFrameUl());
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isBandWidthChange()){
						Scheduler.wimaxConfig.setBandWidth(configurationProperties.getBandWidth());
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isCyclixPrefixChange()){
						Scheduler.wimaxConfig.setCyclixPrefix(configurationProperties.getCyclixPrefix());
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isSifsRifsTimeChange()){
						Scheduler.wifiConfig.setSifsRifs(configurationProperties.getSifsRifsTime());
						refreshWifiCapacity();
						
					}else if (configurationProperties.isBlockACKOnOffChange()){
						Scheduler.wifiConfig.setBlockAck(configurationProperties.getBlockACKOnOff());
						refreshWifiCapacity();
						
					}else if (configurationProperties.isNumPackagesChange()){
						Scheduler.wifiConfig.setNumPackages(configurationProperties.getNumPackages());
						refreshWifiCapacity();
						
					}else if (configurationProperties.isPackagesSizeChange()){
						Scheduler.wifiConfig.setPackagesSize(configurationProperties.getPackagesSize());
						refreshWifiCapacity();
						
					}else if(configurationProperties.isSenbilityWimaxChange()){
						
						configurationProperties.showInformationSuscriber(tableViewer, grandChildWimaxDL, grandChildWimaxUL,
																	grandChildWifiDL, grandChildWifiUL);
						refreshWimaxCapacity();
						
					}else if (configurationProperties.isMimoOrSensibilityWifiChange()){
						
						configurationProperties.showInformationSuscriber(tableViewer, grandChildWimaxDL, grandChildWimaxUL,
																	grandChildWifiDL, grandChildWifiUL);
						refreshWifiCapacity();
						
					}else if (configurationProperties.isRtsCapacityFlagChange()){
						configurationProperties.showInformationSuscriber(tableViewer, grandChildWimaxDL, grandChildWimaxUL,
																	grandChildWifiDL, grandChildWifiUL);
						refreshWifiCapacity();
						
					}else if (configurationProperties.isWifiFractionFrameDlChange()){
						Scheduler.wifiConfig.setFractionFrameDl(configurationProperties.getWifiFractionFrameDl());
						refreshWifiCapacity();
						
					}else if (configurationProperties.isWifiFractionFrameUlChange()){
						Scheduler.wifiConfig.setFractionFrameUl(configurationProperties.getWifiFractionFrameUl());
						refreshWifiCapacity();
					}
				}
			});
	}

	protected void refreshWimaxCapacity() {
		OpenFileAction.scheduler.wimaxScheduler();
		showWimaxCapacityInformation();
	}
	
	protected void refreshWifiCapacity() {
		OpenFileAction.scheduler.wifiScheduler();
		showWifiCapacityInformation();
	}

	private void createTechnologyUsedViewer(Composite parent) {
		GridData gridData = new GridData();
		gridData.widthHint = 700;
		gridData.heightHint = 200;
		parent.setLayoutData(gridData);
		parent.setLayout(new GridLayout(3, true));
		Tree wimaxTree = new Tree(parent, SWT.MULTI | SWT.FULL_SELECTION);
		createColumns(wimaxTree);
		fillTree(wimaxTree, "Wimax");

		Tree wifiTree = new Tree(parent, SWT.MULTI | SWT.FULL_SELECTION);
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
		Font boldFont = JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT);
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
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableViewer);
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(ModelProvider.INSTANCE.getSubscriber());
		// make the selection available to other views
		getSite().setSelectionProvider(tableViewer);

		// define layout for the viewer
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		gridData.horizontalSpan = 2;
		tableViewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return tableViewer;
	}

	// create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		
		int[] bounds = { 100, 100, 100, 100, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			//Devuelve la SNR en el UL
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return p.getSnr(false);
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			//Devuelve la SNR en el DL
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return p.getSnr(true);
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			//Devuelve la distancia entre una SS y una BS
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getDistance());
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			//Devuelve la potencia recibida en el UL
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getPotenciaRecibida_Ul());
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			//Devuelve la potencia recibida en el DL
			@Override
			public String getText(Object element) {
				Subscriber p = (Subscriber) element;
				return String.valueOf(p.getPotenciaRecibida_Dl());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
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
		tableViewer.getControl().setFocus();
	}

	public void clearTree() {
		grandChildWifiDL.setText("");
		grandChildWifiUL.setText("");
		grandChildWifiDL.setText(1,"");
		grandChildWifiUL.setText(1,"");
		grandChildWimaxDL.setText("");
		grandChildWimaxUL.setText("");
		grandChildWimaxDL.setText(1, "");
		grandChildWimaxUL.setText(1, "");
	}

	public void showWimaxCapacityInformation() {
		grandChildWimaxDL.setText(
				1,
				Float.toString(OpenFileAction.scheduler
						.getDlWimaxCapacity()) + " Kbps");
		grandChildWimaxUL.setText(
				1,
				Float.toString(OpenFileAction.scheduler
						.getUlWimaxCapacity()) + " Kbps");
	}
	
	public void showWifiCapacityInformation() {
		grandChildWifiDL.setText(
				1,
				Float.toString(OpenFileAction.scheduler
						.getDlWifiCapacity()) + " Kbps");
		grandChildWifiUL.setText(
				1,
				Float.toString(OpenFileAction.scheduler
						.getUlWifiCapacity()) + " Kbps");
	}
}
