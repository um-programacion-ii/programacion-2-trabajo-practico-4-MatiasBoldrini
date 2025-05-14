package com.biblioteca.gestionDeBiblioteca.modelo;

import com.biblioteca.gestionDeBiblioteca.modelo.enums.EstadoLibro;

/**
 * Clase que representa un libro en el sistema de biblioteca
 */
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;

    // Constructor vacío
    public Libro() {
        this.estado = EstadoLibro.DISPONIBLE;
    }

    // Constructor con parámetros
    public Libro(Long id, String isbn, String titulo, String autor) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Libro [id=" + id + ", isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor + ", estado=" + estado + "]";
    }
    
    // Métodos de negocio
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