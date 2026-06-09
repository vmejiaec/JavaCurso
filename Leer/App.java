import java.nio.file.*;
import java.util.*;

public class App{
    public static void main(String[] args) throws Exception {
        String archivo = "medicinas.txt";
        List<String> lineas = Files.readAllLines(Paths.get(archivo));
        
        for(String linea : lineas){
            System.out.println(linea);

            String[] campos = linea.split(";");
            System.out.println(
                " - "+campos[0] +
                " " +campos[1]
            );

        }
    }
}