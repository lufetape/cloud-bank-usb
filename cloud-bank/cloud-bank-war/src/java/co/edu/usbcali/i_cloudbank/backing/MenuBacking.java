package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * Clase que maneja el backing del menu de la aplicación
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class MenuBacking extends BaseBacking implements Serializable {

    private MenuModel menuModel;
    private static final String BASE_PREFIX = "/app";
    private static final String BASE_SUFFIX = ".xhtml";
    
    @ManagedProperty(value = "#{sesionUsuarioBacking}")
    private SesionUsuarioBacking sesionUsuarioBacking;

    @PostConstruct
    public void initialize() {
        construirMenu();
    }

    public void construirMenu() {
        
        int tipoUsuario = sesionUsuarioBacking.getUsuario().getTusuCodigo().getTusuCodigo().intValue();

        //Este menú se construirá de forma estática en este prototipo:
        setMenuModel(new DefaultMenuModel());

        //HOME:
        DefaultMenuItem home = new DefaultMenuItem("", "home");
        home.setTitle(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_home"));
        home.setUrl("/");
        getMenuModel().addElement(home);

        //Menú de administración:
        if(tipoUsuario == 20 || tipoUsuario == 40 || tipoUsuario == 50){
            DefaultSubMenu administracion = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_administrar"), "administrar");

            //Clientes:
            if(tipoUsuario == 20 || tipoUsuario == 50){
                DefaultMenuItem clientes = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_clientes"), "clientes");
                clientes.setUrl(BASE_PREFIX + "/administracion/clientes/principal" + BASE_SUFFIX);
                administracion.addElement(clientes);
            }
            
            //Usuarios:
            if(tipoUsuario == 40 || tipoUsuario == 50){
                DefaultMenuItem usuarios = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_usuarios"), "usuarios");
                usuarios.setUrl(BASE_PREFIX + "/administracion/usuarios/principal" + BASE_SUFFIX);
                administracion.addElement(usuarios);
            }

            //Submenu de Maestros:
            DefaultSubMenu maestros = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_maestros"), "maestros");

            //Tipos de documentos:
            if(tipoUsuario == 20 || tipoUsuario == 50){
                DefaultMenuItem tiposDocumentos = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_tiposDocumentos"), "tiposDocumentos");
                tiposDocumentos.setUrl(BASE_PREFIX + "/administracion/tiposDocumentos/principal" + BASE_SUFFIX);
                maestros.addElement(tiposDocumentos);
            }
            
            //Tipos de Usuarios
            if(tipoUsuario == 40 || tipoUsuario == 50){
                DefaultMenuItem tiposUsuarios = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_tiposUsuarios"), "tiposUsuarios");
                tiposUsuarios.setUrl(BASE_PREFIX + "/administracion/tiposUsuarios/principal" + BASE_SUFFIX);
                maestros.addElement(tiposUsuarios);
            }

            administracion.addElement(maestros);

            getMenuModel().addElement(administracion);
        }

        if(tipoUsuario == 10 || tipoUsuario == 50){
            //Menú de registro de movimientos:
            DefaultSubMenu movimientos = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_registrar"), "registrar");

            //Consignaciones
            DefaultMenuItem consignaciones = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_consignaciones"), "consignaciones");
            consignaciones.setUrl(BASE_PREFIX + "/transacciones/consignar" + BASE_SUFFIX);
            movimientos.addElement(consignaciones);
            //Retiros
            DefaultMenuItem retiros = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_retiros"), "retiros");
            retiros.setUrl(BASE_PREFIX + "/transacciones/retirar" + BASE_SUFFIX);
            movimientos.addElement(retiros);
            //Transferencias
            DefaultMenuItem transferencias = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_transferencias"), "transferencias");
            transferencias.setUrl(BASE_PREFIX + "/transacciones/transferir" + BASE_SUFFIX);
            movimientos.addElement(transferencias);

            getMenuModel().addElement(movimientos);
        }

        //Menú de ayuda:
        DefaultSubMenu ayuda = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_ayuda"), "ayuda");

        //About
        DefaultMenuItem about = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_about"), "about");
        about.setOnclick("aboutDialog.show()");
        ayuda.addElement(about);

        getMenuModel().addElement(ayuda);
    }

    /**
     * @return the menuModel
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }

    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    /**
     * @return the sesionUsuarioBacking
     */
    public SesionUsuarioBacking getSesionUsuarioBacking() {
        return sesionUsuarioBacking;
    }

    /**
     * @param sesionUsuarioBacking the sesionUsuarioBacking to set
     */
    public void setSesionUsuarioBacking(SesionUsuarioBacking sesionUsuarioBacking) {
        this.sesionUsuarioBacking = sesionUsuarioBacking;
    }
}
