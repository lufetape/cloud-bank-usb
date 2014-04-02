package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para una
 * transferencia
 *
 * @author Felipe
 */
@Local
public interface ITransferenciaService {

    /**
     * Método que realiza una transferencia
     *
     * @param consignacion
     * @param retiro
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void transferir(Consignaciones consignacion, Retiros retiro) throws CloudBankException, Exception;

    /**
     * Método que verifica una transferencia
     *
     * @param consignacion
     * @param retiro
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void verificarTransferencia(Consignaciones consignacion, Retiros retiro) throws CloudBankException, Exception;
}
