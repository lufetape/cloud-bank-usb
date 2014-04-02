package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.ICuentaDAO;
import co.edu.usbcali.cloudbank.model.Cuentas;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad cuentas
 *
 * @author Felipe
 */
@Stateless
public class CuentaDAO extends BaseJpaDAO<Cuentas> implements ICuentaDAO {

    private static final Logger logger = LogManager.getLogger(CuentaDAO.class);

    /**
     * Crea una instancia de acceso a datos para cuentas
     */
    public CuentaDAO() {
        super(Cuentas.class);
    }

    @Override
    public List<Cuentas> consultarPorCliente(Long idCliente) throws Exception {

        logger.entry();

        //Se construye el query por palabra clave
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Cuentas c ");
        consultaQuery.append("JOIN   c.cliId cl ");
        consultaQuery.append("WHERE  cl.cliId = :prmIdCliente ");

        //Se parametriza el query
        query = consultaQuery.toString();
        parametros.put("prmIdCliente", idCliente);

        //Se retorna la consulta
        return logger.exit(find());
    }

    @Override
    public List<Cuentas> consultarPorFiltros(String numero, Long idCliente, String cuentaActiva) throws Exception {

        logger.entry();

        //Se construye el query por palabra clave
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Cuentas c ");
        consultaQuery.append("JOIN   c.cliId cl ");
        consultaQuery.append("WHERE  1 = 1 ");
        if (numero != null) {
            consultaQuery.append("AND  c.cueNumero = :prmNumero ");
        }
        if (idCliente != null) {
            consultaQuery.append("AND  cl.cliId = :prmIdCliente ");
        }
        if (cuentaActiva != null) {
            consultaQuery.append("AND  UPPER(c.cueActiva) = :prmCuentaActiva ");
        }

        //Se parametriza el query
        query = consultaQuery.toString();
        if (numero != null) {
            parametros.put("prmNumero", numero);
        }
        if (idCliente != null) {
            parametros.put("prmIdCliente", idCliente);
        }
        if (cuentaActiva != null) {
            parametros.put("prmCuentaActiva", cuentaActiva.toUpperCase());
        }

        //Se retorna la consulta
        return logger.exit(find());
    }

    @Override
    public Cuentas consultarUltimaCuenta() throws Exception {

        logger.entry();

        //Se construye el query de ultima cuenta
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Cuentas c ");
        consultaQuery.append("WHERE  c.cueNumero = (SELECT MAX(c.cueNumero) FROM Cuentas c) ");
        query = consultaQuery.toString();

        //Se retorna la consulta
        List<Cuentas> cuentas = find();
        if (cuentas.isEmpty()) {
            return logger.exit(new Cuentas("0000-0000-0000"));
        }
        return logger.exit(cuentas.get(0));
    }
}
