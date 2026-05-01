package controller;

import dao.MesaDAO;
import model.Mesa;
import view.MesaView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MesaController {
    private Scanner scanner;
    private MesaView mesaView;
    private MesaDAO mesaDAO;

    public MesaController() {

    }

    public MesaController(Scanner scanner) {
        this.scanner = scanner;
        this.mesaView = new MesaView();
        this.mesaDAO = new MesaDAO();
    }

    public void iniciarMenuMesa() {
        int opcion = -1;

        do {
            mesaView.mostrarMenuMesa();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaMesa();
                    case 2 -> listarMesas();
                    case 3 -> buscarMesa();
                    case 4 -> modificarEstadoMesa();
                    case 5 -> eliminarMesa();
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

    public void darAltaMesa() {
        Mesa.EstadoMesa estado = leerEstadoMesa();

        Mesa mesa = new Mesa(0, estado);

        try {
            int filasInsertadas = mesaDAO.insertarMesa(mesa);
            if (filasInsertadas > 0) {
                System.out.println("Mesa dada de alta correctamente.");
            } else {
                System.out.println("No se ha podido dar de alta la mesa.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar mesa en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarMesas() {
        System.out.println("LISTADO DE MESAS");
        List<Mesa> mesas = mesaDAO.obtenerMesas();
        if (mesas.isEmpty()) {
            System.out.println("No hay mesas registradas.");
        } else {
            for (Mesa mesa : mesas) {
                System.out.println(mesa);
            }
        }
    }

    public void buscarMesa() {
        int idMesa = leerEntero("Introduce el ID de la mesa a buscar: ");

        Mesa mesa = mesaDAO.obtenerMesaPorId(idMesa);

        if (mesa != null) {
            System.out.println(mesa);
        } else {
            System.out.println("No se ha encontrado ninguna mesa con dicho ID.");
        }
    }

    public void modificarEstadoMesa() {
        int idMesa = leerEntero("Introduce el ID de la mesa a modificar: ");

        Mesa mesa = mesaDAO.obtenerMesaPorId(idMesa);

        if (mesa != null) {
            System.out.println(mesa);

            Mesa.EstadoMesa nuevoEstado = leerEstadoMesa();
            mesa.setEstadoMesa(nuevoEstado);

            int filasActualizadas = mesaDAO.actualizarEstadoMesa(mesa);

            if (filasActualizadas > 0) {
                System.out.println("Estado de mesa modificado correctamente.");
                System.out.println(mesa);
            } else {
                System.out.println("No se ha podido modificar el estado de la mesa.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna mesa con dicho ID.");
        }
    }

    public void eliminarMesa() {
        int idMesa = leerEntero("Introduce el ID de la mesa a eliminar: ");

        Mesa mesa = mesaDAO.obtenerMesaPorId(idMesa);

        if (mesa != null) {
            System.out.println(mesa);
            System.out.print("¿Estás seguro de que deseas eliminar esta mesa? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) {
                int filasEliminadas = mesaDAO.eliminarMesa(idMesa);

                if (filasEliminadas > 0) {
                    System.out.println("Mesa eliminada correctamente.");
                } else {
                    System.out.println("No se ha podido eliminar la mesa.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna mesa con dicho ID.");
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

    private Mesa.EstadoMesa leerEstadoMesa() {
        System.out.println("Selecciona el estado de la mesa:");
        System.out.println("1. Disponible");
        System.out.println("2. Reservada");
        System.out.println("3. Mantenimiento");

        while (true) {
            System.out.print("Opción: ");
            if (scanner.hasNextInt()) {
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1: return Mesa.EstadoMesa.disponible;
                    case 2: return Mesa.EstadoMesa.reservada;
                    case 3: return Mesa.EstadoMesa.mantenimiento;
                    default: System.out.println("Opción no válida. Elige 1, 2 o 3.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
            }
        }
    }
}
