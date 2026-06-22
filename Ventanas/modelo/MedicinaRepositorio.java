package modelo;

import controlador.Servicio;
import java.util.List;
import java.util.ArrayList;

public class MedicinaRepositorio{
    // Mètodo para consultar todas las Medicinas
    public List<Medicina> consultar() throws Exception {
        Fuente fuente = new Fuente();
        List<String> lineas = fuente.leerURL("https://raw.githubusercontent.com/vmejiaec/JavaCurso/refs/heads/main/Leer/medicinas.txt");
        List<Medicina> medicinas = new ArrayList<>();

        Servicio servicio = new Servicio();
        medicinas = servicio.convertir(lineas);

        return medicinas;
    }
}
