package com.parque.dinosaurios.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParqueConfig {

    // Instancia única (Patrón Singleton)
    private static ParqueConfig instancia;
    private Properties properties;

    // Constructor privado para evitar que se creen instancias con 'new' desde fuera
    private ParqueConfig() {
        properties = new Properties();
        // Cargamos el archivo desde la carpeta de recursos
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Lo siento, no se pudo encontrar el archivo config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Método público para obtener la única instancia de la configuración
    public static ParqueConfig getInstancia() {
        if (instancia == null) {
            instancia = new ParqueConfig();
        }
        return instancia;
    }

    // Métodos para obtener los valores transformados a su tipo de dato correspondiente
    public int getInt(String clave) {
        String valor = properties.getProperty(clave);
        return valor != null ? Integer.parseInt(valor) : 0;
    }

    public String getString(String clave) {
        return properties.getProperty(clave);
    }
}