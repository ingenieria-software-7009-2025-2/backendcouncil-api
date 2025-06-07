package com.backendcouncil_team.backendcouncil_api.coments.repository.entity;

import jakarta.persistence.*;
import java.math.BigDecimal


@Entity
@Table(name = "comentario")
class Coment (
    @Id
    val comentarioid: Long = 0,
    @Column(name="clienteid")
    var clienteid: Long = 0,
    @Column(name="incidenteid")
    var incidenteid: Long = 0,
    @Column(name="contenido")
    var contenido: String = "",
    @Column(name="likes")
    var likes: BigDecimal =  0.toBigDecimal(),
){}
