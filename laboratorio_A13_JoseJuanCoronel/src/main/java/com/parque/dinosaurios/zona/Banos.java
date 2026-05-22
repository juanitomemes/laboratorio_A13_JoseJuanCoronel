package com.parque.dinosaurios.zona;

import com.parque.dinosaurios.model.Turista;

public class Banos extends Zona {
    private double costoSpa;
    private double ingresosSpa;

    public Banos(int capacidadMaxima, double costoSpa) {
        super("BANOS_Y_SPA", capacidadMaxima);
        this.costoSpa = costoSpa;
        this.ingresosSpa = 0.0;
    }

    /**
     * Simula la entrada al servicio premium del SPA.
     */
    public void ofrecerServicioSpa(Turista turista) {
        // 30% de probabilidad de que un turista en los baños decida relajarse en el SPA
        if (Math.random() < 0.3) {
            if (turista.pagar(costoSpa)) {
                this.ingresosSpa += costoSpa;
                System.out.println("   -> [Baños/SPA] " + turista.getId() + " tomó un servicio de SPA por $" + costoSpa);
            } else {
                System.out.println("   -> [Baños/SPA] " + turista.getId() + " quería SPA, pero su billetera no aguantó.");
            }
        }
    }

    public double getIngresosSpa() { return ingresosSpa; }
}