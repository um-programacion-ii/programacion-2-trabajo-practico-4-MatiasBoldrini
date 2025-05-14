[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Vg2EF-QZ)
# 🚀 Trabajo Práctico: Sistema de Gestión de Biblioteca con Spring Framework

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-3.9.0-red)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.1-green)
![Mockito](https://img.shields.io/badge/Mockito-5.8.0-blue)

## ⚠️ Importante: Antes de Comenzar

1. **Lectura Completa**
   - Es **OBLIGATORIO** leer la consigna completa antes de comenzar a trabajar
   - Asegúrate de entender todos los requisitos y etapas
   - Consulta las dudas antes de iniciar el desarrollo

2. **Configuración del Repositorio**
   - La rama `main` debe estar protegida
   - No se permiten pushes directos a `main`
   - Todo el desarrollo debe realizarse en ramas feature
   - Los cambios deben integrarse mediante Pull Requests

## 🔧 Configuración Inicial del Repositorio

### 1. Protección de la Rama Main
1. En "Branch name pattern" escribir `main`
2. Marcar la siguiente opción:
   - ✓ Require a pull request before merging
3. Hacer clic en "Create"

> 💡 **Nota**: La protección de la rama main es obligatoria y asegura que:
> - No se puedan hacer cambios directos en la rama main
> - Todos los cambios deben hacerse a través de Pull Requests
> - Esto ayuda a mantener un historial de cambios ordenado y a seguir buenas prácticas de desarrollo

### 2. Configuración de Issues y Pull Requests
1. Ir a Settings > General
2. En la sección "Features":
   - ✓ Habilitar Issues
   - ✓ Habilitar Pull Requests
3. En la sección "Pull Requests":
   - ✓ Habilitar "Allow merge commits"
   - ✓ Habilitar "Allow squash merging"
   - ✓ Deshabilitar "Allow rebase merging"

### 3. Configuración de Project Board
1. Ir a la pestaña "Projects"
2. Crear nuevo proyecto "Sistema de Gestión de Biblioteca"
3. Configurar las siguientes columnas:
   - To Do
   - In Progress
   - Code Review
   - Done

### 4. Configuración de Milestones
1. Ir a la pestaña "Milestones"
2. Crear los siguientes milestones:
   - Etapa 1: Configuración y Modelos
   - Etapa 2: Repositories y Services
   - Etapa 3: Controllers
   - Etapa 4: Testing y Documentación

### 5. Configuración de Labels
1. Ir a Issues > Labels
2. Crear las siguientes etiquetas:
   - `enhancement` (verde)
   - `bug` (rojo)
   - `documentation` (azul)
   - `testing` (amarillo)
   - `setup` (gris)
   - `model` (morado)
   - `service` (naranja)
   - `controller` (rosa)
   - `repository` (turquesa)

### 6. Configuración de Templates
1. Verificar que los templates estén correctamente ubicados:
   ```
   .github/
   ├── ISSUE_TEMPLATE/
   │   └── issue_template.yml
   └── PULL_REQUEST_TEMPLATE/
       └── pull_request_template.yml
   ```

### 7. Configuración de Git Local
```bash
# Configurar el repositorio remoto
git remote add origin <URL_DEL_REPOSITORIO>

# Crear y cambiar a la rama main
git checkout -b main

# Subir la rama main
git push -u origin main

# Crear rama de desarrollo
git checkout -b develop

# Subir la rama develop
git push -u origin develop
```

## 🎯 Objetivo General

Desarrollar un sistema de gestión de biblioteca utilizando Spring Framework, implementando una arquitectura en capas y aplicando los principios SOLID. El sistema deberá manejar diferentes tipos de recursos bibliográficos, préstamos y usuarios, utilizando una base de datos en memoria para la persistencia de datos.

## ⏰ Tiempo Estimado y Entrega

- **Tiempo estimado de realización:** 24-30 horas
- **Fecha de entrega:** 14/05/2025 a las 13:00 hs

### Desglose estimado por etapa:
- Configuración inicial y modelos: 6-7 horas
- Repositories y Services: 7-9 horas
- Controllers y Endpoints: 6-7 horas
- Testing y documentación: 5-7 horas

> 💡 **Nota**: Esta estimación considera la experiencia adquirida en trabajos anteriores y la complejidad de implementar una arquitectura en capas con Spring Framework. El tiempo se ha ajustado considerando que no se requiere implementación de persistencia real.

## 👨‍🎓 Información del Alumno
- **Nombre y Apellido**: Matias Boldrini
- **Legajo**: 61073

## 🚀 Instrucciones de Ejecución

### Requisitos Previos
- Java 21 instalado
- Maven 3.9.0 o superior
- IDE compatible con Spring Boot (recomendado: IntelliJ IDEA o Eclipse)

### Pasos para Ejecutar
1. Clonar el repositorio:
```bash
git clone [URL del repositorio]
cd [nombre-del-directorio]
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

4. La aplicación estará disponible en:
```
http://localhost:8080
```

### Endpoints Disponibles
- GET /api/libros - Obtener todos los libros
- GET /api/libros/{id} - Obtener un libro por ID
- POST /api/libros - Crear un nuevo libro
- PUT /api/libros/{id} - Actualizar un libro
- DELETE /api/libros/{id} - Eliminar un libro

## 📋 Requisitos Previos

- Java 21 o superior
- Maven 3.9.0 o superior
- Conocimientos básicos de:
  - Programación orientada a objetos
  - Principios SOLID
  - Spring Framework básico
  - REST APIs

## 🧩 Tecnologías y Herramientas

- Spring Boot 3.4.5
- Spring Web
- Spring Test
- Lombok (opcional)
- JUnit 5.10.1
- Mockito 5.8.0
- Git y GitHub

## 📘 Etapas del Trabajo

### Etapa 1: Configuración del Proyecto y Modelos Base

#### Objetivos
- Configurar un proyecto Spring Boot
- Implementar los modelos base del sistema
- Aplicar el principio SRP (Single Responsibility)

#### Tareas
1. Crear proyecto Spring Boot con las dependencias necesarias
2. Implementar las siguientes clases modelo:
   - `Libro` (id, isbn, titulo, autor, estado)
   - `Usuario` (id, nombre, email, estado)
   - `Prestamo` (id, libro, usuario, fechaPrestamo, fechaDevolucion)

#### Ejemplo de Implementación
```java
// Modelo base
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;
}

