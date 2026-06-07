package victor.dijkstra;

import java.util.*;

public class Dijkstra{
    public Map<String, String> ejecutar (Grafo grafo, String nodoInicio, String nodoFin){

        // Crear la tabla del estado con su nodo y su fila
        Map<String, FilaEstado> tablaEstado = new LinkedHashMap<>();
        for (String nodo : grafo.vecinos.keySet()){
            tablaEstado.put(nodo, new FilaEstado(nodo));  // Con distancias 100 y no visitados
        }
        // A la primera fila poner distancia 0
        tablaEstado.get(nodoInicio).distancia = 0;

        for (String nodo: tablaEstado.keySet()){
            System.out.println("nodo: "+ nodo +" "+ tablaEstado.get(nodo).distancia + " "+ tablaEstado.get(nodo).visitado );
        }

        // Se prepara el mapa de resultados
        Map <String, String> previo = new LinkedHashMap<>();

        while (true){
            // buscar el nodo no visitado con la menor distancia
            String nodoActual = null;
            int menor = 100 +1;
            for (FilaEstado fila : tablaEstado.values()){
                if (!fila.visitado && fila.distancia < menor){
                    menor = fila.distancia;
                    nodoActual = fila.nodo;
                }
            }
            // Si no se encontró ninguno, ya no quedan mejoras por hacer.
            if(nodoActual == null){
                break;
            }

            // En la fila del nodo actual, se marca como visitado.
            FilaEstado filaActual = tablaEstado.get(nodoActual);
            filaActual.visitado = true;

            // Relajar las aristas vecinas.
            for(Map.Entry<String, Integer> vecino : grafo.vecinos.get(nodoActual).entrySet()){
                String nodoVecino = vecino.getKey();
                int peso = vecino.getValue();
                FilaEstado filaVecina = tablaEstado.get(nodoVecino);

                if(filaVecina.visitado){
                    continue;
                }

                // Si el nodo actual sigue en 100, todavía no se alcanzó desde el nodo de inicio.
                if(filaActual.distancia >= 100){
                    continue;
                }

                int nuevaDistancia = filaActual.distancia + peso;
                if(nuevaDistancia < filaVecina.distancia){
                    filaVecina.distancia = nuevaDistancia;
                    previo.put(nodoVecino, nodoActual);
                }
            }

            // Si ya llegamos al nodo final, podemos salir.
            if(nodoActual.equals(nodoFin)){
                break;
            }
        }

        return previo;
    }
}