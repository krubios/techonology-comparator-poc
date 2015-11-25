package comparatortechnologyproject;

import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import comparator.actions.OpenFileAction;
import comparator.scheduler.FileManager;
import comparator.scheduler.Subscriber;

public class NavigationView extends ViewPart {
	
	public static final String DEFAULT_NODE_NAME="BaseStation";
	private ArrayList<Subscriber> subscriberList = new ArrayList<Subscriber>();
	private Subscriber baseStation;
	public static final String ID = "ComparatorTechnologyProject.navigationView";
	
	public static Subscriber _selectedSubscriber = null;
	private TreeViewer treeViewer;
	 
	
    /**
     * We will set up a dummy model to initialize tree heararchy. In real
     * code, you will connect to a real model and expose its hierarchy.
     */
    public TreeObject createDummyModel() {
    	TreeParent rootNode = new TreeParent(new Subscriber(DEFAULT_NODE_NAME));
    	if (!subscriberList.isEmpty()){
    		rootNode = new TreeParent(baseStation);
	        for (Subscriber subscriber: subscriberList){
	        	TreeObject to1 = new TreeObject(subscriber);
	        	rootNode.addChild(to1);
	        }
    	}
        TreeParent parent = new TreeParent(new Subscriber(""));
        parent.addChild(rootNode);
        return parent;
    }

	/**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setLabelProvider(new ViewLabelProvider());
		treeViewer.setInput(createDummyModel());
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() 
		{
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				showSelectedInformation(arg0.getSelection());
			}
		});
		
		setViewer(treeViewer);
		createContextMenu();
		treeViewer.expandAll();
	}

	private void createContextMenu() {
		 MenuManager contextMenu = new MenuManager("#PopUp");
		 contextMenu.add(new Separator("additions"));
		 contextMenu.setRemoveAllWhenShown(true);
		 
		 contextMenu.addMenuListener(new IMenuListener() {
                 public void menuAboutToShow(IMenuManager manager) {
                	 TreeSelection sel = (TreeSelection) treeViewer.getSelection();
                	 Subscriber subs = ((TreeObject)sel.getFirstElement()).getSubscriber();
                	 if (subs != null){
                		 manager.add(ApplicationActionBarAdvisor.preferenceAction);
                	 }
                 }
         });
         
         Menu menu = contextMenu.createContextMenu(treeViewer.getControl());
         treeViewer.getControl().setMenu(menu);
         
         getSite().registerContextMenu(contextMenu, treeViewer);
	}
	
	

	private void showSelectedInformation(ISelection selection) {
		
		TreeSelection sel = (TreeSelection) selection;
		if (sel != null & sel.getFirstElement() != null){
			_selectedSubscriber = ((TreeObject)sel.getFirstElement()).getSubscriber();
			Subscriber BS = FileManager.getBaseStation();
			for (IViewReference view : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences()){
				if (view.getView(true) instanceof InformationView){
					InformationView informationView = (InformationView) view.getView(true);
					if ((!_selectedSubscriber.getName().equals(DEFAULT_NODE_NAME)) && (BS != null && 
							!_selectedSubscriber.getName().equals(BS.getName()))){
						
						InformationView.configurationProperties.showInformationSuscriber(informationView.tableViewer, 
								InformationView.grandChildWimaxDL, InformationView.grandChildWimaxUL, 
								InformationView.grandChildWifiDL, InformationView.grandChildWifiUL);
						
						OpenFileAction.scheduler.wimaxScheduler();
						OpenFileAction.scheduler.wifiScheduler();
						informationView.showWimaxCapacityInformation();
						informationView.showWifiCapacityInformation();
						
					}else{
						
						informationView.getViewer().getTable().clearAll();
						ModelProvider.INSTANCE.clearAll();
						informationView.clearTree();
					}
				}
			}	
		}
	}

	private void setViewer(TreeViewer viewer) {
		treeViewer = viewer;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	public void setSubscriberNodes(ArrayList<Subscriber> subscriberNodes) {
		subscriberList.addAll(subscriberNodes);
	}
	
	public TreeViewer getViewer(){
		return treeViewer;
	}

	public void setBaseStation(Subscriber baseStation){
		this.baseStation = baseStation;
	}

	public void clearSubscriberNodes() {
		subscriberList.clear();
	}

	public static Subscriber getSelectedSubscriber(){
		return _selectedSubscriber;
	}
}