package victor.farmacia.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.farmacia.config.BaseDatos;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.UsuarioRepository;

import java.sql.*;
import java.util.*;

public class UsuarioRepositoryJdbc implements UsuarioRepository{
    private static final Logger logger = 
        LoggerFactory.getLogger(UsuarioRepositoryJdbc.class);

    @Override
    public List<Usuario> listarTodos(){
        String sql = "Select id, nombre, email from usuario ";
        List<Usuario> usuarios = new ArrayList<>();

        try(
            Connection connection = BaseDatos.obtenerConexion();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                usuarios.add(new Usuario(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("email")
                ));
            }

            logger.info(
                "listarTodos() ejecutado. Registros: {}",
                usuarios.size());
            
            return usuarios;
        } catch(SQLException e){
            logger.error(
                "Error al listarTodos() ", e);
            return List.of();
        }
    }

        @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre, email FROM usuario WHERE nombre LIKE ? ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + nombre + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    usuarios.add(new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email")
                    ));
                }
            }

            logger.info("buscarPorNombre '{}' ejecutado. Registros: {}", nombre, usuarios.size());
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al buscar usuario por nombre", e);
            return List.of();
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, email FROM usuario WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email")
                    );
                    logger.info("buscarPorId {} encontrado", id);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar usuario por id {}", id, e);
        }

        logger.info("buscarPorId {} sin resultados", id);
        return Optional.empty();
    }

    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuario(nombre, email) VALUES (?, ?)";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            boolean creado = statement.executeUpdate() > 0;
            logger.info("crear usuario '{}' -> {}", usuario.getNombre(), creado);
            return creado;
        } catch (SQLException e) {
            logger.error("Error al crear usuario '{}'", usuario.getNombre(), e);
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, email = ? WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setInt(3, usuario.getId());
            boolean actualizado = statement.executeUpdate() > 0;
            logger.info("actualizar id {} -> {}", usuario.getId(), actualizado);
            return actualizado;
        } catch (SQLException e) {
            logger.error("Error al actualizar id {}", usuario.getId(), e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            boolean eliminado = statement.executeUpdate() > 0;
            logger.info("eliminar id {} -> {}", id, eliminado);
            return eliminado;
        } catch (SQLException e) {
            logger.error("Error al eliminar id {}", id, e);
            return false;
        }
    }
}