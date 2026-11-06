package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloFechaLlegada(String value) {

  public VueloFechaLlegada {
    final String normalized = Objects.requireNonNull(value, "fechaLlegada no puede ser nula").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("fechaLlegada");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
