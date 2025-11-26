package DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import banco.DBConnection;
import model.CategoriaEntrada;
import model.Entrada;



public class EntradaDAO {
	private DBConnection connection;
	
	public EntradaDAO() {
		this.connection = new DBConnection();
	}
	// lista as categorias de entrada existentes no banco
	public List<CategoriaEntrada> listarCategorias() {
		List<CategoriaEntrada> listaCategorias = new ArrayList<CategoriaEntrada>();
		
		String sql = "SELECT nome_categoria, codigo_categoria FROM categoria_entrada ORDER BY codigo_categoria";
		 try  {
             	PreparedStatement statement = connection.getConnection().prepareStatement(sql);
             	ResultSet rs = statement.executeQuery();
	            while (rs != null && rs.next()) {
	                CategoriaEntrada cat = new CategoriaEntrada();
	                cat.setCod(rs.getInt("codigo_categoria"));
	                cat.setNome(rs.getString("nome_categoria"));
	                listaCategorias.add(cat);
	            }
	            return listaCategorias;
	     } 
		 catch (SQLException e) {
	            e.printStackTrace();
	     }
		 return null;
	        
	}
	// insere a entrada no banco
	public void inserirEntrada(Entrada entrada) {
		try {
			String sql = "call inserir_entrada(?, ?, 1)"; // o usuario inserido no banco tem cod = 1
			PreparedStatement statement = connection.getConnection().prepareStatement(sql);
			statement.setDouble(1, entrada.getValor());
			statement.setInt(2, entrada.getCategoria()); // categoria mudada pra Integer //se deixar String tem que mudar a procedure
			statement.execute();
			statement.close();
			
		}
		catch(SQLException u){
			throw new RuntimeException(u);     
		}
	}
}
