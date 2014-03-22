package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Felipe
 */
public class CuentaServiceTest {

    private static EJBContainer container;
    private static ICuentaService instance;

    public CuentaServiceTest() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ICuentaService) container.getContext().lookup("java:global/classes/CuentaService");
    }

    @BeforeClass
    public static void setUpClass() {
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
     * Test of consultarTodos method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarTodos() throws Exception {
        System.out.println("consultarTodos");
        int expResult = 15;
        List<Cuentas> result = instance.consultarTodos();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of consultarPorId method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        String numero = "4008-5305-0015";
        Long expResult = 151234L;
        Cuentas result = instance.consultarPorNumero(numero);
        assertEquals(expResult, result.getCliId().getCliId());
    }

    /**
     * Test of consultarPorIdCliente method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorIdCliente() throws Exception {
        System.out.println("consultarPorIdCliente");
        Long idCliente = 301234L;
        int expResult = 1;
        List<Cuentas> result = instance.consultarPorCliente(idCliente);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of crear method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCrear() throws Exception {
        System.out.println("crear");
        Cuentas cuenta = new Cuentas();
        cuenta.setCliId(new Clientes(301234L));
        String expResult = "4008-5305-0082";
        Cuentas result = instance.crear(cuenta);
        assertEquals(expResult, result.getCueNumero());
    }
    
    /**
     * Test of modificar method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        Cuentas cuenta = new Cuentas();
        cuenta.setCueNumero("4008-5305-0081");
        cuenta.setCliId(new Clientes(301234L));
        cuenta.setCueSaldo(BigDecimal.valueOf(100000D));
        cuenta.setCueActiva("S");
        cuenta.setCueClave("1234");
        instance.modificar(cuenta);
    }
    
    /**
     * Test of eliminar method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        Cuentas cuenta = new Cuentas("4008-5305-0081");
        instance.eliminar(cuenta);
    }

    /**
     * Test of consultarPorNumero method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorNumero() throws Exception {
        System.out.println("consultarPorNumero");
        String numero = "4008-5305-0080";
        Long expResult = 801234L;
        Cuentas result = instance.consultarPorNumero(numero);
        assertEquals(expResult, result.getCliId().getCliId());
    }

    /**
     * Test of consultarPorCliente method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorCliente() throws Exception {
        System.out.println("consultarPorCliente");
        Long idCliente = 551234L;
        int expResult = 1;
        List<Cuentas> result = instance.consultarPorCliente(idCliente);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of consultarPorFiltros method, of class CuentaService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorFiltros() throws Exception {
        System.out.println("consultarPorFiltros");
        String numero = "4008-5305-0060";
        Long idCliente = 601234L;
        String estado = "S";
        int expResult = 1;
        List<Cuentas> result = instance.consultarPorFiltros(numero, idCliente, estado);
        assertEquals(expResult, result.size());
    }
}
