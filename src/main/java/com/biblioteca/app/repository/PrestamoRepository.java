package com.biblioteca.app.repository;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);

    Optional<Prestamo> findById(Long id);

    List<Prestamo> findByUsuario(Usuario usuario);

    List<Prestamo> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Prestamo> findPrestamosActivos();

    List<Prestamo> findPrestamosVencidos();
}