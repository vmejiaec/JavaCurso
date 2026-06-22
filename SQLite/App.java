import java.sql.*;

public class App{
    public static void main(String[] args) {

        String url = "jdbc:sqlite:basedatos.db";
        try (Connection conn = DriverManager.getConnection(url)){
            Statement stmt = conn.createStatement();
            // Crear la tabla de medicinas
            String sqlCrearTabla = 
                " create table if not exists medicina (" +
                " codigo char(3)," +
                " nombre varchar(100) " +
                ")" 
            ;
            stmt.execute(sqlCrearTabla);
            // Insertar registros
            String sqlInsertaMedicina1 =
                " insert into medicina values (" +
                " 'A01','Aspirina'" +
                " )"
            ;
            String sqlInsertaMedicina2 =
                " insert into medicina values (" +
                " 'D05','Paracetamol'" +
                " )"
            ;

            stmt.execute(sqlInsertaMedicina1);
            stmt.execute(sqlInsertaMedicina2);

        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}