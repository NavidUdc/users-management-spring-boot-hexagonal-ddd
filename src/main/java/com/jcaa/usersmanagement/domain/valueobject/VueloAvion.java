package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloAvion(String value) {

  public VueloAvion {
    final String normalized = Objects.requireNonNull(value, "avion cno puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("avion");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
