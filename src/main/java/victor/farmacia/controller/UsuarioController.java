package victor.farmacia.controller;

import victor.farmacia.dto.UsuarioDto;
import victor.farmacia.service.UsuarioService;
import victor.farmacia.view.UsuarioView;

import java.util.List;
import java.util.Optional;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioView usuarioView;

    public UsuarioController(UsuarioService usuarioService, UsuarioView usuarioView) {
        this.usuarioService = usuarioService;
        this.usuarioView = usuarioView;
    }

    public void iniciar() {
        int opcion;
        do {
            usuarioView.mostrarMenu();
            opcion = usuarioView.leerOpcion();
            procesar(opcion);
        } while (opcion != 0);
    }

    private void procesar(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    listar();
                    break;
                case 2:
                    buscar();
                    break;
                case 3:
                    crear();
                    break;
                case 4:
                    editar();
                    break;
                case 5:
                    eliminar();
                    break;
                case 0:
                    usuarioView.mostrarMensaje("Saliendo del sistema...");
                    break;
                default:
                    usuarioView.mostrarError("Opcion invalida.");
            }
        } catch (IllegalArgumentException e) {
            usuarioView.mostrarError("Validacion: " + e.getMessage());
        } catch (Exception e) {
            usuarioView.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private void listar() {
        List<UsuarioDto> usuarios = usuarioService.listarTodos();
        usuarioView.mostrarUsuarios(usuarios);
    }

    private void buscar() {
        String nombre = usuarioView.pedirNombre();
        usuarioView.mostrarUsuarios(usuarioService.buscarPorNombre(nombre));
    }

    private void crear() {
        boolean ok = usuarioService.crear(usuarioView.pedirNombre(), usuarioView.pedirEmail());
        usuarioView.mostrarMensaje(ok ? "Usuario creado correctamente." : "No se pudo crear el usuario.");
    }

    private void editar() {
        int id = usuarioView.pedirId();
        Optional<UsuarioDto> existente = usuarioService.buscarPorId(id);

        if (existente.isEmpty()) {
            usuarioView.mostrarError("No existe un usuario con ese id.");
            return;
        }

        boolean ok = usuarioService.actualizar(id, usuarioView.pedirNombre(), usuarioView.pedirEmail());
        usuarioView.mostrarMensaje(ok ? "Usuario actualizado correctamente." : "No se pudo actualizar el usuario.");
    }

    private void eliminar() {
        int id = usuarioView.pedirId();
        Optional<UsuarioDto> existente = usuarioService.buscarPorId(id);

        if (existente.isEmpty()) {
            usuarioView.mostrarError("No existe un usuario con ese id.");
            return;
        }

        boolean confirmar = usuarioView.pedirConfirmacion("Confirma eliminar a " + existente.get().nombre() + "?");
        if (!confirmar) {
            usuarioView.mostrarMensaje("Operacion cancelada.");
            return;
        }

        boolean ok = usuarioService.eliminar(id);
        usuarioView.mostrarMensaje(ok ? "Usuario eliminado correctamente." : "No se pudo eliminar el usuario.");
    }
}