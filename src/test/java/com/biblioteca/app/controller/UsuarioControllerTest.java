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

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void obtenerTodosDevuelveListaDeUsuarios() throws Exception {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "Juan Perez", "juan@mail.com", "ACTIVO"),
                new Usuario(2L, "Maria Garcia", "maria@mail.com", "ACTIVO"));

        when(usuarioService.obtenerTodos()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan Perez")))
                .andExpect(jsonPath("$[1].nombre", is("Maria Garcia")));

        verify(usuarioService).obtenerTodos();
    }

    @Test
    void obtenerPorIdExistenteDevuelveUsuario() throws Exception {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Juan Perez", "juan@mail.com", "ACTIVO");

        when(usuarioService.buscarPorId(id)).thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Perez")))
                .andExpect(jsonPath("$.email", is("juan@mail.com")));

        verify(usuarioService).buscarPorId(id);
    }

    @Test
    void obtenerPorIdNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;

        when(usuarioService.buscarPorId(id)).thenThrow(new IllegalArgumentException("No existe el usuario"));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isNotFound());

        verify(usuarioService).buscarPorId(id);
    }

    @Test
    void crearUsuarioValidoDevuelve201() throws Exception {
        // Arrange
        Usuario nuevoUsuario = new Usuario(null, "Pedro Lopez", "pedro@mail.com", "ACTIVO");
        Usuario usuarioGuardado = new Usuario(1L, "Pedro Lopez", "pedro@mail.com", "ACTIVO");

        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuarioGuardado);

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Pedro Lopez")))
                .andExpect(jsonPath("$.email", is("pedro@mail.com")));

        verify(usuarioService).guardar(any(Usuario.class));
    }

    @Test
    void actualizarUsuarioExistenteDevuelveUsuarioActualizado() throws Exception {
        // Arrange
        Long id = 1L;
        Usuario usuarioActualizado = new Usuario(id, "Juan Perez Actualizado", "juan.nuevo@mail.com", "ACTIVO");

        when(usuarioService.actualizar(eq(id), any(Usuario.class))).thenReturn(usuarioActualizado);

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Perez Actualizado")))
                .andExpect(jsonPath("$.email", is("juan.nuevo@mail.com")));

        verify(usuarioService).actualizar(eq(id), any(Usuario.class));
    }

    @Test
    void eliminarUsuarioExistenteDevuelve204() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(usuarioService).eliminar(id);

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/{id}", id))
                .andExpect(status().isNoContent());

        verify(usuarioService).eliminar(id);
    }

    @Test
    void eliminarUsuarioNoExistenteDevuelve404() throws Exception {
        // Arrange
        Long id = 999L;
        doThrow(new IllegalArgumentException("No existe el usuario")).when(usuarioService).eliminar(id);

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/{id}", id))
                .andExpect(status().isNotFound());

        verify(usuarioService).eliminar(id);
    }
}