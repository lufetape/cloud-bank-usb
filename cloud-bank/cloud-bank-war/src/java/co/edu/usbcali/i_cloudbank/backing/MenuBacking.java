package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.i_cloudbank.dto.IdiomaDTO;
import co.edu.usbcali.i_cloudbank.dto.TemaDTO;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * Clase que maneja el backing del menu de la aplicación
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class MenuBacking extends BaseBacking implements Serializable {
    
    private MenuModel menuModel;    
    private List<TemaDTO> temas;
    private List<IdiomaDTO> idiomas;
    private TemaDTO selectedTema;
    private IdiomaDTO selectedIdioma;
    private static final String BASE_PREFIX = "/app";
    private static final String BASE_SUFFIX = ".xhtml";

    @PostConstruct
    public void initialize(){        
        cargarTemas();
        setSelectedTema(temas.get(temas.indexOf(new TemaDTO("flick")))); //OJO: De sesion
        cargarIdiomas();
        setSelectedIdioma(idiomas.get(idiomas.indexOf(new IdiomaDTO("es")))); //OJO: De sesion
        construirMenu();
    }
    
    public void construirMenu() {
        
        //Este menú se construirá de forma estática en este prototipo:
        
        setMenuModel(new DefaultMenuModel());
        
        //Menú de administración:
        DefaultSubMenu admnistracion = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_administrar"), "administrar");                
        
            //Clientes:
            DefaultMenuItem clientes = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_clientes"), "clientes");
            clientes.setUrl(BASE_PREFIX+"/administracion/clientes/clientes"+BASE_SUFFIX);
            admnistracion.addElement(clientes);
            //Usuarios:
            DefaultMenuItem usuarios = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_usuarios"), "usuarios");
            usuarios.setUrl(BASE_PREFIX+"/administracion/usuarios/usuarios"+BASE_SUFFIX);
            admnistracion.addElement(usuarios);
            
            //Submenu de Maestros:
            DefaultSubMenu maestros = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_maestros"), "maestros");
            
                //Tipos de documentos:
                DefaultMenuItem tiposDocumentos = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_tiposDocumentos"), "tiposDocumentos");
                tiposDocumentos.setUrl(BASE_PREFIX+"/administracion/clientes/tiposDocumentos"+BASE_SUFFIX);
                maestros.addElement(tiposDocumentos);
                //Tipos de Usuarios
                DefaultMenuItem tiposUsuarios = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_tiposUsuarios"), "tiposUsuarios");
                tiposUsuarios.setUrl(BASE_PREFIX+"/administracion/usuarios/tiposUsuarios"+BASE_SUFFIX);
                maestros.addElement(tiposUsuarios);
                
            admnistracion.addElement(maestros);
        
        getMenuModel().addElement(admnistracion);
        
        //Menú de registro de movimientos:
        DefaultSubMenu movimientos = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_registrar"), "registrar");
        
            //Consignaciones
            DefaultMenuItem consignaciones = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_consignaciones"), "consignaciones");
            consignaciones.setUrl(BASE_PREFIX+"/transacciones/consignaciones/consignaciones"+BASE_SUFFIX);
            movimientos.addElement(consignaciones);
            //Retiros
            DefaultMenuItem retiros = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_retiros"), "retiros");
            retiros.setUrl(BASE_PREFIX+"/transacciones/retiros/tiposUsuarios.setUrl(BASE_PREFIX+\"/transacciones/consignaciones/consignaciones\"+BASE_SUFFIX);"+BASE_SUFFIX);
            movimientos.addElement(retiros);
            //Transferencias
            DefaultMenuItem transferencias = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_transferencias"), "transferencias");
            transferencias.setUrl(BASE_PREFIX+"/transacciones/transferencias/transferencias"+BASE_SUFFIX);
            movimientos.addElement(transferencias);
            
        getMenuModel().addElement(movimientos);
        
        //Menú de consultas:
        DefaultSubMenu consultas = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_consultar"), "consultar");        
            
            //Consulta de movimientos
            DefaultMenuItem consultaMovimientos = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_movimientos"), "movimimientos");
            consultaMovimientos.setUrl(BASE_PREFIX+"/consultas/movimientos/movimientos"+BASE_SUFFIX);
            consultas.addElement(consultaMovimientos);
            
        getMenuModel().addElement(consultas);

        //Menú de ayuda:
        DefaultSubMenu ayuda = new DefaultSubMenu(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_ayuda"), "ayuda");
        
            //Soporte
            DefaultMenuItem support = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_support"), "support");
            support.setOnclick("alert('SUPPORT')");
            ayuda.addElement(support);
            //Separador
            ayuda.addElement(new DefaultSeparator());
            //About
            DefaultMenuItem about = new DefaultMenuItem(obtenerMensaje(ResourceBundles.RB_MENSAJES.MENU, "label_about"), "about");
            about.setOnclick("alert('ABOUT CLOUS BANK')");
            ayuda.addElement(about);
            
        getMenuModel().addElement(ayuda);
    } 
    
    public void cargarTemas(){
        //Se cargarán de forma estática (puede hacerse desde una BD)
        setTemas(new ArrayList<TemaDTO>());
        getTemas().add(new TemaDTO("afterdark", "afterdark", "afterdark.png"));
        getTemas().add(new TemaDTO("afternoon", "afternoon", "afternoon.png"));
        getTemas().add(new TemaDTO("afterwork", "afterwork", "afterwork.png"));
        getTemas().add(new TemaDTO("aristo", "aristo", "aristo.png"));
        getTemas().add(new TemaDTO("black-tie", "black-tie", "black-tie.png"));
        getTemas().add(new TemaDTO("blitzer", "blitzer", "blitzer.png"));
        getTemas().add(new TemaDTO("bluesky", "bluesky", "bluesky.png"));
        getTemas().add(new TemaDTO("bootstrap", "bootstrap", "bootstrap.png"));
        getTemas().add(new TemaDTO("casablanca", "casablanca", "casablanca.png"));
        getTemas().add(new TemaDTO("cruze", "cruze", "cruze.png"));
        getTemas().add(new TemaDTO("cupertino", "cupertino", "cupertino.png"));
        getTemas().add(new TemaDTO("dark-hive", "dark-hive", "dark-hive.png"));
        getTemas().add(new TemaDTO("dot-luv", "dot-luv", "dot-luv.png"));
        getTemas().add(new TemaDTO("eggplant", "eggplant", "eggplant.png"));
        getTemas().add(new TemaDTO("excite-bike", "excite-bike", "excite-bike.png"));
        getTemas().add(new TemaDTO("flick", "flick", "flick.png"));
        getTemas().add(new TemaDTO("glass-x", "glass-x", "glass-x.png"));
        getTemas().add(new TemaDTO("home", "home", "home.png"));
        getTemas().add(new TemaDTO("hot-sneaks", "hot-sneaks", "hot-sneaks.png"));
        getTemas().add(new TemaDTO("humanity", "humanity", "humanity.png"));
        getTemas().add(new TemaDTO("le-frog", "le-frog", "le-frog.png"));
        getTemas().add(new TemaDTO("midnight", "midnight", "midnight.png"));
        getTemas().add(new TemaDTO("mint-choc", "mint-choc", "mint-choc.png"));
        getTemas().add(new TemaDTO("overcast", "overcast", "overcast.png"));
        getTemas().add(new TemaDTO("pepper-grinder", "pepper-grinder", "pepper-grinder.png"));
        getTemas().add(new TemaDTO("redmond", "redmond", "redmond.png"));
        getTemas().add(new TemaDTO("rocket", "rocket", "rocket.png"));
        getTemas().add(new TemaDTO("sam", "sam", "sam.png"));
        getTemas().add(new TemaDTO("smoothness", "smoothness", "smoothness.png"));
        getTemas().add(new TemaDTO("south-street", "south-street", "south-street.png"));
        getTemas().add(new TemaDTO("start", "start", "start.png"));
        getTemas().add(new TemaDTO("sunny", "sunny", "sunny.png"));
        getTemas().add(new TemaDTO("swanky-purse", "swanky-purse", "swanky-purse.png"));
        getTemas().add(new TemaDTO("trontastic", "trontastic", "trontastic.png"));
        getTemas().add(new TemaDTO("ui-darkness", "ui-darkness", "ui-darkness.png"));
        getTemas().add(new TemaDTO("ui-lightness", "ui-lightness", "ui-lightness.png"));
        getTemas().add(new TemaDTO("vader", "vader", "vader.png"));
    }
    
    public void cargarIdiomas(){
        //Se cargarán de forma estática (puede hacerse desde una BD)
        setIdiomas(new ArrayList<IdiomaDTO>());
        getIdiomas().add(new IdiomaDTO("es", new Locale("es"), "label_idioma_spanish", "spanish"));
        getIdiomas().add(new IdiomaDTO("en", new Locale("en"), "label_idioma_english", "english"));
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
     * @return the selectedTema
     */
    public TemaDTO getSelectedTema() {
        return selectedTema;
    }

    /**
     * @param selectedTema the selectedTema to set
     */
    public void setSelectedTema(TemaDTO selectedTema) {
        this.selectedTema = selectedTema;
    }

    /**
     * @return the selectedIdioma
     */
    public IdiomaDTO getSelectedIdioma() {
        return selectedIdioma;
    }

    /**
     * @param selectedIdioma the selectedIdioma to set
     */
    public void setSelectedIdioma(IdiomaDTO selectedIdioma) {
        this.selectedIdioma = selectedIdioma;
    }

    /**
     * @return the temas
     */
    public List<TemaDTO> getTemas() {
        return temas;
    }

    /**
     * @param temas the temas to set
     */
    public void setTemas(List<TemaDTO> temas) {
        this.temas = temas;
    }

    /**
     * @return the idiomas
     */
    public List<IdiomaDTO> getIdiomas() {
        return idiomas;
    }

    /**
     * @param idiomas the idiomas to set
     */
    public void setIdiomas(List<IdiomaDTO> idiomas) {
        this.idiomas = idiomas;
    }
}