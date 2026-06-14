import java.nio.file.*;
import java.util.List;
import java.net.*;
import java.io.*;

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

}
