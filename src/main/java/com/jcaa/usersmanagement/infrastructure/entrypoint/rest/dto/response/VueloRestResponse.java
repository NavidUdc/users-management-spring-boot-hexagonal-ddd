package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record VueloRestResponse(
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
    String usuarioId) {}
