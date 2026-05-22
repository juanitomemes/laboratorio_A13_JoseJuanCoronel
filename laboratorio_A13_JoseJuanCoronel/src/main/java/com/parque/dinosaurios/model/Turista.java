package com.parque.dinosaurios.model;

import java.util.Random;

public class Turista {
    private String id;
    private double presupuesto;
    private String zonaActual; // "ARRIBO", "CENTRAL", "BANOS", "OBSERVACION", etc.
    private boolean activo; // false si ya salió del parque

    public Turista(String id) {
        this.id = id;
        // Asignamos un presupuesto aleatorio entre $50 y $200 para la simulación
        Random random = new Random();
        this.presupuesto = 50 + (150 * random.nextDouble());
        this.zonaActual = "ARRIBO"; // Todos empiezan en la entrada
        this.activo = true;
    }

    // Método para realizar un pago (valida si tiene dinero suficiente)
    public boolean pagar(double monto) {
        if (this.presupuesto >= monto) {
            this.presupuesto -= monto;
            return true;
        }
        return false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public double getPresupuesto() { return presupuesto; }
    public String getZonaActual() { return zonaActual; }
    public void setZonaActual(String zonaActual) { this.zonaActual = zonaActual; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}