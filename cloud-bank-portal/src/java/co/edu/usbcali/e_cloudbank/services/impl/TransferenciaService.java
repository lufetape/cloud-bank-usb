/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.services.impl;

import co.edu.usbcali.e_cloudbank.services.ITransferenciaService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import co.edu.usbcali.e_cloudbank.utils.UtilBundle;
import co.edu.usbcali.e_cloudbank.wsclient.ClienteWSCuentaSingleton;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import co.edu.usbcali.e_cloudbank.wsclient.EstadoDTO;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion de ITransferenciaService a traves de servicios web
 *
 * @author Felipe
 */
@Stateless
public class TransferenciaService implements ITransferenciaService {
    
    private static final Logger logger = LogManager.getLogger(TransferenciaService.class);

    @Override
    public void realizarTransferencia(long idCliente, CuentaDTO cuentaOrigen, String clave, CuentaDTO cuentaDestino, double valor) throws CloudBankException, Exception {

        logger.entry();
        
        if (cuentaOrigen == null || cuentaOrigen.getNumero() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_seleccione_origen")));
        }

        if (cuentaDestino == null || cuentaDestino.getNumero() == null) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_seleccione_destino")));
        }

        if (cuentaOrigen.getNumero().equals(cuentaDestino.getNumero())) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_misma_cuenta")));
        }

        if (clave == null || clave.equals("")) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_digite_clave")));
        }

        if (valor <= 0) {
            throw logger.throwing(new CloudBankException(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_valor_no_permitido")));
        }

        //Consumo del Web Service:
        EstadoDTO estadoDTO = ClienteWSCuentaSingleton.getInstance().realizarTransferencia(idCliente, cuentaOrigen.getNumero(), clave, cuentaDestino.getNumero(), valor);
        if (!estadoDTO.isExitoso()) {
            throw logger.throwing(new CloudBankException(estadoDTO.getDescripcion()));
        }
        
        logger.exit();
    }
}
