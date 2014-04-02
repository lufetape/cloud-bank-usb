package co.edu.usbcali.e_cloudbank.services;

import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaCuentasDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaMovimientosDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para una
 * cuenta de un usuario
 *
 * @author Felipe
 */
@Local
public interface ICuentaService {

    /**
     * Método que permite listar las cuentas del cliente
     *
     * @param id Identificación del cliente
     * @return Respuesta Listado de cuentas
     * @throws co.edu.usbcali.e_cloudbank.utils.CloudBankException
     * @throws java.lang.Exception
     */
    public RespuestaConsultaCuentasDTO listarCuentas(long id) throws CloudBankException, Exception;

    /**
     * Método que permite listar movimientos de una cuenta del cliente
     *
     * @param id Identificación del cliente
     * @param numero
     * @param fechaInicial
     * @param fechaFinal
     * @return Respuesta Listado de movimientos
     * @throws co.edu.usbcali.e_cloudbank.utils.CloudBankException
     * @throws java.lang.Exception
     */
    public RespuestaConsultaMovimientosDTO listarMovimientos(long id, String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception;

}
