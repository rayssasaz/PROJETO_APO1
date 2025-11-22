package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.CBanner;

public class EntradaView {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EntradaView window = new EntradaView();
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
		shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
		shell.setSize(729, 443);
		shell.setText("SWT Application");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
		tabFolder.setBounds(40, 10, 633, 256);
		
		TabItem tbtmInserirEntrada = new TabItem(tabFolder, SWT.NONE);
		tbtmInserirEntrada.setText("Inserir Entrada");
		
		TabItem tbtmAgendarEntrada = new TabItem(tabFolder, SWT.NONE);
		tbtmAgendarEntrada.setText("Agendar Entrada");

	}
}
