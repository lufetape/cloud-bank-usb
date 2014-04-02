package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IClienteDAO;
import co.edu.usbcali.cloudbank.model.Clientes;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad clientes
 *
 * @author Felipe
 */
@Stateless
public class ClienteDAO extends BaseJpaDAO<Clientes> implements IClienteDAO {

    private static final Logger logger = LogManager.getLogger(ClienteDAO.class);

    /**
     * Crea una instancia de acceso a datos para clientes
     */
    public ClienteDAO() {
        super(Clientes.class);
    }

    @Override
    public List<Clientes> consultarPorPalabraClave(String palabraClave) throws Exception {

        logger.entry();

        //Se construye el query por palabra clave
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Clientes c ");
        consultaQuery.append("WHERE  CONCAT(c.cliId,'') LIKE :prmId ");
        consultaQuery.append("OR     UPPER(c.cliNombre) LIKE :prmNombre ");

        //Se parametriza el query
        query = consultaQuery.toString();
        parametros.put("prmId", "%" + palabraClave + "%");
        parametros.put("prmNombre", "%" + palabraClave.toUpperCase() + "%");

        //Se retorna la consulta
        return logger.exit(find());
    }

    @Override
    public List<Clientes> consultarPorFiltros(Long id, Long idTipoDocumento, String nombre) throws Exception {

        logger.entry();
        //Se construye el query por palabra clave
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT c ");
        consultaQuery.append("FROM   Clientes c ");
        consultaQuery.append("JOIN   c.tdocCodigo td ");
        consultaQuery.append("WHERE  1 = 1 ");
        if (id != null) {
            consultaQuery.append("AND  c.cliId = :prmId ");
        }
        if (idTipoDocumento != null) {
            consultaQuery.append("AND  td.tdocCodigo = :prmTipoDocumento ");
        }
        if (nombre != null) {
            consultaQuery.append("AND  UPPER(c.cliNombre) LIKE :prmNombre ");
        }

        //Se parametriza el query
        query = consultaQuery.toString();
        if (id != null) {
            parametros.put("prmId", id);
        }
        if (idTipoDocumento != null) {
            parametros.put("prmTipoDocumento", idTipoDocumento);
        }
        if (nombre != null) {
            parametros.put("prmNombre", "%" + nombre.toUpperCase() + "%");
        }

        //Se retorna la consulta
        return logger.exit(find());
    }
}
