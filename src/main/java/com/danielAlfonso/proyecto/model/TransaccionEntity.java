package com.danielAlfonso.proyecto.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity(name = "tbl_transacciones")
public class TransaccionEntity {

    @Id
    @GeneratedValue
    Integer id;

    @Column
    String pan;
    @Column
    String referencia;
    @Column
    BigDecimal totalCompra;
    @Column
    String direccion;
    @Column
    LocalDateTime createTransaccion;
}
