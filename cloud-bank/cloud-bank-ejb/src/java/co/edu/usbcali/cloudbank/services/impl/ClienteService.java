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
        if (palabraClave == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "palabraClaveNula")));
        }
        if (!UtilRegExp.isAlphanumeric(palabraClave)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "palabraClaveInvalida")));
        }

        return logger.exit(clienteDAO.consultarPorPalabraClave(palabraClave));
    }

    @Override
    public List<Clientes> consultarPorFiltros(Long id, Long idTipoDocumento, String nombre) throws CloudBankException, Exception {

        logger.entry();
        logger.info("Validando si la consulta equivale a consultar todos");
        if (id == null && idTipoDocumento == null && nombre == null) {
            return logger.exit(consultarTodos());
        }

        return logger.exit(clienteDAO.consultarPorFiltros(id, idTipoDocumento, nombre));
    }

    @Override
    public void eliminar(Long id) throws Exception {

        logger.entry();
        
        logger.info("Validando id");
        if (id == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }

        clienteDAO.remove(new Clientes(), id);
        logger.exit();
    }

    @Override
    public void modificar(Long id, Long tipoDocumento, String nombre, String direccion, String telefono, String email) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarCliente(id, tipoDocumento, nombre, direccion, telefono, email);

        logger.info("Modificando cliente");
        //Se consulta el cliente
        Clientes clienteModificar = consultarPorId(id);

        //Se hidrata el objeto con los nuevos valores
        clienteModificar.setCliNombre(nombre);
        clienteModificar.setTdocCodigo(tipoDocumentoService.consultarPorId(tipoDocumento));
        clienteModificar.setCliNombre(nombre);
        clienteModificar.setCliDireccion(direccion.trim().toUpperCase());
        clienteModificar.setCliTelefono(telefono);
        clienteModificar.setCliMail(email);

        //Se realiza la actualizacion            
        clienteDAO.modify(clienteModificar);
        logger.exit();
    }

    @Override
    public Cuentas crear(Long id, Long tipoDocumento, String nombre, String direccion, String telefono, String email) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarCliente(id, tipoDocumento, nombre, direccion, telefono, email);

        //Se verifica si el cliente ya existe
        logger.info("Verificando si el cliente existe");
        if (consultarPorId(id) != null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "yaExiste")));
        }

        logger.info("Creando cliente");
        //Se hidrata el objeto con los nuevos valores  
        Clientes clienteCrear = new Clientes();
        clienteCrear.setCliId(id);
        clienteCrear.setCliNombre(nombre);
        clienteCrear.setTdocCodigo(tipoDocumentoService.consultarPorId(tipoDocumento));
        clienteCrear.setCliNombre(nombre);
        clienteCrear.setCliDireccion(direccion.trim().toUpperCase());
        clienteCrear.setCliTelefono(telefono);
        clienteCrear.setCliMail(email);

        //Se realiza la creacion del cliente
        clienteDAO.create(clienteCrear);

        //Se realiza la creacion de la cuenta asociada al cliente y se retorna
        return logger.exit(cuentaService.crear(id));
    }

    /**
     * Metodo que valida el formulario para crear/editar clientes
     *
     * @param id
     * @param tipoDocumento
     * @param nombre
     * @param direccion
     * @param telefono
     * @param email
     */
    private void validarCliente(Long id, Long tipoDocumento, String nombre, String direccion, String telefono, String email) throws CloudBankException {

        logger.entry();
        if (id == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteNulo")));
        }
        if (id <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "idClienteMenorIgualCero")));
        }
        if (tipoDocumento == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo")));
        }
        if (nombre == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "nombreClienteNulo")));
        }
        if (nombre.trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "nombreClienteVacio")));
        }
        if (!UtilRegExp.isAlphanumeric(nombre)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "nombreClienteInvalido")));
        }
        if (direccion == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "direccionClienteNula")));
        }
        if (direccion.trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "direccionClienteVacia")));
        }
        if (!UtilRegExp.isAlphanumeric(direccion)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "direccionClienteInvalida")));
        }
        if (telefono == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "telefonoClienteNulo")));
        }
        if (telefono.trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "telefonoClienteVacio")));
        }
        if (!UtilRegExp.isAlphanumeric(telefono)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "telefonoClienteInvalido")));
        }
        if (email != null) {
            if (!UtilRegExp.isEmail(email)) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "emailClienteInvalido")));
            }
        }
        logger.exit();
    }
}
