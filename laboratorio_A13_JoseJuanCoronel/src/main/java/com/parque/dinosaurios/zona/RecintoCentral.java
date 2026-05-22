package com.parque.dinosaurios.zona;

import com.parque.dinosaurios.model.Turista;

public class RecintoCentral extends Zona {
    private double costoSouvenir;
    private double ingresosSouvenirs;

    public RecintoCentral(int capacidadMaxima, double costoSouvenir) {
        super("RECINTO_CENTRAL", capacidadMaxima);
        this.costoSouvenir = costoSouvenir;
        this.ingresosSouvenirs = 0.0;
    }

    /**
     * Simula la interacción en el Recinto Central (intentar comprar un souvenir).
     */
  /*  public void interactuar(Turista turista) {
        // Los turistas intentan comprar un souvenir de forma pseudoaleatoria si les alcanza
        if (Math.random() > 0.5) { // 50% de probabilidad de que quieran comprar
            if (turista.pagar(costoSouvenir)) {
                this.ingresosSouvenirs += costoSouvenir;
                System.out.println("   -> [Central] " + turista.getId() + " compró un souvenir de dinosaurio por $" + costoSouvenir);
            } else {
                System.out.println("   -> [Central] " + turista.getId() + " quería un souvenir pero no le alcanzó.");
            }
        }
    }*/

    public double interactuarMonto(Turista turista) {
        if (Math.random() > 0.5) {
            if (turista.pagar(costoSouvenir)) {
                this.ingresosSouvenirs += costoSouvenir;
                System.out.println("   -> [Central] " + turista.getId() + " compró un souvenir por $" + costoSouvenir);
                return costoSouvenir;
            }
        }
        return 0.0;
    }

    public double getIngresosSouvenirs() { return ingresosSouvenirs; }
}