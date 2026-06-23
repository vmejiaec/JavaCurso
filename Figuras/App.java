import modelo.Cuadrado;
import modelo.Circulo;
import modelo.Triangulo;

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

            List<Cuadrado> listaCuadrados = new ArrayList<>();
            List<Circulo> listaCirculos = new ArrayList<>();
            List<Triangulo> listaTriangulos = new ArrayList<>();

            for(String linea : lineas){
                // Partir la linea
                Object[] campos = linea.split(";");

                String tipoFigura = campos[0].toString();
                switch(tipoFigura){
                    case "Cuadrado":
                        Cuadrado cuadrado = new Cuadrado(campos[1].toString());
                        listaCuadrados.add(cuadrado);
                        break;
                    case "Triangulo":
                        Triangulo triangulo = new Triangulo(
                            campos[1].toString(),
                            campos[2].toString(),
                            campos[3].toString(),
                            campos[4].toString(),
                            campos[5].toString()
                        );
                        listaTriangulos.add(triangulo);
                        break;
                    case "Circulo":
                        Circulo circulo = new Circulo(campos[1].toString());
                        listaCirculos.add(circulo);
                        break;
                }
            }
            // Presentar las tres listas en pantalla
            // - Lista de Cuadrados
            System.out.println("Lista de cuadrados: No "+listaCuadrados.size());
            for (Cuadrado cuadrado: listaCuadrados){
               System.out.println(cuadrado.estado());
            }
            // - Lista de Triángulos
            System.out.println("Lista de triángulos: No "+listaTriangulos.size());
            for (Triangulo triangulo: listaTriangulos){
               System.out.println(triangulo.estado());
            }
            // - Lista de Circulos
            System.out.println("Lista de círculos: No "+listaCirculos.size());
            for (Circulo circulo: listaCirculos){
               System.out.println(circulo.estado());
            }
   }
}











