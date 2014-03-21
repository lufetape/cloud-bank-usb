package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IRetiroDAO;
import co.edu.usbcali.cloudbank.model.Retiros;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad retiros
 *
 * @author Felipe
 */
@Stateless
public class RetiroDAO extends BaseJpaDAO<Retiros> implements IRetiroDAO {
    
    private static final Logger logger = LogManager.getLogger(RetiroDAO.class);

    /**
     * Crea una instancia de acceso a datos para retiros
     */
    public RetiroDAO() {
        super(Retiros.class);
    }

    @Override
    public List<Retiros> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
