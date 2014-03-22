package co.edu.usbcali.cloudbank.services;

import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface local que abstrae los métodos que pueden ser realizados para un
 * tipo de documento
 *
 * @author Felipe
 */
@Local
public interface ITipoDocumentoService {

    /**
     * Método que permite listar todos los tipos de documento
     *
     * @return Listado de tipos de documentos
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public List<TiposDocumentos> consultarTodos() throws CloudBankException, Exception;

    /**
     * Método que consulta un tipo de documento por id
     *
     * @param id Identificación del Cliente
     * @return Tipo de documento encontrado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public TiposDocumentos consultarPorId(Long id) throws CloudBankException, Exception;

    /**
     * Método que crea un tipo de documento
     *
     * @param tipoDocumento
     * @return Tipo de documento creado
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public TiposDocumentos crear(TiposDocumentos tipoDocumento) throws CloudBankException, Exception;

    /**
     * Método que modifica un tipo de documento
     * 
     * @param tipoDocumento
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void modificar(TiposDocumentos tipoDocumento) throws CloudBankException, Exception;

    /**
     * Método que elimina un tipo de documento
     *
     * @param tipoDocumento
     * @throws co.edu.usbcali.cloudbank.util.CloudBankException
     * @throws java.lang.Exception
     */
    public void eliminar(TiposDocumentos tipoDocumento) throws CloudBankException, Exception;
}
