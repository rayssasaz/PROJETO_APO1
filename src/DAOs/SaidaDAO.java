package daos;
import java.sql.*;
import java.util.*;

import banco.DBConnection;
import model.CategoriaSaida;

public class SaidaDAO {
	private DBConnection connection;
	
	public SaidaDAO() {
		this.connection = new DBConnection();
	}
	
	public List<CategoriaSaida> listarCategoriasSaida() {
        List<CategoriaSaida> lista = new ArrayList<>();
        String sql = "SELECT codigo_categoria, nome_categoria FROM categoria_saida ORDER BY codigo_categoria";
        try {
            PreparedStatement stmt = connection.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CategoriaSaida c = new CategoriaSaida();
                c.setCod(rs.getInt("codigo_categoria"));
                c.setNome(rs.getString("nome_categoria"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
