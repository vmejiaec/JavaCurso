import modelo.Figura;
import modelo.Cuadrado;
import modelo.Circulo;
import modelo.Triangulo;
import modelo.Fabrica;

import controlador.Fuente;
import java.util.List;
import java.util.ArrayList;

public class App{

   public static void main(String[] args){
        // Leer el archivo de figuras
            List<String> lineas = new ArrayList<>();

            try{
                Fuente fuente = new Fuente();
                lineas = fuente.leerArchivo("figuras.txt");
            }
            catch(Exception e){
                System.out.println("Error con la lectura del archivo "+ e.getMessage());
            }

            Fabrica fabrica = new Fabrica();

            List<Figura> listaFiguras = new ArrayList<>();
            for(String linea : lineas){
                listaFiguras.add(fabrica.crear(linea));
            }
            // Presentar las figuras en pantalla
            System.out.println("Lista de figuras: No "+listaFiguras.size());
            for (Figura figura: listaFiguras){
               System.out.println(figura.estado());
            }
   }
}











