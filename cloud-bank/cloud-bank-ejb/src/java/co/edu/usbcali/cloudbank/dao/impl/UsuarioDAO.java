package co.edu.usbcali.cloudbank.dao.impl;

import co.edu.usbcali.cloudbank.dao.IUsuarioDAO;
import co.edu.usbcali.cloudbank.model.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa el acceso a datos para la entidad usuarios
 *
 * @author Felipe
 */
@Stateless
public class UsuarioDAO extends BaseJpaDAO<Usuarios> implements IUsuarioDAO {

    private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);

    /**
     * Crea una instancia de acceso a datos para usuarios
     */
    public UsuarioDAO() {
        super(Usuarios.class);
    }

    @Override
    public List<Usuarios> consultarPorFiltros(Long codigo, Long idTipoUsuario, String login, String nombre) throws Exception {

        logger.entry();
        
        //Se construye el query por palabra clave
        StringBuilder consultaQuery = new StringBuilder();
        consultaQuery.append("SELECT u ");
        consultaQuery.append("FROM   Usuarios u ");
        consultaQuery.append("JOIN   u.tusuCodigo tu ");
        consultaQuery.append("WHERE  1 = 1 ");
        if (codigo != null) {
            consultaQuery.append("AND  u.usuCedula = :prmCodigo ");
        }
        if (idTipoUsuario != null) {
            consultaQuery.append("AND  tu.tusuCodigo = :prmTipoUsuario ");
        }
        if (login != null) {
            consultaQuery.append("AND  UPPER(u.usuLogin) LIKE :prmLogin ");
        }
        if (nombre != null) {
            consultaQuery.append("AND  UPPER(u.usuNombre) LIKE :prmNombre ");
        }

        //Se parametriza el query
        query = consultaQuery.toString();
        if (codigo != null) {
            parametros.put("prmCodigo", codigo);
        }
        if (idTipoUsuario != null) {
            parametros.put("prmTipoUsuario", idTipoUsuario);
        }
        if (login != null) {
            parametros.put("prmLogin", "%" + login.toUpperCase() + "%");
        }
        if (nombre != null) {
            parametros.put("prmNombre", "%" + nombre.toUpperCase() + "%");
        }

        //Se retorna la consulta
        return logger.exit(find());
    }

    @Override
    public Usuarios consultarPorLogin(String login) throws Exception {
        
        logger.entry();

        //Se parametriza el named query
        query = "Usuarios.findByUsuLogin";
        parametros.put("usuLogin", login);
        
        Usuarios usuario = null;
        List<Usuarios> usuarios = findNamedQuery();
        if(!usuarios.isEmpty()){
            usuario = usuarios.get(0);
        }

        //Se retorna la consulta
        return logger.exit(usuario);        
    }
}
