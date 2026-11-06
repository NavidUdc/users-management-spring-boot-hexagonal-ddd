package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetVueloByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetVueloByIdQuery;
import com.jcaa.usersmanagement.domain.exception.VueloNotFoundException;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetVueloByIdService")
@ExtendWith(MockitoExtension.class)
class GetVueloByIdServiceTest {

  @Mock private GetVueloByIdPort getVueloByIdPort;

  private GetVueloByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new GetVueloByIdService(getVueloByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() retorna el vuelo si existe")
  void shouldReturnVueloWhenFound() {
    final VueloModel model =
        VueloModel.fromPrimitives(
            "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
            "Despegar", "Avianca", "AV123", "CONFIRMADO", 300000.0,
            "Pedro", "1A", "Airbus A320", "BOG", "MDE", "Capitán Perez", "u-001");
    when(getVueloByIdPort.getById("v-001")).thenReturn(Optional.of(model));

    final VueloModel result = service.execute(new GetVueloByIdQuery("v-001"));
    assertEquals("v-001", result.getId().value());
  }

  @Test
  @DisplayName("execute() lanza VueloNotFoundException si no existe")
  void shouldThrowWhenNotFound() {
    when(getVueloByIdPort.getById("v-999")).thenReturn(Optional.empty());

    assertThrows(
        VueloNotFoundException.class, () -> service.execute(new GetVueloByIdQuery("v-999")));
  }
}
