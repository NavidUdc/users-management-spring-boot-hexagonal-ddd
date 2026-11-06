package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteVueloPort;
import com.jcaa.usersmanagement.application.port.out.GetAllVuelosPort;
import com.jcaa.usersmanagement.application.port.out.GetVueloByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetVuelosByUsuarioIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveVueloPort;
import com.jcaa.usersmanagement.application.port.out.UpdateVueloPort;
import com.jcaa.usersmanagement.domain.exception.VueloNotFoundException;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.VueloPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.VueloPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VueloRepositoryMySQL
    implements SaveVueloPort,
        UpdateVueloPort,
        GetVueloByIdPort,
        GetAllVuelosPort,
        GetVuelosByUsuarioIdPort,
        DeleteVueloPort {

  private static final String SQL_INSERT =
      "INSERT INTO vuelos ("
          + "id, fecha_compra, fecha_salida, fecha_llegada, agencia_viajes, "
          + "aerolinea, numero, estado, valor, cliente, "
          + "puesto, avion, aereopuerto_salida, aereopuerto_llegada, piloto, "
          + "usuario_id, created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE vuelos SET "
          + "fecha_compra = ?, fecha_salida = ?, fecha_llegada = ?, agencia_viajes = ?, "
          + "aerolinea = ?, numero = ?, estado = ?, valor = ?, cliente = ?, "
          + "puesto = ?, avion = ?, aereopuerto_salida = ?, aereopuerto_llegada = ?, "
          + "piloto = ?, usuario_id = ?, updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, fecha_compra, fecha_salida, fecha_llegada, agencia_viajes, "
          + "aerolinea, numero, estado, valor, cliente, "
          + "puesto, avion, aereopuerto_salida, aereopuerto_llegada, piloto, "
          + "usuario_id, created_at, updated_at "
          + "FROM vuelos WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, fecha_compra, fecha_salida, fecha_llegada, agencia_viajes, "
          + "aerolinea, numero, estado, valor, cliente, "
          + "puesto, avion, aereopuerto_salida, aereopuerto_llegada, piloto, "
          + "usuario_id, created_at, updated_at "
          + "FROM vuelos ORDER BY fecha_salida DESC";

  private static final String SQL_SELECT_BY_USUARIO_ID =
      "SELECT id, fecha_compra, fecha_salida, fecha_llegada, agencia_viajes, "
          + "aerolinea, numero, estado, valor, cliente, "
          + "puesto, avion, aereopuerto_salida, aereopuerto_llegada, piloto, "
          + "usuario_id, created_at, updated_at "
          + "FROM vuelos WHERE usuario_id = ? ORDER BY fecha_salida DESC";

  private static final String SQL_DELETE = "DELETE FROM vuelos WHERE id = ?";

  private final DataSource dataSource;

  @Override
  public VueloModel save(final VueloModel vuelo) {
    final VueloPersistenceDto dto = VueloPersistenceMapper.fromModelToDto(vuelo);
    executeSave(dto);
    return findByIdOrFail(vuelo.getId().value());
  }

  @Override
  public VueloModel update(final VueloModel vuelo) {
    final VueloPersistenceDto dto = VueloPersistenceMapper.fromModelToDto(vuelo);
    executeUpdate(dto);
    return findByIdOrFail(vuelo.getId().value());
  }

  @Override
  public Optional<VueloModel> getById(final String id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, id);
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(VueloPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindVueloByIdFailed(id, exception);
    }
  }

  @Override
  public List<VueloModel> getAll() {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return VueloPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllVuelosFailed(exception);
    }
  }

  @Override
  public List<VueloModel> getByUsuarioId(final String usuarioId) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_USUARIO_ID)) {
      statement.setString(1, usuarioId);
      final ResultSet resultSet = statement.executeQuery();
      return VueloPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindVuelosByUsuarioIdFailed(usuarioId, exception);
    }
  }

  @Override
  public void delete(final String id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, id);
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteVueloFailed(id, exception);
    }
  }

  private void executeSave(final VueloPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.fechaCompra());
      statement.setString(3, dto.fechaSalida());
      statement.setString(4, dto.fechaLlegada());
      statement.setString(5, dto.agenciaViajes());
      statement.setString(6, dto.aerolinea());
      statement.setString(7, dto.numero());
      statement.setString(8, dto.estado());
      statement.setDouble(9, dto.valor());
      statement.setString(10, dto.cliente());
      statement.setString(11, dto.puesto());
      statement.setString(12, dto.avion());
      statement.setString(13, dto.aereopuertoSalida());
      statement.setString(14, dto.aereopuertoLlegada());
      statement.setString(15, dto.piloto());
      statement.setString(16, dto.usuarioId());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveVueloFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final VueloPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.fechaCompra());
      statement.setString(2, dto.fechaSalida());
      statement.setString(3, dto.fechaLlegada());
      statement.setString(4, dto.agenciaViajes());
      statement.setString(5, dto.aerolinea());
      statement.setString(6, dto.numero());
      statement.setString(7, dto.estado());
      statement.setDouble(8, dto.valor());
      statement.setString(9, dto.cliente());
      statement.setString(10, dto.puesto());
      statement.setString(11, dto.avion());
      statement.setString(12, dto.aereopuertoSalida());
      statement.setString(13, dto.aereopuertoLlegada());
      statement.setString(14, dto.piloto());
      statement.setString(15, dto.usuarioId());
      statement.setString(16, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateVueloFailed(dto.id(), exception);
    }
  }

  private VueloModel findByIdOrFail(final String id) {
    return getById(id)
        .orElseThrow(() -> VueloNotFoundException.becauseIdWasNotFound(id));
  }
}
