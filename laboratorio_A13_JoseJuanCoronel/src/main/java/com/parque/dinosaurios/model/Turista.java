package com.parque.dinosaurios.model;

import java.util.Random;

public class Turista {
    private String id;
    private double presupuesto;
    private String zonaActual; // "ARRIBO", "CENTRAL", "BANOS", "OBSERVACION", etc.
    private boolean activo; // false si ya salió del parque


    // CONSTRUCTOR 1: Para el factory y la simulación (recibe un String o int)
    public Turista(String id) {
        this.id = id;
        this.presupuesto = 150.0; // O el presupuesto base que use tu simulación
        this.activo = true;
        this.zonaActual = "LUGAR_ARRIBO";
    }

    // CONSTRUCTOR 2: Para las pruebas unitarias (recibe int y double)
    public Turista(int id, double presupuesto) {
        this.id = "TURI-" + String.format("%03d", id);
        this.presupuesto = presupuesto;
        this.activo = true;
        this.zonaActual = "LUGAR_ARRIBO";
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