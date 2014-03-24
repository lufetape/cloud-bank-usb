package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para un
 * tipo de usuario
 *
 * @author Felipe
 */
@Local
public interface ITipoUsuarioService {

    /**
     * Método que permite listar todos los tipos de usuario
     *
     * @return Listado de tipos de usuarios
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<TiposUsuarios> consultarTodos() throws CloudBankException, Exception;

    /**
     * Método que consulta un tipo de usuario por id
     *
     * @param id
     * @return Tipo de documento encontrado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public TiposUsuarios consultarPorId(Long id) throws CloudBankException, Exception;

    /**
     * Método que crea un tipo de usuario
     *
     * @param tipoUsuario
     * @return Tipo de documento creado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public TiposUsuarios crear(TiposUsuarios tipoUsuario) throws CloudBankException, Exception;

    /**
     * Método que modifica un tipo de usuario
     * 
     * @param tipoUsuario
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void modificar(TiposUsuarios tipoUsuario) throws CloudBankException, Exception;

    /**
     * Método que elimina un tipo de usuario
     *
     * @param tipoUsuario
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void eliminar(TiposUsuarios tipoUsuario) throws CloudBankException, Exception;
}
