package com.biblioteca.app.controller;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.model.EstadoLibro;
import com.biblioteca.app.service.PrestamoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PrestamoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PrestamoService prestamoService;

    @InjectMocks
    private PrestamoController prestamoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
    }

    @Test
    void obtenerTodosDevuelveListaDePrestamos() throws Exception {
        // Arrange
        Libro libro1 = new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.PRESTADO);
        Usuario usuario1 = new Usuario(1L, "Juan Perez", "juan@mail.com", "ACTIVO");
        Prestamo prestamo1 = new Prestamo(1L, libro1, usuario1, LocalDate.now(), LocalDate.now().plusDays(7));

        Libro libro2 = new Libro(2L, "ISBN2", "Libro 2", "Autor 2", EstadoLibro.PRESTADO);
        Usuario usuario2 = new Usuario(2L, "Maria Garcia", "maria@mail.com", "ACTIVO");
        Prestamo prestamo2 = new Prestamo(2L, libro2, usuario2, LocalDate.now(), LocalDate.now().plusDays(7));

        List<Prestamo> prestamos = Arrays.asList(prestamo1, prestamo2);

        when(prestamoService.obtenerTodos()).thenReturn(prestamos);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        verify(prestamoService).obtenerTodos();
    }

    @Test
    void obtenerPorIdExistenteDevuelvePrestamo() throws Exception {
        // Arrange
        Long id = 1L;
        Libro libro = new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.PRESTADO);
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@mail.com", "ACTIVO");
        Prestamo prestamo = new Prestamo(id, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        when(prestamoService.buscarPorId(id)).thenReturn(prestamo);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.libro.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.usuario.nombre", is("Juan Perez")));

        verify(prestamoService).buscarPorId(id);
    }

    @Test
    void obtenerPorIdNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;

        when(prestamoService.buscarPorId(id)).thenThrow(new IllegalArgumentException("No existe el prestamo"));

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/{id}", id))
                .andExpect(status().isNotFound());

        verify(prestamoService).buscarPorId(id);
    }

    @Test
    void crearPrestamoValidoDevuelve201() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@mail.com", "ACTIVO");
        Prestamo nuevoPrestamo = new Prestamo(null, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
        Prestamo prestamoGuardado = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        when(prestamoService.guardar(any(Prestamo.class))).thenReturn(prestamoGuardado);

        // Act & Assert
        mockMvc.perform(post("/api/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoPrestamo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.libro.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.usuario.nombre", is("Juan Perez")));

        verify(prestamoService).guardar(any(Prestamo.class));
    }

    @Test
    void devolverLibroExistenteDevuelve200() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(prestamoService).devolverLibro(id);

        // Act & Assert
        mockMvc.perform(patch("/api/prestamos/{id}/devolver", id))
                .andExpect(status().isOk());

        verify(prestamoService).devolverLibro(id);
    }

    @Test
    void devolverLibroNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;
        doThrow(new IllegalArgumentException("No existe el prestamo")).when(prestamoService).devolverLibro(id);

        // Act & Assert
        mockMvc.perform(patch("/api/prestamos/{id}/devolver", id))
                .andExpect(status().isNotFound());

        verify(prestamoService).devolverLibro(id);
    }

    @Test
    void renovarPrestamoExistenteDevuelve200() throws Exception {
        // Arrange
        Long id = 1L;
        LocalDate nuevaFecha = LocalDate.now().plusDays(7);
        Libro libro = new Libro(1L, "ISBN1", "Libro 1", "Autor 1", EstadoLibro.PRESTADO);
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@mail.com", "ACTIVO");
        Prestamo prestamoRenovado = new Prestamo(id, libro, usuario, LocalDate.now(), nuevaFecha);

        when(prestamoService.renovarPrestamo(id)).thenReturn(prestamoRenovado);

        // Act & Assert
        mockMvc.perform(patch("/api/prestamos/{id}/renovar", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.libro.isbn", is("ISBN1")));

        verify(prestamoService).renovarPrestamo(id);
    }

    @Test
    void renovarPrestamoNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;
        when(prestamoService.renovarPrestamo(id)).thenThrow(new IllegalArgumentException("No existe el prestamo"));

        // Act & Assert
        mockMvc.perform(patch("/api/prestamos/{id}/renovar", id))
                .andExpect(status().isNotFound());

        verify(prestamoService).renovarPrestamo(id);
    }
}