# JavaFactory

Este proyecto ahora usa una idea simple para que sea facil extender formatos:

- Main no crea directamente LectorJson ni ReporteHtml.
- Main recibe por parametros el tipo de entrada y salida.
- Una factory separada (CanalFactory) decide que clase concreta instanciar.

## Parametros en Main

Main espera 4 parametros:

1. tipo de entrada
2. origen de datos
3. tipo de salida
4. destino del reporte

Ejemplo:

```bash
java -cp target/classes app.Main json lista.json html reporte.html
```

## Como sabe el programa que implementacion usar

La decision se hace en CanalFactory, no en Main.

Si llega:

- entrada = json -> crea LectorJson
- salida = html -> crea ReporteHtml

Si luego quieres agregar xml o pdf, solo agregas la clase y un caso nuevo en CanalFactory.

## Codigo principal

### Main (solo coordina)

```java
public class Main{
	public static void main(String[] args) throws IOException{
		if (args.length < 4) {
			System.out.println("Uso: java app.Main <tipoEntrada> <origen> <tipoSalida> <destino>");
			System.out.println("Ejemplo: java app.Main json lista.json html reporte.html");
			return;
		}

		String tipoEntrada = args[0];
		String origen = args[1];
		String tipoSalida = args[2];
		String destino = args[3];

		CanalFactory canalFactory = new CanalFactory();
		EntradaFiguras entrada = canalFactory.crearEntrada(tipoEntrada);
		SalidaReporte reporte = canalFactory.crearSalida(tipoSalida);

		if (entrada == null || reporte == null) {
			System.out.println("Tipo de entrada o salida no soportado.");
			return;
		}

		Servicio servicio = new Servicio(entrada, new FiguraFactory());
		List<Figura> figuras = servicio.cargar(origen);
		reporte.generar(destino, figuras);

		System.out.println("Reporte generado.");
	}
}
```

### CanalFactory (elige la clase concreta)

```java
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
```

## Beneficio didactico

- Main queda simple y limpio.
- La creacion de objetos se concentra en un solo lugar.
- Extender formatos nuevos es directo y sin romper la logica principal.

## Separar procesamiento del reporte

Problema actual:

- ReporteHtml hace 2 cosas al mismo tiempo.
- Recorre la lista de figuras y calcula area/perimetro.
- Ademas arma el texto HTML.

Para una explicacion didactica, conviene separar responsabilidades:

1. Una clase procesa figuras (logica de negocio).
2. Otra clase solo formatea y escribe salida (html, txt, pdf).

### Idea simple de diseno

- Crear un ProcesadorFiguras.
- Ese procesador recibe List<Figura>.
- Recorre la lista, descarta null y calcula tipo, area y perimetro.
- Devuelve una lista de datos ya procesados (por ejemplo FilaReporte).

Luego:

- ReporteHtml ya no llama figura.area() ni figura.perimetro().
- ReporteHtml solo recibe List<FilaReporte> y la convierte a html.

Con eso, el procesamiento queda en un punto unico y reutilizable.

### Flujo recomendado

1. EntradaFiguras lee origen y devuelve datos crudos.
2. Servicio convierte datos en List<Figura> con FiguraFactory.
3. ProcesadorFiguras transforma List<Figura> en List<FilaReporte>.
4. SalidaReporte genera el formato final usando esas filas.

### Ventaja para tus clases

- Se entiende mejor el principio de responsabilidad unica.
- Si cambias la salida (html, txt, pdf), no repites calculos.
- Si cambias la forma de calcular, no tocas las salidas.
- Es mas facil de probar: pruebas el procesador por separado del reporte.

### Regla didactica corta

- Servicio y Procesador: piensan y calculan.
- Reporte: solo dibuja/escribe.

## Mini plantilla para clase (sin codigo)

Puedes presentar el diseno con estas piezas:

1. EntradaFiguras (interfaz)
- Responsabilidad: leer una fuente y devolver datos de figuras.
- Ejemplos de implementacion: LectorJsonArchivo, LectorJsonUrl, LectorXmlArchivo.

