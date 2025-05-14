package com.biblioteca.app.service;

import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.repository.UsuarioRepository;
import com.biblioteca.app.service.impl.UsuarioServiceImpl;

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

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorEmailExistenteRetornaUsuario() {
        // Arrange
        String email = "usuario@test.com";
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1L);
        usuarioEsperado.setEmail(email);
        usuarioEsperado.setNombre("Usuario Test");
        usuarioEsperado.setEstado("ACTIVO");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioEsperado));

        // Act
        Usuario resultado = usuarioService.buscarPorEmail(email);

        // Assert
        assertNotNull(resultado);
        assertEquals(email, resultado.getEmail());
        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    void buscarPorEmailNoExistenteLanzaExcepcion() {
        // Arrange
        String email = "noexiste@test.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.buscarPorEmail(email));
        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    void obtenerTodosRetornaListaCompleta() {
        // Arrange
        List<Usuario> usuariosEsperados = Arrays.asList(
                new Usuario(1L, "Usuario 1", "email1@test.com", "ACTIVO"),
                new Usuario(2L, "Usuario 2", "email2@test.com", "SUSPENDIDO"));
        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        // Act
        List<Usuario> resultado = usuarioService.obtenerTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void guardarUsuarioValidoRetornaUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Nuevo Usuario");
        usuario.setEmail("nuevo@test.com");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        Usuario resultado = usuarioService.guardar(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Usuario", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void guardarUsuarioSinNombreLanzaExcepcion() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("email@test.com");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.guardar(usuario));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void guardarUsuarioSinEmailLanzaExcepcion() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario sin email");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.guardar(usuario));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuarioExistenteNoLanzaExcepcion() {
        // Arrange
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);

        // Act
        usuarioService.eliminar(id);

        // Assert
        verify(usuarioRepository).deleteById(id);
    }

    @Test
    void eliminarUsuarioNoExistenteLanzaExcepcion() {
        // Arrange
        Long id = 999L;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.eliminar(id));
        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void actualizarUsuarioExistenteRetornaUsuarioActualizado() {
        // Arrange
        Long id = 1L;
        Usuario usuarioExistente = new Usuario(id, "Nombre Original", "email@test.com", "ACTIVO");
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nombre Actualizado");
        usuarioActualizado.setEmail("actualizado@test.com");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario resultado = usuarioService.actualizar(id, usuarioActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nombre Actualizado", resultado.getNombre());
        assertEquals("actualizado@test.com", resultado.getEmail());
        verify(usuarioRepository).findById(id);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void buscarUsuariosActivosRetornaSoloUsuariosActivos() {
        // Arrange
        List<Usuario> todosLosUsuarios = Arrays.asList(
                new Usuario(1L, "Usuario 1", "email1@test.com", "ACTIVO"),
                new Usuario(2L, "Usuario 2", "email2@test.com", "SUSPENDIDO"),
                new Usuario(3L, "Usuario 3", "email3@test.com", "ACTIVO"));

        when(usuarioRepository.findAll()).thenReturn(todosLosUsuarios);

        // Act
        List<Usuario> resultado = usuarioService.buscarUsuariosActivos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        for (Usuario usuario : resultado) {
            assertEquals("ACTIVO", usuario.getEstado());
        }
        verify(usuarioRepository).findAll();
    }

    @Test
    void suspenderUsuarioActivoCambiaEstado() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Usuario", "email@test.com", "ACTIVO");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        usuarioService.suspenderUsuario(id);

        // Assert
        assertEquals("SUSPENDIDO", usuario.getEstado());
        verify(usuarioRepository).findById(id);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void suspenderUsuarioNoActivoLanzaExcepcion() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Usuario", "email@test.com", "SUSPENDIDO");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> usuarioService.suspenderUsuario(id));
        verify(usuarioRepository).findById(id);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void activarUsuarioSuspendidoCambiaEstado() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Usuario", "email@test.com", "SUSPENDIDO");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        usuarioService.activarUsuario(id);

        // Assert
        assertEquals("ACTIVO", usuario.getEstado());
        verify(usuarioRepository).findById(id);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void activarUsuarioYaActivoLanzaExcepcion() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Usuario", "email@test.com", "ACTIVO");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> usuarioService.activarUsuario(id));
        verify(usuarioRepository).findById(id);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}