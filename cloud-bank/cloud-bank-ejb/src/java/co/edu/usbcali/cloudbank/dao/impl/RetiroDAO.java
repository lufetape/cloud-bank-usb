package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IRetiroDAO;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.RetirosPK;
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

        logger.entry();

        //Se construye el query:
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT r ");
        consultaQuery.append("FROM   Retiros r ");
        consultaQuery.append("WHERE  r.retirosPK.cueNumero = :prmNumero ");
        if (fechaInicial != null && fechaFinal != null) {
            consultaQuery.append("AND    r.retFecha BETWEEN :prmFechaInicial AND :prmFechaFinal ");
        }

        query = consultaQuery.toString();
        parametros.put("prmNumero", numero);
        if (fechaInicial != null && fechaFinal != null) {
            parametros.put("prmFechaInicial", fechaInicial);
            parametros.put("prmFechaFinal", fechaFinal);
        }

        return logger.exit(find());
    }

    @Override
    public Retiros consultarUltimoRetiro() throws Exception {

        logger.entry();

        //Se construye el query de ultimo retiro
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT r ");
        consultaQuery.append("FROM   Retiros r ");
        consultaQuery.append("WHERE  r.retirosPK.retCodigo = (SELECT MAX(r.retirosPK.retCodigo) FROM Retiros r) ");
        query = consultaQuery.toString();

        //Se retorna la consulta
        List<Retiros> retiros = find();
        if (retiros.isEmpty()) {
            return logger.exit(new Retiros(new RetirosPK(0L, "")));
        }
        return logger.exit(retiros.get(0));
    }
}