2. Servicio
- Responsabilidad: convertir datos crudos en objetos Figura usando FiguraFactory.
- No calcula areas ni perimetros para reportar.

3. ProcesadorFiguras
- Responsabilidad: recorrer List<Figura> y producir filas listas para reporte.
- Aqui se invocan tipo, area y perimetro.

4. FilaReporte (DTO simple)
- Responsabilidad: guardar datos ya procesados para mostrar.
- Campos sugeridos: tipo, area, perimetro.

5. SalidaReporte (interfaz)
- Responsabilidad: recibir filas ya procesadas y escribir el formato final.
- Ejemplos de implementacion: ReporteHtml, ReporteTxt, ReportePdf.

6. CanalFactory
- Responsabilidad: elegir implementaciones concretas segun parametros.
- Main no conoce clases concretas, solo usa interfaces.

7. Main
- Responsabilidad: orquestar el flujo.
- Lee parametros, pide canales a CanalFactory, llama Servicio, llama Procesador, llama Salida.

### Mapa mental rapido

Entrada -> Servicio -> Procesador -> Salida

### Frase para explicar a alumnos

Primero entendemos figuras, despues calculamos datos, y al final solo los mostramos en el formato que queramos.

## Separar en dos proyectos: inmutable y mutable

Objetivo:

- Proyecto 1 (inmutable): contiene el motor de la aplicacion y no se modifica en clase.
- Proyecto 2 (mutable): contiene extensiones del programador (nuevas figuras y nuevos canales).
- El proyecto mutable no debe acceder a clases internas del core.

### Vision general

Proyecto 1: geometria-pataforma

- Modulo A: api publica (contratos de extension).
- Modulo B: core interno (orquestacion, procesamiento, arranque).

Proyecto 2: geometria-extensions

- Implementa contratos de la api publica.
- Define nuevas figuras y canales de entrada/salida.
- No puede usar paquetes internos del core.

### Regla de dependencias (muy importante)

- api: no depende de core.
- core: depende de api.
- extensions: depende solo de api.

Nunca:

- extensions -> core

Con esta regla, el programador extiende el sistema sin tocar ni ver la logica interna.

### Que va en cada proyecto

Proyecto inmutable (geometria-pataforma):

- API publica:
- contratos como FiguraPlugin, EntradaPlugin, SalidaPlugin.
- DTOs de intercambio (por ejemplo FilaReporte).
- Core interno:
- motor de carga y flujo principal.
- servicio de procesamiento.
- main oficial.
- descubrimiento de plugins (ServiceLoader).

Proyecto mutable (geometria-extensions):

- figuras nuevas (Ej: Rectangulo, Circulo, Triangulo).
- entradas nuevas (Ej: JsonUrl, XmlArchivo).
- salidas nuevas (Ej: Txt, Pdf).
- registro de implementaciones como plugins.

### Como evitar acceso al core de verdad

Para una version simple y didactica, no hace falta usar module-info.java.
Puedes trabajar con jars normales y una regla muy clara de dependencias:

1. Separar paquetes: api en un paquete publico, core en paquete interno.
2. Generar 2 jars distintos:
- uno con api (se comparte con extensiones).
- otro ejecutable con core (no se usa como dependencia de extensiones).
3. En extensiones, copiar o agregar solo el jar de api.
4. No agregar nunca el jar de core en extensiones.

Si haces eso, el programador solo vera la API publica. No podra usar el core simplemente porque ese jar no estara en su proyecto.

### Flujo en ejecucion

1. Core arranca.
2. Core busca plugins disponibles.
3. Core carga plugins que implementan api.
4. Core ejecuta el flujo usando esas extensiones.

El programador cambia extensiones, pero el motor principal sigue intacto.

### Estructura sugerida de repositorios

Opcion simple (2 repos):

- repo 1: geometria-pataforma
	- api
	- core
- repo 2: geometria-extensions
	- figuras
	- entradas
	- salidas

Opcion practica para curso (1 repo multi-modulo):

- carpeta pataforma
	- modulo api
	- modulo core
- carpeta extensions
	- modulo plugins