public enum EstadoLibro {
    DISPONIBLE,
    PRESTADO,
    EN_REPARACION
}
```

### Etapa 2: Implementación de Repositories y Services

#### Objetivos
- Implementar la capa de servicios usando interfaces
- Aplicar el principio ISP (Interface Segregation)
- Implementar la capa de repositorios
- Aplicar el principio DIP (Dependency Inversion)

#### Tareas
1. Crear interfaces de repositorio:
   - `LibroRepository`
   - `UsuarioRepository`
   - `PrestamoRepository`

2. Implementar servicios:
   - Crear interfaces de servicio:
     - `LibroService`
     - `UsuarioService`
     - `PrestamoService`
   - Implementar clases concretas:
     - `LibroServiceImpl`
     - `UsuarioServiceImpl`
     - `PrestamoServiceImpl`

#### Ejemplo de Implementación
```java
// Interface del repositorio
public interface LibroRepository {
    Libro save(Libro libro);
    Optional<Libro> findById(Long id);
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}

// Implementación del repositorio en memoria
@Repository
public class LibroRepositoryImpl implements LibroRepository {
    private final Map<Long, Libro> libros = new HashMap<>();
    private Long nextId = 1L;
    
    @Override
    public Libro save(Libro libro) {
        if (libro.getId() == null) {
            libro.setId(nextId++);
        }
        libros.put(libro.getId(), libro);
        return libro;
    }
    
    @Override
    public Optional<Libro> findById(Long id) {
        return Optional.ofNullable(libros.get(id));
    }
    
    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        return libros.values().stream()
            .filter(libro -> libro.getIsbn().equals(isbn))
            .findFirst();
    }
    
    @Override
    public List<Libro> findAll() {
        return new ArrayList<>(libros.values());
    }
    
    @Override
    public void deleteById(Long id) {
        libros.remove(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return libros.containsKey(id);
    }
}

// Interface del servicio
public interface LibroService {
    Libro buscarPorIsbn(String isbn);
    List<Libro> obtenerTodos();
    Libro guardar(Libro libro);
    void eliminar(Long id);
    Libro actualizar(Long id, Libro libro);
}

