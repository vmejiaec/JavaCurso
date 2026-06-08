import java.util.*;
import java.nio.file.*;

public class SaludoV3{

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
      String[] campos = linea.split(";");

      Persona persona = new Persona();

      persona.nombre = campos[0];
      persona.correo = campos[1];
      persona.telefono = campos[2];
      persona.titulo = campos[3];

      personas.add(persona);
    }

    return personas;
  }

  // Publicar en consola la lista de personas y sus datos
  public static void publicar(List<Persona> personas){
    int n;
    n = personas.size();

    for (int i=0; i<n; i++){
      Persona persona = personas.get(i);
      System.out.println(persona.saludo());
    }
  }
}
