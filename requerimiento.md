# Requerimientos del sistema crud 
## Sistema de Farmacia con pruebas unitarias y logs

Quiero que me digas cómo crear un crud con interfaz de consola a la base de datos. La tabla en la base de datos es Usuario con id, nombre y email. Quiero que, aunque sea con interfaz de consola texto, debe seguir MVC, Modelo Vista Controlador. Quiero que me expliques el código, la estructura de carpetas, los paquetes, etc. No cambies cosas en el proyecto, dime para que yo lo haga. 

La aplicación debe tener menú, con opciones para:
- Desplegar la lista de usuario
- Buscar un usuario por su nombre
- Crear nuevo Usuario
- Editar un usuario existente
- Borrar un usuario existente

Debe crearse paquetes para organizar las clases, con miras a que el sistema va a crecer con más entidades, como por ejemplo: roles, permisos, logs, etc. Los paquetes permiten que se entienda cómo se puede organizar el proyecto desde el inicio.

Luego, cuando esta aplicación de consola funcione, se va a requerir que también se cuente con una interfaz gráfica de ventanas, y que desde el inicio, se tome en cuenta que debe permitir la reutilización de la mayor parte del código.

Debe contener pruebas unitarias con JUnit, y para probar con la persistencia, que se pueda levantar la base de datos en memoria, durante las pruebas. 

Incluir algún mecanismo para mantener un log de acciones que permita conocer qué operaciones se han realizado, con miras a debugger. La salida del log puede ser un archivo de log.

---

## Respuesta propuesta (sin cambiar tu proyecto, guía para que lo implementes tú)

Perfecto. Actualizo la guía para que cumpla TODOS tus requisitos: MVC, consola, estructura escalable por paquetes, preparación para futura GUI, pruebas unitarias con base en memoria y logs a archivo.

## 1) Objetivo de arquitectura

Separar responsabilidades desde el inicio para que el proyecto crezca sin dolor:

- Modelo: entidades y acceso a datos.
- Vista: interacción por consola (entrada y salida de texto).
- Controlador: coordina flujo de menú y casos de uso.
- Servicio: validaciones y reglas de negocio reutilizables.

Reglas clave:

- La vista no conoce SQL.
- El repositorio no imprime en consola.
- El controlador no arma conexiones JDBC.
- El servicio concentra validaciones para reutilizarse en consola y en GUI.

## 2) Estructura de paquetes sugerida

Dentro de src/main/java/victor/farmacia:

- App.java
- config
  - BaseDatos.java
- model
  - Usuario.java
- dto
    - UsuarioDto.java
- repository
  - UsuarioRepository.java
  - jdbc
    - UsuarioRepositoryJdbc.java
- service
  - UsuarioService.java
- view
  - UsuarioView.java
- controller
  - UsuarioController.java

Para logs de archivo:

- src/main/resources
  - logback.xml

Para pruebas con base en memoria:

- src/test/java/victor/farmacia/repository
  - UsuarioRepositoryJdbcTest.java
- src/test/resources
  - schema-test.sql

## 3) Dependencias Maven (incluye pruebas en memoria y logging)

Agrega o verifica en pom.xml:

- mysql-connector-j (runtime)
- junit-jupiter (test)
- h2 (test)
- slf4j-api
- logback-classic

Ejemplo:

```xml
<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.2.224</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.13</version>
    </dependency>

    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.5.6</version>
    </dependency>
</dependencies>
```

Y en build/plugins:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
</plugin>
```

## 4) Código base por capa

### 4.1 model/Usuario.java

```java
package victor.farmacia.model;

public class Usuario {

    private Integer id;
    private String nombre;
    private String email;

    public Usuario() {
    }

