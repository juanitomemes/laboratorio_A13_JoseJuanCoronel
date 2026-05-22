package com.parque.dinosaurios;

import com.parque.dinosaurios.config.ParqueConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Bienvenido al Parque Turístico de Dinosaurios!");
        System.out.println("------------------------------------------------");

        // Obtenemos la instancia de nuestra configuración singleton
        ParqueConfig config = ParqueConfig.getInstancia();

        // Leemos los valores del archivo properties
        int turistasIniciales = config.getInt("tourists.initial");
        int carnivoros = config.getInt("dinosaurs.carnivores");
        int herbivoros = config.getInt("dinosaurs.herbivores");

        // Imprimimos para verificar
        System.out.println("Configuración cargada con éxito:");
        System.out.println("-> Turistas iniciales en fila: " + turistasIniciales);
        System.out.println("-> Dinosaurios Carnívoros: " + carnivoros);
        System.out.println("-> Dinosaurios Herbívoros: " + herbivoros);
    }
}