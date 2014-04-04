package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.IConsignacionDAO;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.services.IConsignacionService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.services.IUsuarioService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que implementa los métodos que pueden ser realizados para las
 * consignaciones
 *
 * @author Felipe
 */
@Stateless
public class ConsignacionService implements IConsignacionService {
    
    @EJB
    private IUsuarioService usuarioService;

    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @EJB
    private ICuentaService cuentaService;

    @EJB
    private IConsignacionDAO consignacionDAO;

    @Override
    public List<Consignaciones> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Validando entradas");
        if (numero == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo")));
        }
        if (fechaInicial != null || fechaFinal != null) {
            if (fechaInicial == null) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "fechaInicialNula")));
            }
            if (fechaFinal == null) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "fechaFinalNula")));
            }
            if (fechaInicial.after(fechaFinal)) {
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "fechaInicialMayorFechaFinal")));
            }
        }
        
        logger.info("Ajustando fechas para consulta");
        if(fechaInicial != null && fechaFinal != null){
            //Ajustando las fechas
            GregorianCalendar gcInicial = new GregorianCalendar();
            gcInicial.setTime(fechaInicial);
            gcInicial.set(Calendar.HOUR, 0);
            gcInicial.set(Calendar.MINUTE, 0);
            gcInicial.set(Calendar.SECOND, 0);
            gcInicial.set(Calendar.MILLISECOND, 0);
            fechaInicial = gcInicial.getTime();

            GregorianCalendar gcFinal = new GregorianCalendar();
            gcFinal.setTime(fechaFinal);
            gcFinal.set(Calendar.DATE, gcFinal.get(Calendar.DATE) + 1);
            gcFinal.set(Calendar.HOUR, 0);
            gcFinal.set(Calendar.MINUTE, 0);
            gcFinal.set(Calendar.SECOND, 0);
            gcFinal.set(Calendar.MILLISECOND, 0);
            fechaFinal = gcFinal.getTime();
        }
        
        logger.info("Consultando consignaciones");

        return logger.exit(consignacionDAO.consultarPorFiltros(numero, fechaInicial, fechaFinal));

    }

    @Override
    public Consignaciones consignar(Consignaciones consignacion) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        verificarConsignacion(consignacion);
        
        //Se hidrata el objeto con los nuevos valores    
        logger.info("Consultando ultima consignacion");
        Consignaciones ultimaConsignacion = consignacionDAO.consultarUltimaConsignacion();
        
        logger.info("Hidratando objeto de consignacion");
        Consignaciones consignacionCrear = new Consignaciones();
        consignacionCrear.setConsignacionesPK(new ConsignacionesPK(ultimaConsignacion.getConsignacionesPK().getConCodigo()+1, consignacion.getConsignacionesPK().getCueNumero()));
        consignacionCrear.setConDescripcion(consignacion.getConDescripcion());
        consignacionCrear.setConFecha(Calendar.getInstance().getTime());
        consignacionCrear.setConValor(consignacion.getConValor());
        consignacionCrear.setCuentas(consignacion.getCuentas());
        consignacionCrear.setUsuCedula(usuarioService.consultarPorId(consignacion.getUsuCedula().getUsuCedula()));
        //Seteando la descripcion y modificando la cuenta:
        if (consignacionCrear.getCuentas().getCueActiva().equals("N") && consignacionCrear.getCuentas().getCueSaldo().doubleValue() == 0) {
            if(consignacion.getConDescripcion() == null){
                consignacionCrear.setConDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "consignacionApertura"));
            }
            consignacionCrear.getCuentas().setCueActiva("S"); //Dando apertura a la cuenta
        } else {
            if(consignacion.getConDescripcion() == null){
                consignacionCrear.setConDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "consignacionNormal"));
            }
        }
        consignacionCrear.getCuentas().setCueSaldo(new BigDecimal(consignacionCrear.getCuentas().getCueSaldo().doubleValue() + consignacionCrear.getConValor().doubleValue()));

        logger.info("Actualizando cuenta");
        cuentaService.modificar(consignacionCrear.getCuentas());

        logger.info("Creando registro de consignacion");
        return logger.exit(consignacionDAO.create(consignacionCrear));
    }

    @Override
    public void verificarConsignacion(Consignaciones consignacion) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Verificando consignacion");
        
        if (consignacion.getConsignacionesPK().getCueNumero() == null || consignacion.getConsignacionesPK().getCueNumero().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo")));
        }
        consignacion.setCuentas(cuentaService.consultarPorNumero(consignacion.getConsignacionesPK().getCueNumero()));
        if (consignacion.getCuentas() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaDestinoNoExiste")));
        }
        switch (consignacion.getCuentas().getCueActiva()) {
            case "N":
                if (consignacion.getCuentas().getCueSaldo().doubleValue() == 0 && consignacion.getConValor().doubleValue() < 100000) {
                    throw logger.throwing(new CloudBankException(String.format(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "valorMenorQueMinimo"), 100000)));
                } else if (consignacion.getCuentas().getCueSaldo().doubleValue() == 0 && consignacion.getConValor().doubleValue() >= 100000) {
                    break;
                } else {
                    throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaDestinoInactiva")));
                }                
            case "R":
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaDestinoRetirada")));
        }
        if (consignacion.getConValor() == null || consignacion.getConValor().doubleValue() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "valorNoPermitidoConsignar")));
        }
        logger.exit();
    }
}
