package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.ITipoUsuarioDAO;
import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad tipos de usuarios
 *
 * @author Felipe
 */
@Stateless
public class TipoUsuarioDAO extends BaseJpaDAO<TiposUsuarios> implements ITipoUsuarioDAO {
    
    private static final Logger logger = LogManager.getLogger(TipoUsuarioDAO.class);

    /**
     * Crea una instancia de acceso a datos para tipos de documentos
     */
    public TipoUsuarioDAO() {
        super(TiposUsuarios.class);
    }
}
