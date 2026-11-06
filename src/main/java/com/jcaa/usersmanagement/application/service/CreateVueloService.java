package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateVueloUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveVueloPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.application.service.mapper.VueloApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateVueloService implements CreateVueloUseCase {

  private final SaveVueloPort saveVueloPort;
  private final GetUserByIdPort getUserByIdPort;
  private final Validator validator;

  @Override
  public VueloModel execute(final CreateVueloCommand command) {
    validateCommand(command);
    ensureUserExists(command.usuarioId());

    final VueloModel vueloToSave = VueloApplicationMapper.fromCreateCommandToModel(command);
    return saveVueloPort.save(vueloToSave);
  }

  private void validateCommand(final CreateVueloCommand command) {
    final Set<ConstraintViolation<CreateVueloCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureUserExists(final String usuarioId) {
    final UserId userId = new UserId(usuarioId);
    getUserByIdPort
        .getById(userId)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(userId.value()));
  }
}
