package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetVueloByIdQuery;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VueloApplicationMapper {

  public VueloModel fromCreateCommandToModel(final CreateVueloCommand command) {
    final String id =
        (command.id() != null && !command.id().isBlank())
            ? command.id()
            : UUID.randomUUID().toString();

    return VueloModel.fromPrimitives(
        id,
        command.fechaCompra(),
        command.fechaSalida(),
        command.fechaLlegada(),
        command.agenciaViajes(),
        command.aerolinea(),
        command.numero(),
        command.estado(),
        command.valor(),
        command.cliente(),
        command.puesto(),
        command.avion(),
        command.aereopuertoSalida(),
        command.aereopuertoLlegada(),
        command.piloto(),
        command.usuarioId());
  }

  public VueloModel fromUpdateCommandToModel(final UpdateVueloCommand command) {
    return VueloModel.fromPrimitives(
        command.id(),
        command.fechaCompra(),
        command.fechaSalida(),
        command.fechaLlegada(),
        command.agenciaViajes(),
        command.aerolinea(),
        command.numero(),
        command.estado(),
        command.valor(),
        command.cliente(),
        command.puesto(),
        command.avion(),
        command.aereopuertoSalida(),
        command.aereopuertoLlegada(),
        command.piloto(),
        command.usuarioId());
  }

  public String fromGetByIdQueryToId(final GetVueloByIdQuery query) {
    return query.id();
  }

  public String fromDeleteCommandToId(final DeleteVueloCommand command) {
    return command.id();
  }
}
