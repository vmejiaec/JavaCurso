import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class Entrada{

   public List<String> leerArchivo(String archivo) throws Exception{
      List<String> lineas;

      lineas = Files.readAllLines(Paths.get(archivo));

      return lineas;
   }

   public List<String> leerURL(String web, String recurso) throws Exception{
      List<String> lineas;

      String direccion = web + "/"+ recurso;
      URI uri = URI.create(direccion);
      URL url = uri.toURL();

      InputStream inputStream = url.openStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      lineas = bufferedReader.lines().toList();

      return lineas;
   }
}
