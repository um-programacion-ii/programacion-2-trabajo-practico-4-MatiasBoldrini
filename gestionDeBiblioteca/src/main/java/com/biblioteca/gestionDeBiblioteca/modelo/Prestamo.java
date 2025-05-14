package com.biblioteca.gestionDeBiblioteca.modelo;

import java.time.LocalDate;

/**
 * Clase que representa un préstamo de un libro a un usuario
 */
public class Prestamo {
    private Long id;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    // Constructor vacío
    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
    }

    // Constructor con parámetros
    public Prestamo(Long id, Libro libro, Usuario usuario) {
        this.id = id;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        // La fecha de devolución se establece cuando se devuelve el libro
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + id + ", libro=" + libro + ", usuario=" + usuario + ", fechaPrestamo=" + fechaPrestamo
                + ", fechaDevolucion=" + fechaDevolucion + "]";
    }

    // Métodos de negocio
    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public void registrarDevolucion() {
        if (estaActivo()) {
            this.fechaDevolucion = LocalDate.now();
            this.libro.devolver();
        } else {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
    }

    public long diasPrestado() {
        if (estaActivo()) {
            return java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, LocalDate.now());
        } else {
            return java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, fechaDevolucion);
        }
    }
}