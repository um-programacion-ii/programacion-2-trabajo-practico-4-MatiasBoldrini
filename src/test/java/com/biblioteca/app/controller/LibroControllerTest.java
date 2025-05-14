package com.biblioteca.app.controller;

import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.EstadoLibro;
import com.biblioteca.app.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LibroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private LibroController libroController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
    }

    @Test
    void obtenerTodosDevuelveListaDeLibros() throws Exception {
        // Arrange
        List<Libro> libros = Arrays.asList(
                new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE),
                new Libro(2L, "ISBN2", "Libro 2", "Autor 2", EstadoLibro.PRESTADO));

        when(libroService.obtenerTodos()).thenReturn(libros);

        // Act & Assert
        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isbn", is("ISBN1")))
                .andExpect(jsonPath("$[1].isbn", is("ISBN2")));

        verify(libroService).obtenerTodos();
    }

    @Test
    void obtenerPorIdExistenteDevuelveLibro() throws Exception {
        // Arrange
        Long id = 1L;
        Libro libro = new Libro(id, "ISBN123", "El Libro Encontrado", "Autor Test", EstadoLibro.DISPONIBLE);

        when(libroService.buscarPorId(id)).thenReturn(libro);

        // Act & Assert
        mockMvc.perform(get("/api/libros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isbn", is("ISBN123")))
                .andExpect(jsonPath("$.titulo", is("El Libro Encontrado")));

        verify(libroService).buscarPorId(id);
    }

    @Test
    void obtenerPorIdNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;

        when(libroService.buscarPorId(id)).thenThrow(new IllegalArgumentException("No existe el libro"));

        // Act & Assert
        mockMvc.perform(get("/api/libros/{id}", id))
                .andExpect(status().isNotFound());

        verify(libroService).buscarPorId(id);
    }

    @Test
    void obtenerPorIsbnExistenteDevuelveLibro() throws Exception {
        // Arrange
        String isbn = "ISBN123";
        Libro libro = new Libro(1L, isbn, "El Libro por ISBN", "Autor Test", EstadoLibro.DISPONIBLE);

        when(libroService.buscarPorIsbn(isbn)).thenReturn(libro);

        // Act & Assert
        mockMvc.perform(get("/api/libros/isbn/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", is(isbn)))
                .andExpect(jsonPath("$.titulo", is("El Libro por ISBN")));

        verify(libroService).buscarPorIsbn(isbn);
    }

    @Test
    void obtenerLibrosDisponiblesDevuelveListaFiltrada() throws Exception {
        // Arrange
        List<Libro> librosDisponibles = Arrays.asList(
                new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE),
                new Libro(3L, "ISBN3", "Libro 3", "Autor 3", EstadoLibro.DISPONIBLE));

        when(libroService.buscarDisponibles()).thenReturn(librosDisponibles);

        // Act & Assert
        mockMvc.perform(get("/api/libros/disponibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].estado", is("DISPONIBLE")))
                .andExpect(jsonPath("$[1].estado", is("DISPONIBLE")));

        verify(libroService).buscarDisponibles();
    }

    @Test
    void crearLibroValidoDevuelve201() throws Exception {
        // Arrange
        Libro nuevoLibro = new Libro(null, "ISBN-NUEVO", "Nuevo Libro", "Autor Nuevo", EstadoLibro.DISPONIBLE);
        Libro libroGuardado = new Libro(1L, "ISBN-NUEVO", "Nuevo Libro", "Autor Nuevo", EstadoLibro.DISPONIBLE);

        when(libroService.guardar(any(Libro.class))).thenReturn(libroGuardado);

        // Act & Assert
        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoLibro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isbn", is("ISBN-NUEVO")))
                .andExpect(jsonPath("$.titulo", is("Nuevo Libro")));

        verify(libroService).guardar(any(Libro.class));
    }

    @Test
    void actualizarLibroExistenteDevuelveLibroActualizado() throws Exception {
        // Arrange
        Long id = 1L;
        Libro libroActualizado = new Libro(id, "ISBN123", "Título Actualizado", "Autor Actualizado",
                EstadoLibro.DISPONIBLE);

        when(libroService.actualizar(eq(id), any(Libro.class))).thenReturn(libroActualizado);

        // Act & Assert
        mockMvc.perform(put("/api/libros/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libroActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Título Actualizado")))
                .andExpect(jsonPath("$.autor", is("Autor Actualizado")));

        verify(libroService).actualizar(eq(id), any(Libro.class));
    }

    @Test
    void eliminarLibroExistenteDevuelve204() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(libroService).eliminar(id);

        // Act & Assert
        mockMvc.perform(delete("/api/libros/{id}", id))
                .andExpect(status().isNoContent());

        verify(libroService).eliminar(id);
    }

    @Test
    void eliminarLibroNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;
        doThrow(new IllegalArgumentException("No existe el libro")).when(libroService).eliminar(id);

        // Act & Assert
        mockMvc.perform(delete("/api/libros/{id}", id))
                .andExpect(status().isNotFound());

        verify(libroService).eliminar(id);
    }

    @Test
    void enviarAReparacionLibroDisponibleDevuelve200() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(libroService).enviarAReparacion(id);

        // Act & Assert
        mockMvc.perform(patch("/api/libros/{id}/reparacion", id))
                .andExpect(status().isOk());

        verify(libroService).enviarAReparacion(id);
    }

    @Test
    void enviarAReparacionLibroPrestadoDevuelve400() throws Exception {
        // Arrange
        Long id = 2L;
        doThrow(new IllegalStateException("Libro en préstamo, no se puede reparar")).when(libroService)
                .enviarAReparacion(id);

        // Act & Assert
        mockMvc.perform(patch("/api/libros/{id}/reparacion", id))
                .andExpect(status().isBadRequest());

        verify(libroService).enviarAReparacion(id);
    }
}