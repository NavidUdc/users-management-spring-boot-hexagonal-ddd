package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.VueloPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.VueloEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VueloPersistenceMapper {

  public VueloPersistenceDto fromModelToDto(final VueloModel vuelo) {
    return new VueloPersistenceDto(
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
        vuelo.getUsuarioId().value(),
        null,
        null);
  }

  public VueloEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new VueloEntity(
        resultSet.getString("id"),
        resultSet.getString("fecha_compra"),
        resultSet.getString("fecha_salida"),
        resultSet.getString("fecha_llegada"),
        resultSet.getString("agencia_viajes"),
        resultSet.getString("aerolinea"),
        resultSet.getString("numero"),
        resultSet.getString("estado"),
        resultSet.getDouble("valor"),
        resultSet.getString("cliente"),
        resultSet.getString("puesto"),
        resultSet.getString("avion"),
        resultSet.getString("aereopuerto_salida"),
        resultSet.getString("aereopuerto_llegada"),
        resultSet.getString("piloto"),
        resultSet.getString("usuario_id"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public VueloModel fromEntityToModel(final VueloEntity entity) {
    return VueloModel.fromPrimitives(
        entity.id(),
        entity.fechaCompra(),
        entity.fechaSalida(),
        entity.fechaLlegada(),
        entity.agenciaViajes(),
        entity.aerolinea(),
        entity.numero(),
        entity.estado(),
        entity.valor(),
        entity.cliente(),
        entity.puesto(),
        entity.avion(),
        entity.aereopuertoSalida(),
        entity.aereopuertoLlegada(),
        entity.piloto(),
        entity.usuarioId());
  }

  public VueloModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<VueloModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    final List<VueloModel> vuelos = new ArrayList<>();
    while (resultSet.next()) {
      vuelos.add(fromResultSetToModel(resultSet));
    }
    return vuelos;
  }
}
