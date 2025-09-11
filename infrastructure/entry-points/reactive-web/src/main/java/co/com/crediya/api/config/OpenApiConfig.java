package co.com.crediya.api.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CrediYA Solicitudes API")
                        .version("1.0.0")
                        .description("Documentación de la API para el microservicio de Solicitudes de CrediYA")
                        .contact(new Contact()
                                .name("Equipo CrediYA")
                                .email("soporte@crediya.com")
                        )
                );
    }

}
