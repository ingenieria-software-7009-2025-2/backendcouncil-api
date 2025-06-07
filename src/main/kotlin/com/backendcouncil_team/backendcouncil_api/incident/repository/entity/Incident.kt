package com.backendcouncil_team.backendcouncil_api.incident.repository.entity

import com.backendcouncil_team.backendcouncil_api.incident.domain.Estado
import jakarta.persistence.*
import java.math.BigDecimal

/**
* Entidad referente a incidentes provenientes del modelo.
 * @property incidenteid ID del incidente
 * @property clienteid ID del reportador.
 * @property categoriaid ID de la categoría del incidente.
 * @property nombre Nombre del incidente.
 * @property descripcion Descripción del incidente.
 * @property fecha Fecha del incidente.
 * @property hora Hora del incidente.
 * @property latitud Latitud del incidente.
 * @property longitud Longitud del incidente.
 * @property estado Estado del incidente.
*/

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
    @Column(name = "longitud",precision = 30, scale = 10)
    var longitud: BigDecimal = 0.toBigDecimal(),
    @Column(name = "latitud",precision = 30, scale = 10)
    var latitud: BigDecimal = 0.toBigDecimal(),
    //@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    //@Column(name = "estado")
    var estado: String?= null,
    @Column(name = "likes")
    var likes: BigDecimal =  0.toBigDecimal(),
){}