package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.services.IClienteService;
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
public class ClienteServiceTest {

    private static EJBContainer container;
    private static IClienteService instance;

    public ClienteServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (IClienteService) container.getContext().lookup("java:global/classes/ClienteService");
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
     * Test of consultarTodos method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarTodos() throws Exception {
        System.out.println("consultarTodos");
        int cantidadEsperada = 15;
        List<Clientes> result = instance.consultarTodos();
        assertEquals(cantidadEsperada, result.size());
    }

    /**
     * Test of consultarPorId method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        Long id = 801234L;
        String expResult = "Logan Vertis";
        Clientes result = instance.consultarPorId(id);
        assertEquals(expResult, result.getCliNombre());
    }

    /**
     * Test of consultarPorPalabraClave method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorPalabraClave() throws Exception {
        System.out.println("consultarPorPalabraClave");
        String palabraClave = "801234";
        int cantidadEsperada = 1;
        List<Clientes> result = instance.consultarPorPalabraClave(palabraClave);
        assertEquals(cantidadEsperada, result.size());
    }

    /**
     * Test of eliminar method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        Long id = 0L;
        instance.eliminar(id);
    }

    /**
     * Test of modificar method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        Long id = 851234L;
        Long tipoDocumento = 10L;
        String nombre = "Luis Felipe Tabares Pérez";
        String direccion = "Calle 4 73 91";
        String telefono = "317-657-2183";
        String email = "lufetape@gmail.com";
        instance.modificar(id, tipoDocumento, nombre, direccion, telefono, email);
    }

    /**
     * Test of crear method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCrear() throws Exception {
        System.out.println("crear");
        Long id = 851234L;
        Long tipoDocumento = 10L;
        String nombre = "Felipe Tabares";
        String direccion = "Calle 4 73 91";
        String telefono = "123";
        String email = "lufetape@gmail.com";
        String expResult = "4008-5305-0081";
        Cuentas result = instance.crear(id, tipoDocumento, nombre, direccion, telefono, email);
        assertEquals(expResult, result.getCueNumero());
    }

    /**
     * Test of consultarPorFiltros method, of class ClienteService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorFiltros() throws Exception {
        System.out.println("consultarPorFiltros");
        Long id = null;
        Long idTipoDocumento = null;
        String nombre = "ogan";
        int cantidadEsperada = 2;
        List<Clientes> result = instance.consultarPorFiltros(id, idTipoDocumento, nombre);
        assertEquals(cantidadEsperada, result.size());
    }
}
