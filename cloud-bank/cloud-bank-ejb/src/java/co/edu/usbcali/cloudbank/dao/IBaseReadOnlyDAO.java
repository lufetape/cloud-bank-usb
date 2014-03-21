package co.edu.usbcali.cloudbank.dao;

import java.util.List;

/**
 * Interface que abstrae las operaciones de solo-lectura hacia la base de datos
 *
 * @author Felipe
 * @param <T> Entidad de la base de datos
 */
public interface IBaseReadOnlyDAO<T> {

    /**
     * Método que cuenta los elementos la entidad en la base de datos
     *
     * @return Cantidad de registros
     */
    public int count();

    /**
     * Método que consulta un elemento de la entidad por id
     *
     * @param id Llave primaria de la entidad
     * @return Entidad encontrada
     */
    public T findById(Object id);

    /**
     * Método que lista los elementos de la entidad
     *
     * @return Listado de elementos de la entidad
     */
    public List<T> findAll();

    /**
     * Método que lista los elementos de la entidad con limites minimo y maximo
     *
     * @param range Rango de busqueda ([0]=minimo y [1]=maximo)
     * @return Listado de elementos de la entidad en el rango
     */
    public List<T> findRange(int[] range);

    /**
     * Método que lista el resultado de una consulta (query) sobre la entidad
     *
     * @return Listado de elementos de la entidad
     */
    public List<T> find();

    /**
     * Método que lista el resultado de una consulta preestablecida (named
     * query) sobre la entidad
     *
     * @return Listado de elementos de la entidad
     */
    public List<T> findNamedQuery();
}
