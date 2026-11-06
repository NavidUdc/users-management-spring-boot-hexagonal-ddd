package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetVuelosByUsuarioIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetVuelosByUsuarioIdPort;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetVuelosByUsuarioIdService implements GetVuelosByUsuarioIdUseCase {

  private final GetVuelosByUsuarioIdPort getVuelosByUsuarioIdPort;
  private final GetUserByIdPort getUserByIdPort;

  @Override
  public List<VueloModel> execute(final String usuarioId) {
    ensureUserExists(usuarioId);
    return getVuelosByUsuarioIdPort.getByUsuarioId(usuarioId);
  }

  private void ensureUserExists(final String usuarioId) {
    final UserId userId = new UserId(usuarioId);
    getUserByIdPort
        .getById(userId)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(userId.value()));
  }
}
