package controller;

import dao.SocioDAO;
import dao.UsuarioDAO;
import model.Socio;
import model.Usuario;
import view.SocioView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class SocioController {
    private Scanner scanner;
    private SocioView socioView;
    private SocioDAO socioDAO;
    private UsuarioDAO usuarioDAO;

    public SocioController() {

    }

    public SocioController(Scanner scanner) {
        this.scanner = scanner;
        this.socioView = new SocioView();
        this.socioDAO = new SocioDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public void iniciarMenuSocio() {
        int opcion = -1;

        do {
            socioView.mostrarMenuSocio();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaSocio();
                    case 2 -> listarSocios();
                    case 3 -> buscarSocio();
                    case 4 -> modificarSocio();
                    case 5 -> darBajaSocio();
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

    public void darAltaSocio() {
        System.out.print("Introduce el teléfono del usuario que desea hacerse socio: ");
        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Usuario usuario = usuarioDAO.obtenerUsuarioPorTelefono(telefono);

            if (usuario != null) {
                Socio socioExistente = socioDAO.obtenerSocioPorTelefono(telefono);

                if (socioExistente != null) {
                    System.out.println("Este usuario ya es socio.");
                    System.out.println(socioExistente);
                    return;
                }

                Socio socio = new Socio(
                        0,
                        usuario.getIdUsuario(),
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        usuario.getEmail(),
                        usuario.getTelefono(),
                        usuario.getFechaRegistro(),
                        LocalDateTime.now(),
                        null,
                        "activo"
                );

                try {
                    int filasInsertadas = socioDAO.insertarSocio(socio);
                    if (filasInsertadas > 0) {
                        System.out.println("Socio dado de alta correctamente.");
                    } else {
                        System.out.println("No se ha podido dar de alta al socio.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al insertar socio en la base de datos.");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("No se ha encontrado ningún usuario con ese teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void listarSocios() {
        System.out.println("LISTADO DE SOCIOS");
        List<Socio> socios = socioDAO.obtenerSocios();
        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados.");
        } else {
            for (Socio socio : socios) {
                System.out.println(socio);
            }
        }
    }

    public void buscarSocio() {
        System.out.print("Introduce el teléfono del socio a buscar: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Socio socio = socioDAO.obtenerSocioPorTelefono(telefono);

            if (socio != null) {
                System.out.println(socio);
            } else {
                System.out.println("No se ha encontrado ningún socio con dicho teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void modificarSocio() {
        System.out.print("Introduce el teléfono del socio a modificar: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Socio socio = socioDAO.obtenerSocioPorTelefono(telefono);

            if (socio != null) {
                System.out.println(socio);
                System.out.println("\nEstados disponibles: activo, cancelado, mantenimiento");
                String nuevoEstado = leerTexto("Nuevo estado del socio: ");

                if (nuevoEstado.equals("activo") || nuevoEstado.equals("cancelado") || nuevoEstado.equals("mantenimiento")) {
                    socio.setEstadoSocio(nuevoEstado);

                    if (nuevoEstado.equals("cancelado")) {
                        socio.setFechaBaja(LocalDateTime.now());
                    } else {
                        socio.setFechaBaja(null);
                    }

                    int filasActualizadas = socioDAO.actualizarSocio(socio);

                    if (filasActualizadas > 0) {
                        System.out.println("Socio modificado correctamente.");
                        System.out.println(socio);
                    } else {
                        System.out.println("No se ha podido modificar el socio.");
                    }
                } else {
                    System.out.println("Estado no válido. Debe ser: activo, cancelado o mantenimiento.");
                }
            } else {
                System.out.println("No se ha encontrado ningún socio con dicho teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void darBajaSocio() {
        System.out.print("Introduce el teléfono del socio a dar de baja: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Socio socio = socioDAO.obtenerSocioPorTelefono(telefono);

            if (socio != null) {
                if (socio.getEstadoSocio().equals("cancelado")) {
                    System.out.println("Este socio ya está dado de baja.");
                    return;
                }

                int filasActualizadas = socioDAO.darBajaSocio(telefono);

                if (filasActualizadas > 0) {
                    System.out.println("Socio dado de baja correctamente.");
                } else {
                    System.out.println("No se ha podido dar de baja al socio.");
                }
            } else {
                System.out.println("No se ha encontrado ningún socio con ese teléfono.");
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
