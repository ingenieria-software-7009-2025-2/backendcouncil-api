package com.backendcouncil_team.backendcouncil_api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Clase que establece la configuración Cross-Origin Resource Sharing y su seguridad para nuestro sitio web.
 */

@Configuration
class CorsConfig {

    /**
     * Función que regresa un configurador.
     * @return El configurador customizado para nuestra aplicación.
     */
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/v1/**") // Aplica CORS a los endpoints bajo /v1/
                    .allowedOrigins("http://localhost:5173") // Permitir peticiones desde React
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
    }
}