    public Usuario(Integer id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

### 4.1.1 dto/UsuarioDto.java (record)

Usa `record` para transportar datos hacia la vista/controlador sin exponer la entidad directamente.

```java
package victor.farmacia.dto;

public record UsuarioDto(Integer id, String nombre, String email) {
}
```

### 4.2 config/BaseDatos.java

Clave para pruebas en memoria: la conexión se toma de propiedades del sistema, con valores por defecto para desarrollo local.

```java
package victor.farmacia.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {

    private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/farmacia");
    private static final String USER = System.getProperty("db.user", "root");
    private static final String PASSWORD = System.getProperty("db.password", "root");

    private BaseDatos() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### 4.3 repository/UsuarioRepository.java

```java
package victor.farmacia.repository;

import victor.farmacia.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    List<Usuario> listarTodos();

    List<Usuario> buscarPorNombre(String nombre);

    Optional<Usuario> buscarPorId(Integer id);

    boolean crear(Usuario usuario);

    boolean actualizar(Usuario usuario);

    boolean eliminar(Integer id);
}
```

### 4.4 repository/jdbc/UsuarioRepositoryJdbc.java

Incluye logging técnico por operación para depuración.

```java
package victor.farmacia.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import victor.farmacia.config.BaseDatos;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.UsuarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioRepositoryJdbc.class);

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT id, nombre, email FROM Usuario ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                usuarios.add(new Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("email")
                ));
            }

            logger.info("listarTodos ejecutado. Registros: {}", usuarios.size());
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al listar usuarios", e);
            return List.of();
        }
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre, email FROM Usuario WHERE nombre LIKE ? ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + nombre + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    usuarios.add(new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email")
                    ));
                }
            }

            logger.info("buscarPorNombre '{}' ejecutado. Registros: {}", nombre, usuarios.size());
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al buscar usuario por nombre", e);
            return List.of();
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, email FROM Usuario WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email")
                    );
                    logger.info("buscarPorId {} encontrado", id);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar usuario por id {}", id, e);
        }

        logger.info("buscarPorId {} sin resultados", id);
        return Optional.empty();
    }

    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO Usuario(nombre, email) VALUES (?, ?)";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            boolean creado = statement.executeUpdate() > 0;
            logger.info("crear usuario '{}' -> {}", usuario.getNombre(), creado);
            return creado;
        } catch (SQLException e) {
            logger.error("Error al crear usuario '{}'", usuario.getNombre(), e);
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, email = ? WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setInt(3, usuario.getId());
            boolean actualizado = statement.executeUpdate() > 0;
            logger.info("actualizar id {} -> {}", usuario.getId(), actualizado);
            return actualizado;
        } catch (SQLException e) {
            logger.error("Error al actualizar id {}", usuario.getId(), e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";

        try (Connection connection = BaseDatos.obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            boolean eliminado = statement.executeUpdate() > 0;
            logger.info("eliminar id {} -> {}", id, eliminado);
            return eliminado;
        } catch (SQLException e) {
            logger.error("Error al eliminar id {}", id, e);
            return false;
        }
    }
}
```

### 4.5 service/UsuarioService.java

```java
package victor.farmacia.service;

import victor.farmacia.dto.UsuarioDto;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UsuarioService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDto> listarTodos() {
        return usuarioRepository.listarTodos()
                .stream()
                .map(this::aDto)
                .toList();
    }

    public List<UsuarioDto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de busqueda no puede estar vacio");
        }
        return usuarioRepository.buscarPorNombre(nombre.trim())
                .stream()
                .map(this::aDto)
                .toList();
    }

    public boolean crear(String nombre, String email) {
        validarNombre(nombre);
        validarEmail(email);
        return usuarioRepository.crear(new Usuario(nombre.trim(), email.trim()));
    }

    public boolean actualizar(Integer id, String nombre, String email) {
        validarId(id);
        validarNombre(nombre);
        validarEmail(email);
        return usuarioRepository.actualizar(new Usuario(id, nombre.trim(), email.trim()));
    }

    public boolean eliminar(Integer id) {
        validarId(id);
        return usuarioRepository.eliminar(id);
    }

    public Optional<UsuarioDto> buscarPorId(Integer id) {
        validarId(id);
        return usuarioRepository.buscarPorId(id).map(this::aDto);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que cero");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }
    }

    private UsuarioDto aDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
```

### 4.6 view/UsuarioView.java

```java
package victor.farmacia.view;

