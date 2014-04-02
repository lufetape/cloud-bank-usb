package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.ITipoDocumentoDAO;
import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import javax.ejb.Stateless;

/**
 * Clase que implementa el acceso a datos para la entidad tipos de documentos
 *
 * @author Felipe
 */
@Stateless
public class TipoDocumentoDAO extends BaseJpaDAO<TiposDocumentos> implements ITipoDocumentoDAO {
    
    /**
     * Crea una instancia de acceso a datos para tipos de documentos
     */
    public TipoDocumentoDAO() {
        super(TiposDocumentos.class);
    }
}
