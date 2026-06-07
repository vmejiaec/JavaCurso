# JavaDijkstra
Versión simple de Dijkstra para grafos ponderados
Dijkstra							
Grafo							
	Nodos						
		A					
		B					
		C					
		D					
		E					
		F					
	Vertices						
		A,B	7				
		A,C	9				
		A,D	14				
		B,D	10				
		B,E	15				
		C,D	2				
		C,F	11				
		D,E	6				
		D,F	9				
		E,F	4				
							
Estado 1	Nodo	d	visitado	camino			
	A	0	No				
	B	100	No				
	C	100	No				
	D	100	No				
	E	100	No				
	F	100	No				
							
Estado 2	Nodo	d	visitado	camino			
	A	0	Si				
					A,B	7	7
					A,C	9	9
					A,D	14	14
	B	7	No	A,B			
	C	9	No	A,C			
	D	14	No	A,D			
	E	100	No				
	F	100	No				
							
Estado 3	Nodo	d	visitado	camino			
	A	0	Si				
	B	7	Si	A,B			
					B,D	10	17
					B,E	15	22
	C	9	No	A,C			
	D	14	No	A,D			
	E	22	No	A,B-B,E			
	F	100	No				
							
Estado 3	Nodo	d	visitado	camino			
	A	0	Si				
	B	7	Si	A,B			
					B,D	10	17
					B,E	15	22
	C	9	Si	A,C			
					C,D	2	11
					C,F	11	20
	D	11	No	A,C - C, D			
	E	22	No	A,B - B,E			
	F	20	No	A,C - C,F			
							
Estado 4	Nodo	d	visitado	camino			
	A	0	Si				
	B	7	Si	A,B			
					B,D	10	17
					B,E	15	22
	C	9	Si	A,C			
					C,D	2	11
					C,F	11	20
	D	11	Si	A,C - C,D			
					D,E	6	17
					D,F	9	20
	E	17	No	A,C - C,D - D,E			
	F	20	No	A,C - C,F			
							
Estado 5	Nodo	d	visitado	camino			
	A	0	Si				
	B	7	Si	A,B			
					B,D	10	17
					B,E	15	22
	C	9	Si	A,C			
					C,D	2	11
					C,F	11	20
	D	11	Si	A,C - C,D			
					D,E	6	17
					D,F	9	20
	E	17	Si	A,C - C,D - D,E			
					E,F	4	21
	F	20	No	A,C - C,F			



package victor.dijkstra;

import java.util.LinkedHashMap;
import java.util.Map;

// Clase que contiene la lógica del algoritmo de Dijkstra.
public class Dijkstra{

    public Map<String, String> ejecutar(Grafo grafo, String nodoInicio, String nodoFin){

        // Crear la tabla Estado.
        // tablaEstado guarda una fila por nodo con su mejor distancia conocida.
        Map<String, FilaEstado> tablaEstado = new LinkedHashMap<>();
        for(String nodo : grafo.vecinos.keySet()){
            tablaEstado.put(nodo, new FilaEstado(nodo));
        }

        // El nodo de inicio siempre comienza en 0.
        tablaEstado.get(nodoInicio).distancia = 0;

        // El mapa previo recuerda desde qué nodo se llegó al mejor valor.
        Map<String, String> previo = new LinkedHashMap<>();

        while(true){
            // Busca el nodo no visitado con la menor distancia.
            String actual = null;
            int menor = 100 + 1;
            for(FilaEstado estado : tablaEstado.values()){
                if(!estado.visitado && estado.distancia < menor){
                    menor = estado.distancia;
                    actual = estado.nodo;
                }
            }

            // Si no se encontró ninguno, ya no quedan mejoras por hacer.
            if(actual == null){
                break;
            }

            // En la fila del nodo actual, se marca como visitado.
            FilaEstado filaActual = tablaEstado.get(actual);
            filaActual.visitado = true;

            // Relajar las aristas vecinas.
            for(Map.Entry<String, Integer> vecino : grafo.vecinos.get(actual).entrySet()){
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
                    previo.put(nodoVecino, actual);
                }
            }

            // Si ya llegamos al nodo final, podemos salir.
            if(actual.equals(nodoFin)){
                break;
            }
        }

        return previo;
    }
}