import victor.farmacia.dto.UsuarioDto;

import java.util.List;
import java.util.Scanner;

public class UsuarioView {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println();
        System.out.println("==== GESTION DE USUARIOS ====");
        System.out.println("1. Listar usuarios");
        System.out.println("2. Buscar usuario por nombre");
        System.out.println("3. Crear usuario");
        System.out.println("4. Editar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int pedirId() {
        System.out.print("Id: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String pedirNombre() {
        System.out.print("Nombre: ");
        return scanner.nextLine();
    }

    public String pedirEmail() {
        System.out.print("Email: ");
        return scanner.nextLine();
    }

    public boolean pedirConfirmacion(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        return "s".equalsIgnoreCase(scanner.nextLine().trim());
    }

    public void mostrarUsuarios(List<UsuarioDto> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios para mostrar.");
            return;
        }

        System.out.println("ID | NOMBRE | EMAIL");
        for (UsuarioDto usuario : usuarios) {
            System.out.println(usuario.id() + " | " + usuario.nombre() + " | " + usuario.email());
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.err.println(error);
    }
}
```

### 4.7 controller/UsuarioController.java

```java
package victor.farmacia.controller;

import victor.farmacia.dto.UsuarioDto;
import victor.farmacia.service.UsuarioService;
import victor.farmacia.view.UsuarioView;

import java.util.List;
import java.util.Optional;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioView usuarioView;

    public UsuarioController(UsuarioService usuarioService, UsuarioView usuarioView) {
        this.usuarioService = usuarioService;
        this.usuarioView = usuarioView;
    }

    public void iniciar() {
        int opcion;
        do {
            usuarioView.mostrarMenu();
            opcion = usuarioView.leerOpcion();
            procesar(opcion);
        } while (opcion != 0);
    }

    private void procesar(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    listar();
                    break;
                case 2:
                    buscar();
                    break;
                case 3:
                    crear();
                    break;
                case 4:
                    editar();
                    break;
                case 5:
                    eliminar();
                    break;
                case 0:
                    usuarioView.mostrarMensaje("Saliendo del sistema...");
                    break;
                default:
                    usuarioView.mostrarError("Opcion invalida.");
            }
        } catch (IllegalArgumentException e) {
            usuarioView.mostrarError("Validacion: " + e.getMessage());
        } catch (Exception e) {
            usuarioView.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private void listar() {
        List<UsuarioDto> usuarios = usuarioService.listarTodos();
        usuarioView.mostrarUsuarios(usuarios);
    }

    private void buscar() {
        String nombre = usuarioView.pedirNombre();
        usuarioView.mostrarUsuarios(usuarioService.buscarPorNombre(nombre));
    }

    private void crear() {
        boolean ok = usuarioService.crear(usuarioView.pedirNombre(), usuarioView.pedirEmail());
        usuarioView.mostrarMensaje(ok ? "Usuario creado correctamente." : "No se pudo crear el usuario.");
    }

    private void editar() {
        int id = usuarioView.pedirId();
        Optional<UsuarioDto> existente = usuarioService.buscarPorId(id);

        if (existente.isEmpty()) {
            usuarioView.mostrarError("No existe un usuario con ese id.");
            return;
        }

        boolean ok = usuarioService.actualizar(id, usuarioView.pedirNombre(), usuarioView.pedirEmail());
        usuarioView.mostrarMensaje(ok ? "Usuario actualizado correctamente." : "No se pudo actualizar el usuario.");
    }

    private void eliminar() {
        int id = usuarioView.pedirId();
        Optional<UsuarioDto> existente = usuarioService.buscarPorId(id);

        if (existente.isEmpty()) {
            usuarioView.mostrarError("No existe un usuario con ese id.");
            return;
        }

        boolean confirmar = usuarioView.pedirConfirmacion("Confirma eliminar a " + existente.get().nombre() + "?");
        if (!confirmar) {
            usuarioView.mostrarMensaje("Operacion cancelada.");
            return;
        }

        boolean ok = usuarioService.eliminar(id);
        usuarioView.mostrarMensaje(ok ? "Usuario eliminado correctamente." : "No se pudo eliminar el usuario.");
    }
}
```

### 4.8 App.java

```java
package victor.farmacia;

