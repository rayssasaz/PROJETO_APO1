package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import banco.DBConnection;
import model.Movimentacao;

public class MovimentacaoDAO {
	private DBConnection connection;
	
	public MovimentacaoDAO() {
		this.connection = new DBConnection();
	}
	
	public List<Movimentacao> listarMovimentacoes(){
		List<Movimentacao> extrato = new ArrayList<Movimentacao>();
		 String tabela = null;
	     String coluna = null;
	     String nomeCategoria = null;
	        
		String sql = "SELECT tipo, valor, data_movimentacao, cod_categoria FROM movimentacoes where cod_usuario_mov = 1";
		try  {
			Connection con = connection.getConnection(); 
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(); 
            
         //	PreparedStatement statement = connection.getConnection().prepareStatement(sql);
         //	ResultSet rs = statement.executeQuery();
            while (rs != null && rs.next()) {
                Movimentacao mov = new Movimentacao();
                String tipo = rs.getString("tipo"); // Pega o tipo para a busca
                int codCategoria = rs.getInt("cod_categoria");
                mov.setTipo(rs.getString("tipo"));
                mov.setData(rs.getDate("data_movimentacao"));
                mov.setValor(rs.getDouble("valor"));
                
             // Busca o NOME da categoria 

	    	        if (tipo.equals("entrada")) { 	    	        		
	    	            tabela = "categoria_entrada";
	    	            coluna = "codigo_categoria";
	    	        } else if (tipo.equals("saida")) {
	    	            tabela = "categoria_saida";
	    	            coluna = "codigo_categoria";
	    	        } 
	                
	    	        String sql2 = "SELECT nome_categoria FROM " + tabela + " WHERE " + coluna + " = ?";
	    	        try {
	    	        	
	    	        		PreparedStatement stmt = con.prepareStatement(sql2);
	    	        		stmt.setInt(1, codCategoria);	    	        		
	    	            ResultSet rs2 = stmt.executeQuery(); // result set secundario: executa a query para recuperar o nome
	    	            	    	    	        		
	    	            while(rs2 != null && rs2.next()) {
	    	            		nomeCategoria = rs2.getString("nome_categoria");	    	            	
	    	            }
	    	        } catch (SQLException e) {
	    	            e.printStackTrace();
	    	        }    
	            	    	        
                mov.setCategoria(nomeCategoria);                      
                extrato.add(mov);
                
            }
            return extrato;
	     } 
		 catch (SQLException e) {
	            e.printStackTrace();
	     }
		
		return null;
	}
	
}
