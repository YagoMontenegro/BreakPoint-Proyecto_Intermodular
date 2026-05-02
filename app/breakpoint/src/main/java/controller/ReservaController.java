package controller;

import dao.MesaDAO;
import dao.ReservaDAO;
import dao.UsuarioDAO;
import model.Mesa;
import model.Reserva;
import model.Usuario;
import view.ReservaView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class ReservaController {
    private static final double COSTE_HORA_NO_SOCIO = 6.00;
    private static final double COSTE_HORA_SOCIO = 0.00;

    private Scanner scanner;
    private ReservaView reservaView;
    private ReservaDAO reservaDAO;
    private UsuarioDAO usuarioDAO;
    private MesaDAO mesaDAO;

    public ReservaController() {

    }

    public ReservaController(Scanner scanner) {
        this.scanner = scanner;
        this.reservaView = new ReservaView();
        this.reservaDAO = new ReservaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.mesaDAO = new MesaDAO();
    }

    public void iniciarMenuReserva() {
        int opcion = -1;

        do {
            reservaView.mostrarMenuReserva();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> crearReserva();
                    case 2 -> listarReservas();
                    case 3 -> buscarReserva();
                    case 4 -> modificarReserva();
                    case 5 -> cancelarReserva();
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

    public void crearReserva() {
        System.out.print("Teléfono del usuario: ");
        String telefono = scanner.nextLine().trim();

        if (telefono.isEmpty()) {
            System.out.println("Debes introducir un teléfono válido.");
            return;
        }

        Usuario usuario = usuarioDAO.obtenerUsuarioPorTelefono(telefono);
        if (usuario == null) {
            System.out.println("No se ha encontrado ningún usuario con dicho teléfono.");
            return;
        }
        System.out.println("Usuario encontrado: " + usuario.getNombre() + " " + usuario.getApellidos());

        int idMesa = leerEntero("ID de la mesa: ");
        Mesa mesa = mesaDAO.obtenerMesaPorId(idMesa);

        if (mesa == null) {
            System.out.println("No se ha encontrado ninguna mesa con dicho ID.");
            return;
        }
        if (mesa.getEstadoMesa() != Mesa.EstadoMesa.disponible) {
            System.out.println("La mesa no está disponible. Estado actual: " + mesa.getEstadoMesa());
            return;
        }

        LocalDateTime horaInicio = leerFecha("Hora de inicio (dd/MM/yyyy HH:mm): ");
        LocalDateTime horaFin = leerFecha("Hora de fin (dd/MM/yyyy HH:mm): ");

        if (!horaFin.isAfter(horaInicio)) {
            System.out.println("La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        double coste = calcularCoste(usuario.getIdUsuario(), horaInicio, horaFin);

        Reserva reserva = new Reserva(usuario, mesa, horaInicio, horaFin, coste, "confirmada");

        try {
            int filasInsertadas = reservaDAO.insertarReserva(reserva);
            if (filasInsertadas > 0) {
                System.out.println("Reserva creada correctamente.");
                System.out.println(reserva);
            } else {
                System.out.println("No se ha podido crear la reserva.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar reserva en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarReservas() {
        System.out.println("LISTADO DE RESERVAS");
        List<Reserva> reservas = reservaDAO.obtenerReservas();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }
    }

    public void buscarReserva() {
        System.out.print("Introduce el teléfono del usuario: ");
        String telefono = scanner.nextLine().trim();

        if (telefono.isEmpty()) {
            System.out.println("Debes introducir un teléfono válido.");
            return;
        }

        List<Reserva> reservas = reservaDAO.obtenerReservasPorTelefono(telefono);

        if (reservas.isEmpty()) {
            System.out.println("No se han encontrado reservas para ese teléfono.");
        } else {
            System.out.println("Reservas encontradas:");
            for (int i = 0; i < reservas.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + reservas.get(i));
            }
        }
    }

    public void modificarReserva() {
        System.out.print("Introduce el teléfono del usuario: ");
        String telefono = scanner.nextLine().trim();

        if (telefono.isEmpty()) {
            System.out.println("Debes introducir un teléfono válido.");
            return;
        }

        List<Reserva> reservas = reservaDAO.obtenerReservasPorTelefono(telefono);

        if (reservas.isEmpty()) {
            System.out.println("No se han encontrado reservas para ese teléfono.");
            return;
        }

        System.out.println("Reservas activas encontradas:");
        for (Reserva r : reservas) {
            System.out.println(r);
        }

        int idMesaActual = leerEntero("ID de la mesa de la reserva a modificar: ");
        LocalDateTime horaInicioActual = leerFecha("Hora de inicio de la reserva a modificar (dd/MM/yyyy HH:mm): ");

        Reserva reserva = null;
        for (Reserva r : reservas) {
            if (r.getMesa().getIdMesa() == idMesaActual && r.getHoraInicio().equals(horaInicioActual)) {
                reserva = r;
                break;
            }
        }

        if (reserva == null) {
            System.out.println("No se ha encontrado ninguna reserva con esa mesa y hora de inicio.");
            return;
        }

        LocalDateTime horaInicioOriginal = reserva.getHoraInicio();

        int idMesaNueva = leerEntero("Nuevo ID de mesa: ");
        Mesa mesa = mesaDAO.obtenerMesaPorId(idMesaNueva);
        if (mesa == null) {
            System.out.println("No se ha encontrado ninguna mesa con dicho ID.");
            return;
        }
        reserva.setMesa(mesa);

        LocalDateTime nuevaHoraInicio = leerFecha("Nueva hora de inicio (dd/MM/yyyy HH:mm): ");
        LocalDateTime nuevaHoraFin = leerFecha("Nueva hora de fin (dd/MM/yyyy HH:mm): ");

        if (!nuevaHoraFin.isAfter(nuevaHoraInicio)) {
            System.out.println("La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        reserva.setHoraInicio(nuevaHoraInicio);
        reserva.setHoraFin(nuevaHoraFin);

        double coste = calcularCoste(reserva.getUsuario().getIdUsuario(), nuevaHoraInicio, nuevaHoraFin);
        reserva.setCoste(coste);

        reserva.setEstadoReserva(leerEstadoReserva());

        int filasActualizadas = reservaDAO.actualizarReserva(reserva, horaInicioOriginal);

        if (filasActualizadas > 0) {
            System.out.println("Reserva modificada correctamente.");
            System.out.println(reserva);
        } else {
            System.out.println("No se ha podido modificar la reserva.");
        }
    }

    public void cancelarReserva() {
        System.out.print("Introduce el teléfono del usuario: ");
        String telefono = scanner.nextLine().trim();

        if (telefono.isEmpty()) {
            System.out.println("Debes introducir un teléfono válido.");
            return;
        }

        List<Reserva> reservas = reservaDAO.obtenerReservasPorTelefono(telefono);

        if (reservas.isEmpty()) {
            System.out.println("No se han encontrado reservas para ese teléfono.");
            return;
        }

        System.out.println("Reservas activas encontradas:");
        for (Reserva r : reservas) {
            System.out.println(r);
        }

        int idMesa = leerEntero("ID de la mesa de la reserva a cancelar: ");
        LocalDateTime horaInicio = leerFecha("Hora de inicio de la reserva a cancelar (dd/MM/yyyy HH:mm): ");

        Reserva reserva = null;
        for (Reserva r : reservas) {
            if (r.getMesa().getIdMesa() == idMesa && r.getHoraInicio().equals(horaInicio)) {
                reserva = r;
                break;
            }
        }

        if (reserva == null) {
            System.out.println("No se ha encontrado ninguna reserva con esa mesa y hora de inicio.");
            return;
        }

        System.out.print("¿Estás seguro de que deseas cancelar esta reserva? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();

        if (confirmacion.equals("s")) {
            int filasActualizadas = reservaDAO.cancelarReserva(
                    reserva.getUsuario().getIdUsuario(),
                    reserva.getMesa().getIdMesa(),
                    reserva.getHoraInicio()
            );

            if (filasActualizadas > 0) {
                System.out.println("Reserva cancelada correctamente.");
            } else {
                System.out.println("No se ha podido cancelar la reserva.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private double calcularCoste(int idUsuario, LocalDateTime horaInicio, LocalDateTime horaFin) {
        long horas = ChronoUnit.HOURS.between(horaInicio, horaFin);
        if (horas <= 0) {
            horas = 1;
        }

        if (reservaDAO.esSocioActivo(idUsuario)) {
            System.out.println("El usuario es socio activo. Coste: 0.00€/h");
            return COSTE_HORA_SOCIO * horas;
        } else {
            double total = COSTE_HORA_NO_SOCIO * horas;
            System.out.println("El usuario no es socio. Coste: " + COSTE_HORA_NO_SOCIO + "€/h x " + horas + "h = " + total + "€");
            return total;
        }
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

    private String leerEstadoReserva() {
        System.out.println("Selecciona el estado de la reserva:");
        System.out.println("1. Confirmada");
        System.out.println("2. Cancelada");
        System.out.println("3. Completada");

        while (true) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1: return "confirmada";
                    case 2: return "cancelada";
                    case 3: return "completada";
                    default: System.out.println("Opción no válida. Elige 1, 2 o 3.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
            }
        }
    }
}
