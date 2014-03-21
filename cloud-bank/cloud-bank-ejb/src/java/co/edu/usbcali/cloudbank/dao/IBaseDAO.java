package co.edu.usbcali.cloudbank.dao;

/**
 * Interface que abstrae las operaciones de acceso a Datos
 *
 * @author Felipe
 * @param <T> Entidad de la base de datos
 */
public interface IBaseDAO<T> extends IBaseReadOnlyDAO<T> {

    /**
     * Método que crea una entidad en la base de datos
     *
     * @param entity Entidad a crear
     * @return Entidad creada
     */
    public T create(T entity);

    /**
     * Método que actualiza una entidad en la base de datos
     *
     * @param entity Entidad a actualizar
     */
    public void modify(T entity);

    /**
     * Método que remueve una entidad de la base de datos
     *
     * @param entity Entidad a remover
     * @param primaryKey Llave primaria de la Entidad
     * @throws Exception
     */
    public void remove(T entity, Object primaryKey) throws Exception;

    /**
     * Método que actualiza una entidad en el contexto
     *
     * @param entity
     */
    public void refresh(T entity);

    /**
     * Método que permite hacer flush a la base de datos
     */
    public void flush();
}
