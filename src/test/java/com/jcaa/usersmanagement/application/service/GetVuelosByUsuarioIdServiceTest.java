package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetVuelosByUsuarioIdPort;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetVuelosByUsuarioIdService")
@ExtendWith(MockitoExtension.class)
class GetVuelosByUsuarioIdServiceTest {

  @Mock private GetVuelosByUsuarioIdPort getVuelosByUsuarioIdPort;
  @Mock private GetUserByIdPort getUserByIdPort;
  @InjectMocks private GetVuelosByUsuarioIdService service;

  @Test
  @DisplayName("execute() retorna los vuelos del usuario cuando este existe")
  void shouldReturnVuelosWhenUserExists() {
    final UserModel user =
        new UserModel(
            new UserId("u-001"),
            new UserName("Alice"),
            new UserEmail("alice@example.com"),
            UserPassword.fromHash("$2a$12$abcdefghijklmnopqrstuO"),
            UserRole.MEMBER,
            UserStatus.ACTIVE);

    final VueloModel model =
        VueloModel.fromPrimitives(
            "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
            "Despegar", "Avianca", "AV123", "CONFIRMADO", 300000.0,
            "Pedro", "1A", "Airbus A320", "BOG", "MDE", "Capitán Perez", "u-001");

    when(getUserByIdPort.getById(new UserId("u-001"))).thenReturn(Optional.of(user));
    when(getVuelosByUsuarioIdPort.getByUsuarioId("u-001")).thenReturn(List.of(model));

    final List<VueloModel> result = service.execute("u-001");
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("execute() lanza UserNotFoundException cuando el usuario no existe")
  void shouldThrowWhenUserNotFound() {
    when(getUserByIdPort.getById(new UserId("u-999"))).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> service.execute("u-999"));
  }
}
