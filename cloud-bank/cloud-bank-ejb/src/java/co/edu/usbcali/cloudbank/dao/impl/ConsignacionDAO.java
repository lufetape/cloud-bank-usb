package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IConsignacionDAO;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad consignaciones
 *
 * @author Felipe
 */
@Stateless
public class ConsignacionDAO extends BaseJpaDAO<Consignaciones> implements IConsignacionDAO {
    
    private static final Logger logger = LogManager.getLogger(ConsignacionDAO.class);

    /**
     * Crea una instancia de acceso a datos para consignaciones
     */
    public ConsignacionDAO() {
        super(Consignaciones.class);
    }

    @Override
    public List<Consignaciones> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
