package com.biblioteca.app.repository.impl;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.repository.PrestamoRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PrestamoRepositoryImpl implements PrestamoRepository {
    private final Map<Long, Prestamo> prestamos = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Prestamo save(Prestamo prestamo) {
        if (prestamo.getId() == null) {
            prestamo.setId(sequence.incrementAndGet());
        }
        prestamos.put(prestamo.getId(), prestamo);
        return prestamo;
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return Optional.ofNullable(prestamos.get(id));
    }

    @Override
    public List<Prestamo> findByUsuario(Usuario usuario) {
        return prestamos.values().stream()
                .filter(prestamo -> prestamo.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> findAll() {
        return new ArrayList<>(prestamos.values());
    }

    @Override
    public void deleteById(Long id) {
        prestamos.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return prestamos.containsKey(id);
    }

    @Override
    public List<Prestamo> findPrestamosActivos() {
        return prestamos.values().stream()
                .filter(Prestamo::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamo> findPrestamosVencidos() {
        return prestamos.values().stream()
                .filter(prestamo -> prestamo.isActivo() && prestamo.estaVencido())
                .collect(Collectors.toList());
    }
}