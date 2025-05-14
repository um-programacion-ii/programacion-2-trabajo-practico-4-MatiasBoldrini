package com.biblioteca.app.service;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Usuario;
import java.util.List;

public interface PrestamoService {
    Prestamo realizarPrestamo(Long libroId, Long usuarioId);

    void devolverLibro(Long prestamoId);

    List<Prestamo> obtenerPrestamosActivos();

    List<Prestamo> obtenerPrestamosVencidos();

    List<Prestamo> obtenerPrestamosPorUsuario(Long usuarioId);

    Prestamo buscarPorId(Long id);

    List<Prestamo> obtenerTodos();

    void eliminar(Long id);
}