import java.util.List;
import java.util.ArrayList;

public class Servicio{

  public List<Medicina> convertir( List<String> lineas ){
     // Desarma cada línea y construye la lista de medicinas
     List<Medicina> medicinas = new ArrayList<>();
     for (String linea : lineas){
        String[] campos = linea.split(";");
        Medicina medicina = new Medicina(campos[0] ,campos[1],campos[2] ,campos[3]  );
        medicinas.add(medicina);
     }
     return medicinas;
  }

}
