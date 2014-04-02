package co.edu.usbcali.cloudbank.integrator;

import co.edu.usbcali.cloudbank.dto.EstadoDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaCuentasDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaMovimientosDTO;
import java.util.Date;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para
 * cuentas de los clientes que sera integrada
 *
 * @author Felipe
 */
@Local
public interface ICuentaClienteIntegrator {

    /**
     * Método que consulta las cuentas de un cliente
     *
     * @param idCliente Identificación del Cliente
     * @return Respuesta de la operación (Listado de cuentas encontradas)
     */
    public RespuestaConsultaCuentasDTO consultarPorCliente(long idCliente);
    
    /**
     * Método que consulta los movimientos de una cuenta de un cliente
     *
     * @param idCliente Identificación del Cliente
     * @param numero Número de cuenta
     * @param fechaInicial Fecha inicial movimiento
     * @param fechaFinal Fecha final movimiento
     * @return Respuesta de la operación (Listado de movimientos encontrados para la cuenta)
     */
    public RespuestaConsultaMovimientosDTO consultarMovimientos(long idCliente, String numero, String fechaInicial, String fechaFinal);
    
    /**
     * Método que realiza una transferencia entre cuentas del mismo cliente
     *
     * @param idCliente Identificación del Cliente
     * @param numeroOrigen Número de Cuenta Origen
     * @param claveOrigen Clave de Cuenta Origen
     * @param numeroDestino Número de Cuenta Destino
     * @param valor Valor de la Transferencia
     * @return Respuesta de la operación (Estado)
     */
    public EstadoDTO realizarTransferencia(long idCliente, String numeroOrigen, String claveOrigen, String numeroDestino, double valor);
}
