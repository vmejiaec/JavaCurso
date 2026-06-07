package victor.dijkstra;

import java.util.*;

public class App{
    public static void main(String[] args){
        // Cargar el grafo
        Grafo grafo = new Grafo();
        
        grafo.addNodo("A","B", 7);
        grafo.addNodo("A","C", 9);
        grafo.addNodo("A","D", 14);
        grafo.addNodo("B","D", 10);
        grafo.addNodo("B", "E", 15);
        grafo.addNodo("C", "D", 2);
        grafo.addNodo("C", "F", 11);
        grafo.addNodo("D", "E", 6);
        grafo.addNodo("D", "F", 9);
        grafo.addNodo("E", "F", 4);

        System.out.println("Vecinos: "+ grafo.vecinos);

        // Ejecutar el algoritmo de Dijkstra
        Dijkstra programa = new Dijkstra();
        Map<String, String> resultado = programa.ejecutar(grafo, "A", "F");

        System.out.println("Resultado: "+resultado);
    }
}