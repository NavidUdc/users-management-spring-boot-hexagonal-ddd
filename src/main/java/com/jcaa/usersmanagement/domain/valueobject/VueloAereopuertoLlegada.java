package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloAereopuertoLlegada(String value) {

  public VueloAereopuertoLlegada {
    final String normalized = Objects.requireNonNull(value, "aereopuertoLlegada no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("aereopuertoLlegada");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
