package com.parque.dinosaurios.zona;

public class PlantaEnergia extends Zona {
    private int nivelEnergia; // De 0% a 100%
    private double gastosMantenimiento;

    public PlantaEnergia(int capacidadMaxima) {
        // Capacidad de trabajadores en la planta
        super("PLANTA_ENERGIA", capacidadMaxima);
        this.nivelEnergia = 100; // Inicia al 100%
        this.gastosMantenimiento = 0.0;
    }

    public void registrarGastoMantenimiento(double costo) {
        this.gastosMantenimiento += costo;
    }

    public void reducirEnergia(int cantidad) {
        this.nivelEnergia = Math.max(0, this.nivelEnergia - cantidad);
    }

    public void restablecerEnergia() {
        this.nivelEnergia = 100;
    }

    // Getters y Setters
    public int getNivelEnergia() { return nivelEnergia; }
    public double getGastosMantenimiento() { return gastosMantenimiento; }
}