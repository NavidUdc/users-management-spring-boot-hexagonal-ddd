package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloFechaSalida(String value) {

  public VueloFechaSalida {
    final String normalized = Objects.requireNonNull(value, "fechaSalida no puede ser nula").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("fechaSalida");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
