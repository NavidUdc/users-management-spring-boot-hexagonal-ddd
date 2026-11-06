package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloFechaCompra(String value) {

  public VueloFechaCompra {
    final String normalized = Objects.requireNonNull(value, "fechaCompra no puede ser nula").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("fechaCompra");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
