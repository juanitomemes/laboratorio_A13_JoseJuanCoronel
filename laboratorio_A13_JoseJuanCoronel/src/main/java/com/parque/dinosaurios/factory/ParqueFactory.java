package com.parque.dinosaurios.factory;

import com.parque.dinosaurios.model.Dinosaurio;
import com.parque.dinosaurios.model.Turista;
import java.util.Random;

public class ParqueFactory {

    private static final Random random = new Random();

    // Lista de especies de ejemplo para darle realismo a la simulación
    private static final String[] ESPECIES_CARNIVORAS = {"T-Rex", "Velociraptor", "Sposaurus", "Carnotaurus"};
    private static final String[] ESPECIES_HERBIVORAS = {"Triceratops", "Brachiosaurus", "Stegosaurus", "Diplodocus"};

    /**
     * Fábrica para crear Turistas con un ID secuencial.
     */
    public static Turista crearTurista(int numeroSecuencial) {
        String id = "TURI-" + String.format("%03d", numeroSecuencial);
        return new Turista(id);
    }

    /**
     * Fábrica para crear Dinosaurios basados en su tipo (CARNIVORO o HERBIVORO).
     */
    public static Dinosaurio crearDinosaurio(int numeroSecuencial, String tipo) {
        String tipoUpper = tipo.toUpperCase();
        String id = tipoUpper.substring(0, 4) + "-" + String.format("%03d", numeroSecuencial);

        // Seleccionar una especie aleatoria según el tipo
        String especie;
        String nombre;
        if (tipoUpper.equals("CARNIVORO")) {
            especie = ESPECIES_CARNIVORAS[random.nextInt(ESPECIES_CARNIVORAS.length)];
            nombre = "Depredador " + numeroSecuencial;
        } else {
            especie = ESPECIES_HERBIVORAS[random.nextInt(ESPECIES_HERBIVORAS.length)];
            nombre = "Pacífico " + numeroSecuencial;
        }

        return new Dinosaurio(id, nombre, especie, tipoUpper);
    }
}