/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.cloudbank.services.ITipoUsuarioService;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Felipe
 */
public class TipoUsuarioServiceTest {
    
    private static EJBContainer container;
    private static ITipoUsuarioService instance;
    
    public TipoUsuarioServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ITipoUsuarioService) container.getContext().lookup("java:global/classes/TipoUsuarioService");
    }
    
    @AfterClass
    public static void tearDownClass() {
        container.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of consultarTodos method, of class TipoUsuarioService.
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarTodos() throws Exception {
        System.out.println("consultarTodos");
        int expResult = 3;
        List<TiposUsuarios> result = instance.consultarTodos();
        assertEquals(expResult, result);
    }

    /**
     * Test of consultarPorId method, of class TipoUsuarioService.
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        Long id = 10L;
        TiposUsuarios expResult = new TiposUsuarios(10L, "CAJERO");
        TiposUsuarios result = instance.consultarPorId(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminar method, of class TipoUsuarioService.
     * @throws java.lang.Exception
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        TiposUsuarios tipoUsuario = new TiposUsuarios(40L);
        instance.eliminar(tipoUsuario);
    }

    /**
     * Test of modificar method, of class TipoUsuarioService.
     * @throws java.lang.Exception
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        TiposUsuarios tipoUsuario = new TiposUsuarios(40L, "OTro 2");
        instance.modificar(tipoUsuario);
    }

    /**
     * Test of crear method, of class TipoUsuarioService.
     * @throws java.lang.Exception
     */
    @Test
    public void testCrear() throws Exception {
        System.out.println("crear");
        TiposUsuarios tipoUsuario = new TiposUsuarios(40L, "OtrO");
        TiposUsuarios expResult = new TiposUsuarios(40L, "OTRO");
        TiposUsuarios result = instance.crear(tipoUsuario);
        assertEquals(expResult, result);
    }
    
}
