package com.biblioteca.app.controller;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que maneja las operaciones CRUD y otras operaciones
 * específicas para los libros.
 * Todos los endpoints comienzan con /api/libros
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    /**
     * Obtiene todos los libros del sistema.
     * 
     * @return Lista de todos los libros
     * @HTTP GET /api/libros
     * @response 200 - Lista de libros encontrada exitosamente
     */
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    /**
     * Busca un libro por su ID.
     * 
     * @param id ID del libro a buscar
     * @return El libro encontrado
     * @HTTP GET /api/libros/{id}
     * @response 200 - Libro encontrado exitosamente
     * @response 404 - Libro no encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(libroService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca un libro por su ISBN.
     * 
     * @param isbn ISBN del libro a buscar
     * @return El libro encontrado
     * @HTTP GET /api/libros/isbn/{isbn}
     * @response 200 - Libro encontrado exitosamente
     * @response 404 - Libro no encontrado
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        try {
            return ResponseEntity.ok(libroService.buscarPorIsbn(isbn));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los libros que están disponibles para préstamo.
     * 
     * @return Lista de libros disponibles
     * @HTTP GET /api/libros/disponibles
     * @response 200 - Lista de libros disponibles encontrada exitosamente
     */
    @GetMapping("/disponibles")
    public List<Libro> obtenerLibrosDisponibles() {
        return libroService.buscarDisponibles();
    }

    /**
     * Crea un nuevo libro en el sistema.
     * 
     * @param libro Datos del libro a crear
     * @return El libro creado
     * @HTTP POST /api/libros
     * @response 201 - Libro creado exitosamente
     * @response 400 - Datos del libro inválidos
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(libroService.guardar(libro));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un libro existente.
     * 
     * @param id    ID del libro a actualizar
     * @param libro Nuevos datos del libro
     * @return El libro actualizado
     * @HTTP PUT /api/libros/{id}
     * @response 200 - Libro actualizado exitosamente
     * @response 404 - Libro no encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            return ResponseEntity.ok(libroService.actualizar(id, libro));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un libro del sistema.
     * 
     * @param id ID del libro a eliminar
     * @return Respuesta sin contenido
     * @HTTP DELETE /api/libros/{id}
     * @response 204 - Libro eliminado exitosamente
     * @response 404 - Libro no encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            libroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Envía un libro a reparación.
     * 
     * @param id ID del libro a enviar a reparación
     * @return Respuesta sin contenido
     * @HTTP PATCH /api/libros/{id}/reparacion
     * @response 200 - Libro enviado a reparación exitosamente
     * @response 400 - El libro no puede ser enviado a reparación (ej: está
     *           prestado)
     * @response 404 - Libro no encontrado
     */
    @PatchMapping("/{id}/reparacion")
    public ResponseEntity<Void> enviarAReparacion(@PathVariable Long id) {
        try {
            libroService.enviarAReparacion(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}