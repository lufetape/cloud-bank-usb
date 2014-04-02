package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.IClienteDAO;
import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.services.ITipoDocumentoService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import co.edu.usbcali.cloudbank.util.UtilRegExp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa los métodos que pueden ser realizados para un cliente
 *
 * @author Felipe
 */
@Stateless
public class ClienteService implements IClienteService {

    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @EJB
    private ICuentaService cuentaService;

    @EJB
    private ITipoDocumentoService tipoDocumentoService;

    @EJB
    private IClienteDAO clienteDAO;

    @Override
    public List<Clientes> consultarTodos() throws Exception {

        logger.entry();

        return logger.exit(clienteDAO.findAll());
    }

    @Override
    public Clientes consultarPorId(Long id) throws CloudBankException, Exception {

        logger.entry();

        logger.info("Validando id");
        if (id == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }
        return logger.exit(clienteDAO.findById(id));
    }

    @Override
    public List<Clientes> consultarPorPalabraClave(String palabraClave) throws CloudBankException, Exception {

        logger.entry();
        logger.info("Validando palabra clave");

        if (palabraClave != null && !UtilRegExp.isAlphanumeric(palabraClave)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "palabraClaveInvalida")));
        }

        return logger.exit(clienteDAO.consultarPorPalabraClave(palabraClave));
    }

    @Override
    public List<Clientes> consultarPorFiltros(Long id, Long idTipoDocumento, String nombre) throws CloudBankException, Exception {

        logger.entry();
        logger.info("Validando si la consulta equivale a consultar todos");
        if (nombre != null && nombre.trim().equals("")) {
            nombre = null;
        }
        if (id == null && idTipoDocumento == null && nombre == null) {
            return logger.exit(consultarTodos());
        }
        return logger.exit(clienteDAO.consultarPorFiltros(id, idTipoDocumento, nombre));
    }

    @Override
    public void eliminar(Clientes cliente) throws Exception {

        logger.entry();

        logger.info("Validando id");
        if (cliente.getCliId() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }

        cliente.setCuentasCollection(cuentaService.consultarPorCliente(cliente.getCliId()));

        logger.info("Validando cuentas del cliente");
        for (Cuentas cuenta : cliente.getCuentasCollection()) {
            if (!cuenta.getCueActiva().equals("R")) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "noPuedeSerEliminado")));
            }
        }

        clienteDAO.remove(new Clientes(), cliente.getCliId());
        logger.exit();
    }

    @Override
    public void modificar(Clientes cliente) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarCliente(cliente);

        logger.info("Modificando cliente");
        //Se consulta el cliente
        Clientes clienteModificar = consultarPorId(cliente.getCliId());

        //Se hidrata el objeto con los nuevos valores
        clienteModificar.setCliNombre(cliente.getCliNombre());
        clienteModificar.setTdocCodigo(cliente.getTdocCodigo());
        clienteModificar.setCliNombre(cliente.getCliNombre());
        clienteModificar.setCliDireccion(cliente.getCliDireccion().trim().toUpperCase());
        clienteModificar.setCliTelefono(cliente.getCliTelefono());
        clienteModificar.setCliMail(cliente.getCliMail());

        //Se realiza la actualizacion            
        clienteDAO.modify(clienteModificar);
        logger.exit();
    }

    @Override
    public Clientes crear(Clientes cliente) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarCliente(cliente);

        //Se verifica si el cliente ya existe
        logger.info("Verificando si el cliente existe");
        if (consultarPorId(cliente.getCliId()) != null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "yaExiste")));
        }

        logger.info("Creando cliente");
        //Se hidrata el objeto con los nuevos valores
        Clientes clienteCrear = new Clientes(cliente.getCliId());
        clienteCrear.setCliNombre(cliente.getCliNombre());
        clienteCrear.setTdocCodigo(cliente.getTdocCodigo());
        clienteCrear.setCliNombre(cliente.getCliNombre());
        clienteCrear.setCliDireccion(cliente.getCliDireccion().trim().toUpperCase());
        clienteCrear.setCliTelefono(cliente.getCliTelefono());
        clienteCrear.setCliMail(cliente.getCliMail());
        clienteCrear = clienteDAO.create(clienteCrear);

        logger.info("Creando cuenta asociada al cliente");
        Cuentas cuenta = new Cuentas();
        cuenta.setCliId(clienteCrear);
        cuentaService.crear(cuenta);

        //Se realiza la creacion de la cuenta asociada al cliente y se retorna
        return logger.exit(clienteCrear);
    }

    /**
     * Metodo que valida el formulario para crear/editar clientes
     *
     * @param cliente
     */
    private void validarCliente(Clientes cliente) throws CloudBankException, Exception {

        logger.entry();
        if (cliente.getCliId() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }
        if (cliente.getCliId() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteMenorIgualCero")));
        }
        if (cliente.getTdocCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo")));
        }
        cliente.setTdocCodigo(tipoDocumentoService.consultarPorId(cliente.getTdocCodigo().getTdocCodigo()));
        if (cliente.getTdocCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "tipoDocumentoNoExiste")));
        }
        if (cliente.getCliNombre() == null || cliente.getCliNombre().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "nombreClienteNulo")));
        }
        if (!UtilRegExp.isAlphanumeric(cliente.getCliNombre())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "nombreClienteInvalido")));
        }
        if (cliente.getCliDireccion() == null || cliente.getCliDireccion().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "direccionClienteNula")));
        }
        if (!UtilRegExp.isAlphanumeric(cliente.getCliDireccion())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "direccionClienteInvalida")));
        }
        if (cliente.getCliTelefono() == null || cliente.getCliTelefono().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "telefonoClienteNulo")));
        }
        if (!UtilRegExp.isAlphanumeric(cliente.getCliTelefono())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "telefonoClienteInvalido")));
        }
        if (cliente.getCliMail() != null && !cliente.getCliMail().equals("")) {
            if (!UtilRegExp.isEmail(cliente.getCliMail())) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "emailClienteInvalido")));
            }
        }
        logger.exit();
    }
}
