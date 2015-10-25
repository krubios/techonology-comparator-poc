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
import org.eclipse.ui.part.ViewPart;

import comparator.scheduler.FileManager;
import comparator.scheduler.Subscriber;

public class NavigationView extends ViewPart {
	
	public static final String DEFAULT_NODE_NAME="BaseStation";
	public static ArrayList<Subscriber> SUBSCRIBER = new ArrayList<Subscriber>();
	public static Subscriber BASESTATION;
	public static final String ID = "ComparatorTechnologyProject.navigationView";
	
	public static Subscriber _selectedSubscriber = null;
	public static TreeViewer _viewer;
	 
	
    /**
     * We will set up a dummy model to initialize tree heararchy. In real
     * code, you will connect to a real model and expose its hierarchy.
     */
    public TreeObject createDummyModel() {
    	TreeParent rootNode = new TreeParent(new Subscriber(DEFAULT_NODE_NAME));
    	if (!SUBSCRIBER.isEmpty()){
    		rootNode = new TreeParent(BASESTATION);
	        for (Subscriber subscriber: SUBSCRIBER){
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
		_viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		_viewer.setContentProvider(new ViewContentProvider());
		_viewer.setLabelProvider(new ViewLabelProvider());
		_viewer.setInput(createDummyModel());
		
		_viewer.addSelectionChangedListener(new ISelectionChangedListener() 
		{
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				showSelectedInformation(arg0.getSelection());
				
			}
		});
		
		setViewer(_viewer);
		createContextMenu();
		_viewer.expandAll();
	}

	private void createContextMenu() {
		 MenuManager contextMenu = new MenuManager("#PopUp");
		 contextMenu.add(new Separator("additions"));
		 contextMenu.setRemoveAllWhenShown(true);
		 
		 contextMenu.addMenuListener(new IMenuListener() {
                 public void menuAboutToShow(IMenuManager manager) {
                	 TreeSelection sel = (TreeSelection) _viewer.getSelection();
                	 Subscriber subs = ((TreeObject)sel.getFirstElement()).getSubscriber();
                	 if (subs != null){
                		 manager.add(ApplicationActionBarAdvisor.preferenceAction);
                	 }
                 }
         });
         
         Menu menu = contextMenu.createContextMenu(_viewer.getControl());
         _viewer.getControl().setMenu(menu);
         
         getSite().registerContextMenu(contextMenu, _viewer);
	}
	
	

	private void showSelectedInformation(ISelection selection) {
		TreeSelection sel = (TreeSelection) selection;
		if (sel != null & sel.getFirstElement() != null)
			_selectedSubscriber = ((TreeObject)sel.getFirstElement()).getSubscriber();
			Subscriber BS = FileManager.getBaseStation();
			if ((!_selectedSubscriber.getName().equals(DEFAULT_NODE_NAME)) && (BS != null && !_selectedSubscriber.getName().equals(BS.getName()))){
				InformationView.showInformationSuscriber(_selectedSubscriber);		
				ApplicationWorkbenchWindowAdvisor.scheduler.wimaxScheduler();
				ApplicationWorkbenchWindowAdvisor.scheduler.wifiScheduler();
				InformationView.showWimaxCapacityInformation();
				InformationView.showWifiCapacityInformation();
				
			}else{
				InformationView.getViewer().getTable().clearAll();
				ModelProvider.INSTANCE.clearAll();
				InformationView.clearTree();
			}
	}

	private void setViewer(TreeViewer viewer) {
		_viewer = viewer;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		_viewer.getControl().setFocus();
	}

	public void setSubscriberNodes(ArrayList<Subscriber> subscriberNodes) {
		SUBSCRIBER.addAll(subscriberNodes);
	}
	
	public TreeViewer getViewer(){
		return _viewer;
	}

	public void setBaseStation(Subscriber baseStation){
		BASESTATION = baseStation;
	}

	public void clearSubscriberNodes() {
		SUBSCRIBER.clear();
	}

	public static Subscriber getSelectedSubscriber(){
		return _selectedSubscriber;
	}
}