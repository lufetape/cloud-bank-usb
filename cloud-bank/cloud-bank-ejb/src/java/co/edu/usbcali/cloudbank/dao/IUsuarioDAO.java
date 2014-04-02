package co.edu.usbcali.cloudbank.dao;

import co.edu.usbcali.cloudbank.model.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae el comportamiento especifico de un usuario y
 * hereda el comportamiento base de una entidad
 *
 * @author Felipe
 */
@Local
public interface IUsuarioDAO extends IBaseDAO<Usuarios> {

    /**
     * Método que consulta un usuario por login
     *
     * @param login
     * @return Usuario encontrado
     * @throws java.lang.Exception
     */
    public Usuarios consultarPorLogin(String login) throws Exception;
    
    /**
     * Método que consulta usuarios por diferentes filtros de busqueda mezclados
     *
     * @param codigo
     * @param idTipoUsuario
     * @param login
     * @param nombre
     * @return Listado de usuarios encontrados
     * @throws java.lang.Exception
     */
    public List<Usuarios> consultarPorFiltros(Long codigo, Long idTipoUsuario, String login, String nombre) throws Exception;
}
