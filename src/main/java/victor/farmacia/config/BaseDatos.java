package victor.farmacia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseDatos {

    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String URL = leerConfig("db.url", "DB_URL");
    private static final String USER = leerConfig("db.user", "DB_USER");
    private static final String PASSWORD = leerConfig("db.password", "DB_PASSWORD");

    private BaseDatos() {
    }

    private static String leerConfig(String systemKey, String envKey) {
        String valorSistema = System.getProperty(systemKey);
        if (valorSistema != null && !valorSistema.isBlank()) {
            return valorSistema;
        }

        String valorDotenv = DOTENV.get(envKey);
        if (valorDotenv != null && !valorDotenv.isBlank()) {
            return valorDotenv;
        }

        throw new IllegalStateException(
                "Falta configuracion de BD: define -D" + systemKey + " o la variable " + envKey + " en .env"
        );
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}