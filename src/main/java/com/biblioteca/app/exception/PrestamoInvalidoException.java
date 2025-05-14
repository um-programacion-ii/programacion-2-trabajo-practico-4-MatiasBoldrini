package com.biblioteca.app.exception;

public class PrestamoInvalidoException extends RuntimeException {
    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
    }
}