import victor.farmacia.controller.UsuarioController;
import victor.farmacia.repository.UsuarioRepository;
import victor.farmacia.repository.jdbc.UsuarioRepositoryJdbc;
import victor.farmacia.service.UsuarioService;
import victor.farmacia.view.UsuarioView;

public class App {

    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioView usuarioView = new UsuarioView();
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);

        usuarioController.iniciar();
    }
}
```

## 5) Logging a archivo (para depuración)

Archivo src/main/resources/logback.xml:

```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/farmacia.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/farmacia.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>14</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

Con esto, cada operación del repositorio queda trazada en logs/farmacia.log.

## 6) Pruebas unitarias con JUnit y base en memoria (H2)

### 6.1 schema-test.sql

src/test/resources/schema-test.sql:

```sql
DROP TABLE IF EXISTS Usuario;

CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);
```

### 6.2 UsuarioRepositoryJdbcTest.java

```java
package victor.farmacia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import victor.farmacia.model.Usuario;
import victor.farmacia.repository.jdbc.UsuarioRepositoryJdbc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryJdbcTest {

    private UsuarioRepository repository;

    @BeforeEach
    void setUp() {
        System.setProperty("db.url", "jdbc:h2:mem:farmacia_test;MODE=MySQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema-test.sql'");
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");

        repository = new UsuarioRepositoryJdbc();
    }

    @Test
    void crearYListar() {
        boolean creado = repository.crear(new Usuario("Ana", "ana@correo.com"));
        assertTrue(creado);

        List<Usuario> usuarios = repository.listarTodos();
        assertEquals(1, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
    }

    @Test
    void buscarPorNombre() {
        repository.crear(new Usuario("Carlos", "carlos@correo.com"));
        repository.crear(new Usuario("Carla", "carla@correo.com"));

        List<Usuario> usuarios = repository.buscarPorNombre("Carl");
        assertEquals(2, usuarios.size());
    }

    @Test
    void actualizarYEliminar() {
        repository.crear(new Usuario("Luis", "luis@correo.com"));
        Usuario creado = repository.listarTodos().get(0);

        boolean actualizado = repository.actualizar(
                new Usuario(creado.getId(), "Luis R", "luisr@correo.com")
        );
        assertTrue(actualizado);

        Optional<Usuario> actualizadoDb = repository.buscarPorId(creado.getId());
        assertTrue(actualizadoDb.isPresent());
        assertEquals("Luis R", actualizadoDb.get().getNombre());

        boolean eliminado = repository.eliminar(creado.getId());
        assertTrue(eliminado);
        assertTrue(repository.buscarPorId(creado.getId()).isEmpty());
    }
}
```

## 7) Flujo final MVC (consola)

1. App crea repositorio, servicio, vista y controlador.
2. Controlador muestra menú y lee opción.
3. Servicio valida.
4. Repositorio ejecuta SQL.
5. Se registra log de la operación.
6. Vista muestra resultado.

## 8) Qué se reutiliza cuando hagas interfaz gráfica

Se mantiene igual:

- model
- dto
- repository
- service
- config

Solo cambias:

- view (en vez de consola, ventanas)
- controller (eventos de botones en vez de menú por texto)

## 9) Errores comunes a evitar

- Mezclar SQL en la vista o en App.
- Validar en vista en vez de servicio.
- No parametrizar la conexión para pruebas.
- No cerrar recursos JDBC.
- Usar System.out como log técnico en vez de logger.

## 10) Orden recomendado para que lo copies y aprendas

1. Crea la estructura de paquetes.
2. Implementa model, repository y BaseDatos.
3. Implementa service y luego controller/view.
4. Configura logback y verifica el archivo de logs.
5. Agrega H2 + JUnit.
6. Crea y ejecuta tests del repositorio en memoria.
7. Ejecuta la app de consola y prueba el menú completo.

