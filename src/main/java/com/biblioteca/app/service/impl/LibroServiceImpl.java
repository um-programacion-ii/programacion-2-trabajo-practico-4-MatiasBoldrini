package com.biblioteca.app.service.impl;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.EstadoLibro;
import com.biblioteca.app.repository.LibroRepository;
import com.biblioteca.app.service.LibroService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el libro con ISBN: " + isbn));
    }

    @Override
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el libro con ID: " + id));
    }

    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Libro guardar(Libro libro) {
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es requerido");
        }
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es requerido");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor es requerido");
        }

        return libroRepository.save(libro);
    }

    @Override
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el libro con ID: " + id);
        }
        libroRepository.deleteById(id);
    }

    @Override
    public Libro actualizar(Long id, Libro libro) {
        Libro libroExistente = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el libro con ID: " + id));

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setAutor(libro.getAutor());

        return libroRepository.save(libroExistente);
    }

    @Override
    public List<Libro> buscarDisponibles() {
        return libroRepository.findAll().stream()
                .filter(Libro::estaDisponible)
                .collect(Collectors.toList());
    }

    @Override
    public void enviarAReparacion(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el libro con ID: " + id));

        try {
            libro.enviarAReparacion();
            libroRepository.save(libro);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No se puede enviar a reparación: " + e.getMessage());
        }
    }
}