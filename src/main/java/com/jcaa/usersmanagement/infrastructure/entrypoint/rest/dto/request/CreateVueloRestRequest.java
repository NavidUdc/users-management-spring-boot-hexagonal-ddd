package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVueloRestRequest(
    String id,
    @NotBlank(message = "fechaCompra no puede ser nula")
        @JsonAlias({"fecha_compra"})
        String fechaCompra,
    @NotBlank(message = "fechaSalida no puede ser nula")
        @JsonAlias({"FechaSalida", "fecha_salida"})
        String fechaSalida,
    @NotBlank(message = "fechaLlegada no puede ser nula")
        @JsonAlias({"FechaLlegada", "fecha_llegada"})
        String fechaLlegada,
    @NotBlank(message = "agenciaViajes no puede ser nula")
        @JsonAlias({"agencia_viajes"})
        String agenciaViajes,
    @NotBlank(message = "aerolinea no puede ser nula")
        @JsonAlias({"aerolínea", "aerolinea_nombre"})
        String aerolinea,
    @NotBlank(message = "numero no puede ser nulo")
        @JsonAlias({"numero_vuelo", "numeroVuelo"})
        String numero,
    @NotBlank(message = "estado no puede ser nulo")
        String estado,
    @NotNull(message = "valor no puede ser nulo")
        @JsonAlias({"precio"})
        Double valor,
    @NotBlank(message = "cliente no puede ser nulo")
        @JsonAlias({"nombre_cliente", "clienteNombre"})
        String cliente,
    @NotBlank(message = "puesto no puede ser nulo")
        @JsonAlias({"asiento"})
        String puesto,
    @NotBlank(message = "avion no puede ser nulo")
        @JsonAlias({"avión"})
        String avion,
    @NotBlank(message = "aereopuertoSalida no puede ser nulo")
        @JsonAlias({"aeropuertoSalida", "aereopuerto_salida", "aeropuerto_salida"})
        String aereopuertoSalida,
    @NotBlank(message = "aereopuertoLlegada no puede ser nulo")
        @JsonAlias({"aeropuertoLlegada", "aereopuerto_llegada", "aeropuerto_llegada"})
        String aereopuertoLlegada,
    @NotBlank(message = "piloto no puede ser nulo")
        String piloto,
    @NotBlank(message = "usuarioId no puede ser nulo")
        @JsonAlias({"idUsuario", "usuario_id", "id_usuario", "userId"})
        String usuarioId) {}
