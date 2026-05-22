package com.parque.dinosaurios;

import com.parque.dinosaurios.model.Turista;
import com.parque.dinosaurios.zona.LugarArribo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParqueTest {

    @Test
    public void testPagoTuristaExitoso() {
        Turista t = new Turista(1, 100.0); // ID 1, Presupuesto 100
        boolean pago = t.pagar(50.0);
        assertTrue(pago, "El turista debería poder pagar 50 teniendo 100");
        assertEquals(50.0, t.getPresupuesto(), "El presupuesto debería bajar a 50");
    }

    @Test
    public void testPagoTuristaFallo() {
        Turista t = new Turista(2, 20.0);
        boolean pago = t.pagar(50.0);
        assertFalse(pago, "El turista no debería poder pagar más de lo que tiene");
    }

    @Test
    public void testCapacidadZona() {
        LugarArribo zona = new LugarArribo(1, 20.0); // Capacidad 1
        Turista t1 = new Turista(1, 100.0);
        Turista t2 = new Turista(2, 100.0);

        zona.ingresarTurista(t1);
        boolean entroSegundo = zona.ingresarTurista(t2);

        assertFalse(entroSegundo, "La zona no debería permitir más turistas de su capacidad");
    }
}