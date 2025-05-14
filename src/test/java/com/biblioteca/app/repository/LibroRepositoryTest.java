package com.biblioteca.app.repository;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.EstadoLibro;
import com.biblioteca.app.repository.impl.LibroRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibroRepositoryTest {

    private LibroRepository libroRepository;

    @BeforeEach
    void setUp() {
        libroRepository = new LibroRepositoryImpl();
    }

    @Test
    void guardarLibroSinIdAsignaId() {
        // Arrange
        Libro libro = new Libro(null, "ISBN1", "Libro Test", "Autor Test", EstadoLibro.DISPONIBLE);

        // Act
        Libro libroGuardado = libroRepository.save(libro);

        // Assert
        assertNotNull(libroGuardado.getId());
    }

    @Test
    void guardarLibroConIdNoModificaId() {
        // Arrange
        Long id = 5L;
        Libro libro = new Libro(id, "ISBN1", "Libro Test", "Autor Test", EstadoLibro.DISPONIBLE);

        // Act
        Libro libroGuardado = libroRepository.save(libro);

        // Assert
        assertEquals(id, libroGuardado.getId());
    }

    @Test
    void guardarYBuscarPorIdDevuelveLibroCorrecto() {
        // Arrange
        Libro libro = new Libro(null, "ISBN1", "Libro Test", "Autor Test", EstadoLibro.DISPONIBLE);
        Libro libroGuardado = libroRepository.save(libro);

        // Act
        Optional<Libro> libroEncontrado = libroRepository.findById(libroGuardado.getId());

        // Assert
        assertTrue(libroEncontrado.isPresent());
        assertEquals("ISBN1", libroEncontrado.get().getIsbn());
        assertEquals("Libro Test", libroEncontrado.get().getTitulo());
    }

    @Test
    void buscarPorIdNoExistenteDevuelveOptionalVacio() {
        // Act
        Optional<Libro> resultado = libroRepository.findById(999L);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorIsbnDevuelveLibroCorrecto() {
        // Arrange
        String isbn = "ISBN-TEST";
        Libro libro = new Libro(null, isbn, "Libro con ISBN Específico", "Autor Test", EstadoLibro.DISPONIBLE);
        libroRepository.save(libro);

        // Act
        Optional<Libro> libroEncontrado = libroRepository.findByIsbn(isbn);

        // Assert
        assertTrue(libroEncontrado.isPresent());
        assertEquals("Libro con ISBN Específico", libroEncontrado.get().getTitulo());
    }

    @Test
    void buscarPorIsbnNoExistenteDevuelveOptionalVacio() {
        // Act
        Optional<Libro> resultado = libroRepository.findByIsbn("ISBN-INEXISTENTE");

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void findAllDevuelveTodosLosLibros() {
        // Arrange
        Libro libro1 = new Libro(null, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE);
        Libro libro2 = new Libro(null, "ISBN2", "Libro 2", "Autor 2", EstadoLibro.PRESTADO);
        Libro libro3 = new Libro(null, "ISBN3", "Libro 3", "Autor 3", EstadoLibro.EN_REPARACION);

        libroRepository.save(libro1);
        libroRepository.save(libro2);
        libroRepository.save(libro3);

        // Act
        List<Libro> libros = libroRepository.findAll();

        // Assert
        assertEquals(3, libros.size());
        assertTrue(libros.stream().anyMatch(l -> l.getIsbn().equals("ISBN1")));
        assertTrue(libros.stream().anyMatch(l -> l.getIsbn().equals("ISBN2")));
        assertTrue(libros.stream().anyMatch(l -> l.getIsbn().equals("ISBN3")));
    }

    @Test
    void deleteByIdEliminaLibro() {
        // Arrange
        Libro libro = new Libro(null, "ISBN-DELETE", "Libro a Eliminar", "Autor Test", EstadoLibro.DISPONIBLE);
        Libro libroGuardado = libroRepository.save(libro);
        Long id = libroGuardado.getId();

        // Act
        libroRepository.deleteById(id);
        Optional<Libro> resultado = libroRepository.findById(id);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void existsByIdDevuelveTrueSiExiste() {
        // Arrange
        Libro libro = new Libro(null, "ISBN-EXISTS", "Libro Test", "Autor Test", EstadoLibro.DISPONIBLE);
        Libro libroGuardado = libroRepository.save(libro);
        Long id = libroGuardado.getId();

        // Act & Assert
        assertTrue(libroRepository.existsById(id));
    }

    @Test
    void existsByIdDevuelveFalseSiNoExiste() {
        // Act & Assert
        assertFalse(libroRepository.existsById(999L));
    }

    @Test
    void actualizarLibroManteniendoId() {
        // Arrange
        Libro libro = new Libro(null, "ISBN-UPDATE", "Título Original", "Autor Original", EstadoLibro.DISPONIBLE);
        Libro libroGuardado = libroRepository.save(libro);
        Long id = libroGuardado.getId();

        // Act - Modificar y guardar el mismo libro
        libroGuardado.setTitulo("Título Actualizado");
        libroGuardado.setAutor("Autor Actualizado");
        libroRepository.save(libroGuardado);

        // Assert
        Optional<Libro> libroActualizado = libroRepository.findById(id);
        assertTrue(libroActualizado.isPresent());
        assertEquals(id, libroActualizado.get().getId());
        assertEquals("Título Actualizado", libroActualizado.get().getTitulo());
        assertEquals("Autor Actualizado", libroActualizado.get().getAutor());
    }
}