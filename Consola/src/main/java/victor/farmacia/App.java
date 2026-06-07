package victor.farmacia;

import victor.farmacia.controller.UsuarioController;
import victor.farmacia.repository.UsuarioRepository;
import victor.farmacia.repository.jdbc.UsuarioRepositoryJdbc;
import victor.farmacia.service.UsuarioService;
import victor.farmacia.view.UsuarioView;

public class App {

    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioView usuarioView = new UsuarioView();
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);

        usuarioController.iniciar();
    }
}