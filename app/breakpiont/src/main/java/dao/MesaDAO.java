package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public MesaDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarMesa(Mesa mesa) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s) VALUES (?)",
                SchemaBBDD.TAB_MESA,
                SchemaBBDD.COL_ESTADO_MESA
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mesa.getEstadoMesa().name());

        return preparedStatement.executeUpdate();
    }

    public List<Mesa> obtenerMesas() {
        List<Mesa> mesas = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaBBDD.TAB_MESA);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Mesa mesa = new Mesa(
                        resultSet.getInt(SchemaBBDD.COL_ID_MESA),
                        Mesa.EstadoMesa.valueOf(resultSet.getString(SchemaBBDD.COL_ESTADO_MESA))
                );
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return mesas;
    }

    public Mesa obtenerMesaPorId(int idMesa) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", SchemaBBDD.TAB_MESA, SchemaBBDD.COL_ID_MESA);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idMesa);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Mesa(
                        resultSet.getInt(SchemaBBDD.COL_ID_MESA),
                        Mesa.EstadoMesa.valueOf(resultSet.getString(SchemaBBDD.COL_ESTADO_MESA))
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarEstadoMesa(Mesa mesa) {
        String query = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ?",
                SchemaBBDD.TAB_MESA,
                SchemaBBDD.COL_ESTADO_MESA,
                SchemaBBDD.COL_ID_MESA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mesa.getEstadoMesa().name());
            preparedStatement.setInt(2, mesa.getIdMesa());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar mesa");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarMesa(int idMesa) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBBDD.TAB_MESA,
                SchemaBBDD.COL_ID_MESA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idMesa);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar mesa");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
