package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.VueloAereopuertoLlegada;
import com.jcaa.usersmanagement.domain.valueobject.VueloAereopuertoSalida;
import com.jcaa.usersmanagement.domain.valueobject.VueloAerolinea;
import com.jcaa.usersmanagement.domain.valueobject.VueloAgenciaViajes;
import com.jcaa.usersmanagement.domain.valueobject.VueloAvion;
import com.jcaa.usersmanagement.domain.valueobject.VueloCliente;
import com.jcaa.usersmanagement.domain.valueobject.VueloEstado;
import com.jcaa.usersmanagement.domain.valueobject.VueloFechaCompra;
import com.jcaa.usersmanagement.domain.valueobject.VueloFechaLlegada;
import com.jcaa.usersmanagement.domain.valueobject.VueloFechaSalida;
import com.jcaa.usersmanagement.domain.valueobject.VueloId;
import com.jcaa.usersmanagement.domain.valueobject.VueloNumero;
import com.jcaa.usersmanagement.domain.valueobject.VueloPiloto;
import com.jcaa.usersmanagement.domain.valueobject.VueloPuesto;
import com.jcaa.usersmanagement.domain.valueobject.VueloValor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class VueloModel {

  VueloId id;
  VueloFechaCompra fechaCompra;
  VueloFechaSalida fechaSalida;
  VueloFechaLlegada fechaLlegada;
  VueloAgenciaViajes agenciaViajes;
  VueloAerolinea aerolinea;
  VueloNumero numero;
  VueloEstado estado;
  VueloValor valor;
  VueloCliente cliente;
  VueloPuesto puesto;
  VueloAvion avion;
  VueloAereopuertoSalida aereopuertoSalida;
  VueloAereopuertoLlegada aereopuertoLlegada;
  VueloPiloto piloto;
  UserId usuarioId;

  public static VueloModel create(
      final VueloId id,
      final VueloFechaCompra fechaCompra,
      final VueloFechaSalida fechaSalida,
      final VueloFechaLlegada fechaLlegada,
      final VueloAgenciaViajes agenciaViajes,
      final VueloAerolinea aerolinea,
      final VueloNumero numero,
      final VueloEstado estado,
      final VueloValor valor,
      final VueloCliente cliente,
      final VueloPuesto puesto,
      final VueloAvion avion,
      final VueloAereopuertoSalida aereopuertoSalida,
      final VueloAereopuertoLlegada aereopuertoLlegada,
      final VueloPiloto piloto,
      final UserId usuarioId) {
    return VueloModel.builder()
        .id(id)
        .fechaCompra(fechaCompra)
        .fechaSalida(fechaSalida)
        .fechaLlegada(fechaLlegada)
        .agenciaViajes(agenciaViajes)
        .aerolinea(aerolinea)
        .numero(numero)
        .estado(estado)
        .valor(valor)
        .cliente(cliente)
        .puesto(puesto)
        .avion(avion)
        .aereopuertoSalida(aereopuertoSalida)
        .aereopuertoLlegada(aereopuertoLlegada)
        .piloto(piloto)
        .usuarioId(usuarioId)
        .build();
  }

  public static VueloModel fromPrimitives(
      final String id,
      final String fechaCompra,
      final String fechaSalida,
      final String fechaLlegada,
      final String agenciaViajes,
      final String aerolinea,
      final String numero,
      final String estado,
      final Double valor,
      final String cliente,
      final String puesto,
      final String avion,
      final String aereopuertoSalida,
      final String aereopuertoLlegada,
      final String piloto,
      final String usuarioId) {
    return new VueloModel(
        new VueloId(id),
        new VueloFechaCompra(fechaCompra),
        new VueloFechaSalida(fechaSalida),
        new VueloFechaLlegada(fechaLlegada),
        new VueloAgenciaViajes(agenciaViajes),
        new VueloAerolinea(aerolinea),
        new VueloNumero(numero),
        new VueloEstado(estado),
        new VueloValor(valor),
        new VueloCliente(cliente),
        new VueloPuesto(puesto),
        new VueloAvion(avion),
        new VueloAereopuertoSalida(aereopuertoSalida),
        new VueloAereopuertoLlegada(aereopuertoLlegada),
        new VueloPiloto(piloto),
        new UserId(usuarioId));
  }
}
