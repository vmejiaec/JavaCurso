package io;

import core.*;

import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class ReporteHtml implements SalidaReporte{

    public void generar(String archivo, List<FilaReporte> filas) throws IOException{
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset=\"UTF-8\">")
            .append("<title>Reporte</title></head>")
            .append("<body>")
            .append("<h1>Reporte de Figuras</h1>")
            .append("<table>")
            .append("<tr><th>Tipo</th><th>Area</th><th>Perimetro</th></tr>");
        
        for (FilaReporte fila: filas){
            if (fila == null ) continue;
            
            html.append("<tr>")
                .append("<td>").append(fila.tipo).append("</td>")
                .append("<td>").append(fila.area).append("</td>")
                .append("<td>").append(fila.perimetro).append("</td>")
                .append("</tr>");
        }

        html.append("</table></body></html>");

        Files.writeString(Path.of(archivo), html.toString());
    }
}