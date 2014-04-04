/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.wsclient;

import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import co.edu.usbcali.e_cloudbank.utils.UtilBundle;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton para clientes del servicio web de cuentas de clientes
 *
 * @author Felipe
 */
public class ClienteWSCuentaSingleton {

    private static final Logger logger = LogManager.getLogger(ClienteWSCuentaSingleton.class);

    co.edu.usbcali.e_cloudbank.wsclient.CloudBankService_Service service;
    co.edu.usbcali.e_cloudbank.wsclient.CloudBankService port;

    private ClienteWSCuentaSingleton() {

        logger.entry();

        final String WSDL_URL = UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.WS_CLIENT, "wsdlURLCuentas");
        final String ENDPOINT_URL = UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.WS_CLIENT, "endpointURLCuentas");
        final int CONNECT_TIMEOUT = Integer.parseInt(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.WS_CLIENT, "connectTimeout"));
        final int READ_TIMEOUT = Integer.parseInt(UtilBundle.obtenerMensaje(ResourceBundles.RB_MENSAJES.WS_CLIENT, "readTimeout"));

        logger.info("Parametros de consumo del Servicio Web");
        logger.info("WSDL_URL = {} ", WSDL_URL);
        logger.info("ENDPOINT_URL = {} ", ENDPOINT_URL);
        logger.info("CONNECT_TIMEOUT = {} ", CONNECT_TIMEOUT);
        logger.info("READ_TIMEOUT = {} ", READ_TIMEOUT);

        logger.info("Inicializando Servicio Web ...");
        try {
            service = new co.edu.usbcali.e_cloudbank.wsclient.CloudBankService_Service(new URL(WSDL_URL));
        } catch (MalformedURLException ex) {
            logger.fatal("Error inicializando servicio web de cuentas", ex.fillInStackTrace());
        }
        logger.info("Abriendo port con parametros");
        port = service.getCloudBankServicePort();
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT_URL);
        bindingProvider.getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, CONNECT_TIMEOUT * 1000);
        bindingProvider.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, READ_TIMEOUT * 1000);
        logger.info("Servicio Web inicializado correctamente");

        logger.exit();
    }

    public static ClienteWSCuentaSingleton getInstance() {

        logger.entry();

        return logger.exit(ClienteWSCuentaFacadeHolder.INSTANCE);
    }

    private static class ClienteWSCuentaFacadeHolder {

        private static final ClienteWSCuentaSingleton INSTANCE = new ClienteWSCuentaSingleton();
    }

    public RespuestaConsultaCuentasDTO consultarPorCliente(long idCliente) {

        logger.entry();

        return logger.exit(port.consultarPorCliente(idCliente));
    }

    public RespuestaConsultaMovimientosDTO consultarMovimientos(long idCliente, java.lang.String numero, java.lang.String fechaInicial, java.lang.String fechaFinal) {

        logger.entry();

        return logger.exit(port.consultarMovimientos(idCliente, numero, fechaInicial, fechaFinal));
    }

    public EstadoDTO realizarTransferencia(long idCliente, java.lang.String numeroOrigen, java.lang.String claveOrigen, java.lang.String numeroDestino, double valor) {

        logger.entry();

        return logger.exit(port.realizarTransferencia(idCliente, numeroOrigen, claveOrigen, numeroDestino, valor));
    }
    
    public RespuestaConsultaUsuarioDTO consultarUsuarioPorLogin(java.lang.String login) {

        logger.entry();

        return logger.exit(port.consultarUsuarioPorLogin(login));
    }
    
    public EstadoDTO agregarUsuario(long identificacion, long tipoUsuario, java.lang.String nombre, java.lang.String login) {

        logger.entry();

        return logger.exit(port.agregarUsuario(identificacion, tipoUsuario, nombre, login, ""+identificacion));
    }
}
