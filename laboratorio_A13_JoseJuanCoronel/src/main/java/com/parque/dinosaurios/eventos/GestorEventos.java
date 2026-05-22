package com.parque.dinosaurios.eventos;

import com.parque.dinosaurios.persistence.ParqueDAO;
import com.parque.dinosaurios.model.Dinosaurio;
import com.parque.dinosaurios.zona.PlantaEnergia;
import java.util.List;
import java.util.Random;

public class GestorEventos {
    private Random random = new Random();
    private ParqueDAO parqueDAO = new ParqueDAO();

    /**
     * Evalúa de forma aleatoria (35% de probabilidad) si ocurre un evento crítico
     * en el paso actual de la simulación.
     */
    public void procesarPosibleEvento(List<Dinosaurio> dinosaurios, PlantaEnergia planta, int paso) {
        // 35% de probabilidad de que ocurra un evento en cada paso
        if (random.nextDouble() > 0.35) {
            return;
        }

        int tipoEvento = random.nextInt(5); // 5 eventos posibles

        System.out.println("\n [EVENTO DINÁMICO DETECTADO] ");

        switch (tipoEvento) {
            case 0:
                ejecutarEscapeDinosaurio(dinosaurios, paso);
                break;
            case 1:
                ejecutarApagonMasivo(planta, paso);
                break;
            case 2:
                ejecutarTormentaTorrencial(paso);
                break;
            case 3:
                ejecutarHoraOfertas(paso);
                break;
            case 4:
                ejecutarFallaVehiculos(paso);
                break;
        }
        System.out.println("------------------------------------------------");
    }

    private void ejecutarEscapeDinosaurio(List<Dinosaurio> dinosaurios, int paso) {
        // Buscamos un carnívoro al azar para simular el escape
        Dinosaurio fugitivo = dinosaurios.stream()
                .filter(d -> d.getTipo().equals("CARNIVORO") && !d.isEscapado())
                .findAny()
                .orElse(null);

        if (fugitivo != null) {
            fugitivo.setEscapado(true);
            String desc = "¡ALERTA ROJA! El " + fugitivo.getEspecie() + " (" + fugitivo.getNombre() + ") rompió la cerca.";
            System.out.println("! " + desc);
            System.out.println("   [Resolución] Los guardias activaron los dardos tranquilizantes. El dinosaurio fue contenido.");
            fugitivo.setEscapado(false); // Se resuelve de inmediato como pide el flujo
            parqueDAO.registrarEvento("ESCAPE_DINOSAURIO", desc + " Contenido exitosamente.", paso);
        } else {
            System.out.println(" Intento de escape detectado, pero la seguridad de los recintos resistió.");
        }
    }

    private void ejecutarApagonMasivo(PlantaEnergia planta, int paso) {
        planta.reducirEnergia(100); // Energía cae a 0%
        String desc = "Falla eléctrica crítica. El parque se ha quedado a oscuras.";
        System.out.println("⚡ " + desc);
        System.out.println("   [Resolución] Los ingenieros encendieron los generadores de respaldo. Energía restablecida.");
        planta.restablecerEnergia(); // Se resuelve
        parqueDAO.registrarEvento("APAGON_MASIVO", desc + " Generadores activados.", paso);
    }

    private void ejecutarTormentaTorrencial(int paso) {
        String desc = "Una tormenta tropical azota la isla. Los turistas buscan refugio.";
        System.out.println("🌧️ " + desc);
        parqueDAO.registrarEvento("TORMENTA_TORRENCIAL", desc, paso);
    }

    private void ejecutarHoraOfertas(int paso) {
        String desc = "¡Hora Feliz en la tienda de Souvenirs! Descuentos del 50%.";
        System.out.println("$ " + desc);
        parqueDAO.registrarEvento("HORA_OFERTAS", desc, paso);
    }

    private void ejecutarFallaVehiculos(int paso) {
        String desc = "Los autos eléctricos del recorrido perimetral se han detenido por una falla de software.";
        System.out.println("brr brr " + desc);
        System.out.println("   [Resolución] Se aplicó un reinicio remoto al sistema de navegación.");
        parqueDAO.registrarEvento("FALLA_VEHICULOS", desc, paso);
    }
}