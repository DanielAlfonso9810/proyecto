package com.danielAlfonso.proyecto.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarjetaRequest {
    private String pan;
    private String titular;
    private String cedula;
    private String tipo;
    private String telefono;
    private String estado;
}
