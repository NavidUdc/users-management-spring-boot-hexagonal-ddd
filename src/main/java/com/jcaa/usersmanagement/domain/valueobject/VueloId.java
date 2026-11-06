package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloId(String value) {

  public VueloId {
    final String normalized = Objects.requireNonNull(value, "VueloId no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("id");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
