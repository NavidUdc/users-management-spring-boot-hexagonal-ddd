package com.jcaa.usersmanagement.application.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetVueloByIdQuery;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VueloApplicationMapper")
class VueloApplicationMapperTest {

  @Test
  @DisplayName("fromCreateCommandToModel genera id si es nulo")
  void shouldGenerateIdWhenNull() {
    final CreateVueloCommand command =
        CreateVueloCommand.builder()
            .id(null)
            .fechaCompra("2026-11-06")
            .fechaSalida("2026-11-10")
            .fechaLlegada("2026-11-10")
            .agenciaViajes("Despegar")
            .aerolinea("Avianca")
            .numero("AV123")
            .estado("PROGRAMADO")
            .valor(300000.0)
            .cliente("Pedro")
            .puesto("1A")
            .avion("Boeing 737")
            .aereopuertoSalida("BOG")
            .aereopuertoLlegada("MDE")
            .piloto("Capitán Perez")
            .usuarioId("u-001")
            .build();

    final VueloModel model = VueloApplicationMapper.fromCreateCommandToModel(command);
    assertNotNull(model.getId());
    assertFalse(model.getId().value().isBlank());
    assertEquals("Avianca", model.getAerolinea().value());
  }

  @Test
  @DisplayName("fromUpdateCommandToModel mapea correctamente todos los campos")
  void shouldMapUpdateCommand() {
    final UpdateVueloCommand command =
        UpdateVueloCommand.builder()
            .id("v-001")
            .fechaCompra("2026-11-06")
            .fechaSalida("2026-11-10")
            .fechaLlegada("2026-11-10")
            .agenciaViajes("Despegar")
            .aerolinea("LATAM")
            .numero("LA456")
            .estado("CONFIRMADO")
            .valor(350000.0)
            .cliente("Maria")
            .puesto("2B")
            .avion("Airbus A320")
            .aereopuertoSalida("BOG")
            .aereopuertoLlegada("CLO")
            .piloto("Capitán Gómez")
            .usuarioId("u-002")
            .build();

    final VueloModel model = VueloApplicationMapper.fromUpdateCommandToModel(command);
    assertEquals("v-001", model.getId().value());
    assertEquals("LATAM", model.getAerolinea().value());
    assertEquals("u-002", model.getUsuarioId().value());
  }

  @Test
  @DisplayName("fromGetByIdQueryToId y fromDeleteCommandToId extraen el id correctamente")
  void shouldExtractIdFromQueryAndCommand() {
    final GetVueloByIdQuery query = new GetVueloByIdQuery("v-123");
    final DeleteVueloCommand deleteCommand = new DeleteVueloCommand("v-456");

    assertEquals("v-123", VueloApplicationMapper.fromGetByIdQueryToId(query));
    assertEquals("v-456", VueloApplicationMapper.fromDeleteCommandToId(deleteCommand));
  }
}
