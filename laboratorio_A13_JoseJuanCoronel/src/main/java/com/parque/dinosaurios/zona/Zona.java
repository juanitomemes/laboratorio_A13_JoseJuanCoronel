package com.parque.dinosaurios.zona;

import com.parque.dinosaurios.model.Turista;
import java.util.ArrayList;
import java.util.List;

public abstract class Zona {
    private String nombre;
    private int capacidadMaxima;
    protected List<Turista> turistasPresentes;

    public Zona(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.turistasPresentes = new ArrayList<>();
    }

    public boolean ingresarTurista(Turista turista) {
        if (turistasPresentes.size() < capacidadMaxima) {
            turistasPresentes.add(turista);
            turista.setZonaActual(this.nombre);
            return true;
        }
        return false; // Zona llena
    }

    public void salirTurista(Turista turista) {
        turistasPresentes.remove(turista);
    }

    public boolean estaLlena() {
        return turistasPresentes.size() >= capacidadMaxima;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public int getCantidadActual() { return turistasPresentes.size(); }
    public List<Turista> getTuristasPresentes() { return turistasPresentes; }
}