package controller;

import dao.TorneoDAO;
import model.Torneo;
import view.TorneoView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TorneoController {
    private Scanner scanner;
    private TorneoView torneoView;
    private TorneoDAO torneoDAO;

    public TorneoController() {

    }

    public TorneoController(Scanner scanner) {
        this.scanner = scanner;
        this.torneoView = new TorneoView();
        this.torneoDAO = new TorneoDAO();
    }

    public void iniciarMenuTorneo() {
        int opcion = -1;

        do {
            torneoView.mostrarMenuTorneo();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> crearTorneo();
                    case 2 -> listarTorneos();
                    case 3 -> buscarTorneo();
                    case 4 -> modificarTorneo();
                    case 5 -> eliminarTorneo();
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

    public void crearTorneo() {
        String nombre = leerTexto("Nombre del torneo: ");
        String modalidad = leerModalidad();
        LocalDateTime fechaInicio = leerFecha("Fecha de inicio (dd/MM/yyyy HH:mm): ");
        int maxParticipantes = leerEntero("Máximo de participantes: ");
        String premios = leerTexto("Premios: ");

        Torneo torneo = new Torneo(
                0,
                nombre,
                modalidad,
                fechaInicio,
                null,
                maxParticipantes,
                premios,
                "abierto"
        );

        try {
            int filasInsertadas = torneoDAO.insertarTorneo(torneo);
            if (filasInsertadas > 0) {
                System.out.println("Torneo creado correctamente.");
            } else {
                System.out.println("No se ha podido crear el torneo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar torneo en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarTorneos() {
        System.out.println("LISTADO DE TORNEOS");
        List<Torneo> torneos = torneoDAO.obtenerTorneos();
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos registrados.");
        } else {
            for (Torneo torneo : torneos) {
                System.out.println(torneo);
            }
        }
    }

    public void buscarTorneo() {
        int idTorneo = leerEntero("Introduce el ID del torneo a buscar: ");

        Torneo torneo = torneoDAO.obtenerTorneoPorId(idTorneo);

        if (torneo != null) {
            System.out.println(torneo);
        } else {
            System.out.println("No se ha encontrado ningún torneo con dicho ID.");
        }
    }

    public void modificarTorneo() {
        int idTorneo = leerEntero("Introduce el ID del torneo a modificar: ");

        Torneo torneo = torneoDAO.obtenerTorneoPorId(idTorneo);

        if (torneo != null) {
            System.out.println(torneo);

            torneo.setNombre(leerTexto("Nuevo nombre: "));
            torneo.setModalidad(leerModalidad());
            torneo.setFechaInicio(leerFecha("Nueva fecha de inicio (dd/MM/yyyy HH:mm): "));

            System.out.print("¿Desea establecer fecha de fin? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                torneo.setFechaFin(leerFecha("Nueva fecha de fin (dd/MM/yyyy HH:mm): "));
            } else {
                torneo.setFechaFin(null);
            }

            torneo.setMaxParticipantes(leerEntero("Nuevo máximo de participantes: "));
            torneo.setPremios(leerTexto("Nuevos premios: "));
            torneo.setEstadoTorneo(leerEstado());

            int filasActualizadas = torneoDAO.actualizarTorneo(torneo);

            if (filasActualizadas > 0) {
                System.out.println("Torneo modificado correctamente.");
                System.out.println(torneo);
            } else {
                System.out.println("No se ha podido modificar el torneo.");
            }
        } else {
            System.out.println("No se ha encontrado ningún torneo con dicho ID.");
        }
    }

    public void eliminarTorneo() {
        int idTorneo = leerEntero("Introduce el ID del torneo a eliminar: ");

        Torneo torneo = torneoDAO.obtenerTorneoPorId(idTorneo);

        if (torneo != null) {
            System.out.println(torneo);
            System.out.print("¿Estás seguro de que deseas eliminar este torneo? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) {
                int filasEliminadas = torneoDAO.eliminarTorneo(idTorneo);

                if (filasEliminadas > 0) {
                    System.out.println("Torneo eliminado correctamente.");
                } else {
                    System.out.println("No se ha podido eliminar el torneo.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("No se ha encontrado ningún torneo con dicho ID.");
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
        int numero;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("El valor debe ser mayor que 0.");
                }
            } else {
                System.out.println("Debes introducir un número válido.");
                scanner.nextLine();
            }
        }
    }

    private LocalDateTime leerFecha(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Usa el formato dd/MM/yyyy HH:mm");
            }
        }
    }

    private String leerModalidad() {
        System.out.println("Selecciona la modalidad:");
        System.out.println("1. Bola 8");
        System.out.println("2. Bola 9");
        System.out.println("3. Bola 10");

        while (true) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1: return "bola_8";
                    case 2: return "bola_9";
                    case 3: return "bola_10";
                    default: System.out.println("Opción no válida. Elige 1, 2 o 3.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
            }
        }
    }

    private String leerEstado() {
        System.out.println("Selecciona el estado del torneo:");
        System.out.println("1. Abierto");
        System.out.println("2. En curso");
        System.out.println("3. Finalizado");

        while (true) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1: return "abierto";
                    case 2: return "en_curso";
                    case 3: return "finalizado";
                    default: System.out.println("Opción no válida. Elige 1, 2 o 3.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
            }
        }
    }
}
