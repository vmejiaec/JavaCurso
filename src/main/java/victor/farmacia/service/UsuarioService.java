package victor.farmacia.service;

import victor.farmacia.dto.UsuarioDto;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UsuarioService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de busqueda no puede estar vacio");
        }
        return usuarioRepository.buscarPorNombre(nombre.trim())
                .stream()
                .map(this::aDto)
                .toList();
    }

    public boolean crear(String nombre, String email) {
        validarNombre(nombre);
        validarEmail(email);
        return usuarioRepository.crear(new Usuario(nombre.trim(), email.trim()));
    }

    public boolean actualizar(Integer id, String nombre, String email) {
        validarId(id);
        validarNombre(nombre);
        validarEmail(email);
        return usuarioRepository.actualizar(new Usuario(id, nombre.trim(), email.trim()));
    }

    public boolean eliminar(Integer id) {
        validarId(id);
        return usuarioRepository.eliminar(id);
    }

    public Optional<UsuarioDto> buscarPorId(Integer id) {
        validarId(id);
        return usuarioRepository.buscarPorId(id).map(this::aDto);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que cero");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }
    }

    private UsuarioDto aDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}