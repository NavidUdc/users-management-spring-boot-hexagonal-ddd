package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.out.EmailSenderPort;
import com.jcaa.usersmanagement.domain.exception.EmailSenderException;
import com.jcaa.usersmanagement.domain.model.EmailDestinationModel;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.model.VueloModel;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

  private static final String SUBJECT_CREATED = "Tu cuenta ha sido creada — Gestion de Usuarios";
  private static final String SUBJECT_UPDATED  = "Tu cuenta ha sido actualizada — Gestion de Usuarios";
  private static final String SUBJECT_VUELO = "Confirmación de tu vuelo — %s (%s)";

  private static final String TOKEN_NAME     = "name";
  private static final String TOKEN_EMAIL    = "email";
  private static final String TOKEN_PASSWORD = "password";
  private static final String TOKEN_ROLE     = "role";
  private static final String TOKEN_STATUS   = "status";

  private static final String TOKEN_ID                  = "id";
  private static final String TOKEN_FECHA_COMPRA        = "fechaCompra";
  private static final String TOKEN_FECHA_SALIDA        = "fechaSalida";
  private static final String TOKEN_FECHA_LLEGADA       = "fechaLlegada";
  private static final String TOKEN_AGENCIA_VIAJES      = "agenciaViajes";
  private static final String TOKEN_AEROLINEA           = "aerolinea";
  private static final String TOKEN_NUMERO              = "numero";
  private static final String TOKEN_ESTADO              = "estado";
  private static final String TOKEN_VALOR               = "valor";
  private static final String TOKEN_CLIENTE             = "cliente";
  private static final String TOKEN_PUESTO              = "puesto";
  private static final String TOKEN_AVION               = "avion";
  private static final String TOKEN_AEREOPUERTO_SALIDA  = "aereopuertoSalida";
  private static final String TOKEN_AEREOPUERTO_LLEGADA = "aereopuertoLlegada";
  private static final String TOKEN_PILOTO              = "piloto";

  private static final String LOG_SEND_FAILED =
      "[EmailNotificationService] correo no enviado. Causa: {}";

  private final EmailSenderPort emailSenderPort;

  @Async("notificationExecutor")
  public void notifyUserCreated(final UserModel user, final String plainPassword) {
    final String template = loadTemplate("user-created.html");
    final String body =
        renderTemplate(
            template,
            Map.of(
                TOKEN_NAME,     user.getName().value(),
                TOKEN_EMAIL,    user.getEmail().value(),
                TOKEN_PASSWORD, plainPassword,
                TOKEN_ROLE,     user.getRole().name()));
    final EmailDestinationModel destination = buildDestination(user, SUBJECT_CREATED, body);
    sendOrLog(destination);
  }

  @Async("notificationExecutor")
  public void notifyUserUpdated(final UserModel user) {
    final String template = loadTemplate("user-updated.html");
    final String body =
        renderTemplate(
            template,
            Map.of(
                TOKEN_NAME,   user.getName().value(),
                TOKEN_EMAIL,  user.getEmail().value(),
                TOKEN_ROLE,   user.getRole().name(),
                TOKEN_STATUS, user.getStatus().name()));
    final EmailDestinationModel destination = buildDestination(user, SUBJECT_UPDATED, body);
    sendOrLog(destination);
  }

  @Async("notificationExecutor")
  public void notifyVueloAssigned(final UserModel user, final VueloModel vuelo) {
    final String template = loadTemplate("vuelo-created.html");
    final String body =
        renderTemplate(
            template,
            Map.ofEntries(
                Map.entry(TOKEN_NAME, user.getName().value()),
                Map.entry(TOKEN_EMAIL, user.getEmail().value()),
                Map.entry(TOKEN_ID, vuelo.getId().value()),
                Map.entry(TOKEN_FECHA_COMPRA, vuelo.getFechaCompra().value()),
                Map.entry(TOKEN_FECHA_SALIDA, vuelo.getFechaSalida().value()),
                Map.entry(TOKEN_FECHA_LLEGADA, vuelo.getFechaLlegada().value()),
                Map.entry(TOKEN_AGENCIA_VIAJES, vuelo.getAgenciaViajes().value()),
                Map.entry(TOKEN_AEROLINEA, vuelo.getAerolinea().value()),
                Map.entry(TOKEN_NUMERO, vuelo.getNumero().value()),
                Map.entry(TOKEN_ESTADO, vuelo.getEstado().value()),
                Map.entry(TOKEN_VALOR, String.valueOf(vuelo.getValor().value())),
                Map.entry(TOKEN_CLIENTE, vuelo.getCliente().value()),
                Map.entry(TOKEN_PUESTO, vuelo.getPuesto().value()),
                Map.entry(TOKEN_AVION, vuelo.getAvion().value()),
                Map.entry(TOKEN_AEREOPUERTO_SALIDA, vuelo.getAereopuertoSalida().value()),
                Map.entry(TOKEN_AEREOPUERTO_LLEGADA, vuelo.getAereopuertoLlegada().value()),
                Map.entry(TOKEN_PILOTO, vuelo.getPiloto().value())));

    final String subject =
        String.format(
            SUBJECT_VUELO, vuelo.getAerolinea().value(), vuelo.getNumero().value());
    final EmailDestinationModel destination = buildDestination(user, subject, body);
    sendOrLog(destination);
  }

  private static EmailDestinationModel buildDestination(
      final UserModel user, final String subject, final String body) {
    return new EmailDestinationModel(
        user.getEmail().value(), user.getName().value(), subject, body);
  }

  private String loadTemplate(final String templateName) {
    final String path = "/templates/" + templateName;
    try (InputStream inputStream = openResourceStream(path)) {
      if (Objects.isNull(inputStream)) {
        throw EmailSenderException.becauseSendFailed(
            new IllegalStateException("Template not found: " + path));
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (final IOException ioException) {
      throw EmailSenderException.becauseSendFailed(ioException);
    }
  }

  InputStream openResourceStream(final String path) {
    return getClass().getResourceAsStream(path);
  }

  private String renderTemplate(String template, final Map<String, String> values) {
    String result = template;
    for (final Map.Entry<String, String> tokenEntry : values.entrySet()) {
      final String token = "{{" + tokenEntry.getKey() + "}}";
      result = result.replace(token, tokenEntry.getValue());
    }
    return result;
  }

  private void sendOrLog(final EmailDestinationModel destination) {
    try {
      emailSenderPort.send(destination);
    } catch (final EmailSenderException senderException) {
      log.warn(LOG_SEND_FAILED, senderException.getMessage(), senderException);
      throw senderException;
    }
  }
}
