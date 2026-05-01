package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Mesa;
import model.Reserva;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ReservaDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarReserva(Reserva reserva) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO,
                SchemaBBDD.COL_HORA_FIN,
                SchemaBBDD.COL_COSTE
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, reserva.getUsuario().getIdUsuario());
        preparedStatement.setInt(2, reserva.getMesa().getIdMesa());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(reserva.getHoraInicio()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(reserva.getHoraFin()));
        preparedStatement.setDouble(5, reserva.getCoste());

        return preparedStatement.executeUpdate();
    }

    public List<Reserva> obtenerReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String query = String.format(
                "SELECT r.*, u.%s, u.%s, u.%s, u.%s, u.%s, m.%s " +
                        "FROM %s r " +
                        "JOIN %s u ON u.%s = r.%s " +
                        "JOIN %s m ON m.%s = r.%s " +
                        "ORDER BY r.%s DESC",
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_FECHA_REGISTRO,
                SchemaBBDD.COL_ESTADO_MESA,
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.TAB_MESA, SchemaBBDD.COL_ID_MESA, SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservas.add(mapearReserva(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return reservas;
    }

    public List<Reserva> obtenerReservasPorTelefono(String telefono) {
        List<Reserva> reservas = new ArrayList<>();
        String query = String.format(
                "SELECT r.*, u.%s, u.%s, u.%s, u.%s, u.%s, m.%s " +
                        "FROM %s r " +
                        "JOIN %s u ON u.%s = r.%s " +
                        "JOIN %s m ON m.%s = r.%s " +
                        "WHERE u.%s = ? " +
                        "ORDER BY r.%s DESC",
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_FECHA_REGISTRO,
                SchemaBBDD.COL_ESTADO_MESA,
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.TAB_MESA, SchemaBBDD.COL_ID_MESA, SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_HORA_INICIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefono);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservas.add(mapearReserva(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return reservas;
    }

    public Reserva obtenerReservaPorClave(int idUsuario, int idMesa, LocalDateTime horaInicio) {
        String query = String.format(
                "SELECT r.*, u.%s, u.%s, u.%s, u.%s, u.%s, m.%s " +
                        "FROM %s r " +
                        "JOIN %s u ON u.%s = r.%s " +
                        "JOIN %s m ON m.%s = r.%s " +
                        "WHERE r.%s = ? AND r.%s = ? AND r.%s = ?",
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_FECHA_REGISTRO,
                SchemaBBDD.COL_ESTADO_MESA,
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.TAB_MESA, SchemaBBDD.COL_ID_MESA, SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idMesa);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(horaInicio));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapearReserva(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarReserva(Reserva reserva, LocalDateTime horaInicioOriginal) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? " +
                        "WHERE %s = ? AND %s = ? AND %s = ?",
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO,
                SchemaBBDD.COL_HORA_FIN,
                SchemaBBDD.COL_COSTE,
                SchemaBBDD.COL_ESTADO_RESERVA,
                SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reserva.getMesa().getIdMesa());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reserva.getHoraInicio()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(reserva.getHoraFin()));
            preparedStatement.setDouble(4, reserva.getCoste());
            preparedStatement.setString(5, reserva.getEstadoReserva());
            preparedStatement.setInt(6, reserva.getUsuario().getIdUsuario());
            preparedStatement.setInt(7, reserva.getMesa().getIdMesa());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(horaInicioOriginal));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int cancelarReserva(int idUsuario, int idMesa, LocalDateTime horaInicio) {
        String query = String.format(
                "UPDATE %s SET %s = 'cancelada' WHERE %s = ? AND %s = ? AND %s = ?",
                SchemaBBDD.TAB_RESERVA,
                SchemaBBDD.COL_ESTADO_RESERVA,
                SchemaBBDD.COL_ID_USUARIO_RESERVA_FK,
                SchemaBBDD.COL_ID_MESA_FK,
                SchemaBBDD.COL_HORA_INICIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idMesa);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(horaInicio));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al cancelar reserva");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public boolean esSocioActivo(int idUsuario) {
        String query = String.format(
                "SELECT %s FROM %s WHERE %s = ? AND %s = 'activo'",
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ESTADO_SOCIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error al comprobar si el usuario es socio activo");
            System.out.println(e.getMessage());
        }
        return false;
    }

    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getInt(SchemaBBDD.COL_ID_USUARIO_RESERVA_FK),
                rs.getString(SchemaBBDD.COL_NOMBRE),
                rs.getString(SchemaBBDD.COL_APELLIDOS),
                rs.getString(SchemaBBDD.COL_EMAIL),
                rs.getString(SchemaBBDD.COL_TELEFONO),
                rs.getTimestamp(SchemaBBDD.COL_FECHA_REGISTRO).toLocalDateTime()
        );

        Mesa mesa = new Mesa(
                rs.getInt(SchemaBBDD.COL_ID_MESA_FK),
                Mesa.EstadoMesa.valueOf(rs.getString(SchemaBBDD.COL_ESTADO_MESA))
        );

        return new Reserva(
                usuario,
                mesa,
                rs.getTimestamp(SchemaBBDD.COL_HORA_INICIO).toLocalDateTime(),
                rs.getTimestamp(SchemaBBDD.COL_HORA_FIN).toLocalDateTime(),
                rs.getDouble(SchemaBBDD.COL_COSTE),
                rs.getString(SchemaBBDD.COL_ESTADO_RESERVA)
        );
    }
}
