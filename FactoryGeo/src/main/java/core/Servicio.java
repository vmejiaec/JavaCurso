package core;

import domain.*;
import io.*;
import factory.*;
import core.*;

import java.util.*;
import java.io.IOException;


public class Servicio{
    private EntradaFiguras entrada;
    private FiguraFactory fabrica;

    public Servicio(EntradaFiguras entrada, FiguraFactory fabrica){
        this.entrada = entrada;
        this.fabrica = fabrica;
    }

    public List<Figura> cargar(String ruta) throws IOException{
        List<Map<String, Object>> lista = entrada.leer(ruta);
        List<Figura> figuras = new ArrayList<>();

        for (Map<String, Object> item : lista){
            figuras.add(fabrica.crear(item));
        }

        return figuras;
    }

}