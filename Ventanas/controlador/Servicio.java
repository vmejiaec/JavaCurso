package controlador;

import modelo.Medicina;

import java.util.ArrayList;
import java.util.List;

public class Servicio{

  public List<Medicina> convertir( List<String> lineas ){
     // Desarma cada línea y construye la lista de medicinas
     List<Medicina> medicinas = new ArrayList<>();
     for (String linea : lineas){
        String[] campos = linea.split(";");
        Medicina medicina = new Medicina(campos[0] ,campos[1],campos[2] ,campos[3] , campos[4], campos[5] );
        medicinas.add(medicina);
     }
     return medicinas;
  }

}
