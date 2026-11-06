package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.VueloModel;
import java.util.Optional;

public interface GetVueloByIdPort {
  Optional<VueloModel> getById(String id);
}
