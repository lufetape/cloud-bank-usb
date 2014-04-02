package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IConsignacionDAO;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
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

        logger.entry();

        //Se construye el query:
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Consignaciones c ");
        consultaQuery.append("WHERE  c.consignacionesPK.cueNumero = :prmNumero ");
        if (fechaInicial != null && fechaFinal != null) {
            consultaQuery.append("AND    c.conFecha BETWEEN :prmFechaInicial AND :prmFechaFinal ");
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
    public Consignaciones consultarUltimaConsignacion() throws Exception {

        logger.entry();

        //Se construye el query de ultima cuenta
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Consignaciones c ");
        consultaQuery.append("WHERE    c.consignacionesPK.conCodigo = (SELECT MAX(c.consignacionesPK.conCodigo) FROM Consignaciones c) ");
        query = consultaQuery.toString();

        //Se retorna la consulta
        List<Consignaciones> consignaciones = find();
        if (consignaciones.isEmpty()) {
            return logger.exit(new Consignaciones(new ConsignacionesPK(0L, "")));
        }
        return logger.exit(consignaciones.get(0));
    }
}
