package com.danielAlfonso.proyecto.service;

import com.danielAlfonso.proyecto.dto.TransaccionRequest;
import com.danielAlfonso.proyecto.model.TransaccionEntity;

import java.util.List;
import java.util.Map;

public interface TransaccionServices {

    Map<String, Object> crearTransaccion(TransaccionRequest request);

    Map<String, Object> anularTransaccion(TransaccionRequest request);

    List<TransaccionEntity> mostrarTransacciones ();
}
