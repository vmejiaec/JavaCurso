import java.sql.*;

public class CrearDB{
   public static void main (String[] args){
      String url = "jdbc:sqlite:figuras.db";

      try(Connection conn = DriverManager.getConnection(url)){

        String sqlBorrarTabla =
            "drop table if exists figura ";

        PreparedStatement stmtBorrar = conn.prepareStatement(sqlBorrarTabla);
        stmtBorrar.execute();
        System.out.println("Borrar la tabla");

        String sqlCrearTabla =
            "create table if not exists figura (" +
            "  tipo varchar(50)," +
            "  nombre varchar(50)" +
            ")";
        PreparedStatement stmtCrearTabla = conn.prepareStatement(sqlCrearTabla);
        stmtCrearTabla.execute();
        System.out.println("Crear la tabla");

        // Verifica si existen registros en la tabla
        String sqlConsulta = "select count(*) noregs from figura";
        PreparedStatement stmtContar = conn.prepareStatement(sqlConsulta);
        ResultSet rs = stmtContar.executeQuery();
        int noFilas = rs.getInt("noregs");

        if (noFilas == 0){
            String sqlInsertaRegistros =
                "insert into figura values "+
                "  ('Cuadrado', 'Dado' )," +
                "  ('Triangulo', 'Dorito' )," +
                "  ('Circulo', 'Ficha' ) " +
                "";
            PreparedStatement stmtInsertRegs = conn.prepareStatement(sqlInsertaRegistros);
            stmtInsertRegs.execute();
            System.out.println("Registros insertados");
        }
      }
      catch(Exception e){
         System.out.println("Error: "+e.getMessage());
      }
   }
}
