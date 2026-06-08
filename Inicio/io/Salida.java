package io;

import domain.*;
import java.util.*;

public class Salida{
  // Publicar en consola la lista de personas y sus datos
  public  void publicar(List<Persona> personas){
    for(Persona persona : personas){
      System.out.println(persona.saludo());
    }
  }
}


