package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetVueloByIdQuery;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateVueloRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateVueloRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.VueloRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VueloRestMapper {

  public CreateVueloCommand toCreateCommand(final CreateVueloRestRequest request) {
    return CreateVueloCommand.builder()
        .id(request.id())
        .fechaCompra(request.fechaCompra())
        .fechaSalida(request.fechaSalida())
        .fechaLlegada(request.fechaLlegada())
        .agenciaViajes(request.agenciaViajes())
        .aerolinea(request.aerolinea())
        .numero(request.numero())
        .estado(request.estado())
        .valor(request.valor())
        .cliente(request.cliente())
        .puesto(request.puesto())
        .avion(request.avion())
        .aereopuertoSalida(request.aereopuertoSalida())
        .aereopuertoLlegada(request.aereopuertoLlegada())
        .piloto(request.piloto())
        .usuarioId(request.usuarioId())
        .build();
  }

  public UpdateVueloCommand toUpdateCommand(
      final String id, final UpdateVueloRestRequest request) {
    return UpdateVueloCommand.builder()
        .id(id)
        .fechaCompra(request.fechaCompra())
        .fechaSalida(request.fechaSalida())
        .fechaLlegada(request.fechaLlegada())
        .agenciaViajes(request.agenciaViajes())
        .aerolinea(request.aerolinea())
        .numero(request.numero())
        .estado(request.estado())
        .valor(request.valor())
        .cliente(request.cliente())
        .puesto(request.puesto())
        .avion(request.avion())
        .aereopuertoSalida(request.aereopuertoSalida())
        .aereopuertoLlegada(request.aereopuertoLlegada())
        .piloto(request.piloto())
        .usuarioId(request.usuarioId())
        .build();
  }

  public GetVueloByIdQuery toGetByIdQuery(final String id) {
    return new GetVueloByIdQuery(id);
  }

  public DeleteVueloCommand toDeleteCommand(final String id) {
    return new DeleteVueloCommand(id);
  }

  public VueloRestResponse toResponse(final VueloModel vuelo) {
    return new VueloRestResponse(
        vuelo.getId().value(),
        vuelo.getFechaCompra().value(),
        vuelo.getFechaSalida().value(),
        vuelo.getFechaLlegada().value(),
        vuelo.getAgenciaViajes().value(),
        vuelo.getAerolinea().value(),
        vuelo.getNumero().value(),
        vuelo.getEstado().value(),
        vuelo.getValor().value(),
        vuelo.getCliente().value(),
        vuelo.getPuesto().value(),
        vuelo.getAvion().value(),
        vuelo.getAereopuertoSalida().value(),
        vuelo.getAereopuertoLlegada().value(),
        vuelo.getPiloto().value(),
        vuelo.getUsuarioId().value());
  }

  public List<VueloRestResponse> toResponseList(final List<VueloModel> vuelos) {
    return vuelos.stream().map(VueloRestMapper::toResponse).toList();
  }
}
