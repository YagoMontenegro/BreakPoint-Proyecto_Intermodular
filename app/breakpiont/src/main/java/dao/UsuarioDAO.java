package dao;

import database.ConexionBBDD;
import database.SchemaBBDD;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UsuarioDAO() {
        connection = ConexionBBDD.getConexion();
    }

    public int insertarUsuario(Usuario usuario) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, usuario.getNombre());
        preparedStatement.setString(2, usuario.getApellidos());
        preparedStatement.setString(3, usuario.getEmail());
        preparedStatement.setString(4, usuario.getTelefono());

        return preparedStatement.executeUpdate();
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaBBDD.TAB_USUARIO);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt(SchemaBBDD.COL_ID_USUARIO),
                        resultSet.getString(SchemaBBDD.COL_NOMBRE),
                        resultSet.getString(SchemaBBDD.COL_APELLIDOS),
                        resultSet.getString(SchemaBBDD.COL_EMAIL),
                        resultSet.getString(SchemaBBDD.COL_TELEFONO),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_REGISTRO).toLocalDateTime()
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    public Usuario obtenerUsuarioPorTelefono(String telefonobuscado) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", SchemaBBDD.TAB_USUARIO, SchemaBBDD.COL_TELEFONO);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefonobuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Usuario(
                        resultSet.getInt(SchemaBBDD.COL_ID_USUARIO),
                        resultSet.getString(SchemaBBDD.COL_NOMBRE),
                        resultSet.getString(SchemaBBDD.COL_APELLIDOS),
                        resultSet.getString(SchemaBBDD.COL_EMAIL),
                        resultSet.getString(SchemaBBDD.COL_TELEFONO),
                        resultSet.getTimestamp(SchemaBBDD.COL_FECHA_REGISTRO).toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarUsuario(Usuario usuario) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_NOMBRE,
                SchemaBBDD.COL_APELLIDOS,
                SchemaBBDD.COL_EMAIL,
                SchemaBBDD.COL_TELEFONO,
                SchemaBBDD.COL_ID_USUARIO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidos());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getTelefono());
            preparedStatement.setInt(5, usuario.getIdUsuario());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarUsuario(String telefonobuscado) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBBDD.TAB_USUARIO,
                SchemaBBDD.COL_TELEFONO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, telefonobuscado);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
