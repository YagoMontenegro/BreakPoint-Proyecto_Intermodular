package controller;

import view.MenuPrincipalView;

import java.util.Scanner;

public class GestionAppController {
    private Scanner scanner;
    private MenuPrincipalView menuPrincipalView;
    private UsuarioController usuarioController;
    private SocioController socioController;

    public GestionAppController() {
        this.scanner = new Scanner(System.in);
        this.menuPrincipalView = new MenuPrincipalView();
        this.usuarioController = new UsuarioController(scanner);
        this.socioController = new SocioController(scanner);
    }

    public void iniciarAplicacion() {
        int opcion = -1;

        do {
            menuPrincipalView.mostrarMenuPrincipal();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> usuarioController.iniciarMenuUsuario();
                    case 2 -> socioController.iniciarMenuSocio();
                    case 3 -> System.out.println("Módulo de cuotas no implementado aún.");
                    case 4 -> System.out.println("Módulo de mesas no implementado aún.");
                    case 5 -> System.out.println("Módulo de reservas no implementado aún.");
                    case 6 -> System.out.println("Módulo de torneos no implementado aún.");
                    case 7 -> System.out.println("Módulo de inscripciones no implementado aún.");
                    case 0 -> System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");
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
