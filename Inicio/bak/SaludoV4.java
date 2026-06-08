import java.util.*;
import java.nio.file.*;

public class SaludoV4{

  public static void main(String[] args) throws Exception  {
    System.out.println("Vamos a leer un archivo plano con la lista de personas");

    List<String> lineas = Files.readAllLines(Paths.get("personasV3.txt"));
    
    List<Persona> personas ;
    personas = convertir(lineas);
    publicar(personas);
  }

  // Convertir una lista de personas a un arreglo de datos de personas
  public static List<Persona> convertir(List<String> lineas){
    int n;
    n = lineas.size();
    List<Persona> personas = new ArrayList<Persona>();

    for(int i=0; i<n ; i++){
      String linea;
      linea = lineas.get(i);

      Persona persona = new Persona(linea);
      personas.add(persona);
    }

    return personas;
  }

  // Publicar en consola la lista de personas y sus datos
  public static void publicar(List<Persona> personas){
    for(Persona persona : personas){
      System.out.println(persona.saludo());
    }
  }
}
