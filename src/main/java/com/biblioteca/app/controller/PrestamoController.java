package com.biblioteca.app.controller;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que maneja las operaciones relacionadas con los préstamos de
 * libros.
 * Todos los endpoints comienzan con /api/prestamos
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    /**
     * Obtiene todos los préstamos del sistema.
     * 
     * @return Lista de todos los préstamos
     * @HTTP GET /api/prestamos
     * @response 200 - Lista de préstamos encontrada exitosamente
     */
    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    /**
     * Busca un préstamo por su ID.
     * 
     * @param id ID del préstamo a buscar
     * @return El préstamo encontrado
     * @HTTP GET /api/prestamos/{id}
     * @response 200 - Préstamo encontrado exitosamente
     * @response 404 - Préstamo no encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prestamoService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los préstamos activos.
     * 
     * @return Lista de préstamos activos
     * @HTTP GET /api/prestamos/activos
     * @response 200 - Lista de préstamos activos encontrada exitosamente
     */
    @GetMapping("/activos")
    public List<Prestamo> obtenerPrestamosActivos() {
        return prestamoService.obtenerPrestamosActivos();
    }

    /**
     * Obtiene todos los préstamos vencidos.
     * 
     * @return Lista de préstamos vencidos
     * @HTTP GET /api/prestamos/vencidos
     * @response 200 - Lista de préstamos vencidos encontrada exitosamente
     */
    @GetMapping("/vencidos")
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoService.obtenerPrestamosVencidos();
    }

    /**
     * Obtiene todos los préstamos de un usuario específico.
     * 
     * @param usuarioId ID del usuario
     * @return Lista de préstamos del usuario
     * @HTTP GET /api/prestamos/usuario/{usuarioId}
     * @response 200 - Lista de préstamos del usuario encontrada exitosamente
     * @response 404 - Usuario no encontrado
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> obtenerPrestamosPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(prestamoService.obtenerPrestamosPorUsuario(usuarioId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Realiza un nuevo préstamo.
     * 
     * @param libroId   ID del libro a prestar
     * @param usuarioId ID del usuario que solicita el préstamo
     * @return El préstamo creado
     * @HTTP POST /api/prestamos
     * @response 201 - Préstamo creado exitosamente
     * @response 400 - No se puede realizar el préstamo (libro no disponible o
     *           usuario no elegible)
     * @response 404 - Libro o usuario no encontrado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Prestamo> realizarPrestamo(@RequestParam Long libroId, @RequestParam Long usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(prestamoService.realizarPrestamo(libroId, usuarioId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Registra la devolución de un libro prestado.
     * 
     * @param id ID del préstamo a finalizar
     * @return Respuesta sin contenido
     * @HTTP PATCH /api/prestamos/{id}/devolver
     * @response 200 - Libro devuelto exitosamente
     * @response 400 - No se puede devolver el libro (préstamo ya finalizado)
     * @response 404 - Préstamo no encontrado
     */
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Void> devolverLibro(@PathVariable Long id) {
        try {
            prestamoService.devolverLibro(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un préstamo del sistema.
     * 
     * @param id ID del préstamo a eliminar
     * @return Respuesta sin contenido
     * @HTTP DELETE /api/prestamos/{id}
     * @response 204 - Préstamo eliminado exitosamente
     * @response 404 - Préstamo no encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            prestamoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}