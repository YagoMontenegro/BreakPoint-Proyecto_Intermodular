package controller;

import view.MenuPrincipalView;

import java.util.Scanner;

public class GestionAppController {
    private Scanner scanner;
    private MenuPrincipalView menuPrincipalView;

    public GestionAppController() {
        this.scanner = new Scanner(System.in);
        this.menuPrincipalView = new MenuPrincipalView();
    }

    public void iniciarAplicacion() {
        int opcion = -1;

        do {
            menuPrincipalView.mostrarMenuPrincipal();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> new UsuarioController(scanner).iniciarMenuUsuario();
                    case 2 -> new SocioController(scanner).iniciarMenuSocio();
                    case 3 -> new CuotaSocioController(scanner).iniciarMenuCuota();
                    case 4 -> new MesaController(scanner).iniciarMenuMesa();
                    //case 5 -> new ReservaController(scanner).iniciarMenuReserva();
                    case 6 -> new TorneoController(scanner).iniciarMenuTorneo();
                    case 7 -> new InscripcionController(scanner).iniciarMenuInscripcion();
                    case 0 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
                opcion = -1;
            }

        } while (opcion != 0);

        scanner.close();
    }
}
