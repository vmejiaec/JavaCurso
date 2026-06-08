import domain.*;
import io.*;
import services.*;

import java.util.*;

public class Saludos{

  public static void main(String[] args) throws Exception {
    System.out.println("Vamos a leer un archivo plano con la lista de personas");

    Entrada entrada = new Entrada();
    Salida salida = new Salida();
    Servicio servicio = new Servicio();

    List<String> datos = entrada.leerDatos();    
    List<Persona> personas = servicio.convertir(datos);
    salida.publicar(personas);
  }

}
