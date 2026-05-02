package controller;

import dao.InscripcionDAO;
import model.Inscripcion;
import view.InscripcionView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InscripcionController {
    private Scanner scanner;
    private InscripcionView inscripcionView;
    private InscripcionDAO inscripcionDAO;

    public InscripcionController() {

    }

    public InscripcionController(Scanner scanner) {
        this.scanner = scanner;
        this.inscripcionView = new InscripcionView();
        this.inscripcionDAO = new InscripcionDAO();
    }

    public void iniciarMenuInscripcion() {
        int opcion = -1;

        do {
            inscripcionView.mostrarMenuInscripcion();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> inscribirSocio();
                    case 2 -> listarInscripciones();
                    case 3 -> buscarInscripcion();
                    case 4 -> registrarResultado();
                    case 5 -> eliminarInscripcion();
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

    public void inscribirSocio() {
        String telefono = leerTexto("Teléfono del socio: ");

        int idSocio = inscripcionDAO.obtenerIdSocioPorTelefono(telefono);
        if (idSocio == -1) {
            System.out.println("No se ha encontrado ningún socio activo con ese teléfono.");
            return;
        }

        int idTorneo = leerEntero("ID del torneo: ");

        try {
            int filasInsertadas = inscripcionDAO.inscribirSocio(idSocio, idTorneo);
            if (filasInsertadas > 0) {
                System.out.println("Socio inscrito correctamente en el torneo.");
            } else {
                System.out.println("No se ha podido realizar la inscripción.");
            }
        } catch (SQLException e) {
            System.out.println("Error al inscribir al socio en el torneo.");
            System.out.println(e.getMessage());
        }
    }

    public void listarInscripciones() {
        System.out.println("LISTADO DE INSCRIPCIONES");
        List<Inscripcion> inscripciones = inscripcionDAO.obtenerInscripciones();
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas.");
        } else {
            for (Inscripcion inscripcion : inscripciones) {
                System.out.println(inscripcion);
            }
        }
    }

    public void buscarInscripcion() {
        String telefono = leerTexto("Teléfono del socio: ");

        int idSocio = inscripcionDAO.obtenerIdSocioPorTelefono(telefono);
        if (idSocio == -1) {
            System.out.println("No se ha encontrado ningún socio activo con ese teléfono.");
            return;
        }

        int idTorneo = leerEntero("ID del torneo: ");

        Inscripcion inscripcion = inscripcionDAO.obtenerInscripcion(idSocio, idTorneo);

        if (inscripcion != null) {
            System.out.println(inscripcion);
        } else {
            System.out.println("No se ha encontrado ninguna inscripción con esos datos.");
        }
    }

    public void registrarResultado() {
        String telefono = leerTexto("Teléfono del socio: ");

        int idSocio = inscripcionDAO.obtenerIdSocioPorTelefono(telefono);
        if (idSocio == -1) {
            System.out.println("No se ha encontrado ningún socio activo con ese teléfono.");
            return;
        }

        int idTorneo = leerEntero("ID del torneo: ");

        Inscripcion inscripcion = inscripcionDAO.obtenerInscripcion(idSocio, idTorneo);

        if (inscripcion != null) {
            int resultado = leerEntero("Resultado (posición): ");

            int filasActualizadas = inscripcionDAO.registrarResultado(idSocio, idTorneo, resultado);

            if (filasActualizadas > 0) {
                System.out.println("Resultado registrado correctamente.");
            } else {
                System.out.println("No se ha podido registrar el resultado.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna inscripción con esos datos.");
        }
    }

    public void eliminarInscripcion() {
        String telefono = leerTexto("Teléfono del socio: ");

        int idSocio = inscripcionDAO.obtenerIdSocioPorTelefono(telefono);
        if (idSocio == -1) {
            System.out.println("No se ha encontrado ningún socio activo con ese teléfono.");
            return;
        }

        int idTorneo = leerEntero("ID del torneo: ");

        Inscripcion inscripcion = inscripcionDAO.obtenerInscripcion(idSocio, idTorneo);

        if (inscripcion != null) {
            int filasEliminadas = inscripcionDAO.eliminarInscripcion(idSocio, idTorneo);

            if (filasEliminadas > 0) {
                System.out.println("Inscripción eliminada correctamente.");
            } else {
                System.out.println("No se ha podido eliminar la inscripción.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna inscripción con esos datos.");
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

    private int leerEntero(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } else {
                System.out.println("Debes introducir un número válido.");
                scanner.nextLine();
            }
        }
    }
}
