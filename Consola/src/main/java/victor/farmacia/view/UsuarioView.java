package victor.farmacia.view;

import victor.farmacia.dto.UsuarioDto;

import java.util.List;
import java.util.Scanner;

public class UsuarioView {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println();
        System.out.println("==== GESTION DE USUARIOS ====");
        System.out.println("1. Listar usuarios");
        System.out.println("2. Buscar usuario por nombre");
        System.out.println("3. Crear usuario");
        System.out.println("4. Editar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public int leerOpcion() {
        return Integer.parseInt(scanner.nextLine());
    }

    public int pedirId() {
        System.out.print("Id: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String pedirNombre() {
        System.out.print("Nombre: ");
        return scanner.nextLine();
    }

    public String pedirEmail() {
        System.out.print("Email: ");
        return scanner.nextLine();
    }

    public boolean pedirConfirmacion(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        return "s".equalsIgnoreCase(scanner.nextLine());
    }

    public void mostrarUsuarios(List<UsuarioDto> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios para mostrar.");
            return;
        }

        System.out.println("ID | NOMBRE | EMAIL");
        for (UsuarioDto usuario : usuarios) {
            System.out.println(usuario.id() + " | " + usuario.nombre() + " | " + usuario.email());
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.err.println(error);
    }
}