import java.sql.*;

public class Leer{
    public static void main(String[] args) {
        String url = "jdbc:sqlite:basedatos.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt =  conn.createStatement();
            String sqlConsulta = 
                " select codigo, nombre from medicina "
            ;
            try {
                ResultSet rs = stmt.executeQuery(sqlConsulta);
                while (rs.next()) { 
                    System.out.println("Codigo: "+ rs.getString("codigo"));
                }
            } catch (Exception e) {
                System.err.println(" Error: Al leer cada medicina " +e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(" Error: Al abrir la base " +e.getMessage());
        }
    }
}