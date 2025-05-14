package com.biblioteca.app.service;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.EstadoLibro;
import com.biblioteca.app.repository.LibroRepository;
import com.biblioteca.app.service.impl.LibroServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorIsbnExistenteRetornaLibro() {
        // Arrange
        String isbn = "1234567890";
        Libro libroEsperado = new Libro();
        libroEsperado.setId(1L);
        libroEsperado.setIsbn(isbn);
        libroEsperado.setTitulo("El Señor de los Anillos");

        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.of(libroEsperado));

        // Act
        Libro resultado = libroService.buscarPorIsbn(isbn);

        // Assert
        assertNotNull(resultado);
        assertEquals(isbn, resultado.getIsbn());
        verify(libroRepository).findByIsbn(isbn);
    }

    @Test
    void buscarPorIsbnNoExistenteLanzaExcepcion() {
        // Arrange
        String isbn = "1234567890";
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> libroService.buscarPorIsbn(isbn));
        verify(libroRepository).findByIsbn(isbn);
    }

    @Test
    void obtenerTodosRetornaListaCompleta() {
        // Arrange
        List<Libro> librosEsperados = Arrays.asList(
                new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "ISBN2", "Libro 2", "Autor 2", EstadoLibro.PRESTADO));
        when(libroRepository.findAll()).thenReturn(librosEsperados);

        // Act
        List<Libro> resultado = libroService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(libroRepository).findAll();
    }

    @Test
    void guardarLibroValidoRetornaLibro() {
        // Arrange
        Libro libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitulo("Nuevo Libro");
        libro.setAutor("Autor Test");

        when(libroRepository.save(any(Libro.class))).thenReturn(libro);

        // Act
        Libro resultado = libroService.guardar(libro);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Libro", resultado.getTitulo());
        verify(libroRepository).save(libro);
    }

    @Test
    void guardarLibroSinIsbnLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro();
        libro.setTitulo("Libro sin ISBN");
        libro.setAutor("Autor");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> libroService.guardar(libro));
        verify(libroRepository, never()).save(any(Libro.class));
    }

    @Test
    void guardarLibroSinTituloLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setAutor("Autor");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> libroService.guardar(libro));
        verify(libroRepository, never()).save(any(Libro.class));
    }

    @Test
    void guardarLibroSinAutorLanzaExcepcion() {
        // Arrange
        Libro libro = new Libro();
        libro.setIsbn("1234567890");
        libro.setTitulo("Título");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> libroService.guardar(libro));
        verify(libroRepository, never()).save(any(Libro.class));
    }

    @Test
    void eliminarLibroExistenteNoLanzaExcepcion() {
        // Arrange
        Long id = 1L;
        when(libroRepository.existsById(id)).thenReturn(true);

        // Act
        libroService.eliminar(id);

        // Assert
        verify(libroRepository).deleteById(id);
    }

    @Test
    void eliminarLibroNoExistenteLanzaExcepcion() {
        // Arrange
        Long id = 999L;
        when(libroRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> libroService.eliminar(id));
        verify(libroRepository, never()).deleteById(anyLong());
    }

    @Test
    void actualizarLibroExistenteRetornaLibroActualizado() {
        // Arrange
        Long id = 1L;
        Libro libroExistente = new Libro(id, "ISBN1", "Título Original", "Autor Original", EstadoLibro.DISPONIBLE);
        Libro libroActualizado = new Libro();
        libroActualizado.setTitulo("Título Actualizado");
        libroActualizado.setAutor("Autor Actualizado");

        when(libroRepository.findById(id)).thenReturn(Optional.of(libroExistente));
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Libro resultado = libroService.actualizar(id, libroActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Título Actualizado", resultado.getTitulo());
        assertEquals("Autor Actualizado", resultado.getAutor());
        verify(libroRepository).findById(id);
        verify(libroRepository).save(any(Libro.class));
    }

    @Test
    void buscarDisponiblesRetornaSoloLibrosDisponibles() {
        // Arrange
        List<Libro> todosLosLibros = Arrays.asList(
                new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "ISBN2", "Libro 2", "Autor 2", EstadoLibro.PRESTADO),
                new Libro(3L, "ISBN3", "Libro 3", "Autor 3", EstadoLibro.DISPONIBLE));
        List<Libro> librosDisponibles = todosLosLibros.stream()
                .filter(Libro::estaDisponible)
                .collect(Collectors.toList());

        when(libroRepository.findAll()).thenReturn(todosLosLibros);

        // Act
        List<Libro> resultado = libroService.buscarDisponibles();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        for (Libro libro : resultado) {
            assertTrue(libro.estaDisponible());
        }
        verify(libroRepository).findAll();
    }

    @Test
    void enviarAReparacionLibroDisponibleCambiaEstado() {
        // Arrange
        Long id = 1L;
        Libro libro = new Libro(id, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE);

        when(libroRepository.findById(id)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        libroService.enviarAReparacion(id);

        // Assert
        assertEquals(EstadoLibro.EN_REPARACION, libro.getEstado());
        verify(libroRepository).findById(id);
        verify(libroRepository).save(libro);
    }

    @Test
    void enviarAReparacionLibroNoDisponibleLanzaExcepcion() {
        // Arrange
        Long id = 1L;
        Libro libro = new Libro(id, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.PRESTADO);

        when(libroRepository.findById(id)).thenReturn(Optional.of(libro));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> libroService.enviarAReparacion(id));
        verify(libroRepository).findById(id);
        verify(libroRepository, never()).save(any(Libro.class));
    }
}