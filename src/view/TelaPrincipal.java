package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.*;

public class TelaPrincipal {

	protected Shell shlTelaPrincipal;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaPrincipal window = new TelaPrincipal();
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
		shlTelaPrincipal.open();
		shlTelaPrincipal.layout();
		while (!shlTelaPrincipal.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlTelaPrincipal = new Shell();
		shlTelaPrincipal.setSize(450, 300);
		shlTelaPrincipal.setText("SWT Application");
		
		Button btnAbrirEntradaView = new Button(shlTelaPrincipal, SWT.NONE);
		btnAbrirEntradaView.setBounds(50, 50, 160, 30);
		btnAbrirEntradaView.setText("Inserir Nova Entrada");
		
		
		btnAbrirEntradaView.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EntradaView entradaWindow = new EntradaView();
				entradaWindow.open(); // abre a janela inserir entrada
			}
			
		});
		
		Button btnAbrirAgendaView = new Button(shlTelaPrincipal, SWT.NONE);
		btnAbrirAgendaView.setBounds(50, 115, 160, 30);
		btnAbrirAgendaView.setText("Inserir Agendamento");
		
		btnAbrirAgendaView.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AgendaView agendaWindow = new AgendaView();
				agendaWindow.open(); // abre a janela agendar entrada
				
			}
			
		});
		
		Button btnAbrirMovimentacaoView = new Button(shlTelaPrincipal, SWT.NONE);
		btnAbrirMovimentacaoView.setBounds(50, 180, 160, 30);
		btnAbrirMovimentacaoView.setText("Consultar Extrato");
		
		btnAbrirMovimentacaoView.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MovimentacaoView extratoWindow = new MovimentacaoView();
				extratoWindow.open(); // abre a janela consultar extrato
				
			}
			
		});
	}
}
