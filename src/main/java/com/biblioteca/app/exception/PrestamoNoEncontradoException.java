package com.biblioteca.app.exception;

public class PrestamoNoEncontradoException extends RuntimeException {
    public PrestamoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public PrestamoNoEncontradoException(Long id) {
        super("No se encontró el préstamo con ID: " + id);
    }
}