package com.danielAlfonso.proyecto.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class EnrolarTarjetaRequest {
    private int numeroValidacion;
    private String estado;
    private String pan;
}
