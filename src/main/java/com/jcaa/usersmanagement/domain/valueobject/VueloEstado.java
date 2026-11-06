package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloEstado(String value) {

  public VueloEstado {
    final String normalized = Objects.requireNonNull(value, "estado no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("estado");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
