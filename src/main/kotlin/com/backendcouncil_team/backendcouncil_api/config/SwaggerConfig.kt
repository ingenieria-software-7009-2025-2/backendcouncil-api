package com.backendcouncil_team.backendcouncil_api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.GroupedOpenApi

/**
 * Clase que establece la configuración de la documentación con OpenAPI.
 */

@Configuration
class SwaggerConfig {

    /**
     * Función que regresa una instancia OpenAPI customizada.
     * @return instancia OpenAPI customizada a nuestra conveniencia.
     */

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("SISREP - Sistema de Reportes Urbanos - API")
                    .version("1.1.3")
                    .description("Esta es la API para SISREP en un primer acercamiento.")
                    .contact(
                        Contact()
                            .name("El Consejo del Backend")
                            .email("isaacrobled@ciencias.unam.mx")
                    )
                    .license(
                        License()
                            .name("GNU GPL v3")
                            .url("https://www.gnu.org/licenses/gpl-3.0.html")
                    )
            ).servers(
                listOf(
                    Server()
                        .url("http://localhost:8080")
                )
            )
    }


    /**
     * Función que regresa el grupo de varias OpenAPI's, a través de un builder.
     * @return el grupo de varias OpenAPI's.
     */
    @Bean
    fun publicApi() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("build")
            .pathsToMatch("/v1/**")
            .build()
    }
}