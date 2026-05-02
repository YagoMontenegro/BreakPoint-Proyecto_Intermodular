package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public TorneoDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarTorneo(Torneo torneo) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?)",
                SchemaBBDD.TAB_TORNEO,
                SchemaBBDD.COL_NOMBRE_TORNEO,
                SchemaBBDD.COL_MODALIDAD,
                SchemaBBDD.COL_FECHA_INICIO,
                SchemaBBDD.COL_FECHA_FIN,
                SchemaBBDD.COL_MAX_PARTICIPANTES,
                SchemaBBDD.COL_PREMIOS
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, torneo.getNombre());
        preparedStatement.setString(2, torneo.getModalidad());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(torneo.getFechaInicio()));
        if (torneo.getFechaFin() != null) {
            preparedStatement.setTimestamp(4, Timestamp.valueOf(torneo.getFechaFin()));
        } else {
            preparedStatement.setNull(4, java.sql.Types.TIMESTAMP);
        }
        preparedStatement.setInt(5, torneo.getMaxParticipantes());
        preparedStatement.setString(6, torneo.getPremios());

        return preparedStatement.executeUpdate();
    }

    public List<Torneo> obtenerTorneos() {
        List<Torneo> torneos = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaBBDD.TAB_TORNEO);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Torneo torneo = new Torneo(
                        resultSet.getInt(SchemaBBDD.COL_ID_TORNEO),
                        resultSet.getString(SchemaBBDD.COL_NOMBRE_TORNEO),
                        resultSet.getString(SchemaBBDD.COL_MODALIDAD),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_INICIO).toLocalDateTime(),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_FIN) != null
                                ? resultSet.getTimestamp(SchemaBBDD.COL_FECHA_FIN).toLocalDateTime()
                                : null,
                        resultSet.getInt(SchemaBBDD.COL_MAX_PARTICIPANTES),
                        resultSet.getString(SchemaBBDD.COL_PREMIOS),
                        resultSet.getString(SchemaBBDD.COL_ESTADO_TORNEO)
                );
                torneos.add(torneo);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return torneos;
    }

    public Torneo obtenerTorneoPorId(int idTorneo) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", SchemaBBDD.TAB_TORNEO, SchemaBBDD.COL_ID_TORNEO);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idTorneo);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Torneo(
                        resultSet.getInt(SchemaBBDD.COL_ID_TORNEO),
                        resultSet.getString(SchemaBBDD.COL_NOMBRE_TORNEO),
                        resultSet.getString(SchemaBBDD.COL_MODALIDAD),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_INICIO).toLocalDateTime(),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_FIN) != null
                                ? resultSet.getTimestamp(SchemaBBDD.COL_FECHA_FIN).toLocalDateTime()
                                : null,
                        resultSet.getInt(SchemaBBDD.COL_MAX_PARTICIPANTES),
                        resultSet.getString(SchemaBBDD.COL_PREMIOS),
                        resultSet.getString(SchemaBBDD.COL_ESTADO_TORNEO)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarTorneo(Torneo torneo) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBBDD.TAB_TORNEO,
                SchemaBBDD.COL_NOMBRE_TORNEO,
                SchemaBBDD.COL_MODALIDAD,
                SchemaBBDD.COL_FECHA_INICIO,
                SchemaBBDD.COL_FECHA_FIN,
                SchemaBBDD.COL_MAX_PARTICIPANTES,
                SchemaBBDD.COL_PREMIOS,
                SchemaBBDD.COL_ESTADO_TORNEO,
                SchemaBBDD.COL_ID_TORNEO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, torneo.getNombre());
            preparedStatement.setString(2, torneo.getModalidad());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(torneo.getFechaInicio()));
            if (torneo.getFechaFin() != null) {
                preparedStatement.setTimestamp(4, Timestamp.valueOf(torneo.getFechaFin()));
            } else {
                preparedStatement.setNull(4, java.sql.Types.TIMESTAMP);
            }
            preparedStatement.setInt(5, torneo.getMaxParticipantes());
            preparedStatement.setString(6, torneo.getPremios());
            preparedStatement.setString(7, torneo.getEstadoTorneo());
            preparedStatement.setInt(8, torneo.getIdTorneo());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar torneo");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarTorneo(int idTorneo) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBBDD.TAB_TORNEO,
                SchemaBBDD.COL_ID_TORNEO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idTorneo);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar torneo");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
