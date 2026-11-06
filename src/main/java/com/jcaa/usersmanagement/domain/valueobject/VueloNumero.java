package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloNumero(String value) {

  public VueloNumero {
    final String normalized = Objects.requireNonNull(value, "numero no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("numero");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
