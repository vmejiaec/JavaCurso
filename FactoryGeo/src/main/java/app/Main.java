package app;

import io.*;
import domain.*;
import core.*;
import factory.*;

import java.util.*;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException{

        String tipoEntrada = args[0];
        String origen = args[1];
        String tipoSalida = args[2];
        String destino = args[3];

        CanalFactory canalFactory = new CanalFactory();
        EntradaFiguras entrada = canalFactory.crearEntrada(tipoEntrada);
        SalidaReporte reporte = canalFactory.crearSalida(tipoSalida);

        Servicio servicio = new Servicio(entrada, new FiguraFactory());
        List<Figura> figuras = servicio.cargar(origen);
        ProcesadorFiguras procesador = new ProcesadorFiguras();
        List<FilaReporte> filas = procesador.procesar(figuras);
        
        reporte.generar(destino, filas);

        System.out.println("Reporte generado.");
    }
}