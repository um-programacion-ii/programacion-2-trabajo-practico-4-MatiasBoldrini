package com.biblioteca.app.model;

public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;

    public Libro() {
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = EstadoLibro.DISPONIBLE;
    }

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

    public boolean estaDisponible() {
        return this.estado == EstadoLibro.DISPONIBLE;
    }

    public void prestar() {
        if (!estaDisponible()) {
            throw new IllegalStateException("El libro no está disponible para préstamo");
        }
        this.estado = EstadoLibro.PRESTADO;
    }

    public void devolver() {
        if (this.estado != EstadoLibro.PRESTADO) {
            throw new IllegalStateException("El libro no está prestado");
        }
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public void enviarAReparacion() {
        if (this.estado == EstadoLibro.PRESTADO) {
            throw new IllegalStateException("No se puede enviar a reparación un libro prestado");
        }
        this.estado = EstadoLibro.EN_REPARACION;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", estado=" + estado +
                '}';
    }
}