package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidVueloException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Vuelo Value Objects")
class VueloValueObjectsTest {

  @Test
  @DisplayName("VueloId valida que no sea nulo ni vacío")
  void shouldValidateVueloId() {
    assertThrows(NullPointerException.class, () -> new VueloId(null));
    assertThrows(InvalidVueloException.class, () -> new VueloId("   "));
    final VueloId id = new VueloId("v-001");
    assertEquals("v-001", id.value());
    assertEquals("v-001", id.toString());
  }

  @Test
  @DisplayName("VueloFechaCompra valida que no sea nulo ni vacío")
  void shouldValidateFechaCompra() {
    assertThrows(NullPointerException.class, () -> new VueloFechaCompra(null));
    assertThrows(InvalidVueloException.class, () -> new VueloFechaCompra(""));
    assertEquals("2026-11-06", new VueloFechaCompra("2026-11-06").value());
  }

  @Test
  @DisplayName("VueloFechaSalida valida que no sea nulo ni vacío")
  void shouldValidateFechaSalida() {
    assertThrows(NullPointerException.class, () -> new VueloFechaSalida(null));
    assertThrows(InvalidVueloException.class, () -> new VueloFechaSalida(""));
    assertEquals("2026-11-10", new VueloFechaSalida("2026-11-10").value());
  }

  @Test
  @DisplayName("VueloFechaLlegada valida que no sea nulo ni vacío")
  void shouldValidateFechaLlegada() {
    assertThrows(NullPointerException.class, () -> new VueloFechaLlegada(null));
    assertThrows(InvalidVueloException.class, () -> new VueloFechaLlegada(""));
    assertEquals("2026-11-10", new VueloFechaLlegada("2026-11-10").value());
  }

  @Test
  @DisplayName("VueloAgenciaViajes valida que no sea nulo ni vacío")
  void shouldValidateAgenciaViajes() {
    assertThrows(NullPointerException.class, () -> new VueloAgenciaViajes(null));
    assertThrows(InvalidVueloException.class, () -> new VueloAgenciaViajes(" "));
    assertEquals("Despegar", new VueloAgenciaViajes("Despegar").value());
  }

  @Test
  @DisplayName("VueloAerolinea valida que no sea nulo ni vacío")
  void shouldValidateAerolinea() {
    assertThrows(NullPointerException.class, () -> new VueloAerolinea(null));
    assertThrows(InvalidVueloException.class, () -> new VueloAerolinea(""));
    assertEquals("Avianca", new VueloAerolinea("Avianca").value());
  }

  @Test
  @DisplayName("VueloNumero valida que no sea nulo ni vacío")
  void shouldValidateNumero() {
    assertThrows(NullPointerException.class, () -> new VueloNumero(null));
    assertThrows(InvalidVueloException.class, () -> new VueloNumero(""));
    assertEquals("AV123", new VueloNumero("AV123").value());
  }

  @Test
  @DisplayName("VueloEstado valida que no sea nulo ni vacío")
  void shouldValidateEstado() {
    assertThrows(NullPointerException.class, () -> new VueloEstado(null));
    assertThrows(InvalidVueloException.class, () -> new VueloEstado(""));
    assertEquals("CONFIRMADO", new VueloEstado("CONFIRMADO").value());
  }

  @Test
  @DisplayName("VueloValor valida que no sea nulo ni negativo")
  void shouldValidateValor() {
    assertThrows(NullPointerException.class, () -> new VueloValor(null));
    assertThrows(InvalidVueloException.class, () -> new VueloValor(-10.0));
    assertEquals(100.0, new VueloValor(100.0).value());
    assertEquals("100.0", new VueloValor(100.0).toString());
  }

  @Test
  @DisplayName("VueloCliente valida que no sea nulo ni vacío")
  void shouldValidateCliente() {
    assertThrows(NullPointerException.class, () -> new VueloCliente(null));
    assertThrows(InvalidVueloException.class, () -> new VueloCliente(""));
    assertEquals("Pedro", new VueloCliente("Pedro").value());
  }

  @Test
  @DisplayName("VueloPuesto valida que no sea nulo ni vacío")
  void shouldValidatePuesto() {
    assertThrows(NullPointerException.class, () -> new VueloPuesto(null));
    assertThrows(InvalidVueloException.class, () -> new VueloPuesto(""));
    assertEquals("12A", new VueloPuesto("12A").value());
  }

  @Test
  @DisplayName("VueloAvion valida que no sea nulo ni vacío")
  void shouldValidateAvion() {
    assertThrows(NullPointerException.class, () -> new VueloAvion(null));
    assertThrows(InvalidVueloException.class, () -> new VueloAvion(""));
    assertEquals("Airbus A320", new VueloAvion("Airbus A320").value());
  }

  @Test
  @DisplayName("VueloAereopuertoSalida valida que no sea nulo ni vacío")
  void shouldValidateAereopuertoSalida() {
    assertThrows(NullPointerException.class, () -> new VueloAereopuertoSalida(null));
    assertThrows(InvalidVueloException.class, () -> new VueloAereopuertoSalida(""));
    assertEquals("El Dorado", new VueloAereopuertoSalida("El Dorado").value());
  }

  @Test
  @DisplayName("VueloAereopuertoLlegada valida que no sea nulo ni vacío")
  void shouldValidateAereopuertoLlegada() {
    assertThrows(NullPointerException.class, () -> new VueloAereopuertoLlegada(null));
    assertThrows(InvalidVueloException.class, () -> new VueloAereopuertoLlegada(""));
    assertEquals("Rafael Nunez", new VueloAereopuertoLlegada("Rafael Nunez").value());
  }

  @Test
  @DisplayName("VueloPiloto valida que no sea nulo ni vacío")
  void shouldValidatePiloto() {
    assertThrows(NullPointerException.class, () -> new VueloPiloto(null));
    assertThrows(InvalidVueloException.class, () -> new VueloPiloto(""));
    assertEquals("Carlos Ramirez", new VueloPiloto("Carlos Ramirez").value());
  }
}
