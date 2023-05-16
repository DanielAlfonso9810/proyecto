package com.danielAlfonso.proyecto.service.impl;

import com.danielAlfonso.proyecto.dto.EnrolarTarjetaRequest;
import com.danielAlfonso.proyecto.dto.TarjetaRequest;
import com.danielAlfonso.proyecto.model.TarjetaEntity;
import com.danielAlfonso.proyecto.model.TransaccionEntity;
import com.danielAlfonso.proyecto.repository.TarjetaRepositorio;
import com.danielAlfonso.proyecto.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    @Autowired
    TarjetaRepositorio tarjetaRepositorio;

    public TarjetaServiceImpl() {
    }

    @Override
    public Map<String, Object> crearTarjeta(TarjetaRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Generar un número de validación al azar entre 1 y 100
        Random random = new Random();
        int numeroValidacion = random.nextInt(100) + 1;

        // Enmascarar el número de tarjeta PAN
        String panEnmascarado = request.getPan().substring(0, 6) + "****" + request.getPan().substring(request.getPan().length() - 4);

        List<String> panList = tarjetaRepositorio.getAllReturnPan();

        String estado = "Creada";

        String codigoRespuesta;
        String mensaje;
        boolean panExist = false;
        for (String pan : panList) {
            if (pan.equals(request.getPan()))
                panExist = true;
        }

        if (request.getPan().length() < 16 || request.getPan().length() > 19) {
            codigoRespuesta = "01";
            mensaje = "Fallido: Número de tarjeta inválido";
        } else if (request.getCedula().length() < 10 || request.getCedula().length() > 15) {
            codigoRespuesta = "01";
            mensaje = "Fallido: Cédula inválida";
        } else if (!request.getTipo().equals("Credito") && !request.getTipo().equals("Debito")) {
            codigoRespuesta = "01";
            mensaje = "Fallido: Tipo de tarjeta inválido";
        } else if (request.getTelefono().length() != 10) {
            codigoRespuesta = "01";
            mensaje = "Fallido: Número de teléfono inválido";
        } else if (panExist) {
            codigoRespuesta = "01";
            mensaje = "Fallido: Pan ya se encuentra registrado";
        } else {
            codigoRespuesta = "00";
            mensaje = "Éxito";
        }

        // Crear el objeto de respuesta
        response.put("codigo_respuesta", codigoRespuesta);
        response.put("mensaje", mensaje);
        response.put("numero_validacion", numeroValidacion);
        response.put("pan_enmascarado", panEnmascarado);


        TarjetaEntity tarjetaEntity = new TarjetaEntity();
        tarjetaEntity.setCedula(request.getCedula());
        tarjetaEntity.setEstado(mensaje.equals("Éxito") ? "Creado" : "No creado");
        tarjetaEntity.setTitular(request.getTitular());
        tarjetaEntity.setTelefono(request.getTelefono());
        tarjetaEntity.setPan(request.getPan());
        tarjetaEntity.setPanEnmascarado(panEnmascarado);
        tarjetaEntity.setNumeroValidacion(numeroValidacion);
        if (!panExist)
            tarjetaRepositorio.save(tarjetaEntity);

        return response;
    }

    @Override
    public Map<String, Object> EnrolarTarjetaRequest(EnrolarTarjetaRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Validar si la tarjeta existe y si el número de validación es correcto
        boolean tarjetaExiste = false;
        boolean numeroValidacionCorrecto = false;

        List<String> estadoList = tarjetaRepositorio.getAllByEstado(request.getEstado(), request.getPan());
        List<Integer> numeroValidacionList = tarjetaRepositorio.getAllByNumeroValidacion();

        for (String estado : estadoList) {
            if (estado.equals(request.getEstado()))
                tarjetaExiste = true;
        }

        for (Integer numeroValidacion : numeroValidacionList) {
            if (numeroValidacion == request.getNumeroValidacion())
                numeroValidacionCorrecto = true;
        }

        if (tarjetaExiste && numeroValidacionCorrecto) {
            // Cambiar el estado a 'Enrolada'
            String estado = "Enrolada";
            // Actualizar el objeto de respuesta
            response.put("codigo_respuesta", "00");
            response.put("mensaje", "Éxito");
            response.put("pan_enmascarado", enmascararPan(request.getPan()));

            TarjetaEntity tarjetaEntity = tarjetaRepositorio.getByPanAndNAndNumeroValidacionAndEstado(request.getEstado(), request.getPan(), request.getNumeroValidacion());
            tarjetaEntity.setEstado(estado);
            tarjetaRepositorio.save(tarjetaEntity);

        } else if (!tarjetaExiste) {
            response.put("codigo_respuesta", "01");
            response.put("mensaje", "Tarjeta no existe");
        } else {
            response.put("codigo_respuesta", "02");
            response.put("mensaje", "Número de validación inválido");
        }

        return response;
    }

    @Override
    public Map<String, Object> consultarTarjeta(String pan) {
        Map<String, Object> response = new HashMap<>();

        TarjetaEntity tarjetaEntity = tarjetaRepositorio.getAllByPan(pan);

        if (tarjetaEntity != null) {
            // Obtener los datos de la tarjeta
            // Actualizar el objeto de respuesta
            response.put("pan_enmascarado", enmascararPan(tarjetaEntity.getPan()));
            response.put("titular", tarjetaEntity.getTitular());
            response.put("cedula", tarjetaEntity.getCedula());
            response.put("telefono", tarjetaEntity.getTelefono());
            response.put("estado", tarjetaEntity.getEstado());
        } else {
            response.put("mensaje", "Tarjeta no encontrada");
        }
        return response;
    }

    @Override
    public Map<String, Object> eliminarTarjeta(String pan, int numeroValidacion) {
        Map<String, Object> response = new HashMap<>();

        if (tarjetaRepositorio.deleteByPanAndNumeroValidacion(pan, numeroValidacion) == 1) {
            response.put("codigo_respuesta", "00");
            response.put("mensaje", "Se ha eliminado la tarjeta");
        } else {
            response.put("codigo_respuesta", "01");
            response.put("mensaje", "No se ha eliminado la tarjeta");
        }

        return response;
    }

    private String enmascararPan(String pan) {
        return pan.substring(0, 6) + "****" + pan.substring(pan.length() - 4);
    }

    public List<TarjetaEntity> mostrarTarjetas() {
        return tarjetaRepositorio.getAll();
    }
}
