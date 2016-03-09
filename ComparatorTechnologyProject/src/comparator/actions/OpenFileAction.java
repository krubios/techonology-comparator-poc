package comparator.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import comparator.scheduler.Auxiliary;
import comparatortechnologyproject.Activator;
import comparatortechnologyproject.ICommandIds;
import comparatortechnologyproject.NavigationView;

public class OpenFileAction extends Action {

	private final IWorkbenchWindow window;
	// Lista de redes definidas en el fichero de RadioMobile
	private String[] netList = new String[0];
	private static Shell shell;
	

	public OpenFileAction(IWorkbenchWindow window, String label) {
		
		super(label);
		this.window = window;
		setId(ICommandIds.CMD_OPEN_FILE);
		setActionDefinitionId(ICommandIds.CMD_OPEN_FILE);
		setImageDescriptor(Activator.getImageDescriptor("/icons/Open.png"));
	}

	public void run() {
		shell = window.getShell();
		FileDialog fileDialog = new FileDialog(shell);
		String firstFile = fileDialog.open();

		if (firstFile == null) {
			return;
		}

		// Cargamos información del fichero report
		try {
			netList = NavigationView.scheduler
					.loadNet(firstFile);
		} catch (Exception e) {
			Auxiliary.showErrorMessage(shell,
					Messages.ShowErroToReadFilerMessage);
		}

		if (netList.length == 0) {
			Auxiliary.showErrorMessage(shell,
					Messages.ShowErrorToreadNetMessages);
			

		} else {

			ComboDialog.prompt(shell, Messages.SelectNetMessage,
					Messages.SelectImporteNetMessage, netList);
			String netName = ComboDialog.getNetName();
			try {
				NavigationView.scheduler.loadNetFile(firstFile, netName);

			} catch (Exception e) {
				Auxiliary.showErrorMessage(shell,
						Messages.ShowErrorToLoadInformationNetMessage);
			}

			// Actualiza la información mostrada
			refreshNodes();
		}
	}

	private void refreshNodes() {
		
		for (IViewReference view : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences())
		{
			if (view.getView(true) instanceof NavigationView)
			{
				NavigationView navigationView = (NavigationView) view.getView(true);
				navigationView.clearSubscriberNodes();
				navigationView.getViewer().setInput(navigationView.createDummyModel());
			}
		}
	}
	
	public static Shell getShell(){
		return shell;
	}
}
