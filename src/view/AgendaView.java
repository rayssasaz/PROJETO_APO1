package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import java.util.List;

import banco.DBConnection;
import daos.AgendaDAO;
import daos.EntradaDAO;
import daos.SaidaDAO;
import model.Agenda;
import model.CategoriaEntrada;
import model.CategoriaSaida;

public class AgendaView {

    protected Shell shlAgendamento; // Substitui JFrame
    
    // Componentes SWT
    private Combo cbTipo;
    private Combo cbCategoria;
    private Text txtValor;
    private Text txtDiaMes;
    private Text txtRepetir;
    private Button btnSalvar;

    private EntradaDAO entradaDAO;
    private SaidaDAO saidaDAO;
    private AgendaDAO agendaDAO;
    
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

	
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAgendamento.open();
		shlAgendamento.layout();
		while (!shlAgendamento.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

    
    protected void createContents() {
        shlAgendamento = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
        shlAgendamento.setSize(515, 337);
        shlAgendamento.setText("Agendamento");
        
        MessageBox caixaWarning = new MessageBox(shlAgendamento, SWT.OK);
        MessageBox caixaSucessfull = new MessageBox(shlAgendamento, SWT.OK);
     
        DBConnection db = new DBConnection();
        entradaDAO = new EntradaDAO(); 
        saidaDAO = new SaidaDAO(); 
        agendaDAO = new AgendaDAO(db.getConnection()); 
        
       

        // --- Tipo ---
        Label lblTipo = new Label(shlAgendamento, SWT.NONE);
        lblTipo.setBounds(20, 20, 100, 25);
        lblTipo.setText("Tipo:");

        cbTipo = new Combo(shlAgendamento, SWT.READ_ONLY);
        cbTipo.setItems(new String[] {"Entrada", "Saída"});
        cbTipo.setBounds(150, 20, 200, 25);
       

        // --- Valor ---
        Label lblValor = new Label(shlAgendamento, SWT.NONE);
        lblValor.setBounds(20, 60, 100, 25);
        lblValor.setText("Valor:");

        txtValor = new Text(shlAgendamento, SWT.BORDER);
        txtValor.setBounds(150, 60, 200, 25);
       
        // --- Categoria ---
        Label lblCategoria = new Label(shlAgendamento, SWT.NONE);
        lblCategoria.setBounds(20, 100, 100, 25);
        lblCategoria.setText("Categoria:");

        cbCategoria = new Combo(shlAgendamento, SWT.READ_ONLY);
        cbCategoria.setBounds(150, 100, 200, 25);
    

        // --- Dia do mês ---
        Label lblDia = new Label(shlAgendamento, SWT.NONE);
        lblDia.setBounds(20, 140, 100, 25);
        lblDia.setText("Dia do mês:");

        txtDiaMes = new Text(shlAgendamento, SWT.BORDER);
        txtDiaMes.setBounds(150, 140, 200, 25);
      
        // --- Repetir (meses) ---
        Label lblRepetir = new Label(shlAgendamento, SWT.NONE);
        lblRepetir.setBounds(20, 180, 120, 25);
        lblRepetir.setText("Repetir (meses):");

        txtRepetir = new Text(shlAgendamento, SWT.BORDER);
        txtRepetir.setBounds(150, 180, 200, 25);
    
        // --- Botão Salvar ---
        btnSalvar = new Button(shlAgendamento, SWT.PUSH);
        btnSalvar.setBounds(150, 225, 100, 35);
        btnSalvar.setText("Salvar");

        
        
        // Inicializa categorias com "Entrada"
        atualizarCategorias("Entrada");
        
        
        cbTipo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                atualizarCategorias(cbTipo.getText()); // Usa getText() para SWT Combo
            }
        });
        
      
        btnSalvar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               // salvarAgendamento();
            	try {
                    // Resgate do CÓDIGO da Categoria (necessário para o SWT Combo ja que so armazena string)
                    String tipoSelecionado = cbTipo.getText();
                    String nomeSelecionado = cbCategoria.getText();
                    String valorString = txtValor.getText();
                    String diaString = txtDiaMes.getText();
                    String repetirString = txtRepetir.getText();
                    Integer codigoCategoria = null;
                    if (tipoSelecionado == null || tipoSelecionado.isEmpty()) {
	            			caixaWarning.setMessage("Nenhum tipo de movimentação selecionado.");
	            			caixaWarning.open();
	            			return;
                    }
                    if (nomeSelecionado == null || nomeSelecionado.isEmpty()) {
                			caixaWarning.setMessage("Nenhuma categoria selecionada.");
                			caixaWarning.open();
                			return;
                    }
                    if(valorString.isEmpty()){
	                    	caixaWarning.setMessage("Nenhum valor inserido.");
	            			caixaWarning.open();
	            			return;
                    }
                    if(diaString.isEmpty()){
	                    	caixaWarning.setMessage("Nenhum dia inserido.");
	            			caixaWarning.open();
	            			return;
                    }
                    if(repetirString.isEmpty()){
	                    	caixaWarning.setMessage("Insira a quantidade de repetições.");
	            			caixaWarning.open();
	            			return;
                    }
                    if (tipoSelecionado.equals("Entrada")) {
                        // Rebusca a lista e encontra o objeto CategoriaEntrada pelo nome
                        List<CategoriaEntrada> lista = entradaDAO.listarCategorias();
                        for (CategoriaEntrada c : lista) {
                            if (c.getNome().equals(nomeSelecionado)) {
                            		 codigoCategoria = c.getCod();
                            }
                        }
                    } else {
                        // Rebusca a lista e encontra o objeto CategoriaSaida pelo nome
                        List<CategoriaSaida> lista = saidaDAO.listarCategoriasSaida();
                        for (CategoriaSaida c : lista) {
                            if (c.getNome().equals(nomeSelecionado)) {
                            		 codigoCategoria = c.getCod();                      
                            }
                        }
                    }
                    
                    Agenda ag = new Agenda();

                    ag.setTipo(cbTipo.getText()); // SWT
                    ag.setValor(Double.parseDouble(txtValor.getText()));
                    ag.setDia(Integer.parseInt(txtDiaMes.getText()));
                    ag.setRepetirMeses(Integer.parseInt(txtRepetir.getText()));
                    ag.setUsuarioId(1); // fixo por enquanto
                    ag.setCategoriaId(codigoCategoria);
                    
                    agendaDAO.inserir(ag);
                    
                    caixaSucessfull.setMessage("Agendamento inserido com sucesso");
                    caixaSucessfull.open();    
                    
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (Exception alt) {
					// TODO Auto-generated catch block
					alt.printStackTrace();
				}
            }
        });
    } // fim do create contents
    
    // FUNÇÃO AUXILIAR PRA LISTAR AS CATEGORIAS DE ENTRADA E SAIDA
    private void atualizarCategorias(String tipo) {
        cbCategoria.removeAll(); 
        
        if (tipo.equals("Entrada")) {
            // o combo swt so armazena nome
        		List<CategoriaEntrada> lista = entradaDAO.listarCategorias();
            for (CategoriaEntrada c : lista) {
                cbCategoria.add(c.getNome()); 
            }
            
        } else {
            List<CategoriaSaida> lista = saidaDAO.listarCategoriasSaida();
            for (CategoriaSaida c : lista) {
                cbCategoria.add(c.getNome());
            }
        }
       // cbCategoria.select(0); // Seleciona o primeiro item
    }
}