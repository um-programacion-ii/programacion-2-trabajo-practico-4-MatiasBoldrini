package com.biblioteca.gestionDeBiblioteca.modelo;

/**
 * Clase que representa un usuario del sistema de biblioteca
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String estado;

    // Constructor vacío
    public Usuario() {
        this.estado = "ACTIVO";
    }

    // Constructor con parámetros
    public Usuario(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.estado = "ACTIVO";
    }

    // Getters y setters
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

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", estado=" + estado + "]";
    }

    // Métodos de negocio
    public boolean estaActivo() {
        return "ACTIVO".equals(estado);
    }

    public void suspender() {
        if (estaActivo()) {
            this.estado = "SUSPENDIDO";
        } else {
            throw new IllegalStateException("El usuario ya está suspendido o inactivo");
        }
    }

    public void activar() {
        this.estado = "ACTIVO";
    }

    public void darDeBaja() {
        this.estado = "INACTIVO";
    }
}