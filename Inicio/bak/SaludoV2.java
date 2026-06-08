import java.util.*;
import java.nio.file.*;

public class Saludo{

  public static void main(String[] args) throws Exception  {
    System.out.println("Vamos a leer un archivo plano con la lista de personas");

    List<String> lineas = Files.readAllLines(Paths.get("personas.txt"));   
    
    String[][] datos ;
    datos = convertir(lineas);
    publicar(datos);
  }

  // Convertir una lista de personas a un arreglo de datos de personas
  public static String[][] convertir(List<String> lineas){
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

    return datos;
  }

  // Publicar en consola la lista de personas y sus datos
  public static void publicar(String[][] datos){
    int n;
    n = datos.length;

    for (int i=0; i<n; i++){
      String nombre;
      String correo;

      nombre = datos[i][0];
      correo = datos[i][1];

      System.out.println("Saludo: "+nombre + " - " + correo);
    }
  }
}
