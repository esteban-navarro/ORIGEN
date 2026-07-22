package cl.origen.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada principal de la aplicación ORIGEN.
 *
 * <p>Desde esta clase se inicializa el contexto de Spring Boot y se cargan
 * todos los componentes registrados en la aplicación.</p>
 */
@SpringBootApplication
public class OrigenApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrigenApplication.class, args);
    }

}
