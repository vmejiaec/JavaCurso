import java.util.*;
import java.nio.file.*;

public class Saludo{

  public static void main(String[] args) throws Exception  {

    System.out.println("Vamos a leer un archivo plano con la lista de personas");

    List<String> lineas = 
      Files.readAllLines(Paths.get("personas.txt"));   
    
    int n;
    n = lineas.size();
    String[][] datos = new String[n][2];

    for(int i=0; i<n ; i++){
      String linea;
      linea = lineas.get(i);
      String[] campos = linea.split(";");

      String nombre;
      String correo;

      nombre = campos[0];
      correo = campos[1];

      datos[i][0] = nombre;
      datos[i][1] = correo;
    }

    for (int i=0; i<n; i++){
      String nombre;
      String correo;

      nombre = datos[i][0];
      correo = datos[i][1];

      System.out.println("Saludo: "+nombre + " - " + correo);
    }
  }
}
