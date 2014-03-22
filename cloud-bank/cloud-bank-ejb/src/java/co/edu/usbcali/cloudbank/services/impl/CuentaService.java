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
    public void eliminar(Cuentas cuenta) throws CloudBankException, Exception {

        if (cuenta.getCueNumero() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo"));
        }

        cuentaDAO.remove(new Cuentas(), cuenta.getCueNumero());
    }

    @Override
    public void modificar(Cuentas cuenta) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarCuenta(cuenta);

        //Se consulta la cuenta
        Cuentas cuentaModificar = consultarPorNumero(cuenta.getCueNumero());

        //Se hidrata el objeto con los nuevos valores
        cuentaModificar.setCliId(cuenta.getCliId());
        cuentaModificar.setCueActiva(cuenta.getCueActiva());
        cuentaModificar.setCueSaldo(cuenta.getCueSaldo());
        cuentaModificar.setCueClave(cuenta.getCueClave());

        //Se realiza la actualizacion            
        cuentaDAO.modify(cuentaModificar);
    }

    @Override
    public Cuentas crear(Cuentas cuenta) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarCliente(cuenta);

        //Se hidrata el objeto con los nuevos valores 
        cuenta.setCueNumero(generarNumeroCuenta());
        cuenta.setCueSaldo(BigDecimal.ZERO);
        cuenta.setCueActiva("N"); //Siempre se crea inactiva
        cuenta.setCueClave(generarClaveCuenta(cuenta));

        //Se realiza la creacion            
        return cuentaDAO.create(cuenta);
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
     * @param cuenta
     */
    private void validarCliente(Cuentas cuenta) throws CloudBankException, Exception {

        if (cuenta.getCliId().getCliId() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo"));
        }
        cuenta.setCliId(clienteService.consultarPorId(cuenta.getCliId().getCliId()));
        if (cuenta.getCliId() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "clienteNoExiste")));
        }
    }

    /**
     * Metodo que valida el formulario para crear/editar cuentas
     *
     * @param cuenta
     */
    private void validarCuenta(Cuentas cuenta) throws CloudBankException, Exception {

        validarCliente(cuenta);
        if (cuenta.getCueActiva() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaNulo"));
        }
        if (cuenta.getCueClave() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaNula"));
        }
        if (cuenta.getCueClave().trim().equals("")) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaVacia"));
        }
        if (!(UtilRegExp.isNumeric(cuenta.getCueClave()) && cuenta.getCueClave().length() == 4)) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaInvalida"));
        }
    }
}
