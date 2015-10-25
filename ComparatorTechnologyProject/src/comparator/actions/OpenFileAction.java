package comparator.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

import comparator.scheduler.Auxiliary;
import comparatortechnologyproject.Activator;
import comparatortechnologyproject.ApplicationWorkbenchWindowAdvisor;
import comparatortechnologyproject.ICommandIds;
import comparatortechnologyproject.NavigationView;

public class OpenFileAction extends Action {

	private final IWorkbenchWindow window;
	// Lista de redes definidas en el fichero de RadioMobile
	private String[] netList = new String[0];

	public OpenFileAction(IWorkbenchWindow window, String label) {
		super(label);
		this.window = window;
		setId(ICommandIds.CMD_OPEN_FILE);
		setActionDefinitionId(ICommandIds.CMD_OPEN_FILE);

		setImageDescriptor(Activator.getImageDescriptor("/icons/Open.png"));
	}

	public void run() {

		Shell shell = window.getShell();
		FileDialog fileDialog = new FileDialog(shell);
		String firstFile = fileDialog.open();

		if (firstFile == null) {
			return;
		}

		// Cargamos informaci�n del fichero report
		try {
			netList = ApplicationWorkbenchWindowAdvisor.scheduler
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
				ApplicationWorkbenchWindowAdvisor.scheduler.loadNetFile(firstFile, netName);

			} catch (Exception e) {
				Auxiliary.showErrorMessage(shell,
						Messages.ShowErrorToLoadInformationNetMessage);
			}

			// Actualiza la informaci�n mostrada
			refreshNodes();
		}
	}

	private void refreshNodes() {

		NavigationView view = new NavigationView();
		view.clearSubscriberNodes();
		view.setSubscriberNodes(ApplicationWorkbenchWindowAdvisor.scheduler.getSubscriberNodes());
		view.setBaseStation(ApplicationWorkbenchWindowAdvisor.scheduler.getBaseStation());
		NavigationView._viewer.setInput(view.createDummyModel());
	}
}