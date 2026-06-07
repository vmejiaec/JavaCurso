package core;

import domain.Figura;

import java.util.ArrayList;
import java.util.List;

public class ProcesadorFiguras {

    public List<FilaReporte> procesar(List<Figura> figuras) {
        List<FilaReporte> filas = new ArrayList<>();

        for (Figura figura : figuras) {
            if (figura == null) continue;

            filas.add(new FilaReporte(
                figura.tipo(),
                figura.area(),
                figura.perimetro()
            ));
        }

        return filas;
    }
}