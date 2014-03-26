package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para una
 * cuenta
 *
 * @author Felipe
 */
@Local
public interface ICuentaService {

    /**
     * Método que permite listar todas las cuentas
     *
     * @return Listado de cuentas
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Cuentas> consultarTodos() throws CloudBankException, Exception;

    /**
     * Método que consulta una cuenta por numero
     *
     * @param numero Numero de Cuenta
     * @return Cuenta encontrada
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Cuentas consultarPorNumero(String numero) throws CloudBankException, Exception;

    /**
     * Método que consulta cuentas por cliente
     *
     * @param idCliente Identificación del Cliente
     * @return Listado de cuentas encontradas
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Cuentas> consultarPorCliente(Long idCliente) throws CloudBankException, Exception;

    /**
     * Método que consulta cuentas por diferentes filtros
     *
     * @param numero
     * @param idCliente Identificación del Cliente
     * @param estado Estado de la cuenta (Activa o Inactiva)
     * @return Listado de cuentas encontradas
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Cuentas> consultarPorFiltros(String numero, Long idCliente, String estado) throws CloudBankException, Exception;

    /**
     * Método que crea una cuenta
     *
     * @param cuenta
     * @return Cuenta creada
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Cuentas crear(Cuentas cuenta) throws CloudBankException, Exception;

    /**
     * Método que modifica una cuenta
     *
     * @param cuenta
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void modificar(Cuentas cuenta) throws CloudBankException, Exception;

    /**
     * Método que elimina una cuenta
     *
     * @param cuenta
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void eliminar(Cuentas cuenta) throws CloudBankException, Exception;
    
    /**
     * Método que retira una cuenta
     *
     * @param cuenta
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void retirar(Cuentas cuenta) throws CloudBankException, Exception;
    
    /**
     * Método que desactiva una cuenta
     *
     * @param cuenta
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void desactivar(Cuentas cuenta) throws CloudBankException, Exception;
    
    /**
     * Método que activa una cuenta
     *
     * @param cuenta
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void activar(Cuentas cuenta) throws CloudBankException, Exception;
}
