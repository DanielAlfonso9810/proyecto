package com.danielAlfonso.proyecto.repository;

import com.danielAlfonso.proyecto.model.TarjetaEntity;
import com.danielAlfonso.proyecto.model.TransaccionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TarjetaRepositorio extends CrudRepository<TarjetaEntity, Integer> {

    @Query(value = "select pan from tbl_tarjeta")
    List<String> getAllReturnPan();

    @Query(value = "select estado from tbl_tarjeta where estado = :estado and pan = :pan")
    List<String> getAllByEstado(@Param("estado")String estado, @Param("pan") String pan);

    @Query(value = "select numeroValidacion from tbl_tarjeta")
    List<Integer> getAllByNumeroValidacion();

    @Query(value = "select t from tbl_tarjeta t where t.estado= :estado and t.pan = :pan and t.numeroValidacion = :numero")
    TarjetaEntity getByPanAndNAndNumeroValidacionAndEstado(@Param("estado")String estado, @Param("pan") String pan, @Param("numero") Integer numero);
    @Query(value = "select t from tbl_tarjeta t where t.pan = :pan")
    TarjetaEntity getAllByPan(@Param("pan") String pan);

    @Modifying
    @Transactional
    @Query("delete from tbl_tarjeta t where t.pan = :pan and t.numeroValidacion = :numero")
    Integer deleteByPanAndNumeroValidacion(@Param("pan") String pan, @Param("numero") Integer numero);

    @Query(value = "select t from tbl_tarjeta t")
    List<TarjetaEntity> getAll();

}
