package io;

import core.FilaReporte;

import java.io.IOException;
import java.util.List;

public interface SalidaReporte {
    void generar(String destino, List<FilaReporte> filas) throws IOException;
}