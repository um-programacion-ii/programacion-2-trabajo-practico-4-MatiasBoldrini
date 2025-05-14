package com.biblioteca.app.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String estado;
    private List<Libro> librosPrestados;

    public Usuario() {
        this.estado = "ACTIVO";
        this.librosPrestados = new ArrayList<>();
    }

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.estado = "ACTIVO";
        this.librosPrestados = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Libro> getLibrosPrestados() {
        return new ArrayList<>(librosPrestados);
    }

    public void agregarLibroPrestado(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        if (!libro.getEstado().equals(EstadoLibro.PRESTADO)) {
            throw new IllegalStateException("El libro debe estar en estado PRESTADO");
        }
        this.librosPrestados.add(libro);
    }

    public void devolverLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        if (!librosPrestados.contains(libro)) {
            throw new IllegalStateException("El usuario no tiene este libro prestado");
        }
        libro.devolver();
        this.librosPrestados.remove(libro);
    }

    public boolean puedePrestarLibro() {
        return "ACTIVO".equals(this.estado) && this.librosPrestados.size() < 3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", librosPrestados=" + librosPrestados.size() +
                '}';
    }
}