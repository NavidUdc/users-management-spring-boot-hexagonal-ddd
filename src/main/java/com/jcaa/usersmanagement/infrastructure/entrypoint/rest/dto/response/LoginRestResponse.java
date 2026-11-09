package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record LoginRestResponse(
    String accessToken,
    String tokenType,
    long expiresIn,
    String userId,
    String name,
    String email,
    String role) {

  public LoginRestResponse(final String accessToken, final String tokenType, final long expiresIn) {
    this(accessToken, tokenType, expiresIn, null, null, null, null);
  }
}
