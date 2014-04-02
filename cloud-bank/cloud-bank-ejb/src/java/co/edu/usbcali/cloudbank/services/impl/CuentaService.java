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

        logger.entry();
        return logger.exit(cuentaDAO.findAll());
    }

    @Override
    public Cuentas consultarPorNumero(String numero) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando numero");
        if (numero == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo")));
        }

        return logger.exit(cuentaDAO.findById(numero));
    }

    @Override
    public List<Cuentas> consultarPorCliente(Long idCliente) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando id");
        if (idCliente == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "idClienteNulo")));
        }

        return logger.exit(cuentaDAO.consultarPorCliente(idCliente));
    }

    @Override
    public List<Cuentas> consultarPorFiltros(String numero, Long idCliente, String estado) throws CloudBankException, Exception {

        logger.entry();
        
        if (numero == null && idCliente == null && estado == null) {
            return logger.exit(consultarTodos());
        }

        return logger.exit(cuentaDAO.consultarPorFiltros(numero, idCliente, estado));
    }

    @Override
    public void eliminar(Cuentas cuenta) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando cuenta");
        if (cuenta.getCueNumero() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo")));
        }

        cuentaDAO.remove(new Cuentas(), cuenta.getCueNumero());
        logger.exit();
    }

    @Override
    public void retirar(Cuentas cuenta) throws CloudBankException, Exception {
        
        logger.entry();
        
        logger.info("Validando cuenta");
        if (cuenta.getCueActiva().equals("R")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaRetirado")));
        }
        if (cuenta.getCueSaldo().doubleValue() > 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "saldoMayorCeroRetirar")));
        }
        cuenta.setCueActiva("R");

        logger.info("Retirando cuenta");
        modificar(cuenta);
        logger.exit();
    }
    
    @Override
    public void desactivar(Cuentas cuenta) throws CloudBankException, Exception {
        
        logger.entry();
        
        logger.info("Validando cuenta");
        if (cuenta.getCueActiva().equals("R")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaRetirado")));
        }
        if (cuenta.getCueActiva().equals("N")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaInactivo")));
        }
        cuenta.setCueActiva("N");

        logger.info("Desactivando cuenta");
        modificar(cuenta);
        logger.exit();
    }
    
    @Override
    public void activar(Cuentas cuenta) throws CloudBankException, Exception {
        
        logger.entry();
        
        logger.info("Validando cuenta");
        if (cuenta.getCueActiva().equals("R")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaRetirado")));
        }
        if (cuenta.getCueActiva().equals("S")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaActivo")));
        }
        if (cuenta.getCueSaldo().doubleValue() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "saldoCeroActivar")));
        }
        cuenta.setCueActiva("S");

        logger.info("Activando cuenta");
        modificar(cuenta);
        logger.exit();
    }

    @Override
    public void modificar(Cuentas cuenta) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarCuenta(cuenta);

        //Se consulta la cuenta
        logger.info("Consultando cuenta");
        Cuentas cuentaModificar = consultarPorNumero(cuenta.getCueNumero());

        //Se hidrata el objeto con los nuevos valores
        logger.info("Seteando cuenta");
        cuentaModificar.setCliId(cuenta.getCliId());
        cuentaModificar.setCueActiva(cuenta.getCueActiva());
        cuentaModificar.setCueSaldo(cuenta.getCueSaldo());
        cuentaModificar.setCueClave(cuenta.getCueClave());

        //Se realiza la actualizacion            
        logger.info("Actualizando cuenta");
        cuentaDAO.modify(cuentaModificar);
        logger.exit();
    }

    @Override
    public Cuentas crear(Cuentas cuenta) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarCliente(cuenta);

        //Se hidrata el objeto con los nuevos valores 
        logger.info("Hidratando objeto cuenta");
        cuenta.setCueNumero(generarNumeroCuenta());
        cuenta.setCueSaldo(BigDecimal.ZERO);
        cuenta.setCueActiva("N"); //Siempre se crea inactiva
        cuenta.setCueClave(generarClaveCuenta(cuenta));

        //Se realiza la creacion            
        logger.info("Creando cuenta");
        return logger.exit(cuentaDAO.create(cuenta));
    }

    /**
     * Metodo que genera un numero de cuenta nuevo
     *
     * @return Numero de cuenta generado
     */
    private String generarNumeroCuenta() throws Exception {

        logger.entry();
        
        //Se consulta la ultima cuenta
        logger.info("Consultando ultima cuenta");
        Cuentas ultimaCuenta = cuentaDAO.consultarUltimaCuenta();

        logger.info("Generando numero de cuenta");
        //Se obtiene el numero de cuenta sin guiones y se suman 1 posición
        Long cuentaLong = (Long.valueOf(ultimaCuenta.getCueNumero().replaceAll("-", "")));
        cuentaLong++;

        //Se genera el nuevo numero y se retorna
        String cuentaString = UtilString.lpad(String.valueOf(cuentaLong), 12, "0");
        String numeroCuenta = cuentaString.substring(0, 4) + "-" + cuentaString.substring(4, 8) + "-" + cuentaString.substring(8, 12);
        return logger.exit(numeroCuenta);
    }

    /**
     * Metodo que genera una clave para una cuenta
     *
     * @return Clave generada para la cuenta
     */
    private String generarClaveCuenta(Cuentas cuenta) throws Exception {

        logger.entry();
        
        //La cuenta se genera con los ultimos 4 digitos del id del cliente
        logger.info("Generando clave de cuenta");
        String idClienteString = String.valueOf(cuenta.getCliId().getCliId());
        return logger.exit(idClienteString.substring(idClienteString.length() - 4));
    }

    /**
     * Metodo que valida el formulario para crear cuentas
     *
     * @param cuenta
     */
    private void validarCliente(Cuentas cuenta) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando cliente para la cuenta");
        if (cuenta.getCliId().getCliId() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }
        cuenta.setCliId(clienteService.consultarPorId(cuenta.getCliId().getCliId()));
        if (cuenta.getCliId() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "clienteNoExiste")));
        }
        logger.exit();
    }

    /**
     * Metodo que valida el formulario para crear/editar cuentas
     *
     * @param cuenta
     */
    private void validarCuenta(Cuentas cuenta) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando cuenta");
        
        validarCliente(cuenta);
        if (cuenta.getCueActiva() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "estadoCuentaNulo")));
        }
        if (cuenta.getCueClave() == null || cuenta.getCueClave().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaNula")));
        }
        if (!(UtilRegExp.isNumeric(cuenta.getCueClave()) && cuenta.getCueClave().length() == 4)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "claveCuentaInvalida")));
        }
        logger.exit();
    }
}
