package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.services.IConsignacionService;
import co.edu.usbcali.cloudbank.services.IRetiroService;
import co.edu.usbcali.cloudbank.services.ITransferenciaService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa los métodos que pueden ser realizados para las
 * transferencia
 *
 * @author Felipe
 */
@Stateless
public class TransferenciaService implements ITransferenciaService {

    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @EJB
    private IRetiroService retiroService;
    
    @EJB
    private IConsignacionService consignacionService;

    @Override
    public void transferir(Consignaciones consignacion, Retiros retiro) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        verificarTransferencia(consignacion, retiro);

        //Se consigna:
        consignacion.setConDescripcion(String.format(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "consignacionTransferencia"), retiro.getRetirosPK().getCueNumero()));
        consignacionService.consignar(consignacion);
        
        //Se retira:
        retiro.setRetDescripcion(String.format(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "retiroTransferencia"), consignacion.getConsignacionesPK().getCueNumero()));
        retiroService.retirar(retiro);
        
        logger.exit();
    }

    @Override
    public void verificarTransferencia(Consignaciones consignacion, Retiros retiro) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Verificando si cuentas son iguales");
        
        if (consignacion.getConsignacionesPK().getCueNumero().equals(retiro.getRetirosPK().getCueNumero())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "origenIgualDestino")));
        }
        
        //Se verifica la consignacion
        consignacionService.verificarConsignacion(consignacion);
        
        //Se verifica el retiro
        retiroService.verificarRetiro(retiro);
        
        logger.exit();
    }
}
