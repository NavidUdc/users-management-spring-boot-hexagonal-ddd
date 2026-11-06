package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloPiloto(String value) {

  public VueloPiloto {
    final String normalized = Objects.requireNonNull(value, "piloto no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("piloto");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
