package comparatortechnologyproject;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import comparator.actions.OpenFileAction;

/**
 * Se encarga de crear todas las acciones 
 * @author Karla
 *
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
	public static IWorkbenchAction preferenceAction;
	private OpenFileAction openFileAction;
    

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    
    protected void makeActions(final IWorkbenchWindow window) {

        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(newWindowAction);
        
        preferenceAction = ActionFactory.PREFERENCES.create(window);
        register(preferenceAction);
        
        openFileAction = new OpenFileAction(window, "Abrir Archivo");
        register(openFileAction);
    }
    
    protected void fillMenuBar(IMenuManager menuBar) {
    	
        MenuManager fileMenu = new MenuManager("&Archivo", IWorkbenchActionConstants.M_FILE);
        MenuManager helpMenu = new MenuManager("&Ayuda", IWorkbenchActionConstants.M_HELP);
        MenuManager toolMenu = new MenuManager("&Configuración", IWorkbenchActionConstants.M_WINDOW);
        
        menuBar.add(fileMenu);
        // Add a group marker indicating where action set menus will appear.
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(toolMenu);
        menuBar.add(helpMenu);
        
        // File
        fileMenu.add(openFileAction);
        fileMenu.add(newWindowAction);
        fileMenu.add(new Separator());
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        
        //Configuration
        toolMenu.add(preferenceAction);
        
        // Help
        helpMenu.add(aboutAction);
    }
    
    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "main"));  
        toolbar.add(openFileAction);
    }
}
