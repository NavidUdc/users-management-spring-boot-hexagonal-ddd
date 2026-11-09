package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetVueloByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateVueloPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.exception.VueloNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UpdateVueloService")
@ExtendWith(MockitoExtension.class)
class UpdateVueloServiceTest {

  @Mock private UpdateVueloPort updateVueloPort;
  @Mock private GetVueloByIdPort getVueloByIdPort;
  @Mock private GetUserByIdPort getUserByIdPort;
  @Mock private EmailNotificationService emailNotificationService;

  private UpdateVueloService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateVueloService(
              updateVueloPort,
              getVueloByIdPort,
              getUserByIdPort,
              emailNotificationService,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza el vuelo exitosamente y notifica al usuario por email")
  void shouldUpdateVueloSuccessfully() {
    final VueloModel current =
        VueloModel.fromPrimitives(
            "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
            "Despegar", "Avianca", "AV123", "PROGRAMADO", 300000.0,
            "Pedro", "1A", "Airbus A320", "BOG", "MDE", "Capitán Perez", "u-001");

    final UserModel user =
        new UserModel(
            new UserId("u-001"),
            new UserName("Alice"),
            new UserEmail("alice@example.com"),
            UserPassword.fromHash("$2a$12$abcdefghijklmnopqrstuO"),
            UserRole.MEMBER,
            UserStatus.ACTIVE);

    when(getVueloByIdPort.getById("v-001")).thenReturn(Optional.of(current));
    when(getUserByIdPort.getById(new UserId("u-001"))).thenReturn(Optional.of(user));

    final UpdateVueloCommand command =
        UpdateVueloCommand.builder()
            .id("v-001")
            .fechaCompra("2026-11-06")
            .fechaSalida("2026-11-10")
            .fechaLlegada("2026-11-10")
            .agenciaViajes("Despegar")
            .aerolinea("Avianca")
            .numero("AV123")
            .estado("CONFIRMADO")
            .valor(320000.0)
            .cliente("Pedro")
            .puesto("1B")
            .avion("Airbus A320")
            .aereopuertoSalida("BOG")
            .aereopuertoLlegada("MDE")
            .piloto("Capitán Gomez")
            .usuarioId("u-001")
            .build();

    final VueloModel updated =
        VueloModel.fromPrimitives(
            "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
            "Despegar", "Avianca", "AV123", "CONFIRMADO", 320000.0,
            "Pedro", "1B", "Airbus A320", "BOG", "MDE", "Capitán Gomez", "u-001");

    when(updateVueloPort.update(any(VueloModel.class))).thenReturn(updated);

    final VueloModel result = service.execute(command);
    assertEquals("CONFIRMADO", result.getEstado().value());
    verify(emailNotificationService).notifyVueloAssigned(user, updated);
  }

  @Test
  @DisplayName("execute() lanza VueloNotFoundException si el vuelo no existe")
  void shouldThrowWhenVueloNotFound() {
    when(getVueloByIdPort.getById("v-999")).thenReturn(Optional.empty());

    final UpdateVueloCommand command =
        UpdateVueloCommand.builder()
            .id("v-999")
            .fechaCompra("2026-11-06")
            .fechaSalida("2026-11-10")
            .fechaLlegada("2026-11-10")
            .agenciaViajes("Despegar")
            .aerolinea("Avianca")
            .numero("AV123")
            .estado("CONFIRMADO")
            .valor(320000.0)
            .cliente("Pedro")
            .puesto("1B")
            .avion("Airbus A320")
            .aereopuertoSalida("BOG")
            .aereopuertoLlegada("MDE")
            .piloto("Capitán Gomez")
            .usuarioId("u-001")
            .build();

    assertThrows(VueloNotFoundException.class, () -> service.execute(command));
    verify(emailNotificationService, never()).notifyVueloAssigned(any(), any());
  }

  @Test
  @DisplayName("execute() lanza UserNotFoundException si el usuario asociado no existe")
  void shouldThrowWhenUserNotFound() {
    final VueloModel current =
        VueloModel.fromPrimitives(
            "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
            "Despegar", "Avianca", "AV123", "PROGRAMADO", 300000.0,
            "Pedro", "1A", "Airbus A320", "BOG", "MDE", "Capitán Perez", "u-001");

    when(getVueloByIdPort.getById("v-001")).thenReturn(Optional.of(current));
    when(getUserByIdPort.getById(new UserId("u-999"))).thenReturn(Optional.empty());

    final UpdateVueloCommand command =
        UpdateVueloCommand.builder()
            .id("v-001")
            .fechaCompra("2026-11-06")
            .fechaSalida("2026-11-10")
            .fechaLlegada("2026-11-10")
            .agenciaViajes("Despegar")
            .aerolinea("Avianca")
            .numero("AV123")
            .estado("CONFIRMADO")
            .valor(320000.0)
            .cliente("Pedro")
            .puesto("1B")
            .avion("Airbus A320")
            .aereopuertoSalida("BOG")
            .aereopuertoLlegada("MDE")
            .piloto("Capitán Gomez")
            .usuarioId("u-999")
            .build();

    assertThrows(UserNotFoundException.class, () -> service.execute(command));
    verify(emailNotificationService, never()).notifyVueloAssigned(any(), any());
  }
}
