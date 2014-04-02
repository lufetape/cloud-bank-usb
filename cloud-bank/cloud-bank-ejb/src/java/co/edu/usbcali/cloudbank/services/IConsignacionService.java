package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.model.Cuentas;
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
public interface IConsignacionService {

    /**
     * Método que consulta clientes por diferentes filtros
     *
     * return Listado de clientes encontrados
     *
     * @param numero Número de cuenta
     * @param fechaInicial Fecha inicial de movimiento
     * @param fechaFinal Fecha final de movimiento
     * @return Consignaciones encontradas
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Consignaciones> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception;

    /**
     * Método que realiza una consignacion
     *
     * @param consignacion
     * @return Consignacion realizada
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Consignaciones consignar(Consignaciones consignacion) throws CloudBankException, Exception;
    
    /**
     * Método que verifica una consignacion
     *
     * @param consignacion
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void verificarConsignacion(Consignaciones consignacion) throws CloudBankException, Exception;
}
