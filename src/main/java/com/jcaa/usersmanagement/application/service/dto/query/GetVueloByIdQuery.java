package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetVueloByIdQuery(
    @NotBlank(message = "id no puede ser nulo") String id) {}
