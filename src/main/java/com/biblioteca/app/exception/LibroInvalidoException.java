package com.biblioteca.app.exception;

public class LibroInvalidoException extends RuntimeException {
    public LibroInvalidoException(String mensaje) {
        super(mensaje);
    }

    public LibroInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}