package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record DeleteVueloCommand(
    @NotBlank(message = "id no puede ser nulo") String id) {}
