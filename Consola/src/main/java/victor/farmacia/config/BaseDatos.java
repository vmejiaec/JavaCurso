package victor.farmacia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseDatos {

    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String URL = DOTENV.get("DB_URL");
    private static final String USER = DOTENV.get("DB_USER");
    private static final String PASSWORD = DOTENV.get("DB_PASSWORD");

    private BaseDatos() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}