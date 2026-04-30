package controller;

import dao.UsuarioDAO;
import model.Usuario;
import view.UsuarioView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UsuarioController {
    private Scanner scanner;
    private UsuarioView usuarioView;
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {

    }

    public UsuarioController(Scanner scanner) {
        this.scanner = scanner;
        this.usuarioView = new UsuarioView();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void iniciarMenuUsuario() {
        int opcion = -1;

        do {
            usuarioView.mostrarMenuUsuario();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> buscarUsuario();
                    case 4 -> modificarUsuario();
                    case 5 -> eliminarUsuario();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
                opcion = -1;
            }

        } while (opcion != 0);
    }

    public void darAltaUsuario() {
        String nombre = leerTexto("Nombre: ");
        String apellidos = leerTexto("Apellidos: ");
        String email = leerTexto("Email: ");
        String telefono = leerTexto("Teléfono: ");

        Usuario usuario = new Usuario(
                0,
                nombre,
                apellidos,
                email,
                telefono,
                null
        );

        try {
            int filasInsertadas = usuarioDAO.insertarUsuario(usuario);
            if (filasInsertadas > 0) {
                System.out.println("Usuario dado de alta correctamente.");
            } else {
                System.out.println("No se ha podido dar de alta el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarUsuarios() {
        System.out.println("LISTADO DE USUARIOS");
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
    }

    public void buscarUsuario() {
        System.out.print("Introduce el telefono del usuario a buscar: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorTelefono(telefono);

            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.out.println("No se ha encontrado ningún usuario con dicho teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void modificarUsuario() {
        System.out.print("Introduce el teléfono del usuario a modificar: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorTelefono(telefono);

            if (usuario != null) {
                usuario.setNombre(leerTexto("Nuevo nombre: "));
                usuario.setApellidos(leerTexto("Nuevos apellidos: "));
                usuario.setEmail(leerTexto("Nuevo email: "));
                usuario.setTelefono(leerTexto("Nuevo teléfono: "));

                int filasActualizadas = usuarioDAO.actualizarUsuario(usuario);

                if (filasActualizadas > 0) {
                    System.out.println("Usuario modificado correctamente.");
                    System.out.println(usuario);
                } else {
                    System.out.println("No se ha podido modificar el usuario.");
                }
            } else {
                System.out.println("No se ha encontrado ningún usuario con dicho teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void eliminarUsuario() {
        System.out.print("Introduce el teléfono del usuario a eliminar: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorTelefono(telefono);

            if (usuario != null) {
                int filasEliminadas = usuarioDAO.eliminarUsuario(telefono);

                if (filasEliminadas > 0) {
                    System.out.println("Usuario eliminado correctamente.");
                } else if (filasEliminadas == 0) {
                    System.out.println("No se ha encontrado ningún usuario con ese teléfono.");
                } else {
                    System.out.println("No se ha podido eliminar el usuario.");
                }
            } else {
                System.out.println("No se ha encontrado ningún usuario con ese teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    private String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }
}