## 11) Modo estudio guiado (checkpoint por checkpoint)

Objetivo: que no solo copies codigo, sino que entiendas por que cada parte existe.

Regla de estudio recomendada:

- Implementa un checkpoint.
- Ejecuta y valida.
- Responde las preguntas de control.
- Si todo pasa, avanza al siguiente.

### Checkpoint 1: Entender MVC en tu propio proyecto

Tarea:

- Crea los paquetes model, repository, service, view, controller y config.
- Coloca cada clase en su capa correspondiente.

Preguntas de control:

- Por que UsuarioView no debe ejecutar SQL?
- Por que UsuarioService no debe imprimir por consola?
- Que ganarias cuando migres a GUI?

Criterio de exito:

- Puedes explicar en 3 frases el rol de cada capa sin mirar apuntes.

### Checkpoint 2: Modelo y repositorio basico

Tarea:

- Implementa Usuario.java.
- Implementa UsuarioDto como `record`.
- Implementa UsuarioRepository (interfaz).
- Implementa UsuarioRepositoryJdbc con listar, crear y buscarPorId.

Mini ejercicio:

- Antes de ejecutar, escribe tu prediccion: que devolvera listarTodos() en base vacia?

Criterio de exito:

- crear y listar funcionan con datos reales.
- No tienes System.out dentro del repositorio (solo logger).

### Checkpoint 3: Validaciones en service

Tarea:

- Implementa UsuarioService con validarId, validarNombre y validarEmail.

Mini ejercicio:

- Prueba estos casos y anota el mensaje esperado:
    - nombre vacio
    - nombre de 1 caracter
    - email sin @

Criterio de exito:

- Las validaciones fallan donde deben (service), no en repository.

### Checkpoint 4: Vista y controlador de consola

Tarea:

- Implementa UsuarioView y UsuarioController.
- Conecta el menu completo en iniciar().

Mini ejercicio:

- Fuerza una opcion invalida y verifica que no se cae el programa.

Criterio de exito:

- El menu permite listar, buscar, crear, editar y eliminar.
- El flujo vuelve al menu despues de cada operacion.

### Checkpoint 5: Logging a archivo

Tarea:

- Agrega logback.xml.
- Verifica que se genere logs/farmacia.log.

Mini ejercicio:

- Ejecuta crear, editar y eliminar un usuario.
- Luego abre el log y busca esas 3 operaciones.

Criterio de exito:

- El log registra operaciones y errores tecnicos con fecha/hora.

### Checkpoint 6: Pruebas con H2 en memoria

Tarea:

- Agrega dependencia H2 y JUnit.
- Crea schema-test.sql.
- Crea UsuarioRepositoryJdbcTest.

Mini ejercicio:

- Rompe temporalmente una consulta SQL (a proposito) y ejecuta test.
- Observa que test falla y por que.
- Repara y vuelve a ejecutar.

Criterio de exito:

- Los test pasan sin depender de MySQL local.
- Entiendes que el entorno de test es aislado y reproducible.

### Checkpoint 7: Preparacion para GUI

Tarea:

- Escribe una lista de clases que no tendrias que tocar al migrar a GUI.

Respuesta esperada:

- model, repository, service, config.

Mini ejercicio:

- Imagina un boton "Crear Usuario" en una ventana.
- Explica a que metodo del service llamaria.

Criterio de exito:

- Puedes describir la migracion a GUI sin reescribir la logica de negocio.

## 12) Rutina de practica (30-40 min por sesion)

1. 10 min: codifica un checkpoint.
2. 10 min: ejecuta app/tests y corrige errores.
3. 10 min: responde preguntas de control por escrito.
4. 5-10 min: repaso rapido de logs y decisiones tecnicas.

## 13) Reto final de aprendizaje

Cuando termines todo, intenta este reto sin mirar la guia:

- Agrega una nueva entidad simple (por ejemplo Rol con id y nombre).
- Repite la misma arquitectura MVC + repository + service + test en memoria + log.

Si puedes hacerlo con pocas dudas, ya internalizaste el patron y no solo copiaste codigo.

