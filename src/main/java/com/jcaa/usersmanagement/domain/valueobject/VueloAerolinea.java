package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloAerolinea(String value) {

  public VueloAerolinea {
    final String normalized = Objects.requireNonNull(value, "aerolinea no puede ser nula").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("aerolinea");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
