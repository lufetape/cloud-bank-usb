package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.ITipoDocumentoDAO;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.cloudbank.services.ITipoDocumentoService;
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
 * documento
 *
 * @author Felipe
 */
@Stateless
public class TipoDocumentoService implements ITipoDocumentoService {
    
    private static final Logger logger = LogManager.getLogger(TipoDocumentoService.class);

    @EJB
    private ITipoDocumentoDAO tipoDocumentoDAO;

    @Override
    public List<TiposDocumentos> consultarTodos() throws CloudBankException, Exception {

        return tipoDocumentoDAO.findAll();
    }

    @Override
    public TiposDocumentos consultarPorId(Long id) throws CloudBankException, Exception {

        if (id == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo"));
        }

        return tipoDocumentoDAO.findById(id);
    }

    @Override
    public void eliminar(Long id) throws Exception {

        if (id == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo"));
        }

        tipoDocumentoDAO.remove(new TiposDocumentos(), id);
    }

    @Override
    public void modificar(Long id, String descripcion) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarTipoDocumento(id, descripcion);

        //Se consulta el tipo de documento
        TiposDocumentos tipoDocumentoModificar = consultarPorId(id);

        //Se hidrata el objeto con los nuevos valores
        tipoDocumentoModificar.setTdocNombre(descripcion.trim().toUpperCase());

        //Se realiza la actualizacion            
        tipoDocumentoDAO.modify(tipoDocumentoModificar);
    }

    @Override
    public TiposDocumentos crear(Long id, String descripcion) throws CloudBankException, Exception {

        //Se validan los datos de entrada
        validarTipoDocumento(id, descripcion);

        //Se verifica si el tipo de documento ya existe
        if (consultarPorId(id) != null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "yaExiste"));
        }

        //Se hidrata el objeto con los nuevos valores  
        TiposDocumentos tipoDocumentoCrear = new TiposDocumentos();
        tipoDocumentoCrear.setTdocCodigo(id);
        tipoDocumentoCrear.setTdocNombre(descripcion.trim().toUpperCase());

        //Se realiza la creacion            
        return tipoDocumentoDAO.create(tipoDocumentoCrear);
    }

    /**
     * Metodo que valida el formulario para crear/editar tipos de documentos
     *
     * @param id
     * @param descripcion
     */
    private void validarTipoDocumento(Long id, String descripcion) throws CloudBankException {

        if (id == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo"));
        }
        if (id <= 0) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoMenorIgualCero"));
        }
        if (descripcion == null) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "descripcionTipoDocumentoNula"));
        }
        if (descripcion.trim().equals("")) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "descripcionTipoDocumentoVacia"));
        }
        if (!UtilRegExp.isAlphanumeric(descripcion)) {
            throw new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "descripcionTipoDocumentoInvalida"));
        }
    }
}
