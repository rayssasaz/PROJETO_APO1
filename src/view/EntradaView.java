package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Text;

import DAOs.EntradaDAO;
import model.CategoriaEntrada;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.DateTime;

public class EntradaView {

	protected Shell shlInserirEntrada;
	private LocalResourceManager localResourceManager;
	private Text text;

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
		shlInserirEntrada.open();
		shlInserirEntrada.layout();
		while (!shlInserirEntrada.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlInserirEntrada = new Shell();
		createResourceManager();
		shlInserirEntrada.setSize(555, 325);
		shlInserirEntrada.setText("Inserir Entrada");
		
		Label lblValorEntrada = new Label(shlInserirEntrada, SWT.NONE);
		lblValorEntrada.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblValorEntrada.setBounds(40, 40, 70, 33);
		lblValorEntrada.setText("Valor");
		
		text = new Text(shlInserirEntrada, SWT.BORDER);
		text.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 14, SWT.NORMAL)));
		text.setBounds(176, 40, 174, 33);
		
		EntradaDAO dao = new EntradaDAO();
		java.util.List<CategoriaEntrada> categorias = dao.listarCategorias()	;	
		
		// cria um array de String para armazenar apenas os nomes das categorias:
		String[] nomesCategorias = new String[categorias.size()];
		
		// itera sobre a lista de objetos CategoriaEntrada e preenche o array:
		for (int i = 0; i < categorias.size(); i++) {
		    nomesCategorias[i] = categorias.get(i).getNome();
		}

		Combo comboCategoria = new Combo(shlInserirEntrada, SWT.NONE);
		comboCategoria.setItems(nomesCategorias);
		comboCategoria.setBounds(176, 112, 174, 25);
		comboCategoria.setText("Selecione a categoria");
		
		Label lblCategoriaEntrada = new Label(shlInserirEntrada, SWT.NONE);
		lblCategoriaEntrada.setText("Categoria");
		lblCategoriaEntrada.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblCategoriaEntrada.setBounds(40, 104, 114, 33);
		
		Button btnNewButton = new Button(shlInserirEntrada, SWT.NONE);
		btnNewButton.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.BOLD)));
		btnNewButton.setBounds(329, 204, 185, 45);
		btnNewButton.setText("Inserir Entrada");
		

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shlInserirEntrada);
	}
}
