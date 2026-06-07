package victor.farmacia.service;

import victor.farmacia.dto.UsuarioDto;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDto> listarTodos() {
        return usuarioRepository.listarTodos()
                .stream()
                .map(this::aDto)
                .toList();
    }

    public List<UsuarioDto> buscarPorNombre(String nombre) {
        return usuarioRepository.buscarPorNombre(nombre)
                .stream()
                .map(this::aDto)
                .toList();
    }

    public boolean crear(String nombre, String email) {
        return usuarioRepository.crear(new Usuario(nombre, email));
    }

    public boolean actualizar(Integer id, String nombre, String email) {
        return usuarioRepository.actualizar(new Usuario(id, nombre, email));
    }

    public boolean eliminar(Integer id) {
        return usuarioRepository.eliminar(id);
    }

    public Optional<UsuarioDto> buscarPorId(Integer id) {
        return usuarioRepository.buscarPorId(id).map(this::aDto);
    }

    private UsuarioDto aDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}