package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

  private static final String MESSAGE_SAVE_VUELO = "Fallo al guardar vuelo con ID: '%s'.";
  private static final String MESSAGE_UPDATE_VUELO = "Fallo al actualizar vuelo con ID: '%s'.";
  private static final String MESSAGE_FIND_VUELO = "Fallo al encontrar vuelo con ID: '%s'.";
  private static final String MESSAGE_FIND_VUELOS_USUARIO = "Fallo al buscar vuelos por usuario con ID: '%s'.";
  private static final String MESSAGE_ALL_VUELOS = "Fallo al obtener todos los vueslos";
  private static final String MESSAGE_DELETE_VUELO = "Fallo al eliminar vuelo con ID: '%s'.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }

  public static PersistenceException becauseSaveVueloFailed(
      final String vueloId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE_VUELO, vueloId), cause);
  }

  public static PersistenceException becauseUpdateVueloFailed(
      final String vueloId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE_VUELO, vueloId), cause);
  }

  public static PersistenceException becauseFindVueloByIdFailed(
      final String vueloId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND_VUELO, vueloId), cause);
  }

  public static PersistenceException becauseFindVuelosByUsuarioIdFailed(
      final String usuarioId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND_VUELOS_USUARIO, usuarioId), cause);
  }

  public static PersistenceException becauseFindAllVuelosFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL_VUELOS, cause);
  }

  public static PersistenceException becauseDeleteVueloFailed(
      final String vueloId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE_VUELO, vueloId), cause);
  }
}
