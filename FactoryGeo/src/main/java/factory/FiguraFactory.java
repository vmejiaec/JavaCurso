package factory;

import domain.*;

import java.util.*;

public class FiguraFactory{
    
    public Figura crear(Map<String, Object> datos){

        String tipo = (String) datos.get("tipo");

        switch(tipo){
            case "cuadrado" :
                return new Cuadrado(
                    toDouble(datos.get("lado"))
                );
            default:
                return null;
        }
    }

    public double toDouble(Object valor){
        return ((Number) valor).doubleValue();
    }
}