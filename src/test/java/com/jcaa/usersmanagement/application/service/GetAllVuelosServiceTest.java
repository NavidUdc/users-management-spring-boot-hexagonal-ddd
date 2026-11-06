package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllVuelosPort;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllVuelosService")
@ExtendWith(MockitoExtension.class)
class GetAllVuelosServiceTest {

  @Mock private GetAllVuelosPort getAllVuelosPort;
  @InjectMocks private GetAllVuelosService service;

  @Test
  @DisplayName("execute() retorna la lista de vuelos")
  void shouldReturnAllVuelos() {
    final List<VueloModel> list =
        List.of(
            VueloModel.fromPrimitives(
                "v-001", "2026-11-06", "2026-11-10", "2026-11-10",
                "Despegar", "Avianca", "AV123", "CONFIRMADO", 300000.0,
                "Pedro", "1A", "Airbus A320", "BOG", "MDE", "Capitán Perez", "u-001"));
    when(getAllVuelosPort.getAll()).thenReturn(list);

    final List<VueloModel> result = service.execute();
    assertEquals(1, result.size());
  }
}
