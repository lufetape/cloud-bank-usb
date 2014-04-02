package co.edu.usbcali.e_cloudbank.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que maneja utilidades relacionadas con el LOCALE
 *
 * @author Felipe
 */
public class UtilBundle {

    /**
     * Atributo que maneja el LOCALE de la aplicacion de forma estatica (Español
     * por defecto)
     */
    public static Locale locale = new Locale("ES");

    /**
     * Método que obtiene un mensaje de los archivos de recursos con el LOCALE
     * de la aplicacion
     *
     * @param resourceBundle Archivo de Recursos
     * @param key Clave del mensaje a recuperar
     * @return Valor del mensaje
     */
    public static String obtenerMensaje(Enum resourceBundle, String key) {
        //Se recupera el mensae del Bundle:
        ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle.toString(), locale);
        return bundle.getString(key);
    }

}
