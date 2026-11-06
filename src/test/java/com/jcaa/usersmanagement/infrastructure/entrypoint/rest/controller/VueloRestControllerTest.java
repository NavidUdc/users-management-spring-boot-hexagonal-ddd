package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jcaa.usersmanagement.application.port.in.CreateVueloUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteVueloUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllVuelosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetVueloByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.GetVuelosByUsuarioIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateVueloUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateVueloCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetVueloByIdQuery;
import com.jcaa.usersmanagement.domain.exception.VueloNotFoundException;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import com.jcaa.usersmanagement.infrastructure.security.JwtAuthenticationFilter;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenService;
import com.jcaa.usersmanagement.infrastructure.security.RestAccessDeniedHandler;
import com.jcaa.usersmanagement.infrastructure.security.RestAuthenticationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.security.SecurityConfig;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = VueloRestController.class,
    properties = "spring.main.web-application-type=servlet")
@Import({
  SecurityConfig.class,
  JwtAuthenticationFilter.class,
  RestAuthenticationEntryPoint.class,
  RestAccessDeniedHandler.class
})
@DisplayName("VueloRestController")
class VueloRestControllerTest {

  private static final String ID = "10000000-0000-0000-0000-000000000001";
  private static final String USER_ID = "00000000-0000-0000-0000-000000000001";

  @Autowired private MockMvc mockMvc;

  @MockBean private CreateVueloUseCase createVueloUseCase;
  @MockBean private UpdateVueloUseCase updateVueloUseCase;
  @MockBean private DeleteVueloUseCase deleteVueloUseCase;
  @MockBean private GetVueloByIdUseCase getVueloByIdUseCase;
  @MockBean private GetAllVuelosUseCase getAllVuelosUseCase;
  @MockBean private GetVuelosByUsuarioIdUseCase getVuelosByUsuarioIdUseCase;
  @MockBean private JwtTokenService jwtTokenService;

  private VueloModel buildSampleModel() {
    return VueloModel.fromPrimitives(
        ID,
        "2026-11-06 10:00:00",
        "2026-11-10 08:30:00",
        "2026-11-10 10:45:00",
        "Despegar",
        "Avianca",
        "AV8520",
        "CONFIRMADO",
        450000.0,
        "Juan Perez",
        "12A",
        "Airbus A320",
        "El Dorado",
        "Rafael Nunez",
        "Carlos Ramirez",
        USER_ID);
  }

  @Test
  @DisplayName("POST /api/vuelos crea un vuelo exitosamente")
  void shouldCreateVuelo() throws Exception {
    final String request =
        """
        {
          "id": "%s",
          "fechaCompra": "2026-11-06 10:00:00",
          "fechaSalida": "2026-11-10 08:30:00",
          "fechaLlegada": "2026-11-10 10:45:00",
          "agenciaViajes": "Despegar",
          "aerolinea": "Avianca",
          "numero": "AV8520",
          "estado": "CONFIRMADO",
          "valor": 450000.0,
          "cliente": "Juan Perez",
          "puesto": "12A",
          "avion": "Airbus A320",
          "aereopuertoSalida": "El Dorado",
          "aereopuertoLlegada": "Rafael Nunez",
          "piloto": "Carlos Ramirez",
          "usuarioId": "%s"
        }
        """.formatted(ID, USER_ID);

    when(createVueloUseCase.execute(any(CreateVueloCommand.class))).thenReturn(buildSampleModel());

    mockMvc
        .perform(post("/api/vuelos").contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID))
        .andExpect(jsonPath("$.aerolinea").value("Avianca"))
        .andExpect(jsonPath("$.usuarioId").value(USER_ID));
  }

  @Test
  @DisplayName("GET /api/vuelos retorna la lista de vuelos")
  void shouldGetAllVuelos() throws Exception {
    when(getAllVuelosUseCase.execute()).thenReturn(List.of(buildSampleModel()));

    mockMvc
        .perform(get("/api/vuelos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(ID))
        .andExpect(jsonPath("$[0].aerolinea").value("Avianca"));
  }

  @Test
  @DisplayName("GET /api/vuelos/{id} retorna el vuelo cuando existe")
  void shouldGetById() throws Exception {
    when(getVueloByIdUseCase.execute(new GetVueloByIdQuery(ID))).thenReturn(buildSampleModel());

    mockMvc
        .perform(get("/api/vuelos/" + ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID));
  }

  @Test
  @DisplayName("GET /api/vuelos/{id} retorna 404 cuando no existe")
  void shouldReturn404WhenNotFound() throws Exception {
    when(getVueloByIdUseCase.execute(new GetVueloByIdQuery("not-found")))
        .thenThrow(VueloNotFoundException.becauseIdWasNotFound("not-found"));

    mockMvc
        .perform(get("/api/vuelos/not-found"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").exists());
  }

  @Test
  @DisplayName("GET /api/vuelos/usuario/{usuarioId} retorna los vuelos del usuario")
  void shouldGetByUsuarioId() throws Exception {
    when(getVuelosByUsuarioIdUseCase.execute(USER_ID)).thenReturn(List.of(buildSampleModel()));

    mockMvc
        .perform(get("/api/vuelos/usuario/" + USER_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].usuarioId").value(USER_ID));
  }

  @Test
  @DisplayName("PUT /api/vuelos/{id} actualiza el vuelo")
  void shouldUpdateVuelo() throws Exception {
    final String request =
        """
        {
          "fechaCompra": "2026-11-06 10:00:00",
          "fechaSalida": "2026-11-10 08:30:00",
          "fechaLlegada": "2026-11-10 10:45:00",
          "agenciaViajes": "Despegar",
          "aerolinea": "Avianca",
          "numero": "AV8520",
          "estado": "MODIFICADO",
          "valor": 450000.0,
          "cliente": "Juan Perez",
          "puesto": "12B",
          "avion": "Airbus A320",
          "aereopuertoSalida": "El Dorado",
          "aereopuertoLlegada": "Rafael Nunez",
          "piloto": "Carlos Ramirez",
          "usuarioId": "%s"
        }
        """.formatted(USER_ID);

    final VueloModel updated =
        VueloModel.fromPrimitives(
            ID,
            "2026-11-06 10:00:00",
            "2026-11-10 08:30:00",
            "2026-11-10 10:45:00",
            "Despegar",
            "Avianca",
            "AV8520",
            "MODIFICADO",
            450000.0,
            "Juan Perez",
            "12B",
            "Airbus A320",
            "El Dorado",
            "Rafael Nunez",
            "Carlos Ramirez",
            USER_ID);

    when(updateVueloUseCase.execute(any(UpdateVueloCommand.class))).thenReturn(updated);

    mockMvc
        .perform(put("/api/vuelos/" + ID).contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.puesto").value("12B"))
        .andExpect(jsonPath("$.estado").value("MODIFICADO"));
  }

  @Test
  @DisplayName("DELETE /api/vuelos/{id} elimina el vuelo y retorna 204")
  void shouldDeleteVuelo() throws Exception {
    doNothing().when(deleteVueloUseCase).execute(new DeleteVueloCommand(ID));

    mockMvc
        .perform(delete("/api/vuelos/" + ID))
        .andExpect(status().isNoContent());

    verify(deleteVueloUseCase).execute(new DeleteVueloCommand(ID));
  }
}
