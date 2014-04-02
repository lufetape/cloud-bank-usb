package co.edu.usbcali.e_cloudbank.services;

import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para una
 * transferencia entre cuentas de un cliente
 *
 * @author Felipe
 */
@Local
public interface ITransferenciaService {

    /**
     * Método que realiza una transferencia
     *
     * @param idCliente
     * @param cuentaOrigen
     * @param clave
     * @param cuentaDestino
     * @param valor
     * @throws co.edu.usbcali.e_cloudbank.utils.CloudBankException
     * @throws java.lang.Exception
     */
    public void realizarTransferencia(long idCliente, CuentaDTO cuentaOrigen, String clave, CuentaDTO cuentaDestino, double valor) throws CloudBankException, Exception;
}
