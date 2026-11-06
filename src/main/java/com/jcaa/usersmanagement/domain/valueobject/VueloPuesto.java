package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloPuesto(String value) {

  public VueloPuesto {
    final String normalized = Objects.requireNonNull(value, "puesto no puede ser nulo").trim();
    if (normalized.isEmpty()) {
      throw InvalidVueloException.becauseFieldIsEmpty("puesto");
    }
    value = normalized;
  }

  @Override
  public String toString() {
    return value;
  }
}
