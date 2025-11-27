package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.widgets.Text;

import DAOs.EntradaDAO;
import model.CategoriaEntrada;
import model.Entrada;

import org.eclipse.swt.widgets.MessageBox;

public class EntradaView {

	protected Shell shlInserirEntrada;
	private LocalResourceManager localResourceManager;
	private Text textInserirValor;
	private 	java.util.List<CategoriaEntrada> categorias;

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
		
		// criando objetos de MessageBOX
		MessageBox caixaWarning = new MessageBox(shlInserirEntrada, SWT.OK);
		
		Label lblValorEntrada = new Label(shlInserirEntrada, SWT.NONE);
		lblValorEntrada.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 15, SWT.NORMAL)));
		lblValorEntrada.setBounds(40, 40, 70, 33);
		lblValorEntrada.setText("Valor");
		
		textInserirValor = new Text(shlInserirEntrada, SWT.BORDER);
		textInserirValor.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textInserirValor.setBounds(176, 40, 174, 33);
		
		EntradaDAO dao = new EntradaDAO();
		this.categorias = dao.listarCategorias()	;	
		
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
		
		Button btnInserirEntrada = new Button(shlInserirEntrada, SWT.NONE);
		btnInserirEntrada.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.BOLD)));
		btnInserirEntrada.setBounds(329, 204, 185, 45);
		btnInserirEntrada.setText("Inserir Entrada");
		
		btnInserirEntrada.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					// pega o valor digitado para converter em float
					String valorString = textInserirValor.getText();
					
					// pega o indice do item selecionado no combo
					int indiceSelecionado = comboCategoria.getSelectionIndex();
				
					
					// Verifica se o valor e a categoria foram selecionados/preenchidos
			        if (valorString.isEmpty()) {
			            caixaWarning.setMessage("Por favor, preencha o valor.");
			            caixaWarning.open();
			            return;
			        }
			        if (indiceSelecionado == -1) {
			        		caixaWarning.setMessage("Por favor, selecione uma categoria.");
			        		caixaWarning.open();
			            return;
			        }
					Double valor = Double.parseDouble(valorString.replace(",", "."));
					// usa o indice para obter o objeto de CategoriaEntrada 
					CategoriaEntrada categoriaSelecionada = categorias.get(indiceSelecionado);
					
					int codCategoria = categoriaSelecionada.getCod();
					//String nomeCategoria = categoriaSelecionada.getNome(); // usa pra nada
					
					Entrada novaEntrada = new Entrada(valor, codCategoria);
					
					dao.inserirEntrada(novaEntrada);
					
				}
				catch(NumberFormatException ex){
					ex.printStackTrace();
				}
			}
			
			
		});
			

	}
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),shlInserirEntrada);
	}
}
