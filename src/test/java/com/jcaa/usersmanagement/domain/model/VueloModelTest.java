package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.valueobject.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VueloModel")
class VueloModelTest {

  @Test
  @DisplayName("fromPrimitives() y create() deben preservar todos los campos recibidos en sus Value Objects")
  void shouldCreateVueloAndPreserveAllFields() {
    final VueloModel model =
        VueloModel.fromPrimitives(
            "v-001",
            "2026-11-06 10:00:00",
            "2026-11-10 08:30:00",
            "2026-11-10 10:45:00",
            "Despegar",
            "Avianca",
            "AV8520",
            "CONFIRMADO",
            450000.0,
            "Juan Perez",
            "12A",
            "Airbus A320",
            "El Dorado",
            "Rafael Nunez",
            "Carlos Ramirez",
            "u-001");

    assertAll(
        "VueloModel fields",
        () -> assertEquals(new VueloId("v-001"), model.getId()),
        () -> assertEquals("v-001", model.getId().value()),
        () -> assertEquals("2026-11-06 10:00:00", model.getFechaCompra().value()),
        () -> assertEquals("2026-11-10 08:30:00", model.getFechaSalida().value()),
        () -> assertEquals("2026-11-10 10:45:00", model.getFechaLlegada().value()),
        () -> assertEquals("Despegar", model.getAgenciaViajes().value()),
        () -> assertEquals("Avianca", model.getAerolinea().value()),
        () -> assertEquals("AV8520", model.getNumero().value()),
        () -> assertEquals("CONFIRMADO", model.getEstado().value()),
        () -> assertEquals(450000.0, model.getValor().value()),
        () -> assertEquals("Juan Perez", model.getCliente().value()),
        () -> assertEquals("12A", model.getPuesto().value()),
        () -> assertEquals("Airbus A320", model.getAvion().value()),
        () -> assertEquals("El Dorado", model.getAereopuertoSalida().value()),
        () -> assertEquals("Rafael Nunez", model.getAereopuertoLlegada().value()),
        () -> assertEquals("Carlos Ramirez", model.getPiloto().value()),
        () -> assertEquals("u-001", model.getUsuarioId().value()));
  }

  @Test
  @DisplayName("create() con VOs instancia correctamente")
  void shouldCreateWithVOs() {
    final VueloModel model =
        VueloModel.create(
            new VueloId("v-001"),
            new VueloFechaCompra("2026-11-06 10:00:00"),
            new VueloFechaSalida("2026-11-10 08:30:00"),
            new VueloFechaLlegada("2026-11-10 10:45:00"),
            new VueloAgenciaViajes("Despegar"),
            new VueloAerolinea("Avianca"),
            new VueloNumero("AV8520"),
            new VueloEstado("CONFIRMADO"),
            new VueloValor(450000.0),
            new VueloCliente("Juan Perez"),
            new VueloPuesto("12A"),
            new VueloAvion("Airbus A320"),
            new VueloAereopuertoSalida("El Dorado"),
            new VueloAereopuertoLlegada("Rafael Nunez"),
            new VueloPiloto("Carlos Ramirez"),
            new UserId("u-001"));

    assertEquals("v-001", model.getId().value());
    assertEquals("Avianca", model.getAerolinea().value());
  }
}
