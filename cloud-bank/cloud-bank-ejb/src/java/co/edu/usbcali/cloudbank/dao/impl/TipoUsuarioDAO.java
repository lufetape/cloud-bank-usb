package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.ITipoUsuarioDAO;
import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import javax.ejb.Stateless;

/**
 * Clase que implementa el acceso a datos para la entidad tipos de usuarios
 *
 * @author Felipe
 */
@Stateless
public class TipoUsuarioDAO extends BaseJpaDAO<TiposUsuarios> implements ITipoUsuarioDAO {
    
    /**
     * Crea una instancia de acceso a datos para tipos de documentos
     */
    public TipoUsuarioDAO() {
        super(TiposUsuarios.class);
    }
}
