package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.ITipoDocumentoDAO;
import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad tipos de documentos
 *
 * @author Felipe
 */
@Stateless
public class TipoDocumentoDAO extends BaseJpaDAO<TiposDocumentos> implements ITipoDocumentoDAO {
    
    private static final Logger logger = LogManager.getLogger(TipoDocumentoDAO.class);

    /**
     * Crea una instancia de acceso a datos para tipos de documentos
     */
    public TipoDocumentoDAO() {
        super(TiposDocumentos.class);
    }
}
