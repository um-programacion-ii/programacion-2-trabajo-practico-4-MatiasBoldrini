package com.biblioteca.app.service.impl;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.repository.LibroRepository;
import com.biblioteca.app.repository.PrestamoRepository;
import com.biblioteca.app.repository.UsuarioRepository;
import com.biblioteca.app.service.PrestamoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Prestamo realizarPrestamo(Long libroId, Long usuarioId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el libro con ID: " + libroId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con ID: " + usuarioId));

        try {
            Prestamo prestamo = new Prestamo(libro, usuario);
            return prestamoRepository.save(prestamo);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No se puede realizar el préstamo: " + e.getMessage());
        }
    }

    @Override
    public void devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el préstamo con ID: " + prestamoId));

        try {
            prestamo.finalizar();
            prestamoRepository.save(prestamo);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No se puede devolver el libro: " + e.getMessage());
        }
    }

    @Override
    public List<Prestamo> obtenerPrestamosActivos() {
        return prestamoRepository.findPrestamosActivos();
    }

    @Override
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoRepository.findPrestamosVencidos();
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con ID: " + usuarioId));

        return prestamoRepository.findByUsuario(usuario);
    }

    @Override
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el préstamo con ID: " + id));
    }

    @Override
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el préstamo con ID: " + id);
        }
        prestamoRepository.deleteById(id);
    }
}