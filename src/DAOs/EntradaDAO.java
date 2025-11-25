package DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import banco.DBConnection;
import model.CategoriaEntrada;



public class EntradaDAO {
	private DBConnection connection;
	
	public EntradaDAO() {
		this.connection = new DBConnection();
	}
	// lista as categorias de entrada existentes no banco
	public List<CategoriaEntrada> listarCategorias() {
		List<CategoriaEntrada> listaCategorias = new ArrayList<CategoriaEntrada>();
		
		String sql = "SELECT nome_categoria, codigo_categoria FROM categoria_entrada ORDER BY codigo_categoria";
		 try (Connection con = new DBConnection().getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                CategoriaEntrada cat = new CategoriaEntrada();
	                cat.setCod(rs.getInt("codigo_categoria"));
	                cat.setNome(rs.getString("nome_categoria"));
	                listaCategorias.add(cat);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return listaCategorias;
	}
	// insere a entrada no banco
}
