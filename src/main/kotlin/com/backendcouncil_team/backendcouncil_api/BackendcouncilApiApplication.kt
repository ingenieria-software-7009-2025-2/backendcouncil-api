package com.backendcouncil_team.backendcouncil_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Clase que inicia la aplicación gracias a SpringBoot.
 * @param args Argumentos. `UNUSED`.
 */

@SpringBootApplication
class BackendcouncilApiApplication

fun main(args: Array<String>) {
	runApplication<BackendcouncilApiApplication>(*args)
}
