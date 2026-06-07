package factory;

import io.*;

public class CanalFactory {

    public EntradaFiguras crearEntrada(String tipoEntrada) {
        if (tipoEntrada == null) return null;

        switch (tipoEntrada.toLowerCase()) {
            case "json":
                return new LectorJson();
            default:
                return null;
        }
    }

    public SalidaReporte crearSalida(String tipoSalida) {
        if (tipoSalida == null) return null;

        switch (tipoSalida.toLowerCase()) {
            case "html":
                return new ReporteHtml();
            default:
                return null;
        }
    }
}