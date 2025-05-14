package com.biblioteca.app.controller;

import com.biblioteca.app.model.Prestamo;
import com.biblioteca.app.model.Libro;
import com.biblioteca.app.model.Usuario;
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

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerTodos_DebeRetornarListaDePrestamos() throws Exception {
        // Arrange
        Libro libro1 = new Libro(1L, "123", "Libro 1", "Autor 1", "PRESTADO");
        Usuario usuario1 = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Prestamo prestamo1 = new Prestamo(1L, libro1, usuario1, LocalDate.now(), LocalDate.now().plusDays(7));

        List<Prestamo> prestamos = Arrays.asList(prestamo1);
        when(prestamoService.obtenerTodos()).thenReturn(prestamos);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void obtenerPorId_CuandoExiste_DebeRetornarPrestamo() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "123", "Libro 1", "Autor 1", "PRESTADO");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Prestamo prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        when(prestamoService.buscarPorId(1L)).thenReturn(prestamo);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void obtenerPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        // Arrange
        when(prestamoService.buscarPorId(1L)).thenThrow(new IllegalArgumentException());

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerPrestamosActivos_DebeRetornarListaDePrestamosActivos() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "123", "Libro 1", "Autor 1", "PRESTADO");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Prestamo prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        List<Prestamo> prestamosActivos = Arrays.asList(prestamo);
        when(prestamoService.obtenerPrestamosActivos()).thenReturn(prestamosActivos);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void obtenerPrestamosVencidos_DebeRetornarListaDePrestamosVencidos() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "123", "Libro 1", "Autor 1", "PRESTADO");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Prestamo prestamo = new Prestamo(1L, libro, usuario, LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(3));

        List<Prestamo> prestamosVencidos = Arrays.asList(prestamo);
        when(prestamoService.obtenerPrestamosVencidos()).thenReturn(prestamosVencidos);

        // Act & Assert
        mockMvc.perform(get("/api/prestamos/vencidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void realizarPrestamo_ConDatosValidos_DebeRetornarPrestamoCreado() throws Exception {
        // Arrange
        Libro libro = new Libro(1L, "123", "Libro 1", "Autor 1", "PRESTADO");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Prestamo nuevoPrestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        when(prestamoService.realizarPrestamo(1L, 1L)).thenReturn(nuevoPrestamo);

        // Act & Assert
        mockMvc.perform(post("/api/prestamos")
                .param("libroId", "1")
                .param("usuarioId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void devolverLibro_CuandoExiste_DebeRetornar200() throws Exception {
        // Act & Assert
        mockMvc.perform(patch("/api/prestamos/1/devolver"))
                .andExpect(status().isOk());

        verify(prestamoService).devolverLibro(1L);
    }

    @Test
    void eliminar_CuandoExiste_DebeRetornar204() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isNoContent());

        verify(prestamoService).eliminar(1L);
    }
}