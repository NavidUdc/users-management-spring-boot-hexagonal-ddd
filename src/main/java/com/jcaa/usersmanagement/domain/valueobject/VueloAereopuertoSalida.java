package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloAereopuertoSalida(String value) {

  public VueloAereopuertoSalida {
    final String normalized = Objects.requireNonNull(value, "aereopuertoSalida no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("aereopuertoSalida");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
