package co.edu.usbcali.cloudbank.services.impl;

import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.cloudbank.services.ITipoDocumentoService;
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
public class TipoDocumentoServiceTest {

    private static EJBContainer container;
    private static ITipoDocumentoService instance;

    public TipoDocumentoServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ITipoDocumentoService) container.getContext().lookup("java:global/classes/TipoDocumentoService");
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
     * Test of consultarTodos method, of class TipoDocumentoService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarTodos() throws Exception {
        System.out.println("consultarTodos");
        int expResult = 3;
        List<TiposDocumentos> result = instance.consultarTodos();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of consultarPorId method, of class TipoDocumentoService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testConsultarPorId() throws Exception {
        System.out.println("consultarPorId");
        Long id = 10L;
        String expResult = "CEDULA";
        TiposDocumentos result = instance.consultarPorId(id);
        assertEquals(expResult, result.getTdocNombre());
    }

    /**
     * Test of eliminar method, of class TipoDocumentoService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        TiposDocumentos tipoDocumento = new TiposDocumentos(40L);
        instance.eliminar(tipoDocumento);
    }

    /**
     * Test of modificar method, of class TipoDocumentoService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        TiposDocumentos tipoDocumento = new TiposDocumentos(40L, "Otro EditaDo");
        instance.modificar(tipoDocumento);
    }

    /**
     * Test of crear method, of class TipoDocumentoService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCrear() throws Exception {
        System.out.println("crear");
        TiposDocumentos tipoDocumento = new TiposDocumentos(40L, "Otro");
        String expResult = "OTRO";
        TiposDocumentos result = instance.crear(tipoDocumento);
        assertEquals(expResult, result.getTdocNombre());
    }
}
