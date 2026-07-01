package modelo;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;


public class Fuente{
   // Lee un archivo y devuelve una lista de líneas
   public List<String> leerArchivo(String archivo)throws Exception{
       List<String> lineas = Files.readAllLines(Paths.get(archivo));
       return lineas;
   }

   // Lee una URL y devuelve una lista de líneas
   public List<String> leerURL(String url)throws Exception{
      URI uri = URI.create(url);
      URL direccion = uri.toURL();
      InputStream stream = direccion.openStream();
      InputStreamReader lector = new InputStreamReader(stream);
      BufferedReader buffer = new BufferedReader(lector);
      List<String> lineas = buffer.lines().toList();
      return lineas;
   }
   // Lee la base de datos local
   public List<Medicina> leerDBMedicina() throws SQLException {

    String url = "jdbc:mysql://localhost:3306/farmacias";
    String usuario = "root";
    String clave = "";

    String sql = "select codigo,nombre_comercial, tipo,cantidad,precio, farmaceutica from medicamento";

    List<Medicina> medicinas = new ArrayList<>();
    try {
        Connection conexion = DriverManager.getConnection(url, usuario, clave);
        Statement sentencia = conexion.createStatement();
        ResultSet resultados = sentencia.executeQuery(sql);

        while (resultados.next()) {

            Medicina medicina = new Medicina(
                    resultados.getString("codigo"),
                    resultados.getString("nombre_comercial"),
                    resultados.getString("farmaceutica"),
                    resultados.getString("tipo"),
                    resultados.getInt("cantidad"),
                    resultados.getDouble("precio")
            );
            medicinas.add(medicina);
        }
    } catch(Exception e){
        System.out.println("Error: "+e.getMessage());
    }
    return medicinas;
}

}
