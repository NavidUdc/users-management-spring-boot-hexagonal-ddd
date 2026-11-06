package com.jcaa.usersmanagement.domain.exception;

public final class InvalidVueloException extends DomainException {

  private static final String MESSAGE_FIELD_EMPTY = "The flight %s must not be empty.";
  private static final String MESSAGE_VALOR_NEGATIVE = "The flight value must be greater than or equal to 0.";

  private InvalidVueloException(final String message) {
    super(message);
  }

  public static InvalidVueloException becauseFieldIsEmpty(final String fieldName) {
    return new InvalidVueloException(String.format(MESSAGE_FIELD_EMPTY, fieldName));
  }

  public static InvalidVueloException becauseValorIsNegative() {
    return new InvalidVueloException(MESSAGE_VALOR_NEGATIVE);
  }
}
