package services;

import domain.*;
import java.util.*;

public class Servicio{
    // Convertir una lista de personas a un arreglo de datos de personas
    public static List<Persona> convertir(List<String> datos){
        List<Persona> personas = new ArrayList<Persona>();

        for(String dato : datos){      
            personas.add(new Persona(dato));
        }

        return personas;
    }
}