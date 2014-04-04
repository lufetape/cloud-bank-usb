package co.edu.usbcali.e_cloudbank.services;

import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.wsclient.EstadoDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaUsuarioDTO;
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
     * Método que permite consultar un usuario por Login
     *
     * @param login Identificación del cliente
     * @return Respuesta Consulta
     * @throws co.edu.usbcali.e_cloudbank.utils.CloudBankException
     * @throws java.lang.Exception
     */
    public RespuestaConsultaUsuarioDTO consultarUsuarioPorId(String login) throws CloudBankException, Exception;

    /**
     * Método que permite agregar un usuario
     *
     * @param identificacion
     * @param tipoUsuario
     * @param nombre
     * @param login
     * @return Respuesta Creacion
     * @throws co.edu.usbcali.e_cloudbank.utils.CloudBankException
     * @throws java.lang.Exception
     */
    public EstadoDTO agregarUsuario(long identificacion, long tipoUsuario, String nombre, String login) throws CloudBankException, Exception;

}
