import java.nio.file.*;
import java.util.*;

public class App{
  public static void main(String [] args) throws Exception{
     List<String> lineas = Files.readAllLines(Paths.get("medicinas.txt"));
     for (String linea : lineas)
        System.out.println(linea);
  }
}
