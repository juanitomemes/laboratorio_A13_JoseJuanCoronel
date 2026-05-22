package com.parque.dinosaurios;

import com.parque.dinosaurios.config.ParqueConfig;
import com.parque.dinosaurios.model.Turista;
import com.parque.dinosaurios.factory.ParqueFactory;
import com.parque.dinosaurios.model.Dinosaurio;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Bienvenido al Parque Turístico de Dinosaurios!");
        System.out.println("------------------------------------------------");

        // Obtenemos la instancia de nuestra configuración singleton
       // ParqueConfig config = ParqueConfig.getInstancia();

        // Leemos los valores del archivo properties
        //int turistasIniciales = config.getInt("tourists.initial");
       // int carnivoros = config.getInt("dinosaurs.carnivores");
       /* int herbivoros = config.getInt("dinosaurs.herbivores");

        // Imprimimos para verificar
        System.out.println("Configuración cargada con éxito:");
        System.out.println("-> Turistas iniciales en fila: " + turistasIniciales);
        System.out.println("-> Dinosaurios Carnívoros: " + carnivoros);
        System.out.println("-> Dinosaurios Herbívoros: " + herbivoros);*/

        // 1. Cargar configuración global
        ParqueConfig config = ParqueConfig.getInstancia();
        int turistasAcrear = config.getInt("tourists.initial");
        int carnivorosaCrear = config.getInt("dinosaurs.carnivores");
        int herbivorosaCrear = config.getInt("dinosaurs.herbivores");

        // 2. Listas para almacenar la población del parque
        List<Turista> filaTuristas = new ArrayList<>();
        List<Dinosaurio> listaDinosaurios = new ArrayList<>();

        // 3. Generar Turistas usando la Fábrica
        for (int i = 1; i <= turistasAcrear; i++) {
            filaTuristas.add(ParqueFactory.crearTurista(i));
        }

        // 4. Generar Dinosaurios usando la Fábrica
        int contadorDino = 1;
        for (int i = 1; i <= carnivorosaCrear; i++) {
            listaDinosaurios.add(ParqueFactory.crearDinosaurio(contadorDino++, "CARNIVORO"));
        }
        for (int i = 1; i <= herbivorosaCrear; i++) {
            listaDinosaurios.add(ParqueFactory.crearDinosaurio(contadorDino++, "HERBIVORO"));
        }

        // 5. Mostrar resultados del monitoreo inicial
        System.out.println("\n--- ESTADO INICIAL DEL PARQUE (POBLACIÓN GENERAL) ---");
        System.out.println("Total de Turistas creados en fila: " + filaTuristas.size());
        System.out.println("Total de Dinosaurios en recintos: " + listaDinosaurios.size());
        System.out.println("\nMuestra de dinosaurios generados:");

        // Imprimimos los primeros 3 dinosaurios para verificar que la fábrica funcione bien
        for (int i = 0; i < Math.min(5, listaDinosaurios.size()); i++) {
            Dinosaurio d = listaDinosaurios.get(i);
            System.out.println(" -> [" + d.getId() + "] " + d.getNombre() + " (" + d.getEspecie() + ") - Tipo: " + d.getTipo());
        }
    }
}