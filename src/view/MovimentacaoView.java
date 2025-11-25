package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class MovimentacaoView {

	protected Shell shell;
	private LocalResourceManager localResourceManager;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MovimentacaoView window = new MovimentacaoView();
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
		shell.setSize(732, 455);
		shell.setText("SWT Application");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.BOLD)));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(40, 25, 185, 55);
		btnNewButton.setText("Consultar Extrato");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(40, 112, 648, 280);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnTipo = new TableColumn(table, SWT.NONE);
		tblclmnTipo.setWidth(163);
		tblclmnTipo.setText("Tipo");
		
		TableColumn tblclmnValor = new TableColumn(table, SWT.NONE);
		tblclmnValor.setWidth(159);
		tblclmnValor.setText("Valor");
		
		TableColumn tblclmnData = new TableColumn(table, SWT.NONE);
		tblclmnData.setWidth(151);
		tblclmnData.setText("Data");
		
		TableColumn tblclmnCategoria = new TableColumn(table, SWT.NONE);
		tblclmnCategoria.setWidth(238);
		tblclmnCategoria.setText("Categoria");

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
	}
}
