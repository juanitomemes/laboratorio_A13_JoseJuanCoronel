package com.parque.dinosaurios.zona;

import com.parque.dinosaurios.model.Dinosaurio;
import com.parque.dinosaurios.model.Turista;
import java.util.ArrayList;
import java.util.List;

public class RecintoObservacion extends Zona {
    private double costoEntrada;
    private double ingresosEntradas;
    private List<Dinosaurio> dinosauriosHospedados;

    public RecintoObservacion(String nombreEspecifico, int capacidadMaxima, double costoEntrada) {
        super(nombreEspecifico, capacidadMaxima);
        this.costoEntrada = costoEntrada;
        this.ingresosEntradas = 0.0;
        this.dinosauriosHospedados = new ArrayList<>();
    }

    public void alojarDinosaurio(Dinosaurio dino) {
        this.dinosauriosHospedados.add(dino);
    }

    /**
     * Intenta cobrar la entrada al recinto de dinosaurios y registra su experiencia.
     */
    public boolean visitarRecinto(Turista turista) {
        if (estaLlena()) {
            return false;
        }

        if (turista.pagar(costoEntrada)) {
            this.ingresosEntradas += costoEntrada;
            ingresarTurista(turista);
            System.out.println("   -> [" + getNombre() + "] " + turista.getId() + " pagó $" + costoEntrada + " por ver los dinosaurios.");

            // Simular respuesta rápida a la encuesta de satisfacción
            generarEncuestaSatisfaccion(turista);
            return true;
        }
        return false;
    }

    private void generarEncuestaSatisfaccion(Turista turista) {
        // Si hay dinosaurios con hambre alta (>70), la satisfacción baja
        boolean dinosHambrientos = dinosauriosHospedados.stream().anyMatch(d -> d.getNivelHambre() > 70);
        if (dinosHambrientos) {
            System.out.println("      [Encuesta] " + turista.getId() + " opina: '¡Los dinosaurios se ven peligrosamente hambrientos! Mala experiencia.'");
        } else {
            System.out.println("      [Encuesta] " + turista.getId() + " opina: '¡Increíble ver a los " + dinosauriosHospedados.size() + " dinosaurios!'");
        }
    }

    public double getIngresosEntradas() { return ingresosEntradas; }
    public List<Dinosaurio> getDinosauriosHospedados() { return dinosauriosHospedados; }
}