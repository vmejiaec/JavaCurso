package io;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EntradaFiguras {
    List<Map<String, Object>> leer(String origen) throws IOException;
}