/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.soap;

import co.edu.usbcali.cloudbank.dto.EstadoDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaCuentasDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaMovimientosDTO;
import co.edu.usbcali.cloudbank.dto.RespuestaConsultaUsuarioDTO;
import co.edu.usbcali.cloudbank.integrator.ICuentaClienteIntegrator;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Servicio Web para operaciones básicas de un cliente
 *
 * @author Felipe
 */
@WebService(serviceName = "CloudBankService")
public class CloudBankService {

    @EJB
    private ICuentaClienteIntegrator cuentaClienteIntegrator;

    @WebMethod(operationName = "consultarPorCliente")
    public RespuestaConsultaCuentasDTO consultarPorCliente(@WebParam(name = "idCliente") long idCliente) {
        return cuentaClienteIntegrator.consultarPorCliente(idCliente);
    }

    @WebMethod(operationName = "consultarMovimientos")
    public RespuestaConsultaMovimientosDTO consultarMovimientos(@WebParam(name = "idCliente") long idCliente, @WebParam(name = "numero") String numero, @WebParam(name = "fechaInicial") String fechaInicial, @WebParam(name = "fechaFinal") String fechaFinal) {
        return cuentaClienteIntegrator.consultarMovimientos(idCliente, numero, fechaInicial, fechaFinal);
    }

    @WebMethod(operationName = "realizarTransferencia")
    public EstadoDTO realizarTransferencia(@WebParam(name = "idCliente") long idCliente, @WebParam(name = "numeroOrigen") String numeroOrigen, @WebParam(name = "claveOrigen") String claveOrigen, @WebParam(name = "numeroDestino") String numeroDestino, @WebParam(name = "valor") double valor) {
        return cuentaClienteIntegrator.realizarTransferencia(idCliente, numeroOrigen, claveOrigen, numeroDestino, valor);
    }

    @WebMethod(operationName = "consultarUsuarioPorLogin")
    public RespuestaConsultaUsuarioDTO consultarUsuarioPorLogin(@WebParam(name = "login") String login) {
        return cuentaClienteIntegrator.consultarUsuarioPorLogin(login);
    }

    @WebMethod(operationName = "agregarUsuario")
    public EstadoDTO agregarUsuario(@WebParam(name = "identificacion") long identificacion, @WebParam(name = "tipoUsuario") long tipoUsuario, @WebParam(name = "nombre") String nombre, @WebParam(name = "login") String login, @WebParam(name = "clave") String clave) {
        return cuentaClienteIntegrator.agregarUsuario(identificacion, tipoUsuario, nombre, login, clave);
    }
}
