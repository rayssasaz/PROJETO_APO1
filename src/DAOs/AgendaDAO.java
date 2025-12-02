package daos;

import java.sql.*;
import model.Agenda;

public class AgendaDAO {

    private Connection conn;

    public AgendaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Agenda ag) throws Exception {

        String sql = "INSERT INTO Agenda (Tipo, Dia, Valor, RepetirMeses, UsuarioId, CategoriaId, Data) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, ag.getTipo());
        ps.setInt(2, ag.getDia());
        ps.setDouble(3, ag.getValor());
        ps.setInt(4, ag.getRepetirMeses());
        ps.setInt(5, ag.getUsuarioId());
        ps.setInt(6, ag.getCategoriaId());

        if (ag.getData() != null) {
            ps.setDate(7, Date.valueOf(ag.getData()));
        } else {
            ps.setNull(7, java.sql.Types.DATE);
        }

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int novoId = 0;
        if (rs.next()) {
            novoId = rs.getInt(1);
        }

        CallableStatement cs = conn.prepareCall("{ CALL GerarEventosAgenda(?) }");
        cs.setInt(1, novoId);
        cs.execute();
    }


    // LISTAR TRAZENDO O NOME DA CATEGORIA
    public ResultSet listarAgendaPorUsuario(int usuarioId) throws Exception {

        String sql =
            "SELECT a.Id, a.Tipo, a.Dia, a.Valor, a.RepetirMeses, " +
            "       a.CategoriaId, c.Nome AS CategoriaNome, a.Data " +
            "FROM Agenda a " +
            "INNER JOIN CategoriaEntrada c ON c.Cod = a.CategoriaId " +
            "WHERE a.UsuarioId = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, usuarioId);

        return ps.executeQuery();
    }
}
