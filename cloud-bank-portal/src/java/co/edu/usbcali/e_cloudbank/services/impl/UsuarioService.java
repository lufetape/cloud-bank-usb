/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.services.impl;

import co.edu.usbcali.e_cloudbank.services.IUsuarioService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.wsclient.ClienteWSCuentaSingleton;
import co.edu.usbcali.e_cloudbank.wsclient.EstadoDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaUsuarioDTO;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementacion de IUsuarioService a traves de servicios web
 *
 * @author Felipe
 */
@Stateless
public class UsuarioService implements IUsuarioService {

    private static final Logger logger = LogManager.getLogger(UsuarioService.class);

    @Override
    public RespuestaConsultaUsuarioDTO consultarUsuarioPorId(String login) throws CloudBankException, Exception {

        logger.entry();

        //Consumo del Web Service:
        logger.info("Consultando usuario por login via servicio web");
        RespuestaConsultaUsuarioDTO respuestaConsultaUsuarioDTO = ClienteWSCuentaSingleton.getInstance().consultarUsuarioPorLogin(login);
        if (respuestaConsultaUsuarioDTO.getEstado().isExitoso()) {
            return logger.exit(respuestaConsultaUsuarioDTO);
        } else {
            throw logger.throwing(new CloudBankException(respuestaConsultaUsuarioDTO.getEstado().getDescripcion()));
        }
    }

    @Override
    public EstadoDTO agregarUsuario(long identificacion, long tipoUsuario, String nombre, String login) throws CloudBankException, Exception {

        logger.entry();

        //Consumo del Web Service:
        logger.info("Agregando usuario via servicio web");
        EstadoDTO estadoDTO = ClienteWSCuentaSingleton.getInstance().agregarUsuario(identificacion, tipoUsuario, nombre, login);
        if (estadoDTO.isExitoso()) {
            return logger.exit(estadoDTO);
        } else {
            throw logger.throwing(new CloudBankException(estadoDTO.getDescripcion()));
        }
    }
}
