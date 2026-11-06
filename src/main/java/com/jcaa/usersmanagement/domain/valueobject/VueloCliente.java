package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloCliente(String value) {

  public VueloCliente {
    final String normalized = Objects.requireNonNull(value, "cliente no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("cliente");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
