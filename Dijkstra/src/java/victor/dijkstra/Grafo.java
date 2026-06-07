package victor.dijkstra;

import java.util.*;

public class Grafo{
    
    // El nodo, sus vecinos y las distancias que los separan
    public Map<String, Map<String , Integer>> vecinos = new LinkedHashMap<>();
    
    // Agregar nodo al grafo
    public void addNodo(String nodo){
        vecinos.putIfAbsent(nodo, new LinkedHashMap<>());
    }

    // Agregar nodo al grafo con sus vecinos y distancias
    public void addNodo(String nodoA, String nodoB, int distancia){
        addNodo(nodoA);
        addNodo(nodoB);
        vecinos.get(nodoA).put(nodoB,distancia);
        vecinos.get(nodoB).put(nodoA,distancia);
    }

    
}