package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de una consignacion
 * y hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface IConsignacionDAO extends IBaseDAO<Consignaciones> {

    /**
     * Método que consulta consignaciones de una cuenta por diferentes filtros
     *
     * @param numero Numero de cuenta
     * @param fechaInicial Fecha Inicial de consulta
     * @param fechaFinal Fecha Final de consulta
     * @return Listado de consignaciones encontradas
     * @throws java.lang.Exception
     */
    public List<Consignaciones> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws Exception;
    
    /**
     * Método que consulta la ultima consignacion realizada para ua cuenta
     *
     * @return Ultima cosignacion
     * @throws java.lang.Exception
     */
    public Consignaciones consultarUltimaConsignacion() throws Exception;

}
