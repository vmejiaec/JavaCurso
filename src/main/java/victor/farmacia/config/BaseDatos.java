package victor.farmacia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos{
    private static final String URL = System.getProperty(
        "db.url",
        "jdbc:mysql://localhost:3306/farmacia"
    );
    private static final String USER = System.getProperty(
        "db.user",
        "root"
    );
    private static final String PASSWORD = System.getProperty(
        "db.password",
        "root"
    );

    private BaseDatos(){

    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}