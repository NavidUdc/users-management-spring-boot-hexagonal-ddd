package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import java.util.Objects;

public record VueloValor(Double value) {

  public VueloValor {
    Objects.requireNonNull(value, "valor no puede ser nulo");
    if (value < 0.0) {
      throw InvalidVueloException.becauseValorIsNegative();
    }
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
