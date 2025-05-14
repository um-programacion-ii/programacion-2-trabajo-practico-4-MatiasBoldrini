package com.biblioteca.app.exception;

public class UsuarioInvalidoException extends RuntimeException {
    public UsuarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}