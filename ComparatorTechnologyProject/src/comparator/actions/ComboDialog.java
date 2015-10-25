package comparator.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import comparator.scheduler.Messages;

public class ComboDialog 
{
	public enum Resultado {
		OK, CANCEL
	}
	
	public static class PromptResult {
		public static String value;
		public Resultado result = Resultado.CANCEL;
	}

	public static PromptResult prompt(Shell parent, String title, String message, String[] netList) {
		final Shell dialog = new Shell(parent, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		final PromptResult result = new PromptResult();
	
		dialog.setText(title);
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout(formLayout);
	
		Label label = new Label(dialog, SWT.NONE);
		label.setText(message);
		FormData data = new FormData();
		label.setLayoutData(data);
	
		Button cancel = new Button(dialog, SWT.PUSH);
		cancel.setText("Cancelar");
		data = new FormData();
		data.width = 60;
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, 0);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				result.result = Resultado.CANCEL;
				dialog.close();
			}
		});
	
	
		final Combo combo = new Combo(dialog, SWT.READ_ONLY | SWT.BORDER);
		data= new FormData();
		data.width = 200;
		data.left = new FormAttachment(label, 0, SWT.DEFAULT);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(label, 0, SWT.CENTER);
		data.bottom = new FormAttachment(cancel, 0, SWT.DEFAULT);
		combo.setLayoutData(data);
		combo.setItems(netList);
		combo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e)
			{
				PromptResult.value = combo.getItem(combo.getSelectionIndex());
			}
		});
		
		Button ok = new Button(dialog, SWT.PUSH);
		ok.setText(Messages.ComboAceptMessage);
		data = new FormData();
		data.width = 60;
		data.right = new FormAttachment(cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment(100, 0);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				result.result = Resultado.OK;
				dialog.close();
			}
		});
	
		dialog.setDefaultButton(ok);
		dialog.pack();
		dialog.open();
	
		while (!dialog.isDisposed()) {
			if (!dialog.getDisplay().readAndDispatch())
				dialog.getDisplay().sleep();
		}
	
		return result;
	}
	
	public static String getNetName()
	{
		return PromptResult.value;
	}

}
