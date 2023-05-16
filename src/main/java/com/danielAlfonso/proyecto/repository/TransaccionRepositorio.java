package com.danielAlfonso.proyecto.repository;

import com.danielAlfonso.proyecto.model.TransaccionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransaccionRepositorio extends CrudRepository<TransaccionEntity, Integer> {

    @Query(value = "select t from tbl_transacciones t where t.pan = :pan and t.referencia = :referencia and t.totalCompra = :compra")
    TransaccionEntity getByPan(@Param("pan")String pan, @Param("referencia") String referencia, @Param("compra")BigDecimal compra);

    @Query(value = "select t from tbl_transacciones t")
    List<TransaccionEntity> getAll();
}
