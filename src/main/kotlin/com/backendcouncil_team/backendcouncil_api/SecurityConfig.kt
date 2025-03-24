package com.backendcouncil_team.backendcouncil_api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/v1/api-docs/**",    // Allow access to OpenAPI docs
                    "/docs-api/**",     // Allow access to Swagger UI
                    "/docs-api.html" ,
                    "/swagger-ui/**",     // Allow access to Swagger UI
                    "/swagger-ui.html" ,
                    "/v1/users/**",
                    "/v1/incident/**"
                ).permitAll()
                it.anyRequest().authenticated() // Secure other endpoints
            }
            .csrf { it.disable() }  // Disable CSRF for APIs
        return http.build()
    }
}