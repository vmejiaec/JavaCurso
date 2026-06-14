import java.util.*;

public class App{
  public static void main(String [] args) throws Exception{
     Fuente fuente = new Fuente();
     List<String> lineas = fuente.leerURL("https://raw.githubusercontent.com/vmejiaec/JavaCurso/refs/heads/main/Leer/medicinas.txt");
     List<Medicina> medicinas = new ArrayList<>();

     Servicio servicio = new Servicio();
     medicinas = servicio.convertir(lineas);

     // Publicar la lista de medicinas
     for (Medicina medicina : medicinas){
        System.out.println(medicina.toString());
     }
  }
}
