package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.IUsuarioDAO;
import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.services.ITipoUsuarioService;
import co.edu.usbcali.cloudbank.services.IUsuarioService;
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
 * Clase que implementa los métodos que pueden ser realizados para un usuario
 *
 * @author Felipe
 */
@Stateless
public class UsuarioService implements IUsuarioService {

    private static final Logger logger = LogManager.getLogger(UsuarioService.class);

    @EJB
    private ITipoUsuarioService tipoUsuarioService;

    @EJB
    private IUsuarioDAO usuarioDAO;

    @Override
    public List<Usuarios> consultarTodos() throws Exception {

        logger.entry();

        return logger.exit(usuarioDAO.findAll());
    }

    @Override
    public Usuarios consultarPorId(Long codigo) throws CloudBankException, Exception {

        logger.entry();

        logger.info("Validando codigo");
        if (codigo == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "idUsuarioNulo")));
        }
        return logger.exit(usuarioDAO.findById(codigo));
    }

    @Override
    public List<Usuarios> consultarPorFiltros(Long codigo, Long idTipoUsuario, String login, String nombre) throws CloudBankException, Exception {

        logger.entry();
        logger.info("Validando si la consulta equivale a consultar todos");
        if (codigo == null && idTipoUsuario == null && login == null && nombre == null) {
            return logger.exit(consultarTodos());
        }

        return logger.exit(usuarioDAO.consultarPorFiltros(codigo, idTipoUsuario, login, nombre));
    }

    @Override
    public void eliminar(Usuarios usuario) throws Exception {

        logger.entry();

        logger.info("Validando codigo");
        if (usuario.getUsuCedula() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "idUsuarioNulo")));
        }

        usuarioDAO.remove(new Usuarios(), usuario.getUsuCedula());
        logger.exit();
    }

    @Override
    public void modificar(Usuarios usuario) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarUsuario(usuario);

        logger.info("Modificando usuario");
        //Se consulta el usuario
        Usuarios usuarioModificar = consultarPorId(usuario.getUsuCedula());

        //Se hidrata el objeto con los nuevos valores
        usuarioModificar.setUsuLogin(usuario.getUsuLogin());
        usuarioModificar.setTusuCodigo(usuario.getTusuCodigo());
        usuarioModificar.setUsuNombre(usuario.getUsuNombre().trim().toUpperCase());
        usuarioModificar.setUsuClave(usuario.getUsuClave());

        //Se realiza la actualizacion            
        usuarioDAO.modify(usuarioModificar);
        logger.exit();
    }

    @Override
    public Usuarios crear(Usuarios usuario) throws CloudBankException, Exception {

        logger.entry();
        //Se validan los datos de entrada
        validarUsuario(usuario);

        //Se verifica si el usuario ya existe
        logger.info("Verificando si el usuario existe");
        if (consultarPorId(usuario.getUsuCedula()) != null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "yaExiste")));
        }

        logger.info("Creando usuario");
        //Se hidrata el objeto con los valores
        Usuarios usuarioCrear = new Usuarios(usuario.getUsuCedula());
        usuarioCrear.setUsuLogin(usuario.getUsuLogin());
        usuarioCrear.setTusuCodigo(usuario.getTusuCodigo());
        usuarioCrear.setUsuNombre(usuario.getUsuNombre().trim().toUpperCase());
        usuarioCrear.setUsuClave(usuario.getUsuClave());
        return logger.exit(usuarioDAO.create(usuarioCrear));
    }

    /**
     * Metodo que valida el formulario para crear/editar usuarios
     *
     * @param usuario
     */
    private void validarUsuario(Usuarios usuario) throws CloudBankException, Exception {

        logger.entry();
        if (usuario.getUsuCedula() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "idUsuarioNulo")));
        }
        if (usuario.getUsuCedula() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "idUsuarioMenorIgualCero")));
        }
        if (usuario.getTusuCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoUsuarioNulo")));
        }
        usuario.setTusuCodigo(tipoUsuarioService.consultarPorId(usuario.getTusuCodigo().getTusuCodigo()));
        if (usuario.getTusuCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "tipoUsuarioNoExiste")));
        }
        if (usuario.getUsuNombre() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "nombreUsuarioNulo")));
        }
        if (usuario.getUsuNombre().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "nombreUsuarioVacio")));
        }
        if (!UtilRegExp.isAlphanumeric(usuario.getUsuNombre())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "nombreUsuarioInvalido")));
        }
        if (usuario.getUsuLogin() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "loginUsuarioNulo")));
        }
        if (usuario.getUsuLogin().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "loginUsuarioVacio")));
        }
        if (!UtilRegExp.isAlphanumeric(usuario.getUsuLogin())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "loginUsuarioInvalido")));
        }
        if (usuario.getUsuClave() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "claveUsuarioNula")));
        }
        if (usuario.getUsuClave().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "claveUsuarioVacia")));
        }
        if (!UtilRegExp.isAlphanumeric(usuario.getUsuClave())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "claveUsuarioInvalida")));
        }
        logger.exit();
    }
}
