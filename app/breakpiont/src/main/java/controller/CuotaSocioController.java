package controller;

import dao.CuotaSocioDAO;
import dao.SocioDAO;
import model.CuotaSocio;
import model.Socio;
import view.CuotaSocioView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CuotaSocioController {
    private Scanner scanner;
    private CuotaSocioView cuotaSocioView;
    private CuotaSocioDAO cuotaSocioDAO;
    private SocioDAO socioDAO;

    public CuotaSocioController() {

    }

    public CuotaSocioController(Scanner scanner) {
        this.scanner = scanner;
        this.cuotaSocioView = new CuotaSocioView();
        this.cuotaSocioDAO = new CuotaSocioDAO();
        this.socioDAO = new SocioDAO();
    }

    public void iniciarMenuCuota() {
        int opcion = -1;

        do {
            cuotaSocioView.mostrarMenuCuotaSocio();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> generarCuota();
                    case 2 -> listarCuotas();
                    case 3 -> buscarCuotasPorSocio();
                    case 4 -> registrarPago();
                    case 5 -> eliminarCuota();
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

    public void generarCuota() {
        System.out.print("Introduce el teléfono del socio: ");
        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            Socio socio = socioDAO.obtenerSocioPorTelefono(telefono);

            if (socio != null) {
                if (socio.getEstadoSocio().equals("cancelado")) {
                    System.out.println("No se puede generar cuota a un socio cancelado.");
                    return;
                }

                int mes = leerEntero("Mes (1-12): ");
                if (mes < 1 || mes > 12) {
                    System.out.println("El mes debe estar entre 1 y 12.");
                    return;
                }

                int anio = leerEntero("Año: ");
                if (anio < 2000) {
                    System.out.println("El año no es válido.");
                    return;
                }

                double importe;
                if (socio.getEstadoSocio().equals("mantenimiento")) {
                    importe = 5.00;
                } else {
                    importe = 30.00;
                }

                CuotaSocio cuota = new CuotaSocio(
                        0,
                        socio,
                        null,
                        mes,
                        anio,
                        "pendiente",
                        importe
                );

                try {
                    int filasInsertadas = cuotaSocioDAO.insertarCuota(cuota);
                    if (filasInsertadas > 0) {
                        System.out.println("Cuota generada correctamente. Importe: " + importe + "€");
                    } else {
                        System.out.println("No se ha podido generar la cuota.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al generar la cuota en la base de datos.");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("No se ha encontrado ningún socio con ese teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void listarCuotas() {
        System.out.println("LISTADO DE CUOTAS");
        List<CuotaSocio> cuotas = cuotaSocioDAO.obtenerCuotas();
        if (cuotas.isEmpty()) {
            System.out.println("No hay cuotas registradas.");
        } else {
            for (CuotaSocio cuota : cuotas) {
                System.out.println(cuota);
            }
        }
    }

    public void buscarCuotasPorSocio() {
        System.out.print("Introduce el teléfono del socio: ");

        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            List<CuotaSocio> cuotas = cuotaSocioDAO.obtenerCuotasPorTelefono(telefono);

            if (!cuotas.isEmpty()) {
                for (CuotaSocio cuota : cuotas) {
                    System.out.println(cuota);
                }
            } else {
                System.out.println("No se han encontrado cuotas para ese teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void registrarPago() {
        System.out.print("Introduce el teléfono del socio: ");
        String telefono = scanner.nextLine();

        if (!telefono.isEmpty()) {
            List<CuotaSocio> cuotas = cuotaSocioDAO.obtenerCuotasPorTelefono(telefono);

            if (!cuotas.isEmpty()) {
                System.out.println("Cuotas pendientes:");
                boolean hayPendientes = false;

                for (CuotaSocio cuota : cuotas) {
                    if (!cuota.getEstadoCuota().equals("pagada")) {
                        System.out.println(cuota);
                        hayPendientes = true;
                    }
                }

                if (!hayPendientes) {
                    System.out.println("Este socio no tiene cuotas pendientes.");
                    return;
                }

                int idCuota = leerEntero("Introduce el ID de la cuota a pagar: ");

                int filasActualizadas = cuotaSocioDAO.registrarPago(idCuota);

                if (filasActualizadas > 0) {
                    System.out.println("Pago registrado correctamente.");
                } else if (filasActualizadas == 0) {
                    System.out.println("La cuota ya estaba pagada o no se ha encontrado.");
                } else {
                    System.out.println("No se ha podido registrar el pago.");
                }
            } else {
                System.out.println("No se han encontrado cuotas para ese teléfono.");
            }
        } else {
            System.out.println("Debes introducir un teléfono válido.");
        }
    }

    public void eliminarCuota() {
        int idCuota = leerEntero("Introduce el ID de la cuota a eliminar: ");

        CuotaSocio cuota = cuotaSocioDAO.obtenerCuotaPorId(idCuota);

        if (cuota != null) {
            int filasEliminadas = cuotaSocioDAO.eliminarCuota(idCuota);

            if (filasEliminadas > 0) {
                System.out.println("Cuota eliminada correctamente.");
            } else {
                System.out.println("No se ha podido eliminar la cuota.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna cuota con ese ID.");
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
                return numero;
            } else {
                System.out.println("Debes introducir un número válido.");
                scanner.nextLine();
            }
        }
    }
}
