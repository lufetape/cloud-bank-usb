package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para un
 * cliente
 *
 * @author Felipe
 */
@Local
public interface IClienteService {

    /**
     * Método que permite listar todos los clientes
     *
     * @return Listado de clientes
     * @throws java.lang.Exception
     */
    public List<Clientes> consultarTodos() throws Exception;

    /**
     * Método que consulta un cliente por identificación
     *
     * @param id Identificación del Cliente
     * @return Cliente encontrado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Clientes consultarPorId(Long id) throws CloudBankException, Exception;

    /**
     * Método que consulta clientes por palabra clave (id o nombre)
     *
     * @param palabraClave Palabra clave por donde serán consultados los
     * clientes
     * @return Listado de clientes encontrados
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Clientes> consultarPorPalabraClave(String palabraClave) throws CloudBankException, Exception;
    
    /**
     * Método que consulta clientes por diferentes filtros
     *
     * @param id Identificación del Cliente
     * @param idTipoDocumento
     * @param nombre
     * @return Listado de clientes encontrados
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Clientes> consultarPorFiltros(Long id, Long idTipoDocumento, String nombre) throws CloudBankException, Exception;

    /**
     * Método que crea un cliente
     *
     * @param id Identificación del Cliente
     * @param tipoDocumento Tipo de Documento
     * @param nombre
     * @param direccion
     * @param telefono
     * @param email
     * @return Cuenta creada para el cliente
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Cuentas crear(Long id, Long tipoDocumento, String nombre, String direccion, String telefono, String email) throws CloudBankException, Exception;

    /**
     * Método que modifica un cliente
     *
     * @param id Identificación del Cliente
     * @param tipoDocumento Tipo de Documento
     * @param nombre
     * @param direccion
     * @param telefono
     * @param email
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void modificar(Long id, Long tipoDocumento, String nombre, String direccion, String telefono, String email) throws CloudBankException, Exception;

    /**
     * Método que elimina un cliente
     *
     * @param id Identificación del Cliente
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void eliminar(Long id) throws CloudBankException, Exception;
}
