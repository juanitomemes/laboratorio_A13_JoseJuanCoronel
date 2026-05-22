package com.parque.dinosaurios.zona;

import com.parque.dinosaurios.model.Turista;

public class LugarArribo extends Zona {
    private double costoBoleto;
    private double ingresosPorBoletos;

    public LugarArribo(int capacidadMaxima, double costoBoleto) {
        super("LUGAR_ARRIBO", capacidadMaxima);
        this.costoBoleto = costoBoleto;
        this.ingresosPorBoletos = 0.0;
    }

    /**
     * Intenta procesar la entrada de un turista vendiéndole un boleto.
     */
    public boolean procesarIngresoParque(Turista turista) {
        if (estaLlena()) {
            System.out.println("-> [Arribo] Capacidad máxima alcanzada. " + turista.getId() + " debe esperar.");
            return false;
        }

        // Intenta cobrarle el boleto al turista
        if (turista.pagar(costoBoleto)) {
            this.ingresosPorBoletos += costoBoleto;
            ingresarTurista(turista);
            System.out.println("-> [Arribo] " + turista.getId() + " compró boleto ($" + costoBoleto + ") e ingresó al parque.");
            return true;
        } else {
            System.out.println("-> [Arribo] " + turista.getId() + " no tiene dinero suficiente para el boleto. Se retira.");
            turista.setActivo(false);
            return false;
        }
    }

    public double getIngresosPorBoletos() { return ingresosPorBoletos; }
}