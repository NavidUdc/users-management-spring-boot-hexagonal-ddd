package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.VueloPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.VueloEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VueloPersistenceMapper")
class VueloPersistenceMapperTest {

  @Test
  @DisplayName("fromModelToDto mapea correctamente")
  void shouldMapModelToDto() {
    final VueloModel model =
        VueloModel.fromPrimitives(
            "v-001",
            "2026-11-06",
            "2026-11-10",
            "2026-11-10",
            "Despegar",
            "Avianca",
            "AV123",
            "CONFIRMADO",
            250000.0,
            "Carlos",
            "10A",
            "Airbus A320",
            "BOG",
            "MDE",
            "Capitán Gómez",
            "u-001");

    final VueloPersistenceDto dto = VueloPersistenceMapper.fromModelToDto(model);
    assertEquals("v-001", dto.id());
    assertEquals("Avianca", dto.aerolinea());
    assertEquals("u-001", dto.usuarioId());
  }

  @Test
  @DisplayName("fromResultSetToEntity mapea correctamente")
  void shouldMapResultSetToEntity() throws SQLException {
    final ResultSet rs = mock(ResultSet.class);
    when(rs.getString("id")).thenReturn("v-001");
    when(rs.getString("fecha_compra")).thenReturn("2026-11-06");
    when(rs.getString("fecha_salida")).thenReturn("2026-11-10");
    when(rs.getString("fecha_llegada")).thenReturn("2026-11-10");
    when(rs.getString("agencia_viajes")).thenReturn("Despegar");
    when(rs.getString("aerolinea")).thenReturn("Avianca");
    when(rs.getString("numero")).thenReturn("AV123");
    when(rs.getString("estado")).thenReturn("CONFIRMADO");
    when(rs.getDouble("valor")).thenReturn(250000.0);
    when(rs.getString("cliente")).thenReturn("Carlos");
    when(rs.getString("puesto")).thenReturn("10A");
    when(rs.getString("avion")).thenReturn("Airbus A320");
    when(rs.getString("aereopuerto_salida")).thenReturn("BOG");
    when(rs.getString("aereopuerto_llegada")).thenReturn("MDE");
    when(rs.getString("piloto")).thenReturn("Capitán Gómez");
    when(rs.getString("usuario_id")).thenReturn("u-001");
    when(rs.getString("created_at")).thenReturn("2026-11-06 12:00:00");
    when(rs.getString("updated_at")).thenReturn("2026-11-06 12:00:00");

    final VueloEntity entity = VueloPersistenceMapper.fromResultSetToEntity(rs);
    assertEquals("v-001", entity.id());
    assertEquals("Carlos", entity.cliente());
    assertEquals(250000.0, entity.valor());
  }

  @Test
  @DisplayName("fromResultSetToModelList mapea lista")
  void shouldMapResultSetToList() throws SQLException {
    final ResultSet rs = mock(ResultSet.class);
    when(rs.next()).thenReturn(true, false);
    when(rs.getString("id")).thenReturn("v-001");
    when(rs.getString("fecha_compra")).thenReturn("2026-11-06");
    when(rs.getString("fecha_salida")).thenReturn("2026-11-10");
    when(rs.getString("fecha_llegada")).thenReturn("2026-11-10");
    when(rs.getString("agencia_viajes")).thenReturn("Despegar");
    when(rs.getString("aerolinea")).thenReturn("Avianca");
    when(rs.getString("numero")).thenReturn("AV123");
    when(rs.getString("estado")).thenReturn("CONFIRMADO");
    when(rs.getDouble("valor")).thenReturn(250000.0);
    when(rs.getString("cliente")).thenReturn("Carlos");
    when(rs.getString("puesto")).thenReturn("10A");
    when(rs.getString("avion")).thenReturn("Airbus A320");
    when(rs.getString("aereopuerto_salida")).thenReturn("BOG");
    when(rs.getString("aereopuerto_llegada")).thenReturn("MDE");
    when(rs.getString("piloto")).thenReturn("Capitán Gómez");
    when(rs.getString("usuario_id")).thenReturn("u-001");

    final List<VueloModel> list = VueloPersistenceMapper.fromResultSetToModelList(rs);
    assertEquals(1, list.size());
    assertEquals("v-001", list.get(0).getId().value());
  }
}
