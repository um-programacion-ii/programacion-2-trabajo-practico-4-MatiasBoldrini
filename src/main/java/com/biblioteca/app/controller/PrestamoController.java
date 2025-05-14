package com.biblioteca.app.controller;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prestamoService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public List<Prestamo> obtenerPrestamosActivos() {
        return prestamoService.obtenerPrestamosActivos();
    }

    @GetMapping("/vencidos")
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoService.obtenerPrestamosVencidos();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Prestamo> obtenerPrestamosPorUsuario(@PathVariable Long usuarioId) {
        return prestamoService.obtenerPrestamosPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<Prestamo> realizarPrestamo(
            @RequestParam Long libroId,
            @RequestParam Long usuarioId) {
        try {
            Prestamo nuevoPrestamo = prestamoService.realizarPrestamo(libroId, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

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