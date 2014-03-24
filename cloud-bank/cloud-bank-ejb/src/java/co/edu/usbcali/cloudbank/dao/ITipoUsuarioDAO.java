package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de un tipo de
 * usuario y hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface ITipoUsuarioDAO extends IBaseDAO<TiposUsuarios> {

}
