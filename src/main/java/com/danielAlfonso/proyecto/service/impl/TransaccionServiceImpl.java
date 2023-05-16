package com.danielAlfonso.proyecto.service.impl;

import com.danielAlfonso.proyecto.dto.TransaccionRequest;
import com.danielAlfonso.proyecto.model.TarjetaEntity;
import com.danielAlfonso.proyecto.model.TransaccionEntity;
import com.danielAlfonso.proyecto.repository.TarjetaRepositorio;
import com.danielAlfonso.proyecto.repository.TransaccionRepositorio;
import com.danielAlfonso.proyecto.service.TransaccionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransaccionServiceImpl implements TransaccionServices {

    @Autowired
    TransaccionRepositorio transaccionRepositorio;

    @Autowired
    TarjetaRepositorio tarjetaRepositorio;

    public TransaccionServiceImpl() {
    }

    @Override
    public Map<String, Object> crearTransaccion(TransaccionRequest request) {
        Map<String, Object> response = new HashMap<>();

        TarjetaEntity tarjeta = tarjetaRepositorio.getAllByPan(request.getPAN());

        if (tarjeta != null && tarjeta.getEstado().equals("Enrolada")) {

            String numeroReferencia = generarNumeroReferencia();
            response.put("codigo_respuesta", "00");
            response.put("mensaje", "Compra exitosa");
            response.put("estado_transaccion", "Aprobado");
            response.put("numero_referencia", numeroReferencia);

            TransaccionEntity transaccionEntity = new TransaccionEntity();
            transaccionEntity.setPan(request.getPAN());
            transaccionEntity.setReferencia(numeroReferencia);
            transaccionEntity.setTotalCompra(request.getTotalCompra());
            transaccionEntity.setDireccion(request.getDireccionCompra());
            transaccionEntity.setCreateTransaccion(LocalDateTime.now());

            transaccionRepositorio.save(transaccionEntity);

        } else if (tarjeta != null) {
            response.put("codigo_respuesta", "02");
            response.put("mensaje", "Tarjeta no enrolada");
        } else {
            response.put("codigo_respuesta", "01");
            response.put("mensaje", "Tarjeta no existe");
        }

        return response;
    }

    private String generarNumeroReferencia() {

        Random random = new Random();
        StringBuilder referenciaBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digito = random.nextInt(10);
            referenciaBuilder.append(digito);
        }

        return referenciaBuilder.toString();
    }

    public Map<String, Object> anularTransaccion(TransaccionRequest request) {
        TransaccionEntity transaccion = transaccionRepositorio.getByPan(request.getPAN(), request.getNumeroReferencia(), request.getTotalCompra());
        if (transaccion == null || !transaccion.getReferencia().equals(request.getNumeroReferencia())) {
            return generarRespuesta("01", "Número de referencia inválido", request.getNumeroReferencia());
        }

        // Validar si la transacción se hizo hace menos de 5 minutos
        if (!esMenorA5Minutos(transaccion.getCreateTransaccion())) {
            return generarRespuesta("02", "No se puede anular la transacción", request.getNumeroReferencia());
        }

        transaccionRepositorio.delete(transaccionRepositorio.getByPan(request.getPAN(), request.getNumeroReferencia(), request.getTotalCompra()));

        return generarRespuesta("00", "Compra anulada", request.getNumeroReferencia());
    }

    // Método auxiliar para validar si una fecha es menor a 5 minutos
    private boolean esMenorA5Minutos(LocalDateTime fecha) {
        // Obtener la fecha y hora actual
        LocalDateTime fechaActual = LocalDateTime.now();

        // Calcular la diferencia en milisegundos entre la fecha actual y la fecha de la transacción
        long diferenciaMillis = fechaActual.getMinute() - fecha.getMinute();

        // Calcular la diferencia en minutos
        long diferenciaMinutos = diferenciaMillis / (60 * 1000);

        // Retornar true si la diferencia es menor a 5 minutos, false en caso contrario
        return diferenciaMinutos < 5;
    }
    private Map<String, Object> generarRespuesta(String codigo, String mensaje, String numeroReferencia) {
        Map<String, Object> response = new HashMap<>();
        response.put("codigoRespuesta", codigo);
        response.put("mensaje", mensaje);
        response.put("numeroReferencia", numeroReferencia);
        return response;
    }
    @Override
    public List<TransaccionEntity> mostrarTransacciones() {
        return transaccionRepositorio.getAll();
    }
}
