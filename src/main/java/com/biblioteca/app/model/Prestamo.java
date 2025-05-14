package com.biblioteca.app.model;

import java.time.LocalDate;

public class Prestamo {
    private Long id;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean activo;

    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
        this.activo = true;
    }

    public Prestamo(Libro libro, Usuario usuario) {
        if (!usuario.puedePrestarLibro()) {
            throw new IllegalStateException("El usuario no puede realizar más préstamos");
        }
        if (!libro.estaDisponible()) {
            throw new IllegalStateException("El libro no está disponible para préstamo");
        }

        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.activo = true;

        libro.prestar();
        usuario.agregarLibroPrestado(libro);
    }

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

    public boolean isActivo() {
        return activo;
    }

    public void finalizar() {
        if (!this.activo) {
            throw new IllegalStateException("El préstamo ya está finalizado");
        }
        this.activo = false;
        this.fechaDevolucion = LocalDate.now();
        this.usuario.devolverLibro(this.libro);
    }

    public boolean estaVencido() {
        if (!this.activo) {
            return false;
        }
        LocalDate fechaLimite = this.fechaPrestamo.plusDays(7);
        return LocalDate.now().isAfter(fechaLimite);
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", libro=" + libro.getTitulo() +
                ", usuario=" + usuario.getNombre() +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                ", activo=" + activo +
                '}';
    }
}