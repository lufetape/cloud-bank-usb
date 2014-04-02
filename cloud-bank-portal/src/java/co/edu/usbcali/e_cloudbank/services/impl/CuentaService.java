/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.services.impl;

import co.edu.usbcali.e_cloudbank.services.ICuentaService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.utils.UtilDate;
import co.edu.usbcali.e_cloudbank.wsclient.ClienteWSCuentaSingleton;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaCuentasDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaMovimientosDTO;
import java.util.Date;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion de ICuentaService a traves de servicios web
 *
 * @author Felipe
 */
@Stateless
public class CuentaService implements ICuentaService {
    
    private static final Logger logger = LogManager.getLogger(CuentaService.class);

    @Override
    public RespuestaConsultaCuentasDTO listarCuentas(long id) throws CloudBankException, Exception {
        
        logger.entry();
        
        //Consumo del Web Service:
        logger.info("Consultando cuentas del cliente via servicio web");
        RespuestaConsultaCuentasDTO respuestaConsultaCuentasDTO = ClienteWSCuentaSingleton.getInstance().consultarPorCliente(id);
        if (respuestaConsultaCuentasDTO.getEstado().isExitoso()) {
            return logger.exit(respuestaConsultaCuentasDTO);
        } else {
            throw logger.throwing(new CloudBankException(respuestaConsultaCuentasDTO.getEstado().getDescripcion()));
        }
    }

    @Override
    public RespuestaConsultaMovimientosDTO listarMovimientos(long id, String numero, Date fechaInicial, Date fechaFinal) throws CloudBankException, Exception {
        
        logger.entry();
        
        //Fechas en el formato para el servicio web:
        logger.info("Ajustando fechas para el consumo");
        String fechaInicialString = null;
        if(fechaInicial != null){
            fechaInicialString = UtilDate.getFormattedDate(fechaInicial, "dd/MM/yyyy");
        }

        String fechaFinalString = null;
        if(fechaFinal != null){
            fechaFinalString = UtilDate.getFormattedDate(fechaFinal, "dd/MM/yyyy");
        }
        
        //Consumo del Web Service:
        logger.info("Consultando movimientos de la cuenta del cliente via servicio web");
        RespuestaConsultaMovimientosDTO respuestaConsultaMovimientosDTO = ClienteWSCuentaSingleton.getInstance().consultarMovimientos(id, numero, fechaInicialString, fechaFinalString);
        if (respuestaConsultaMovimientosDTO.getEstado().isExitoso()) {
            return logger.exit(respuestaConsultaMovimientosDTO);
        } else {
            throw logger.throwing(new CloudBankException(respuestaConsultaMovimientosDTO.getEstado().getDescripcion()));
        }
    }
}
