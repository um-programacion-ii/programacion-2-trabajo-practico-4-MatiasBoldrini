package com.biblioteca.app.controller;

import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.service.UsuarioService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerTodos_DebeRetornarListaDeUsuarios() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        Usuario usuario2 = new Usuario(2L, "Ana", "ana@mail.com", "ACTIVO");
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioService.obtenerTodos()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].nombre", is("Ana")));
    }

    @Test
    void obtenerPorId_CuandoExiste_DebeRetornarUsuario() throws Exception {
        // Arrange
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan")))
                .andExpect(jsonPath("$.email", is("juan@mail.com")));
    }

    @Test
    void obtenerPorId_CuandoNoExiste_DebeRetornar404() throws Exception {
        // Arrange
        when(usuarioService.buscarPorId(1L)).thenThrow(new IllegalArgumentException());

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crear_ConDatosValidos_DebeRetornarUsuarioCreado() throws Exception {
        // Arrange
        Usuario nuevoUsuario = new Usuario(null, "Juan", "juan@mail.com", "ACTIVO");
        Usuario usuarioCreado = new Usuario(1L, "Juan", "juan@mail.com", "ACTIVO");

        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuarioCreado);

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan")));
    }

    @Test
    void actualizar_CuandoExiste_DebeRetornarUsuarioActualizado() throws Exception {
        // Arrange
        Usuario usuarioActualizado = new Usuario(1L, "Juan Actualizado", "juan@mail.com", "ACTIVO");
        when(usuarioService.actualizar(eq(1L), any(Usuario.class))).thenReturn(usuarioActualizado);

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan Actualizado")));
    }

    @Test
    void eliminar_CuandoExiste_DebeRetornar204() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).eliminar(1L);
    }

    @Test
    void suspenderUsuario_CuandoExiste_DebeRetornar200() throws Exception {
        // Act & Assert
        mockMvc.perform(patch("/api/usuarios/1/suspender"))
                .andExpect(status().isOk());

        verify(usuarioService).suspenderUsuario(1L);
    }

    @Test
    void activarUsuario_CuandoExiste_DebeRetornar200() throws Exception {
        // Act & Assert
        mockMvc.perform(patch("/api/usuarios/1/activar"))
                .andExpect(status().isOk());

        verify(usuarioService).activarUsuario(1L);
    }
}