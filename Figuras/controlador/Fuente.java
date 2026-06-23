package controlador;

import java.nio.file.*;
import java.util.List;
import java.net.*;
import java.io.*;

public class Fuente{
   // Lee un archivo y devuelve una lista de líneas
   public List<String> leerArchivo(String archivo)throws Exception{

       return Files.readAllLines(Paths.get(archivo));
   }

}
