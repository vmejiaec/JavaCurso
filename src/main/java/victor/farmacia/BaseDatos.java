package victor.farmacia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String url;
    private String usuario;
    private String contraseña;

    public BaseDatos(String url, String usuario, String contraseña) {
        this.url = url;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Connection conectar() throws Exception {
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    public List<Usuario> obtenerUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nombre, email FROM usuario";

        try (
            Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                Usuario u = new Usuario(id, nombre, email);

                usuarios.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}