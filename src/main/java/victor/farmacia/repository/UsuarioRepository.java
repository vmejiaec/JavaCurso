package victor.farmacia.repository;

import victor.farmacia.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository{
    List<Usuario> listarTodos();
    List<Usuario> buscarPorNombre(String nombre);
    Optional<Usuario> buscarPorId(Integer id);

    boolean crear(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(Integer id);
}