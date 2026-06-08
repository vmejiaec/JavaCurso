package io;

import java.util.*;
import java.nio.file.*;

public class Entrada {
    // Lee los datos de entrada de un archivo txt
    public List<String> leerDatos() throws Exception {
        return Files.readAllLines(Paths.get("personasV3.txt"));
    }
}