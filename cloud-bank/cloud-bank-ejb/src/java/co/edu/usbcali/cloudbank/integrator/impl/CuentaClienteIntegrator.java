/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.cloudbank.integrator.impl;

import co.edu.usbcali.cloudbank.dto.CuentaDTO;
import co.edu.usbcali.cloudbank.dto.EstadoDTO;
import co.edu.usbcali.cloudbank.dto.MovimientoDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaCuentasDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaMovimientosDTO;
import co.edu.usbcali.cloudbank.integrator.ICuentaClienteIntegrator;
import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.RetirosPK;
import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.IConsignacionService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.services.IRetiroService;
import co.edu.usbcali.cloudbank.services.ITransferenciaService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.cloudbank.util.Constantes;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.cloudbank.util.UtilBundle;
import co.edu.usbcali.cloudbank.util.UtilDate;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementación de las operaciones de integración para cuentas de clientes
 *
 * @author Felipe
 */
@Stateless
public class CuentaClienteIntegrator implements ICuentaClienteIntegrator {

    private static final Logger logger = LogManager.getLogger(CuentaClienteIntegrator.class);

    @EJB
    private IClienteService clienteService;

    @EJB
    private ICuentaService cuentaService;

    @EJB
    private IConsignacionService consignacionService;

    @EJB
    private IRetiroService retiroService;

    @EJB
    private ITransferenciaService transferenciaService;

    @Override
    public RespuestaConsultaCuentasDTO consultarPorCliente(long idCliente) {

        logger.entry();

        //Configuracion de la salida
        RespuestaConsultaCuentasDTO respuestaConsultaCuentasDTO = new RespuestaConsultaCuentasDTO();
        EstadoDTO estado = new EstadoDTO();
        respuestaConsultaCuentasDTO.setEstado(estado);

        //1. Se valida que el cliente exista:
        logger.info("Validando que cliente exista");
        try {
            if (!validarCliente(idCliente)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "clienteNoExiste"));
                return logger.exit(respuestaConsultaCuentasDTO);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(respuestaConsultaCuentasDTO);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorConsultandoCliente"));
            return logger.exit(respuestaConsultaCuentasDTO);
        }

