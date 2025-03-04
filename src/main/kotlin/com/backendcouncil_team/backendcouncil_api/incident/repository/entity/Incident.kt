package com.backendcouncil_team.backendcouncil_api.incident.repository.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "incidente")
class Incident constructor(
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    val incidenteid: Long = 0,
    @Column(name = "clienteid")
    var clienteid: Long = 0,
    @Column(name = "categoriaid")
    var categoriaid: Long = 0,
    @Column(name = "nombre")
    var nombre: String = "",
    @Column(name = "descripcion")
    var descripcion: String = "",
    @Column(name = "fecha")
    var fecha: String = "",
    @Column(name = "hora")
    var hora: String = "",
    @Column(name = "longitud")
    var longitud: BigDecimal = 0.toBigDecimal(),
    @Column(name = "latitud")
    var latitud: BigDecimal = 0.toBigDecimal(),
    @Column(name = "estado")
    var estado: String = "",
){}