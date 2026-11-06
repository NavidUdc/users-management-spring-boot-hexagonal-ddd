package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record VueloEntity(
    String id,
    String fechaCompra,
    String fechaSalida,
    String fechaLlegada,
    String agenciaViajes,
    String aerolinea,
    String numero,
    String estado,
    Double valor,
    String cliente,
    String puesto,
    String avion,
    String aereopuertoSalida,
    String aereopuertoLlegada,
    String piloto,
    String usuarioId,
    String createdAt,
    String updatedAt) {}
