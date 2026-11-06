package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveVueloPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateVueloService")
@ExtendWith(MockitoExtension.class)
class CreateVueloServiceTest {

  @Mock private SaveVueloPort saveVueloPort;
  @Mock private GetUserByIdPort getUserByIdPort;
  @Mock private EmailNotificationService emailNotificationService;

  private CreateVueloService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateVueloService(
              saveVueloPort,
              getUserByIdPort,
              emailNotificationService,
              validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda el vuelo correctamente y notifica al usuario por email")
  void shouldSaveVueloWhenUserExists() {
    final UserModel user =
        new UserModel(
            new UserId("u-001"),
            new UserName("Alice"),
            new UserEmail("alice@example.com"),
            UserPassword.fromHash("$2a$12$abcdefghijklmnopqrstuO"),
            UserRole.MEMBER,
            UserStatus.ACTIVE);

    when(getUserByIdPort.getById(new UserId("u-001"))).thenReturn(Optional.of(user));

    final CreateVueloCommand command =
        CreateVueloCommand.builder()
            .id("v-001")
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

    final VueloModel savedModel =
        VueloModel.fromPrimitives(
            "v-001",
            "2026-11-06",
            "2026-11-10",
            "2026-11-10",
            "Despegar",
            "Avianca",
            "AV123",
            "PROGRAMADO",
            300000.0,
            "Pedro",
            "1A",
            "Boeing 737",
            "BOG",
            "MDE",
            "Capitán Perez",
            "u-001");

    when(saveVueloPort.save(any(VueloModel.class))).thenReturn(savedModel);

    final VueloModel result = service.execute(command);
    assertNotNull(result);
    assertEquals("v-001", result.getId().value());
    verify(saveVueloPort).save(any(VueloModel.class));
    verify(emailNotificationService).notifyVueloAssigned(user, savedModel);
  }

  @Test
  @DisplayName("execute() lanza UserNotFoundException si el usuario asociado no existe")
  void shouldThrowWhenUserNotFound() {
    when(getUserByIdPort.getById(new UserId("u-999"))).thenReturn(Optional.empty());

    final CreateVueloCommand command =
        CreateVueloCommand.builder()
            .id("v-001")
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
            .usuarioId("u-999")
            .build();

    assertThrows(UserNotFoundException.class, () -> service.execute(command));
    verify(saveVueloPort, never()).save(any());
    verify(emailNotificationService, never()).notifyVueloAssigned(any(), any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException si el comando tiene campos vacíos")
  void shouldThrowWhenCommandInvalid() {
    final CreateVueloCommand command =
        CreateVueloCommand.builder()
            .id("v-001")
            .fechaCompra("")
            .build();

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
  }
}
