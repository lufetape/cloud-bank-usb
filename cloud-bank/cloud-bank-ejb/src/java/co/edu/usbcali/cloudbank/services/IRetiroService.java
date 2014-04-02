package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para un
 * cliente
 *
 * @author Felipe
 */
@Local
public interface IRetiroService {

    /**
     * Método que consulta retiros por diferentes filtros
     *
     * return Listado de retiros encontrados
     *
     * @param numero Número de cuenta
     * @param fechaInicial Fecha inicial de movimiento
     * @param fechaFinal Fecha final de movimiento
     * @return Retiros encontradas
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Retiros> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception;

    /**
     * Método que realiza un retiro
     *
     * @param retiro
     * @return Retiro realizado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Retiros retirar(Retiros retiro) throws CloudBankException, Exception;
    
    /**
     * Método que verifica un retiro
     *
     * @param retiro
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void verificarRetiro(Retiros retiro) throws CloudBankException, Exception;
}
