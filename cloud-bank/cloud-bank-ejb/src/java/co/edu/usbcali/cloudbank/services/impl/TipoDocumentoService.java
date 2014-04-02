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

        logger.entry();
        
        return logger.exit(tipoDocumentoDAO.findAll());
    }

    @Override
    public TiposDocumentos consultarPorId(Long id) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando id");
        
        if (id == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo")));
        }

        return logger.exit(tipoDocumentoDAO.findById(id));
    }

    @Override
    public void eliminar(TiposDocumentos tipoDocumento) throws Exception {

        logger.entry();
        
        logger.info("Validando tipo de documento");
        if (tipoDocumento.getTdocCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo")));
        }

        tipoDocumentoDAO.remove(new TiposDocumentos(), tipoDocumento.getTdocCodigo());
        logger.exit();
    }

    @Override
    public void modificar(TiposDocumentos tipoDocumento) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarTipoDocumento(tipoDocumento);

        //Se consulta el tipo de documento
        logger.info("Consultando tipo de documento");
        TiposDocumentos tipoDocumentoModificar = consultarPorId(tipoDocumento.getTdocCodigo());

        //Se hidrata el objeto con los nuevos valores
        logger.info("Hidratando tipo de documento");
        tipoDocumentoModificar.setTdocNombre(tipoDocumento.getTdocNombre().trim().toUpperCase());

        //Se realiza la actualizacion   
        logger.info("Actualizando tipo de documento");
        tipoDocumentoDAO.modify(tipoDocumentoModificar);
        
        logger.exit();
    }

    @Override
    public TiposDocumentos crear(TiposDocumentos tipoDocumento) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        validarTipoDocumento(tipoDocumento);
        
        logger.info("Validando si tipo de documento ya existe");

        //Se verifica si el tipo de documento ya existe
        if (consultarPorId(tipoDocumento.getTdocCodigo()) != null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "yaExiste")));
        }

        //Se hidrata el objeto con los nuevos valores 
        logger.info("Hidratando tipo de documento");
        TiposDocumentos tipoDocumentoCrear = new TiposDocumentos();
        tipoDocumentoCrear.setTdocCodigo(tipoDocumento.getTdocCodigo());
        tipoDocumentoCrear.setTdocNombre(tipoDocumento.getTdocNombre().trim().toUpperCase());

        //Se realiza la creacion  
        logger.info("Creando tipo de documento");
        return logger.exit(tipoDocumentoDAO.create(tipoDocumentoCrear));
    }

    /**
     * Metodo que valida el formulario para crear/editar tipos de documentos
     *
     * @param tipoDocumento
     */
    private void validarTipoDocumento(TiposDocumentos tipoDocumento) throws CloudBankException {

        logger.entry();
        
        logger.info("Validando tipo de documento");
        
        if (tipoDocumento.getTdocCodigo() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoNulo")));
        }
        if (tipoDocumento.getTdocCodigo() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "idTipoDocumentoMenorIgualCero")));
        }
        if (tipoDocumento.getTdocNombre() == null || tipoDocumento.getTdocNombre().trim().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "descripcionTipoDocumentoNula")));
        }
        if (!UtilRegExp.isAlphanumeric(tipoDocumento.getTdocNombre())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "descripcionTipoDocumentoInvalida")));
        }
        
        logger.exit();
    }
}
