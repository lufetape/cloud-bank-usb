package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.Clientes;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de un cliente y
 * hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface IClienteDAO extends IBaseDAO<Clientes> {

    /**
     * Método que consulta clientes por palabra clave (id o nombre)
     *
     * @param palabraClave Palabra clave por donde serán consultados los
     * clientes
     * @return Listado de clientes encontrados
     * @throws java.lang.Exception
     */
    public List<Clientes> consultarPorPalabraClave(String palabraClave) throws Exception;

    /**
     * Método que consulta clientes por diferentes filtros de busqueda mezclados
     *
     * @param id Identificación del Cliente
     * @param idTipoDocumento
     * @param nombre
     * @return Listado de clientes encontrados
     * @throws java.lang.Exception
     */
    public List<Clientes> consultarPorFiltros(Long id, Long idTipoDocumento, String nombre) throws Exception;
}
