package com.jcaa.usersmanagement.domain.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("VueloNotFoundException")
class VueloNotFoundExceptionTest {

  @Test
  @DisplayName("becauseIdWasNotFound debe contener el id en el mensaje")
  void shouldContainIdInMessage() {
    final VueloNotFoundException exception = VueloNotFoundException.becauseIdWasNotFound("v-123");
    assertTrue(exception.getMessage().contains("v-123"));
  }
}