        //2. Se consultan las cuentas del cliente:
        try {
            logger.info("Consultando cuentas del cliente");
            List<CuentaDTO> cuentaDTOs = new ArrayList<>();
            respuestaConsultaCuentasDTO.setCuentas(cuentaDTOs);
            List<Cuentas> cuentasCliente = cuentaService.consultarPorCliente(idCliente);
            for (Cuentas cuenta : cuentasCliente) {
                CuentaDTO cuentaDTO = new CuentaDTO();
                cuentaDTO.setNumero(cuenta.getCueNumero());
                cuentaDTO.setSaldo(cuenta.getCueSaldo().doubleValue());
                cuentaDTO.setEstado(cuenta.getCueActiva());
                cuentaDTOs.add(cuentaDTO);
            }

            estado.setExitoso(true);
            estado.setDescripcion(Constantes.OPERACION_EXITOSA);
            return logger.exit(respuestaConsultaCuentasDTO);

        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(respuestaConsultaCuentasDTO);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorListandoCuentas"));
            return logger.exit(respuestaConsultaCuentasDTO);
        }
    }

    @Override
    public RespuestaConsultaMovimientosDTO consultarMovimientos(long idCliente, String numero, String fechaInicial, String fechaFinal) {

        logger.entry();

        //Configuracion de la salida
        RespuestaConsultaMovimientosDTO respuestaConsultaMovimientosDTO = new RespuestaConsultaMovimientosDTO();
        EstadoDTO estado = new EstadoDTO();
        respuestaConsultaMovimientosDTO.setEstado(estado);

        Date dateInicial = null, dateFinal = null;

        //1. Se validan las fechas de entrada
        logger.info("Validando fecha inicial");
        if (fechaInicial != null) {
            try {
                dateInicial = UtilDate.getDate(fechaInicial, "dd/MM/yyyy");
            } catch (ParseException pe) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "fechaInicialFormatoInvalido"));
                return logger.exit(respuestaConsultaMovimientosDTO);
            }
        }
        logger.info("Validando fecha final");
        if (fechaFinal != null) {
            try {
                dateFinal = UtilDate.getDate(fechaFinal, "dd/MM/yyyy");
            } catch (ParseException pe) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "fechaFinalFormatoInvalido"));
                return logger.exit(respuestaConsultaMovimientosDTO);
            }
        }

        //2. Se valida que el cliente exista:
        logger.info("Validando que el cliente exista");
        try {
            if (!validarCliente(idCliente)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "clienteNoExiste"));
                return logger.exit(respuestaConsultaMovimientosDTO);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(respuestaConsultaMovimientosDTO);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorConsultandoCliente"));
            return logger.exit(respuestaConsultaMovimientosDTO);
        }

        //2. Se valida que la cuenta le pertenezca al cliente:
        logger.info("Validando que cuenta sea del cliente");
        try {
            if (!validarCuenta(idCliente, numero)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "cuentaDestinoNoEsDeCliente"));
                return logger.exit(respuestaConsultaMovimientosDTO);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(respuestaConsultaMovimientosDTO);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorVerificandoCuentaDestino"));
            return logger.exit(respuestaConsultaMovimientosDTO);
        }

        //3. Se consultan los movimientos de la cuenta
        logger.info("Consultando movimientos de la cuenta");
        try {
            //Consignaciones:
            logger.info("Consultando consignaciones");
            List<MovimientoDTO> consignacionesDTOs = new ArrayList<>();
            respuestaConsultaMovimientosDTO.setConsignaciones(consignacionesDTOs);
            List<Consignaciones> consignaciones = consignacionService.consultarPorFiltros(numero, dateInicial, dateFinal);
            for (Consignaciones consignacion : consignaciones) {
                MovimientoDTO movimientoDTO = new MovimientoDTO();
                movimientoDTO.setCodigo(consignacion.getConsignacionesPK().getConCodigo());
                movimientoDTO.setDescripcion(consignacion.getConDescripcion());
                movimientoDTO.setFecha(UtilDate.getFormattedDate(consignacion.getConFecha(), "yyyy-MM-dd hh:mm:ss"));
                movimientoDTO.setValor(consignacion.getConValor().doubleValue());
                consignacionesDTOs.add(movimientoDTO);
            }

            //Retiros:
            logger.info("Consultando retiros");
            List<MovimientoDTO> retirosDTOs = new ArrayList<>();
            respuestaConsultaMovimientosDTO.setRetiros(retirosDTOs);
            List<Retiros> retiros = retiroService.consultarPorFiltros(numero, dateInicial, dateFinal);
            for (Retiros retiro : retiros) {
                MovimientoDTO movimientoDTO = new MovimientoDTO();
                movimientoDTO.setCodigo(retiro.getRetirosPK().getRetCodigo());
                movimientoDTO.setDescripcion(retiro.getRetDescripcion());
                movimientoDTO.setFecha(UtilDate.getFormattedDate(retiro.getRetFecha(), "yyyy-MM-dd hh:mm:ss"));
                movimientoDTO.setValor(retiro.getRetValor().doubleValue());
                retirosDTOs.add(movimientoDTO);
            }

            estado.setExitoso(true);
            estado.setDescripcion(Constantes.OPERACION_EXITOSA);
            return logger.exit(respuestaConsultaMovimientosDTO);

        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(respuestaConsultaMovimientosDTO);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorListandoMovimientos"));
            return logger.exit(respuestaConsultaMovimientosDTO);
        }
    }

    @Override
    public EstadoDTO realizarTransferencia(long idCliente, String numeroOrigen, String claveOrigen, String numeroDestino, double valor) {

        logger.entry();

        //Configuracion de la salida
        EstadoDTO estado = new EstadoDTO();

        //1. Se valida que el cliente exista:
        logger.info("Validando que cliente exista");
        try {
            if (!validarCliente(idCliente)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "clienteNoExiste"));
                return logger.exit(estado);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(estado);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorConsultandoCliente"));
            return logger.exit(estado);
        }

        //2. Se valida que la cuenta origen le pertenezca al cliente:
        logger.info("Validando que cuenta origen sea del cliente");
        try {
            if (!validarCuenta(idCliente, numeroOrigen)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "cuentaOrigenNoEsDeCliente"));
                return logger.exit(estado);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(estado);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorVerificandoCuentaOrigen"));
            return logger.exit(estado);
        }

        //3. Se valida que la cuenta destino le pertenezca al cliente:
        logger.info("Validando que cliente destino sea del cliente");
        try {
            if (!validarCuenta(idCliente, numeroOrigen)) {
                estado.setExitoso(false);
                estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "cuentaDestinoNoEsDeCliente"));
                return logger.exit(estado);
            }
        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(estado);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorVerificandoCuentaDestino"));
            return logger.exit(estado);
        }

        //4. Se realiza la transferencia:
        logger.info("Realizando transferencia");
        try {

            //Hidratando objetos:
            //Consignacion:
            Consignaciones consignacion = new Consignaciones(new ConsignacionesPK(0, numeroDestino));
            consignacion.setConValor(new BigDecimal(valor));
            consignacion.setUsuCedula(new Usuarios(idCliente));
            consignacion.setCuentas(new Cuentas(numeroDestino));

            //Retiro:
            Retiros retiro = new Retiros(new RetirosPK(0, numeroOrigen));
            retiro.setRetValor(new BigDecimal(valor));
            retiro.setUsuCedula(new Usuarios(idCliente));
            retiro.setCuentas(new Cuentas(numeroOrigen));
            retiro.getCuentas().setCueClave(claveOrigen);

            transferenciaService.transferir(consignacion, retiro);

            estado.setExitoso(true);
            estado.setDescripcion(Constantes.OPERACION_EXITOSA);
            return logger.exit(estado);

        } catch (CloudBankException cbe) {
            estado.setExitoso(false);
            estado.setDescripcion(cbe.getMessage());
            return logger.exit(estado);
        } catch (Exception e) {
            estado.setExitoso(false);
            estado.setDescripcion(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.INTEGRADOR_CUENTA, "errorRealizandoTransferencia"));
            return logger.exit(estado);
        }
    }

    /**
     * Método que valida si un cliente existe
     */
    private boolean validarCliente(long idCliente) throws CloudBankException, Exception {

        logger.entry();

        Clientes cliente = clienteService.consultarPorId(idCliente);
        return logger.exit(cliente != null && cliente.getCliId() != null);
    }

    /**
     * Método que valida si una cuenta es de un cliente
     */
    private boolean validarCuenta(long idCliente, String numero) throws CloudBankException, Exception {

        logger.entry();

        List<Cuentas> cuentasCliente = cuentaService.consultarPorCliente(idCliente);
        for (Cuentas cuenta : cuentasCliente) {
            if (cuenta.getCueNumero().equals(numero)) {
                return logger.exit(true);
            }
        }
        return logger.exit(false);
    }
}
