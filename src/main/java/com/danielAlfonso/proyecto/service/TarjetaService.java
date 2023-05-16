package com.danielAlfonso.proyecto.service;

import com.danielAlfonso.proyecto.dto.EnrolarTarjetaRequest;
import com.danielAlfonso.proyecto.dto.TarjetaRequest;
import com.danielAlfonso.proyecto.model.TarjetaEntity;
import com.danielAlfonso.proyecto.model.TransaccionEntity;

import java.util.List;
import java.util.Map;

public interface TarjetaService {

    Map<String, Object> crearTarjeta (TarjetaRequest tarjetaRequest );
    Map<String, Object> EnrolarTarjetaRequest (EnrolarTarjetaRequest request);
    Map<String, Object> consultarTarjeta (String pan);
    Map<String, Object> eliminarTarjeta (String pan, int numeroValidacion);

    List<TarjetaEntity> mostrarTarjetas ();

}
