package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public SocioDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarSocio(Socio socio) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_ESTADO_SOCIO
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, socio.getIdUsuario());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(socio.getFechaAlta()));
        preparedStatement.setString(3, socio.getEstadoSocio());

        return preparedStatement.executeUpdate();
    }

    public List<Socio> obtenerSocios() {
        List<Socio> socios = new ArrayList<>();
        String query = String.format(
                "SELECT s.%s, u.%s, u.%s, u.%s, u.%s, u.%s, u.%s, s.%s, s.%s, s.%s " +
                        "FROM %s s JOIN %s u ON s.%s = u.%s",
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_FECHA_REGISTRO,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_FECHA_BAJA,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Socio socio = construirSocioDesdeResultSet(resultSet);
                socios.add(socio);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return socios;
    }

    public Socio obtenerSocioPorTelefono(String telefonoBuscado) {
        String query = String.format(
                "SELECT s.%s, u.%s, u.%s, u.%s, u.%s, u.%s, u.%s, s.%s, s.%s, s.%s " +
                        "FROM %s s JOIN %s u ON s.%s = u.%s WHERE u.%s = ?",
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_FECHA_REGISTRO,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_FECHA_BAJA,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_TELEFONO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefonoBuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return construirSocioDesdeResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarSocio(Socio socio) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_FECHA_BAJA,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.COL_ID_SOCIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(socio.getFechaAlta()));
            preparedStatement.setTimestamp(2, socio.getFechaBaja() != null ? Timestamp.valueOf(socio.getFechaBaja()) : null);
            preparedStatement.setString(3, socio.getEstadoSocio());
            preparedStatement.setInt(4, socio.getIdSocio());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar socio");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int darBajaSocio(String telefonoBuscado) {
        String query = String.format(
                "UPDATE %s s JOIN %s u ON s.%s = u.%s " +
                        "SET s.%s = 'cancelado', s.%s = NOW() " +
                        "WHERE u.%s = ? AND s.%s <> 'cancelado'",
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.COL_FECHA_BAJA,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_ESTADO_SOCIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefonoBuscado);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al dar de baja al socio");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    private Socio construirSocioDesdeResultSet(ResultSet rs) throws SQLException {
        return new Socio(
                rs.getInt(SchemaBBDD.COL_ID_SOCIO),
                rs.getInt(SchemaBBDD.COL_ID_USUARIO),
                rs.getString(SchemaBBDD.COL_NOMBRE),
                rs.getString(SchemaBBDD.COL_APELLIDOS),
                rs.getString(SchemaBBDD.COL_EMAIL),
                rs.getString(SchemaBBDD.COL_TELEFONO),
                rs.getTimestamp(SchemaBBDD.COL_FECHA_REGISTRO).toLocalDateTime(),
                rs.getTimestamp(SchemaBBDD.COL_FECHA_ALTA).toLocalDateTime(),
                rs.getTimestamp(SchemaBBDD.COL_FECHA_BAJA) != null ? rs.getTimestamp(SchemaBBDD.COL_FECHA_BAJA).toLocalDateTime() : null,
                rs.getString(SchemaBBDD.COL_ESTADO_SOCIO)
        );
    }
}
