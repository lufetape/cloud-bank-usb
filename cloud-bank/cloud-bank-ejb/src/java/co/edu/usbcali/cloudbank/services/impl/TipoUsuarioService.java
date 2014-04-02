package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.ITipoUsuarioDAO;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.cloudbank.services.ITipoUsuarioService;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import co.edu.usbcali.cloudbank.util.UtilRegExp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa los métodos que pueden ser realizados para un tipo de
 * usuario
 *
 * @author Felipe
 */
@Stateless
public class TipoUsuarioService implements ITipoUsuarioService {

    private static final Logger logger = LogManager.getLogger(TipoUsuarioService.class);

    @EJB
    private ITipoUsuarioDAO tipoUsuarioDAO;

    @Override
    public List<TiposUsuarios> consultarTodos() throws CloudBankException, Exception {

        logger.entry();
        
        return logger.exit(tipoUsuarioDAO.findAll());
    }

    @Override
    public TiposUsuarios consultarPorId(Long id) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando id");
        if (id == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo")));
        }

        return logger.exit(tipoUsuarioDAO.findById(id));
    }

    @Override
    public void eliminar(TiposUsuarios tipoUsuario) throws Exception {

        logger.entry();
        
        logger.info("Validando tipo de usuario");
        if (tipoUsuario.getTusuCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo")));
        }

        tipoUsuarioDAO.remove(new TiposUsuarios(), tipoUsuario.getTusuCodigo());
        
        logger.exit();
    }

    @Override
    public void modificar(TiposUsuarios tipoUsuario) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarTipoUsuario(tipoUsuario);

        //Se consulta el tipo de usuario
        logger.info("Consultando tipo de usuario");
        TiposUsuarios tipoUsuarioModificar = consultarPorId(tipoUsuario.getTusuCodigo());

        //Se hidrata el objeto con los nuevos valores
        logger.info("Hidratando tipo de usuario");
        tipoUsuarioModificar.setTusuNombre(tipoUsuario.getTusuNombre().trim().toUpperCase());

        //Se realiza la actualizacion            
        logger.info("Actualizando tipo de usuario");
        tipoUsuarioDAO.modify(tipoUsuarioModificar);
        
        logger.exit();
    }

    @Override
    public TiposUsuarios crear(TiposUsuarios tipoUsuario) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarTipoUsuario(tipoUsuario);

        logger.info("Validando si tipo de usuario ya existe");
        //Se verifica si el tipo de usuario ya existe
        if (consultarPorId(tipoUsuario.getTusuCodigo()) != null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "yaExiste")));
        }

        //Se hidrata el objeto con los nuevos valores 
        logger.info("Hidratando tipo de usuario");
        TiposUsuarios tipoUsuarioCrear = new TiposUsuarios();
        tipoUsuarioCrear.setTusuCodigo(tipoUsuario.getTusuCodigo());
        tipoUsuarioCrear.setTusuNombre(tipoUsuario.getTusuNombre().trim().toUpperCase());

        //Se realiza la creacion            
        logger.info("Creando tipo de usuario");
        return logger.exit(tipoUsuarioDAO.create(tipoUsuarioCrear));
    }

    /**
     * Metodo que valida el formulario para crear/editar tipos de usuarios
     *
     * @param tipoUsuario
     */
    private void validarTipoUsuario(TiposUsuarios tipoUsuario) throws CloudBankException {

        logger.entry();
        
        logger.info("Validando tipo de usuario");
        
        if (tipoUsuario.getTusuCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo")));
        }
        if (tipoUsuario.getTusuCodigo() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioMenorIgualCero")));
        }
        if (tipoUsuario.getTusuNombre() == null || tipoUsuario.getTusuNombre().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "descripcionTipoUsuarioNula")));
        }
        if (!UtilRegExp.isAlphanumeric(tipoUsuario.getTusuNombre())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "descripcionTipoUsuarioInvalida")));
        }
        
        logger.exit();
    }
}
