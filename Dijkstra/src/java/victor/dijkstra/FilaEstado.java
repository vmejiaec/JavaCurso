package victor.dijkstra;

import java.util.*;

public class FilaEstado{
    public String nodo;
    public int distancia;
    public boolean visitado;
    public List<String> camino;

    public FilaEstado(String nodo){
        this(nodo, 100, false, List.of());
    }

    public FilaEstado(String nodo, int distancia, boolean visitado, List<String> camino){
        this.nodo = nodo;
        this.distancia = distancia;
        this.visitado = visitado;
        this.camino = camino;
    }
}