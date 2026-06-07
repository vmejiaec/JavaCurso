package io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class LectorJson implements EntradaFiguras{
    ObjectMapper mapp = new ObjectMapper();

    public List<Map<String, Object>> leer(String json) throws IOException {
        return mapp.readValue(
            new File(json),
            new TypeReference<>(){}
        );
    }
}