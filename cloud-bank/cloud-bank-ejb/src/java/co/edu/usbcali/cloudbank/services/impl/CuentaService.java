package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.ICuentaDAO;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import co.edu.usbcali.cloudbank.util.UtilRegExp;
import co.edu.usbcali.cloudbank.util.UtilString;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa los métodos que pueden ser realizados para una cuenta
 *
 * @author Felipe
 */
@Stateless
public class CuentaService implements ICuentaService {
    
    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @EJB
    private IClienteService clienteService;

    @EJB
    private ICuentaDAO cuentaDAO;

    @Override
    public List<Cuentas> consultarTodos() throws CloudBankException, Exception {

        return cuentaDAO.findAll();
    }

    @Override
    public Cuentas consultarPorNumero(String numero) throws CloudBankException, Exception {

        if (numero == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo"));
        }

        return cuentaDAO.findById(numero);
    }

    @Override
    public List<Cuentas> consultarPorCliente(Long idCliente) throws CloudBankException, Exception {

        if (idCliente == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "idClienteNulo"));
        }

        return cuentaDAO.consultarPorCliente(idCliente);
    }

    @Override
    public List<Cuentas> consultarPorFiltros(String numero, Long idCliente, String estado) throws CloudBankException, Exception {

        if (numero == null && idCliente == null && estado == null) {
            return consultarTodos();
        }

        return cuentaDAO.consultarPorFiltros(numero, idCliente, estado);
    }

    @Override
    public void eliminar(String numero) throws CloudBankException, Exception {

        if (numero == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo"));
        }

        cuentaDAO.remove(new Cuentas(), numero);
    }
    
    @Override
    public void modificarEstado(String numero, String cuentaActiva) throws CloudBankException, Exception {

        if (numero == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo"));
        }
        if (cuentaActiva == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaNulo"));
        }

        //Se consulta la cuenta
        Cuentas cuentaModificar = consultarPorNumero(numero);
        
        //Se hidrata el objeto con los nuevos valores
        cuentaModificar.setCueActiva(cuentaActiva);

        //Se realiza la actualizacion            
        cuentaDAO.modify(cuentaModificar);
    }

    @Override
    public void modificar(String numero, Long idCliente, String cuentaActiva, String clave) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarCuentaEditar(idCliente, cuentaActiva, clave);

        //Se consulta la cuenta
        Cuentas cuentaModificar = consultarPorNumero(numero);

        //Se hidrata el objeto con los nuevos valores
        cuentaModificar.setCliId(clienteService.consultarPorId(idCliente));
        cuentaModificar.setCueActiva(cuentaActiva);
        cuentaModificar.setCueClave(clave);

        //Se realiza la actualizacion            
        cuentaDAO.modify(cuentaModificar);
    }

    @Override
    public Cuentas crear(Long idCliente) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarCuentaCrear(idCliente);

        //Se hidrata el objeto con los nuevos valores  
        Cuentas cuentaCrear = new Cuentas();
        cuentaCrear.setCueNumero(generarNumeroCuenta());
        cuentaCrear.setCliId(clienteService.consultarPorId(idCliente));
        cuentaCrear.setCueSaldo(BigDecimal.ZERO);
        cuentaCrear.setCueActiva("N"); //Siempre se crea inactiva
        cuentaCrear.setCueClave(generarClaveCuenta(cuentaCrear));

        //Se realiza la creacion            
        return cuentaDAO.create(cuentaCrear);
    }

    /**
     * Metodo que genera un numero de cuenta nuevo
     *
     * @return Numero de cuenta generado
     */
    private String generarNumeroCuenta() throws Exception {

        //Se consulta la ultima cuenta
        Cuentas ultimaCuenta = cuentaDAO.consultarUltimaCuenta();

        //Se obtiene el numero de cuenta sin guiones y se suman 1 posición
        Long cuentaLong = (Long.valueOf(ultimaCuenta.getCueNumero().replaceAll("-", "")));
        cuentaLong++;

        //Se genera el nuevo numero y se retorna
        String cuentaString = UtilString.lpad(String.valueOf(cuentaLong), 12, "0");
        String numeroCuenta = cuentaString.substring(0, 4) + "-" + cuentaString.substring(4, 8) + "-" + cuentaString.substring(8, 12);
        return numeroCuenta;
    }

    /**
     * Metodo que genera una clave para una cuenta
     *
     * @return Clave generada para la cuenta
     */
    private String generarClaveCuenta(Cuentas cuenta) throws Exception {

        //La cuenta se genera con los ultimos 4 digitos del id del cliente
        String idClienteString = String.valueOf(cuenta.getCliId().getCliId());
        return idClienteString.substring(idClienteString.length() - 4);
    }

    /**
     * Metodo que valida el formulario para crear cuentas
     *
     * @param idCliente
     * @param saldo
     */
    private void validarCuentaCrear(Long idCliente) throws CloudBankException {

        if (idCliente == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo"));
        }
        if (idCliente <= 0) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteMenorIgualCero"));
        }
    }

    /**
     * Metodo que valida el formulario para crear/editar cuentas
     *
     * @param numero
     * @param idCliente
     * @param saldo
     * @param cuentaActiva
     * @param clave
     */
    private void validarCuentaEditar(Long idCliente, String cuentaActiva, String clave) throws CloudBankException {

        validarCuentaCrear(idCliente);
        if (cuentaActiva == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaNulo"));
        }
        if (clave == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaNula"));
        }
        if (clave.trim().equals("")) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaVacia"));
        }
        if (!(UtilRegExp.isNumeric(clave) && clave.length() == 4)) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaInvalida"));
        }
    }
}
