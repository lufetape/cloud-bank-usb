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

        return tipoUsuarioDAO.findAll();
    }

    @Override
    public TiposUsuarios consultarPorId(Long id) throws CloudBankException, Exception {

        if (id == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo"));
        }

        return tipoUsuarioDAO.findById(id);
    }

    @Override
    public void eliminar(TiposUsuarios tipoUsuario) throws Exception {

        if (tipoUsuario.getTusuCodigo() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo"));
        }

        tipoUsuarioDAO.remove(new TiposUsuarios(), tipoUsuario.getTusuCodigo());
    }

    @Override
    public void modificar(TiposUsuarios tipoUsuario) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarTipoUsuario(tipoUsuario);

        //Se consulta el tipo de usuario
        TiposUsuarios tipoUsuarioModificar = consultarPorId(tipoUsuario.getTusuCodigo());

        //Se hidrata el objeto con los nuevos valores
        tipoUsuarioModificar.setTusuNombre(tipoUsuario.getTusuNombre().trim().toUpperCase());

        //Se realiza la actualizacion            
        tipoUsuarioDAO.modify(tipoUsuarioModificar);
    }

    @Override
    public TiposUsuarios crear(TiposUsuarios tipoUsuario) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarTipoUsuario(tipoUsuario);

        //Se verifica si el tipo de usuario ya existe
        if (consultarPorId(tipoUsuario.getTusuCodigo()) != null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "yaExiste"));
        }

        //Se hidrata el objeto con los nuevos valores  
        TiposUsuarios tipoUsuarioCrear = new TiposUsuarios();
        tipoUsuarioCrear.setTusuCodigo(tipoUsuario.getTusuCodigo());
        tipoUsuarioCrear.setTusuNombre(tipoUsuario.getTusuNombre().trim().toUpperCase());

        //Se realiza la creacion            
        return tipoUsuarioDAO.create(tipoUsuarioCrear);
    }

    /**
     * Metodo que valida el formulario para crear/editar tipos de usuarios
     *
     * @param tipoUsuario
     */
    private void validarTipoUsuario(TiposUsuarios tipoUsuario) throws CloudBankException {

        if (tipoUsuario.getTusuCodigo() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioNulo"));
        }
        if (tipoUsuario.getTusuCodigo() <= 0) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "idTipoUsuarioMenorIgualCero"));
        }
        if (tipoUsuario.getTusuNombre() == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "descripcionTipoUsuarioNula"));
        }
        if (tipoUsuario.getTusuNombre().trim().equals("")) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "descripcionTipoUsuarioVacia"));
        }
        if (!UtilRegExp.isAlphanumeric(tipoUsuario.getTusuNombre())) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "descripcionTipoUsuarioInvalida"));
        }
    }
}
