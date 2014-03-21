package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de un tipo de
 * documento y hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface ITipoDocumentoDAO extends IBaseDAO<TiposDocumentos> {

}
