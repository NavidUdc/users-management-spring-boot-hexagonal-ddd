package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateVueloUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetVueloByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateVueloPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.application.service.mapper.VueloApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.exception.VueloNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateVueloService implements UpdateVueloUseCase {

  private final UpdateVueloPort updateVueloPort;
  private final GetVueloByIdPort getVueloByIdPort;
  private final GetUserByIdPort getUserByIdPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  @Override
  public VueloModel execute(final UpdateVueloCommand command) {
    validateCommand(command);
    ensureVueloExists(command.id());
    final UserModel user = findUserOrFail(command.usuarioId());

    final VueloModel vueloToUpdate = VueloApplicationMapper.fromUpdateCommandToModel(command);
    final VueloModel updatedVuelo = updateVueloPort.update(vueloToUpdate);

    emailNotificationService.notifyVueloAssigned(user, updatedVuelo);

    return updatedVuelo;
  }

  private void validateCommand(final UpdateVueloCommand command) {
    final Set<ConstraintViolation<UpdateVueloCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureVueloExists(final String id) {
    getVueloByIdPort
        .getById(id)
        .orElseThrow(() -> VueloNotFoundException.becauseIdWasNotFound(id));
  }

  private UserModel findUserOrFail(final String usuarioId) {
    final UserId userId = new UserId(usuarioId);
    return getUserByIdPort
        .getById(userId)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(userId.value()));
  }
}
