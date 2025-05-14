package com.biblioteca.gestionDeBiblioteca.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoLibro.DISPONIBLE;
    }

    public void prestar() {
        if (estaDisponible()) {
            this.estado = EstadoLibro.PRESTADO;
        } else {
            throw new IllegalStateException("El libro no está disponible para préstamo");
        }
    }

    public void devolver() {
        if (estado == EstadoLibro.PRESTADO) {
            this.estado = EstadoLibro.DISPONIBLE;
        } else {
            throw new IllegalStateException("El libro no está prestado actualmente");
        }
    }

    public void enviarAReparacion() {
        if (estado == EstadoLibro.DISPONIBLE) {
            this.estado = EstadoLibro.EN_REPARACION;
        } else {
            throw new IllegalStateException("El libro no está disponible para enviar a reparación");
        }
    }

    public void terminarReparacion() {
        if (estado == EstadoLibro.EN_REPARACION) {
            this.estado = EstadoLibro.DISPONIBLE;
        } else {
            throw new IllegalStateException("El libro no está en reparación");
        }
    }
}