package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para un
 * usuario
 *
 * @author Felipe
 */
@Local
public interface IUsuarioService {

    /**
     * Método que permite listar todos los usuarios
     *
     * @return Listado de usuarios
     * @throws java.lang.Exception
     */
    public List<Usuarios> consultarTodos() throws Exception;

    /**
     * Método que consulta un usuario por código
     *
     * @param codigo
     * @return Usuario encontrado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Usuarios consultarPorId(Long codigo) throws CloudBankException, Exception;

    /**
     * Método que consulta usuarios por diferentes filtros
     *
     * @param codigo
     * @param idTipoUsuario
     * @param nombre
     * @param login
     * @return Listado de usuarios encontrados
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<Usuarios> consultarPorFiltros(Long codigo, Long idTipoUsuario, String login, String nombre) throws CloudBankException, Exception;

    /**
     * Método que crea un usuario
     *
     * @param usuario
     * @return Usuario creado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public Usuarios crear(Usuarios usuario) throws CloudBankException, Exception;

    /**
     * Método que modifica un usuario
     *
     * @param usuario
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void modificar(Usuarios usuario) throws CloudBankException, Exception;

    /**
     * Método que elimina un usuario
     *
     * @param usuario
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void eliminar(Usuarios usuario) throws CloudBankException, Exception;
}
