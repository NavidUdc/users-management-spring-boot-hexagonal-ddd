package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateVueloRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateVueloRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.VueloRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contrato OpenAPI / Swagger para {@link VueloRestController}.
 */
@Tag(name = "Vuelos", description = "Gestión de vuelos: crear, consultar, actualizar y eliminar.")
public interface VueloRestControllerDocs {

  @Operation(
      summary = "Crear vuelo",
      description = "Registra un nuevo vuelo asignado a un usuario (FK usuarioId). Si no se proporciona un ID, se generará un UUID automáticamente.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Vuelo creado exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VueloRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos (campos requeridos vacíos o formato incorrecto).",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "El usuario asociado al vuelo no existe.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  VueloRestResponse create(@Valid @RequestBody CreateVueloRestRequest request);

  @Operation(
      summary = "Listar todos los vuelos",
      description = "Retorna la lista completa de vuelos registrados.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de vuelos obtenida exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = VueloRestResponse.class)))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<VueloRestResponse> getAll();

  @Operation(
      summary = "Buscar vuelo por ID",
      description = "Retorna los datos de un vuelo específico según su identificador único.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Vuelo encontrado exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VueloRestResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No se encontró ningún vuelo con el ID especificado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  VueloRestResponse getById(
      @Parameter(description = "Identificador único del vuelo", example = "10000000-0000-0000-0000-000000000001")
          @PathVariable
          String id);

  @Operation(
      summary = "Listar vuelos por ID de usuario",
      description = "Retorna todos los vuelos asociados a un usuario específico.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de vuelos del usuario obtenida exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = VueloRestResponse.class)))),
    @ApiResponse(
        responseCode = "404",
        description = "El usuario especificado no existe.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<VueloRestResponse> getByUsuarioId(
      @Parameter(description = "Identificador único del usuario", example = "00000000-0000-0000-0000-000000000001")
          @PathVariable
          String usuarioId);

  @Operation(
      summary = "Actualizar vuelo",
      description = "Actualiza todos los datos de un vuelo existente.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Vuelo actualizado exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VueloRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No se encontró el vuelo o el usuario asociado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  VueloRestResponse update(
      @Parameter(description = "Identificador único del vuelo a actualizar", example = "10000000-0000-0000-0000-000000000001")
          @PathVariable
          String id,
      @Valid @RequestBody UpdateVueloRestRequest request);

  @Operation(
      summary = "Eliminar vuelo",
      description = "Elimina permanentemente un vuelo del sistema por su ID.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "204",
        description = "Vuelo eliminado exitosamente (sin contenido)."),
    @ApiResponse(
        responseCode = "404",
        description = "No se encontró ningún vuelo con el ID especificado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  void delete(
      @Parameter(description = "Identificador único del vuelo a eliminar", example = "10000000-0000-0000-0000-000000000001")
          @PathVariable
          String id);
}
