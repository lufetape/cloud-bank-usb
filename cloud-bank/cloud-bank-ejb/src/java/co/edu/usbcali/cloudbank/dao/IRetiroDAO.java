package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.Retiros;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de un retiro y
 * hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface IRetiroDAO extends IBaseDAO<Retiros> {

    /**
     * Método que consulta retiros de una cuenta por diferenres filtros
     *
     * @param numero Numero de cuenta
     * @param fechaInicial Fecha Inicial de consulta
     * @param fechaFinal Fecha Final de consulta
     * @return Listado de retiros encontrados
     * @throws java.lang.Exception
     */
    public List<Retiros> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws Exception;

}
