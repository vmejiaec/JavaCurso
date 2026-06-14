import java.nio.file.*;
import java.net.URL;
import java.io.*;
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

        String url = "https://raw.githubusercontent.com/vmejiaec/JavaCurso/main/Leer/medicinas.txt";

        java.net.URL direccion = new java.net.URL(url);

        BufferedReader buffer =
            new BufferedReader(
                new InputStreamReader(
                    direccion.openStream()
                )
            );

        List<String> lineasUrl = buffer.lines().toList();


        for(String linea : lineasUrl){
            System.out.println(linea);

            String[] campos = linea.split(";");
            System.out.println(
                " - "+campos[0] +
                " " +campos[1]
            );
        }
    }
}

