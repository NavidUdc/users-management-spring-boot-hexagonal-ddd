package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloAgenciaViajes(String value) {

  public VueloAgenciaViajes {
    final String normalized = Objects.requireNonNull(value, "agenciaViajes no puede ser nula").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("agenciaViajes");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
