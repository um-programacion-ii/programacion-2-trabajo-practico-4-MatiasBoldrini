package com.biblioteca.app.repository;

import com.biblioteca.app.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}