// Implementación del servicio
@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;
    
    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    
    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
            .orElseThrow(() -> new LibroNoEncontradoException(isbn));
    }
    
    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }
    
    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }
    
    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }
    
    @Override
    public Libro actualizar(Long id, Libro libro) {
        if (!libroRepository.existsById(id)) {
            throw new LibroNoEncontradoException(id);
        }
        libro.setId(id);
        return libroRepository.save(libro);
    }
}
```

### Etapa 3: Implementación de Controllers

#### Objetivos
- Implementar la capa de controladores REST
- Aplicar el principio DIP (Dependency Inversion)
- Manejar excepciones HTTP

#### Tareas
1. Crear controladores REST:
   - `LibroController`
   - `UsuarioController`
   - `PrestamoController`

2. Implementar endpoints básicos:
   - GET /api/libros
   - GET /api/libros/{id}
   - POST /api/libros
   - PUT /api/libros/{id}
   - DELETE /api/libros/{id}

#### Ejemplo de Implementación
```java
@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;
    
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
    
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroService.buscarPorId(id);
    }
    
    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }
    
    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
```

### Etapa 4: Testing y Documentación

#### Objetivos
- Implementar tests unitarios y de integración
- Documentar la API y el código
- Asegurar la calidad del código

#### Tareas
1. Implementar tests:
   - Tests unitarios para servicios
   - Tests unitarios para repositorios
   - Tests de integración para controladores

2. Documentar:
   - Documentar endpoints con comentarios
   - Actualizar README con instrucciones
   - Documentar arquitectura y decisiones de diseño

#### Ejemplo de Test
```java
@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {
    @Mock
    private LibroRepository libroRepository;
    
    @InjectMocks
    private LibroServiceImpl libroService;
    
    @Test
    void cuandoBuscarPorIsbnExiste_entoncesRetornaLibro() {
        // Arrange
        String isbn = "123-456-789";
        Libro libroEsperado = new Libro(1L, isbn, "Test Book", "Test Author", EstadoLibro.DISPONIBLE);
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.of(libroEsperado));
        
        // Act
        Libro resultado = libroService.buscarPorIsbn(isbn);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(isbn, resultado.getIsbn());
        verify(libroRepository).findByIsbn(isbn);
    }
    
    @Test
    void cuandoBuscarPorIsbnNoExiste_entoncesLanzaExcepcion() {
        // Arrange
        String isbn = "123-456-789";
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(LibroNoEncontradoException.class, () -> 
            libroService.buscarPorIsbn(isbn)
        );
    }
}
```

## ✅ Entrega y Flujo de Trabajo con GitHub

1. **Configuración del Repositorio**
   - Proteger la rama `main`
   - Crear template de Issues y Pull Requests

2. **Project Kanban**
   - `To Do`
   - `In Progress`
   - `Code Review`
   - `Done`

3. **Milestones**
   - Etapa 1: Configuración y Modelos
   - Etapa 2: Repositories y Services
   - Etapa 3: Controllers
   - Etapa 4: Testing y Documentación

4. **Issues y Pull Requests**
   - Crear Issues detallados para cada funcionalidad
   - Asociar cada Issue a un Milestone
   - Implementar en ramas feature
   - Revisar código antes de merge

## ✅ Requisitos para la Entrega

- ✅ Implementación completa de todas las etapas
- ✅ Código bien documentado
- ✅ Todos los Issues cerrados
- ✅ Todos los Milestones completados
- ✅ Pull Requests revisados y aprobados
- ✅ Project actualizado
- ✅ README.md completo con:
  - Instrucciones de instalación
  - Requisitos del sistema
  - Ejemplos de uso
  - Documentación de endpoints

## 📚 Recursos Adicionales

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [REST API Best Practices](https://restfulapi.net/)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Test Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Testing Spring Boot Applications](https://www.baeldung.com/spring-boot-testing)

## 📋 Guía de Testing

### 1. Testing de Servicios
- Usar `@ExtendWith(MockitoExtension.class)`
- Mockear dependencias con `@Mock`
- Inyectar mocks con `@InjectMocks`
- Seguir el patrón Arrange-Act-Assert
- Probar casos positivos y negativos
- Verificar interacciones con mocks

### 2. Testing de Controladores
- Usar `@WebMvcTest` para pruebas de integración
- Mockear servicios con `@MockBean`
- Usar `MockMvc` para simular peticiones HTTP
- Verificar respuestas HTTP y contenido JSON
- Probar diferentes escenarios de error

### 3. Testing de Repositorios
- Probar operaciones CRUD básicas
- Verificar manejo de IDs
- Probar búsquedas y filtros
- Validar comportamiento con datos inválidos

### 4. Buenas Prácticas de Testing
- Nombres descriptivos para tests
- Un assert por test cuando sea posible
- Cobertura de código significativa
- Tests independientes y aislados
- Uso de `@BeforeEach` para setup común
- Documentación de casos de prueba

## 📝 Consideraciones Éticas sobre el Uso de IA

El uso de Inteligencia Artificial (IA) en este trabajo práctico debe seguir las siguientes pautas:

1. **Transparencia**
   - Documentar el uso de IA en el desarrollo
   - Explicar las modificaciones realizadas al código generado
   - Mantener un registro de las herramientas utilizadas

2. **Aprendizaje**
   - La IA debe usarse como herramienta de aprendizaje
   - Comprender y ser capaz de explicar el código generado
   - Utilizar la IA para mejorar la comprensión de conceptos

3. **Integridad Académica**
   - El trabajo final debe reflejar tu aprendizaje
   - No se permite la presentación de código sin comprensión
   - Debes poder explicar y defender cualquier parte del código

4. **Responsabilidad**
   - Verificar la corrección del código generado
   - Asegurar que el código cumple con los requisitos
   - Mantener la calidad y estándares de código

5. **Desarrollo Individual**
   - La IA puede usarse para facilitar el aprendizaje
   - Documentar el proceso de desarrollo
   - Mantener un registro del progreso

## 📝 Licencia

Este trabajo es parte del curso de Programación II de Ingeniería en Informática. Uso educativo únicamente.

## 📖 Guía de Uso

### Requisitos Previos
1. Tener instalado Java 21 o superior
2. Tener instalado Maven 3.9.0 o superior
3. Tener un IDE compatible con Spring Boot (recomendado: IntelliJ IDEA o VS Code)

### Cómo Ejecutar el Proyecto
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/um-programacion-ii/programacion-2-trabajo-practico-4-MatiasBoldrini.git
   cd programacion-2-trabajo-practico-4-MatiasBoldrini
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

4. La aplicación estará disponible en `http://localhost:8080`

