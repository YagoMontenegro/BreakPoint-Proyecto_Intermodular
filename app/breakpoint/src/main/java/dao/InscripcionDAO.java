package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Inscripcion;
import model.Socio;
import model.Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public InscripcionDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int inscribirSocio(int idSocio, int idTorneo) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)",
                SchemaBBDD.TAB_INSCRIPCION,
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idSocio);
        preparedStatement.setInt(2, idTorneo);

        return preparedStatement.executeUpdate();
    }

    public List<Inscripcion> obtenerInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = String.format(
                "SELECT i.%s, i.%s, i.%s, i.%s, " +
                        "s.%s, s.%s, s.%s, " +
                        "u.%s, u.%s, u.%s, u.%s, " +
                        "t.%s, t.%s, t.%s, t.%s, t.%s, t.%s, t.%s " +
                        "FROM %s i " +
                        "JOIN %s s ON s.%s = i.%s " +
                        "JOIN %s u ON u.%s = s.%s " +
                        "JOIN %s t ON t.%s = i.%s " +
                        "ORDER BY i.%s",
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK,
                SchemaBBDD.COL_FECHA_INSCRIPCION,
                SchemaBBDD.COL_RESULTADO,
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_ID_TORNEO,
                SchemaBBDD.COL_NOMBRE_TORNEO,
                SchemaBBDD.COL_MODALIDAD,
                SchemaBBDD.COL_FECHA_INICIO,
                SchemaBBDD.COL_FECHA_FIN,
                SchemaBBDD.COL_MAX_PARTICIPANTES,
                SchemaBBDD.COL_ESTADO_TORNEO,
                SchemaBBDD.TAB_INSCRIPCION,
                SchemaBBDD.TAB_SOCIO, SchemaBBDD.COL_ID_SOCIO, SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.TAB_TORNEO, SchemaBBDD.COL_ID_TORNEO, SchemaBBDD.COL_ID_TORNEO_FK,
                SchemaBBDD.COL_FECHA_INSCRIPCION
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                inscripciones.add(mapearInscripcion(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return inscripciones;
    }

    public Inscripcion obtenerInscripcion(int idSocio, int idTorneo) {
        String query = String.format(
                "SELECT i.%s, i.%s, i.%s, i.%s, " +
                        "s.%s, s.%s, s.%s, " +
                        "u.%s, u.%s, u.%s, u.%s, " +
                        "t.%s, t.%s, t.%s, t.%s, t.%s, t.%s, t.%s " +
                        "FROM %s i " +
                        "JOIN %s s ON s.%s = i.%s " +
                        "JOIN %s u ON u.%s = s.%s " +
                        "JOIN %s t ON t.%s = i.%s " +
                        "WHERE i.%s = ? AND i.%s = ?",
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK,
                SchemaBBDD.COL_FECHA_INSCRIPCION,
                SchemaBBDD.COL_RESULTADO,
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.COL_FECHA_ALTA,
                SchemaBBDD.COL_ESTADO_SOCIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_ID_TORNEO,
                SchemaBBDD.COL_NOMBRE_TORNEO,
                SchemaBBDD.COL_MODALIDAD,
                SchemaBBDD.COL_FECHA_INICIO,
                SchemaBBDD.COL_FECHA_FIN,
                SchemaBBDD.COL_MAX_PARTICIPANTES,
                SchemaBBDD.COL_ESTADO_TORNEO,
                SchemaBBDD.TAB_INSCRIPCION,
                SchemaBBDD.TAB_SOCIO, SchemaBBDD.COL_ID_SOCIO, SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.TAB_TORNEO, SchemaBBDD.COL_ID_TORNEO, SchemaBBDD.COL_ID_TORNEO_FK,
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSocio);
            preparedStatement.setInt(2, idTorneo);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapearInscripcion(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int registrarResultado(int idSocio, int idTorneo, int resultado) {
        String query = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?",
                SchemaBBDD.TAB_INSCRIPCION,
                SchemaBBDD.COL_RESULTADO,
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resultado);
            preparedStatement.setInt(2, idSocio);
            preparedStatement.setInt(3, idTorneo);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar resultado");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarInscripcion(int idSocio, int idTorneo) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ? AND %s = ?",
                SchemaBBDD.TAB_INSCRIPCION,
                SchemaBBDD.COL_ID_SOCIO_INS_FK,
                SchemaBBDD.COL_ID_TORNEO_FK
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSocio);
            preparedStatement.setInt(2, idTorneo);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar inscripción");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int obtenerIdSocioPorTelefono(String telefono) {
        String query = String.format(
                "SELECT s.%s FROM %s s " +
                        "JOIN %s u ON u.%s = s.%s " +
                        "WHERE u.%s = ? AND s.%s = 'activo'",
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_ID_USUARIO, SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_ESTADO_SOCIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefono);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(SchemaBBDD.COL_ID_SOCIO);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar socio por teléfono");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    private Inscripcion mapearInscripcion(ResultSet rs) throws SQLException {
        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt(SchemaBBDD.COL_ID_SOCIO));
        socio.setNombre(rs.getString(SchemaBBDD.COL_NOMBRE));
        socio.setApellidos(rs.getString(SchemaBBDD.COL_APELLIDOS));
        socio.setEmail(rs.getString(SchemaBBDD.COL_EMAIL));
        socio.setTelefono(rs.getString(SchemaBBDD.COL_TELEFONO));
        socio.setFechaAlta(rs.getTimestamp(SchemaBBDD.COL_FECHA_ALTA).toLocalDateTime());
        socio.setEstadoSocio(rs.getString(SchemaBBDD.COL_ESTADO_SOCIO));

        Torneo torneo = new Torneo();
        torneo.setIdTorneo(rs.getInt(SchemaBBDD.COL_ID_TORNEO));
        torneo.setNombre(rs.getString(SchemaBBDD.COL_NOMBRE_TORNEO));
        torneo.setModalidad(rs.getString(SchemaBBDD.COL_MODALIDAD));
        torneo.setFechaInicio(rs.getTimestamp(SchemaBBDD.COL_FECHA_INICIO).toLocalDateTime());
        if (rs.getTimestamp(SchemaBBDD.COL_FECHA_FIN) != null) {
            torneo.setFechaFin(rs.getTimestamp(SchemaBBDD.COL_FECHA_FIN).toLocalDateTime());
        }
        torneo.setMaxParticipantes(rs.getInt(SchemaBBDD.COL_MAX_PARTICIPANTES));
        torneo.setEstadoTorneo(rs.getString(SchemaBBDD.COL_ESTADO_TORNEO));

        Integer resultado = rs.getObject(SchemaBBDD.COL_RESULTADO) != null
                ? rs.getInt(SchemaBBDD.COL_RESULTADO) : null;

        return new Inscripcion(
                socio,
                torneo,
                rs.getTimestamp(SchemaBBDD.COL_FECHA_INSCRIPCION).toLocalDateTime(),
                resultado
        );
    }
}
