package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.Cuentas;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de una cuenta y
 * hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface ICuentaDAO extends IBaseDAO<Cuentas> {

    /**
     * Método que consulta cuentas por cliente (identificación del cliente)
     *
     * @param idCliente Identificación del Cliente
     * @return Listado de clientes encontrados
     * @throws java.lang.Exception
     */
    public List<Cuentas> consultarPorCliente(Long idCliente) throws Exception;

    /**
     * Método que consulta cuentas por diferentes filtros de busqueda mezclados
     *
     * @param numero
     * @param idCliente Identificación del cliente
     * @param cuentaActiva Indica si la cuenta se encuentra activa o no
     * @return Listado de cuentas encontradas
     * @throws java.lang.Exception
     */
    public List<Cuentas> consultarPorFiltros(String numero, Long idCliente, String cuentaActiva) throws Exception;
    
    /**
     * Método que consulta la ultima cuenta creada en el sistema
     *
     * @return Ultima cuenta creada en el sistema
     * @throws java.lang.Exception
     */
    public Cuentas consultarUltimaCuenta() throws Exception;
}
