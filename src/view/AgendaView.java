package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;

public class AgendaView {

	protected Shell shell;
	private LocalResourceManager localResourceManager;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AgendaView window = new AgendaView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		createResourceManager();
		shell.setSize(627, 352);
		shell.setText("SWT Application");
		
		Label lblValor = new Label(shell, SWT.NONE);
		lblValor.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblValor.setBounds(35, 28, 118, 42);
		lblValor.setText("Valor");
		
		Label lblCategoria = new Label(shell, SWT.NONE);
		lblCategoria.setText("Categoria");
		lblCategoria.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblCategoria.setBounds(35, 76, 118, 43);
		
		Label lblData = new Label(shell, SWT.NONE);
		lblData.setText("Data");
		lblData.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblData.setBounds(35, 125, 118, 42);
		
		text = new Text(shell, SWT.BORDER);
		text.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 14, SWT.NORMAL)));
		text.setBounds(177, 27, 151, 21);
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(177, 84, 151, 23);
		
		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		dateTime.setBounds(177, 131, 151, 22);
		
		Button btnAgendarEntrada = new Button(shell, SWT.NONE);
		btnAgendarEntrada.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.BOLD)));
		btnAgendarEntrada.setBounds(355, 207, 203, 54);
		btnAgendarEntrada.setText("Agendar Entrada");

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
	}

}
