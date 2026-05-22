package com.parque.dinosaurios.model;

public class Dinosaurio {
    private String id;
    private String nombre;
    private String especie;
    private String tipo; // "CARNIVORO" o "HERBIVORO"
    private int nivelHambre; // De 0 (satisfecho) a 100 (hambriento)
    private boolean escapado;

    public Dinosaurio(String id, String nombre, String especie, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.tipo = tipo.toUpperCase();
        this.nivelHambre = 20; // Inician con un hambre moderada
        this.escapado = false;
    }

    // Métodos de negocio básicos
    public void alimentar(int cantidad) {
        this.nivelHambre = Math.max(0, this.nivelHambre - cantidad);
    }

    public void incrementarHambre(int cantidad) {
        this.nivelHambre = Math.min(100, this.nivelHambre + cantidad);
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getTipo() { return tipo; }
    public int getNivelHambre() { return nivelHambre; }
    public void setNivelHambre(int nivelHambre) { this.nivelHambre = nivelHambre; }
    public boolean isEscapado() { return escapado; }
    public void setEscapado(boolean escapado) { this.escapado = escapado; }
}