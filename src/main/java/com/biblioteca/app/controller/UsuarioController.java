package com.biblioteca.app.controller;

import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que maneja las operaciones CRUD y otras operaciones
 * específicas para los usuarios.
 * Todos los endpoints comienzan con /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return Lista de todos los usuarios
     * @HTTP GET /api/usuarios
     * @response 200 - Lista de usuarios encontrada exitosamente
     */
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario a buscar
     * @return El usuario encontrado
     * @HTTP GET /api/usuarios/{id}
     * @response 200 - Usuario encontrado exitosamente
     * @response 404 - Usuario no encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca un usuario por su email.
     * 
     * @param email Email del usuario a buscar
     * @return El usuario encontrado
     * @HTTP GET /api/usuarios/email/{email}
     * @response 200 - Usuario encontrado exitosamente
     * @response 404 - Usuario no encontrado
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los usuarios activos del sistema.
     * 
     * @return Lista de usuarios activos
     * @HTTP GET /api/usuarios/activos
     * @response 200 - Lista de usuarios activos encontrada exitosamente
     */
    @GetMapping("/activos")
    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioService.buscarUsuariosActivos();
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario Datos del usuario a crear
     * @return El usuario creado
     * @HTTP POST /api/usuarios
     * @response 201 - Usuario creado exitosamente
     * @response 400 - Datos del usuario inválidos
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param id      ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return El usuario actualizado
     * @HTTP PUT /api/usuarios/{id}
     * @response 200 - Usuario actualizado exitosamente
     * @response 404 - Usuario no encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id ID del usuario a eliminar
     * @return Respuesta sin contenido
     * @HTTP DELETE /api/usuarios/{id}
     * @response 204 - Usuario eliminado exitosamente
     * @response 404 - Usuario no encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Suspende un usuario activo.
     * 
     * @param id ID del usuario a suspender
     * @return Respuesta sin contenido
     * @HTTP PATCH /api/usuarios/{id}/suspender
     * @response 200 - Usuario suspendido exitosamente
     * @response 400 - El usuario no puede ser suspendido (ej: no está activo)
     * @response 404 - Usuario no encontrado
     */
    @PatchMapping("/{id}/suspender")
    public ResponseEntity<Void> suspenderUsuario(@PathVariable Long id) {
        try {
            usuarioService.suspenderUsuario(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
        try {
            usuarioService.activarUsuario(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}