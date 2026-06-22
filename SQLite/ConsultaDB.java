import java.sql.*;

public class ConsultaDB{
    public static void main (String[] args){
        String url = "jdbc:sqlite:figuras.db";

        try(Connection conn = DriverManager.getConnection(url)){
            String sqlConsulta = "select tipo, nombre from figura";
            PreparedStatement stmt = conn.prepareStatement(sqlConsulta);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("tipo")+ " " + rs.getString("nombre"));
            }
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
