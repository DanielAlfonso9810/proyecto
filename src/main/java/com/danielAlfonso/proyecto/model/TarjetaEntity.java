package com.danielAlfonso.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity(name = "tbl_tarjeta")
public class TarjetaEntity {

    @Id
    @GeneratedValue
    Integer id;
    @Column
    String estado;
    @Column
    String titular;
    @Column
    String cedula;
    @Column
    String telefono;
    @Column
    String pan;
    @Column
    String panEnmascarado;
    @Column
    int numeroValidacion;

}
