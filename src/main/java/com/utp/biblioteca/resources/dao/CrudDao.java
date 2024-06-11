package com.utp.biblioteca.resources.dao;

import java.util.List;

/**
 * @param <T>  el tipo de la entidad que maneja este repositorio
 * @param <ID> el tipo del identificador único de la entidad
 */
public interface CrudDao<T, ID> {

    void crear(T entidad);
    List<T> buscarTodos();
    T buscarUno(ID id);
    void actualizar(T entidad);
    void eliminar(ID id);
    Boolean existe(ID id);

}
