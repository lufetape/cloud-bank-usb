package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.dao.IRetiroDAO;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.RetirosPK;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.services.IRetiroService;
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
 * Clase que implementa los métodos que pueden ser realizados para los retiros
 *
 * @author Felipe
 */
@Stateless
public class RetiroService implements IRetiroService {

    @EJB
    private IUsuarioService usuarioService;

    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @EJB
    private ICuentaService cuentaService;

    @EJB
    private IRetiroDAO retiroDAO;

    @Override
    public List<Retiros> consultarPorFiltros(String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception {

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
        if (fechaInicial != null && fechaFinal != null) {
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

        logger.info("Consultando retiros");
        return logger.exit(retiroDAO.consultarPorFiltros(numero, fechaInicial, fechaFinal));
    }

    @Override
    public Retiros retirar(Retiros retiro) throws CloudBankException, Exception {

        logger.entry();
        
        //Se validan los datos de entrada
        verificarRetiro(retiro);

        //Se hidrata el objeto con los nuevos valores    
        logger.info("Consultando ultimo retiro");
        Retiros ultimoRetiro = retiroDAO.consultarUltimoRetiro();

        logger.info("Hidratando objeto de retiro");
        Retiros retiroCrear = new Retiros();
        retiroCrear.setRetirosPK(new RetirosPK(ultimoRetiro.getRetirosPK().getRetCodigo() + 1, retiro.getRetirosPK().getCueNumero()));
        retiroCrear.setRetDescripcion(retiro.getRetDescripcion());
        retiroCrear.setRetFecha(Calendar.getInstance().getTime());
        retiroCrear.setRetValor(retiro.getRetValor());
        retiroCrear.setCuentas(retiro.getCuentas());
        retiroCrear.setUsuCedula(usuarioService.consultarPorId(retiro.getUsuCedula().getUsuCedula()));
        //Seteando la descripcion:
        if (retiro.getRetDescripcion() == null) {
            retiroCrear.setRetDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "retiroNormal"));
        }

        //Modificando la cuenta:
        retiroCrear.getCuentas().setCueSaldo(new BigDecimal(retiroCrear.getCuentas().getCueSaldo().doubleValue() - retiroCrear.getRetValor().doubleValue()));

        logger.info("Actualizando cuenta");
        cuentaService.modificar(retiroCrear.getCuentas());

        logger.info("Creando registro de retiro");
        return logger.exit(retiroDAO.create(retiroCrear));
    }

    @Override
    public void verificarRetiro(Retiros retiro) throws CloudBankException, Exception {

        logger.entry();
        
        logger.info("Verificando retiro");
        
        if (retiro.getRetirosPK().getCueNumero() == null || retiro.getRetirosPK().getCueNumero().equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "numeroCuentaNulo")));
        }
        String claveCuenta = retiro.getCuentas().getCueClave();
        retiro.setCuentas(cuentaService.consultarPorNumero(retiro.getRetirosPK().getCueNumero()));
        if (retiro.getCuentas() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaOrigenNoExiste")));
        }
        if (!retiro.getCuentas().getCueClave().equals(claveCuenta)) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "claveInvalida")));
        }
        switch (retiro.getCuentas().getCueActiva()) {
            case "N":
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaOrigenInactiva")));
            case "R":
                throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "cuentaOrigenRetirada")));
        }
        if (retiro.getRetValor() == null || retiro.getRetValor().doubleValue() <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "valorNoPermitidoRetirar")));
        }
        if (retiro.getRetValor().doubleValue() > retiro.getCuentas().getCueSaldo().doubleValue()) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSACCION, "fondosInsuficientes")));
        }
        logger.exit();
    }
}
