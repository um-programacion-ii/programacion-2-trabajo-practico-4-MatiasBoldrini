package com.biblioteca.app.controller;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(libroService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        try {
            return ResponseEntity.ok(libroService.buscarPorIsbn(isbn));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disponibles")
    public List<Libro> obtenerLibrosDisponibles() {
        return libroService.buscarDisponibles();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(libroService.guardar(libro));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            return ResponseEntity.ok(libroService.actualizar(id, libro));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            libroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

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