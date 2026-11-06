package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("VueloRepositoryMySQL")
@ExtendWith(MockitoExtension.class)
class VueloRepositoryMySQLTest {

  private static final String ID = "v-001";

  @Mock private DataSource dataSource;
  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private VueloRepositoryMySQL repository;
  private VueloModel vueloModel;

  @BeforeEach
  void setUp() {
    repository = new VueloRepositoryMySQL(dataSource);
    vueloModel =
        VueloModel.fromPrimitives(
            ID,
            "2026-11-06",
            "2026-11-10",
            "2026-11-10",
            "Despegar",
            "Avianca",
            "AV123",
            "CONFIRMADO",
            300000.0,
            "Pedro",
            "1A",
            "Airbus A320",
            "BOG",
            "MDE",
            "Capitán Gomez",
            "u-001");
  }

  private void configureStatementAndResultSet() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
  }

  private void configureResultSetRow() throws SQLException {
    when(resultSet.getString("id")).thenReturn(ID);
    when(resultSet.getString("fecha_compra")).thenReturn("2026-11-06");
    when(resultSet.getString("fecha_salida")).thenReturn("2026-11-10");
    when(resultSet.getString("fecha_llegada")).thenReturn("2026-11-10");
    when(resultSet.getString("agencia_viajes")).thenReturn("Despegar");
    when(resultSet.getString("aerolinea")).thenReturn("Avianca");
    when(resultSet.getString("numero")).thenReturn("AV123");
    when(resultSet.getString("estado")).thenReturn("CONFIRMADO");
    when(resultSet.getDouble("valor")).thenReturn(300000.0);
    when(resultSet.getString("cliente")).thenReturn("Pedro");
    when(resultSet.getString("puesto")).thenReturn("1A");
    when(resultSet.getString("avion")).thenReturn("Airbus A320");
    when(resultSet.getString("aereopuerto_salida")).thenReturn("BOG");
    when(resultSet.getString("aereopuerto_llegada")).thenReturn("MDE");
    when(resultSet.getString("piloto")).thenReturn("Capitán Gomez");
    when(resultSet.getString("usuario_id")).thenReturn("u-001");
  }

  @Test
  @DisplayName("save() inserta y retorna el vuelo")
  void shouldSaveVuelo() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenReturn(1);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final VueloModel saved = repository.save(vueloModel);
    assertNotNull(saved);
    assertEquals(ID, saved.getId().value());
  }

  @Test
  @DisplayName("update() actualiza y retorna el vuelo")
  void shouldUpdateVuelo() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenReturn(1);
    when(statement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final VueloModel updated = repository.update(vueloModel);
    assertNotNull(updated);
    assertEquals(ID, updated.getId().value());
  }

  @Test
  @DisplayName("getById() retorna Optional con vuelo si existe")
  void shouldGetByIdFound() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final Optional<VueloModel> result = repository.getById(ID);
    assertTrue(result.isPresent());
    assertEquals(ID, result.get().getId().value());
  }

  @Test
  @DisplayName("getById() retorna Optional.empty si no existe")
  void shouldGetByIdNotFound() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    final Optional<VueloModel> result = repository.getById(ID);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("getById() lanza PersistenceException ante SQLException")
  void shouldThrowPersistenceExceptionOnSqlError() throws SQLException {
    when(dataSource.getConnection()).thenThrow(new SQLException("DB error"));
    assertThrows(PersistenceException.class, () -> repository.getById(ID));
  }

  @Test
  @DisplayName("getAll() retorna todos los vuelos")
  void shouldGetAllVuelos() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true, false);
    configureResultSetRow();

    final List<VueloModel> result = repository.getAll();
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("getByUsuarioId() retorna los vuelos del usuario")
  void shouldGetByUsuarioId() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true, false);
    configureResultSetRow();

    final List<VueloModel> result = repository.getByUsuarioId("u-001");
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("delete() ejecuta DELETE correctamente")
  void shouldDeleteVuelo() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenReturn(1);

    assertDoesNotThrow(() -> repository.delete(ID));
    verify(statement).executeUpdate();
  }
}
