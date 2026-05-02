package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.CuotaSocio;
import model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CuotaSocioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CuotaSocioDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarCuota(CuotaSocio cuota) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.COL_ID_SOCIO_FK,
                SchemaBBDD.COL_MES,
                SchemaBBDD.COL_ANIO,
                SchemaBBDD.COL_ESTADO_CUOTA,
                SchemaBBDD.COL_IMPORTE
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cuota.getSocio().getIdSocio());
        preparedStatement.setInt(2, cuota.getMes());
        preparedStatement.setInt(3, cuota.getAnio());
        preparedStatement.setString(4, cuota.getEstadoCuota());
        preparedStatement.setDouble(5, cuota.getImporte());

        return preparedStatement.executeUpdate();
    }

    public List<CuotaSocio> obtenerCuotas() {
        List<CuotaSocio> cuotas = new ArrayList<>();
        String query = String.format(
                "SELECT cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, " +
                        "s.%s, u.%s, u.%s, u.%s, u.%s, u.%s, u.%s, s.%s, s.%s, s.%s " +
                        "FROM %s cs " +
                        "JOIN %s s ON cs.%s = s.%s " +
                        "JOIN %s u ON s.%s = u.%s",
                SchemaBBDD.COL_ID_CUOTA,
                SchemaBBDD.COL_FECHA_PAGO,
                SchemaBBDD.COL_MES,
                SchemaBBDD.COL_ANIO,
                SchemaBBDD.COL_ESTADO_CUOTA,
                SchemaBBDD.COL_IMPORTE,
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
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_ID_SOCIO_FK,
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CuotaSocio cuota = construirCuotaDesdeResultSet(resultSet);
                cuotas.add(cuota);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return cuotas;
    }

    public List<CuotaSocio> obtenerCuotasPorTelefono(String telefonoBuscado) {
        List<CuotaSocio> cuotas = new ArrayList<>();
        String query = String.format(
                "SELECT cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, " +
                        "s.%s, u.%s, u.%s, u.%s, u.%s, u.%s, u.%s, s.%s, s.%s, s.%s " +
                        "FROM %s cs " +
                        "JOIN %s s ON cs.%s = s.%s " +
                        "JOIN %s u ON s.%s = u.%s " +
                        "WHERE u.%s = ?",
                SchemaBBDD.COL_ID_CUOTA,
                SchemaBBDD.COL_FECHA_PAGO,
                SchemaBBDD.COL_MES,
                SchemaBBDD.COL_ANIO,
                SchemaBBDD.COL_ESTADO_CUOTA,
                SchemaBBDD.COL_IMPORTE,
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
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_ID_SOCIO_FK,
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_TELEFONO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefonoBuscado);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CuotaSocio cuota = construirCuotaDesdeResultSet(resultSet);
                cuotas.add(cuota);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return cuotas;
    }

    public CuotaSocio obtenerCuotaPorId(int idCuota) {
        String query = String.format(
                "SELECT cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, cs.%s, " +
                        "s.%s, u.%s, u.%s, u.%s, u.%s, u.%s, u.%s, s.%s, s.%s, s.%s " +
                        "FROM %s cs " +
                        "JOIN %s s ON cs.%s = s.%s " +
                        "JOIN %s u ON s.%s = u.%s " +
                        "WHERE cs.%s = ?",
                SchemaBBDD.COL_ID_CUOTA,
                SchemaBBDD.COL_FECHA_PAGO,
                SchemaBBDD.COL_MES,
                SchemaBBDD.COL_ANIO,
                SchemaBBDD.COL_ESTADO_CUOTA,
                SchemaBBDD.COL_IMPORTE,
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
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.TAB_SOCIO,
                SchemaBBDD.COL_ID_SOCIO_FK,
                SchemaBBDD.COL_ID_SOCIO,
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_ID_USUARIO_FK,
                SchemaBBDD.COL_ID_USUARIO,
                SchemaBBDD.COL_ID_CUOTA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCuota);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return construirCuotaDesdeResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int registrarPago(int idCuota) {
        String query = String.format(
                "UPDATE %s SET %s = NOW(), %s = 'pagada' WHERE %s = ? AND %s <> 'pagada'",
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.COL_FECHA_PAGO,
                SchemaBBDD.COL_ESTADO_CUOTA,
                SchemaBBDD.COL_ID_CUOTA,
                SchemaBBDD.COL_ESTADO_CUOTA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCuota);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar pago de cuota");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarCuota(int idCuota) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBBDD.TAB_CUOTA_SOCIO,
                SchemaBBDD.COL_ID_CUOTA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCuota);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar cuota");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    private CuotaSocio construirCuotaDesdeResultSet(ResultSet rs) throws SQLException {
        Socio socio = new Socio(
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

        return new CuotaSocio(
                rs.getInt(SchemaBBDD.COL_ID_CUOTA),
                socio,
                rs.getTimestamp(SchemaBBDD.COL_FECHA_PAGO) != null ? rs.getTimestamp(SchemaBBDD.COL_FECHA_PAGO).toLocalDateTime() : null,
                rs.getInt(SchemaBBDD.COL_MES),
                rs.getInt(SchemaBBDD.COL_ANIO),
                rs.getString(SchemaBBDD.COL_ESTADO_CUOTA),
                rs.getDouble(SchemaBBDD.COL_IMPORTE)
        );
    }
}