Ambas opciones sirven. Para enseñar, la multi-modulo en un repo suele ser mas facil al inicio.

### Mensaje didactico para alumnos

Core es el motor del auto y no se abre.
Extensiones son accesorios: puedes cambiar llantas, radio o pintura sin tocar el motor.

## Codigo guia para separar de verdad (2 proyectos)

Abajo tienes codigo minimo para entender la separacion.

### 1) Proyecto inmutable: jar api

Contrato para figuras extensibles:

```java
package com.geometria.api;

public interface FiguraPlugin {
	String tipo();
	double area();
	double perimetro();
}
```

Contrato para entrada extensible:

```java
package com.geometria.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EntradaPlugin {
	String id();
	List<Map<String, Object>> leer(String origen) throws IOException;
}
```

Contrato para salida extensible:

```java
package com.geometria.api;

import java.io.IOException;
import java.util.List;

public interface SalidaPlugin {
	String id();
	void generar(String destino, List<FilaReporte> filas) throws IOException;
}
```

DTO inmutable para reporte:

```java
package com.geometria.api;

public record FilaReporte(String tipo, double area, double perimetro) {}
```

### 2) Proyecto inmutable: jar core

Punto clave:

- core usa plugins por interfaz.
- core depende del jar api.
- extensiones no deben recibir este jar.

Ejemplo de carga de plugins en core con ServiceLoader:

```java
package com.geometria.core;

import com.geometria.api.EntradaPlugin;
import com.geometria.api.SalidaPlugin;

import java.util.ServiceLoader;

public class PluginRegistro {

	public EntradaPlugin buscarEntrada(String id) {
		for (EntradaPlugin p : ServiceLoader.load(EntradaPlugin.class)) {
			if (p.id().equalsIgnoreCase(id)) return p;
		}
		return null;
	}

	public SalidaPlugin buscarSalida(String id) {
		for (SalidaPlugin p : ServiceLoader.load(SalidaPlugin.class)) {
			if (p.id().equalsIgnoreCase(id)) return p;
		}
		return null;
	}
}
```

### 3) Proyecto mutable: jar extensions

Implementacion de entrada JSON en extensions:

```java
package com.geometria.extensions.io;

import com.geometria.api.EntradaPlugin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EntradaJson implements EntradaPlugin {
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String id() {
		return "json";
	}

	@Override
	public List<Map<String, Object>> leer(String origen) throws IOException {
		return mapper.readValue(new File(origen), new TypeReference<>() {});
	}
}
```

Implementacion de salida HTML en extensions:

```java
package com.geometria.extensions.io;

import com.geometria.api.FilaReporte;
import com.geometria.api.SalidaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SalidaHtml implements SalidaPlugin {
	@Override
	public String id() {
		return "html";
	}

	@Override
	public void generar(String destino, List<FilaReporte> filas) throws IOException {
		StringBuilder html = new StringBuilder();
		html.append("<html><body><table>")
			.append("<tr><th>Tipo</th><th>Area</th><th>Perimetro</th></tr>");

		for (FilaReporte f : filas) {
			html.append("<tr>")
				.append("<td>").append(f.tipo()).append("</td>")
				.append("<td>").append(f.area()).append("</td>")
				.append("<td>").append(f.perimetro()).append("</td>")
				.append("</tr>");
		}

		html.append("</table></body></html>");
		Files.writeString(Path.of(destino), html.toString());
	}
}
```

### 4) Dependencias Maven (idea minima)

En extensions, depende solo del api:

```xml
<dependency>
	<groupId>com.geometria</groupId>
	<artifactId>geometria-pataforma-api</artifactId>
	<version>1.0.0</version>
</dependency>
```

No agregar dependencia a core en extensions.

Con esto, el proyecto mutable extiende comportamiento, pero no toca ni importa la logica interna del proyecto inmutable.

### 5) Idea practica de uso

Proyecto 1 genera:

- geometria-pataforma-api.jar
- geometria-pataforma-core.jar

Proyecto 2 usa solo:

- geometria-pataforma-api.jar

En otras palabras: copiar y usar el jar de api, pero no el jar de core.

