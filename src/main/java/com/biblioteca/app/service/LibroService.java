package com.biblioteca.app.service;

import com.biblioteca.app.model.Libro;
import java.util.List;

public interface LibroService {
    Libro buscarPorIsbn(String isbn);

    Libro buscarPorId(Long id);

    List<Libro> obtenerTodos();

    Libro guardar(Libro libro);

    void eliminar(Long id);

    Libro actualizar(Long id, Libro libro);

    List<Libro> buscarDisponibles();

    void enviarAReparacion(Long id);
}