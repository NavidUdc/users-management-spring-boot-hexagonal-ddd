package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateVueloCommand(
    @NotBlank(message = "id no puede ser nulo") String id,
    @NotBlank(message = "fechaCompra no puede ser nula") String fechaCompra,
    @NotBlank(message = "fechaSalida no puede ser nula") String fechaSalida,
    @NotBlank(message = "fechaLlegada no puede ser nula") String fechaLlegada,
    @NotBlank(message = "agenciaViajes no puede ser nula") String agenciaViajes,
    @NotBlank(message = "aerolinea no puede ser nula") String aerolinea,
    @NotBlank(message = "numero no puede ser nulo") String numero,
    @NotBlank(message = "estado no puede ser nulo") String estado,
    @NotNull(message = "valor no puede ser nulo") Double valor,
    @NotBlank(message = "cliente no puede ser nulo") String cliente,
    @NotBlank(message = "puesto no puede ser nulo") String puesto,
    @NotBlank(message = "avion no puede ser nulo") String avion,
    @NotBlank(message = "aereopuertoSalida no puede ser nulo") String aereopuertoSalida,
    @NotBlank(message = "aereopuertoLlegada no puede ser nulo") String aereopuertoLlegada,
    @NotBlank(message = "piloto no puede ser nulo") String piloto,
    @NotBlank(message = "usuarioId no puede ser nulo") String usuarioId) {}
