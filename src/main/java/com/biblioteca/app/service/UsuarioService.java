package com.biblioteca.app.service;

import com.biblioteca.app.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario buscarPorEmail(String email);

    Usuario buscarPorId(Long id);

    List<Usuario> obtenerTodos();

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    Usuario actualizar(Long id, Usuario usuario);

    List<Usuario> buscarUsuariosActivos();

    void suspenderUsuario(Long id);

    void activarUsuario(Long id);
}