### Endpoints Disponibles

#### Libros
- `GET /api/libros`: Obtiene todos los libros
- `GET /api/libros/{id}`: Obtiene un libro por ID
- `GET /api/libros/isbn/{isbn}`: Obtiene un libro por ISBN
- `GET /api/libros/disponibles`: Obtiene los libros disponibles
- `POST /api/libros`: Crea un nuevo libro
- `PUT /api/libros/{id}`: Actualiza un libro
- `DELETE /api/libros/{id}`: Elimina un libro
- `PATCH /api/libros/{id}/reparacion`: Envía un libro a reparación

#### Usuarios
- `GET /api/usuarios`: Obtiene todos los usuarios
- `GET /api/usuarios/{id}`: Obtiene un usuario por ID
- `GET /api/usuarios/email/{email}`: Obtiene un usuario por email
- `GET /api/usuarios/activos`: Obtiene los usuarios activos
- `POST /api/usuarios`: Crea un nuevo usuario
- `PUT /api/usuarios/{id}`: Actualiza un usuario
- `DELETE /api/usuarios/{id}`: Elimina un usuario
- `PATCH /api/usuarios/{id}/suspender`: Suspende un usuario

#### Préstamos
- `GET /api/prestamos`: Obtiene todos los préstamos
- `GET /api/prestamos/{id}`: Obtiene un préstamo por ID
- `GET /api/prestamos/activos`: Obtiene los préstamos activos
- `GET /api/prestamos/vencidos`: Obtiene los préstamos vencidos
- `GET /api/prestamos/usuario/{usuarioId}`: Obtiene los préstamos de un usuario
- `POST /api/prestamos`: Realiza un nuevo préstamo
- `PATCH /api/prestamos/{id}/devolver`: Registra la devolución de un libro
- `DELETE /api/prestamos/{id}`: Elimina un préstamo

### Ejemplos de Uso

#### Crear un Libro
```bash
curl -X POST http://localhost:8080/api/libros \
  -H "Content-Type: application/json" \
  -d '{
    "isbn": "978-0-13-468599-1",
    "titulo": "Clean Code",
    "autor": "Robert C. Martin",
    "estado": "DISPONIBLE"
  }'
```

#### Crear un Usuario
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Perez",
    "email": "juan@mail.com",
    "estado": "ACTIVO"
  }'
```

#### Realizar un Préstamo
```bash
curl -X POST "http://localhost:8080/api/prestamos?libroId=1&usuarioId=1"
```

### Notas Importantes
- Los IDs se generan automáticamente
- Un usuario puede tener máximo 3 libros prestados
- Los préstamos tienen una duración de 7 días
- Un libro prestado no puede ser enviado a reparación
- Un usuario suspendido no puede realizar préstamos

### Manejo de Errores
- 400 Bad Request: Datos inválidos o regla de negocio violada
- 404 Not Found: Recurso no encontrado
- 409 Conflict: Estado inconsistente (ej: libro ya prestado)