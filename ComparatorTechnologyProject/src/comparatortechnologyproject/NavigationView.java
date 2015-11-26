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

import comparator.scheduler.Scheduler;
import comparator.scheduler.Subscriber;

public class NavigationView extends ViewPart {
	
	public static final String DEFAULT_NODE_NAME="BaseStation";
	public static final String ID = "ComparatorTechnologyProject.navigationView";
	
	private static Subscriber _selectedSubscriber = null;
	private TreeViewer treeViewer;
	public static Scheduler scheduler;
	
	public NavigationView(){
		scheduler = new Scheduler();
	}
	 
	
    /**
     * We will set up a dummy model to initialize tree heararchy. In real
     * code, you will connect to a real model and expose its hierarchy.
     */
    public TreeObject createDummyModel() {
    	TreeParent rootNode = new TreeParent(new Subscriber(DEFAULT_NODE_NAME));
    	ArrayList<Subscriber> subscriberList = scheduler.getSubscriberNodes();
    	if (!subscriberList.isEmpty()){
    		rootNode = new TreeParent(scheduler.getBaseStation());
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
			for (IViewReference view : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences()){
				if (view.getView(true) instanceof InformationView){
					Subscriber baseStation = scheduler.getBaseStation();
					InformationView informationView = (InformationView) view.getView(true);
					if ((!_selectedSubscriber.getName().equals(DEFAULT_NODE_NAME)) && (baseStation != null && 
							!_selectedSubscriber.getName().equals(scheduler.getBaseStation().getName()))){
						
						InformationView.configurationProperties.showInformationSuscriber(informationView.tableViewer, 
								InformationView.grandChildWimaxDL, InformationView.grandChildWimaxUL, 
								InformationView.grandChildWifiDL, InformationView.grandChildWifiUL);
						
						InformationView.wimaxCapacity.wimaxScheduler(scheduler);
						InformationView.wifiCapacity.wifiScheduler(scheduler);
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

	public TreeViewer getViewer(){
		return treeViewer;
	}

	public void clearSubscriberNodes() {
		scheduler.getSubscriberNodes().clear();
	}

	public static Subscriber getSelectedSubscriber(){
		return _selectedSubscriber;
	}

	public Scheduler getScheduler(){
		return scheduler;
	}
}